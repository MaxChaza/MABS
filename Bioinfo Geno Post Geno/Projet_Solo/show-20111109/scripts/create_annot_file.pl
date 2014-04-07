#!/usr/local/bin/perl -w

use strict;

my $prefix = "/usr/local";
my $exec_prefix = "${prefix}";
my $bindir = "${exec_prefix}/bin";
my $libexecdir = "${exec_prefix}/libexec";
my $datadir = "${prefix}/share";

my $SeqFile;
my $SHOWmodel;
my $Eoutput;
my $SHOWdir;
my $p_cds_threshold;
my $p_start_threshold;
my $p_rbs_threshold;
my $annotformat = undef;

if (@ARGV <6) {
    die "Usage: createAnnotFile.pl <SHOWDIR> <SeqFile> <SHOWmodel> <p_cds_threshold> <p_start_threshold> <p_rbs_threshold> <GFF|GBK>\n
**********************************************************\n";
} else {
    $SHOWdir = shift @ARGV;
    $SeqFile = shift @ARGV; 
    $SHOWmodel = shift @ARGV;
    $p_cds_threshold = shift @ARGV;
    $p_start_threshold = shift @ARGV;
    $p_rbs_threshold = shift @ARGV;
    $annotformat = shift @ARGV;
}

if ($p_cds_threshold < 0.01) {
    print "Warning: p_cds_threshold lower than 0.01, set to 0.1\n";
    $p_cds_threshold = 0.1
}
if ($p_start_threshold < 0.01) {
    print "Warning: p_start_threshold lower than 0.01, set to 0.1\n";
    $p_start_threshold = 0.1
}
if (! defined $annotformat || $annotformat ne 'GFF') {
    $annotformat = 'GBK';
}


open(IN_SEQ, "<$SeqFile") || die "could not open to read $SeqFile";

my $seqfile = <IN_SEQ>;
while ($seqfile) {
    if ( $seqfile !~ /^$/ ) {
	
	chomp($seqfile);
	print $seqfile,"\n";
	
	open(OUT, ">seq.txt");
	print OUT "seq_identifier: genomic_dna\n";
	print OUT "seq_type: dna\n";
	print OUT "seq_files:\n"; 
	print OUT $seqfile,"\n";
	close(OUT); 
	
	
	my $e_columns = {};
	
	&create_Eoutput_file($SHOWmodel, $e_columns);
	
	my $command = "$SHOWdir/show_emfit -model ".$SHOWmodel." -em em_final.desc -seq seq.txt -output outputfromparse.desc 1> show_emfit.stdout 2>show_emfit.stderr";
	
	print $command, "\n";

	system($command);

	my $e_output = $seqfile;
	$e_output =~ s/\.[^\.]+$//;
	if ($e_output =~ /([^\/]+)$/) {
	    $e_output = $1;
	}
	$e_output .= ".e";
	
	my $segments = &retrieve_and_segment_seq($seqfile);
	&parse_output($p_cds_threshold, $p_start_threshold, 
		      $p_rbs_threshold, $e_columns, 
		      $e_output, $segments, $annotformat);
	
    }
    $seqfile = <IN_SEQ>;
}

close(IN_SEQ);


exit 0;


#####################################"

sub retrieve_and_segment_seq {
    my $seqfile = shift @_;
    
    my @sequences = ();

    
    my $seqline;

    open(IN_SEQF, "<$seqfile") || die "couldn't open $seqfile in read mode\n";

    $seqline = <IN_SEQF>;
    while ($seqline && $seqline !~ /^\>/) {
	$seqline = <IN_SEQF>;
    }

    while ($seqline && $seqline =~ /^\>/) {
	my %seq;
	$seq{'comment'} = $seqline;
	$seq{'seq'} = "";
	$seqline = <IN_SEQF>;	
	while ($seqline && $seqline !~ /^\>/) {
	    $seq{'seq'} .= $seqline;
	    $seqline = <IN_SEQF>;
	}
	$seq{'seq'} =~ s/\s+//g;
	$seq{'seq'} =~ tr/A-Z/a-z/;
	$seq{'lgseq'} = length($seq{'seq'});
	push @sequences, \%seq;
    }
    close(IN_SEQF);

    foreach (my $iseq = 0; $iseq < @sequences ; $iseq++) {
	my $r_seq = $sequences[$iseq];
	print $r_seq->{'comment'}, "\n";
	$r_seq->{'segments_stop'} = &segment_stop(\$r_seq->{'seq'}, $iseq); 	
    }

    return \@sequences;

}

sub segment_stop {
    my $r_seq = shift @_;
    my $iseq = shift @_;

 
    my %segments;

    my %stops_d = ('tga' => 1, 'tag' => 2, 'taa' => 3); 
    my %stops_r = ('tca' => 1, 'cta' => 2, 'tta' => 3); 

    for (my $i = 0 ; $i < 3 ; $i++) {
	my $frame = $i%3 + 1;
	
	print "FRAME $frame\n";
	$segments{$frame} = segment_stop_frame($r_seq, \%stops_d, $i, $iseq, $frame);
	
	$frame *= -1;
	print "FRAME $frame\n";
	$segments{$frame} = segment_stop_frame($r_seq, \%stops_r, $i, $iseq, $frame);
    }
    
    return \%segments;
}

sub segment_stop_frame {
    my $r_seq = shift @_;
    my $stops = shift @_;
    my $start_i = shift @_;
    my $iseq = shift @_;
    my $frame = shift @_;


    my $filename = "seq".$iseq."_orf_frame".$frame.".tmp";
    print $filename, "\n";
    open(OUTSEG, ">$filename") || die "could not open $filename in write mode\n";

    my $begin_segment = 'u';

    my $lg_seq = length($$r_seq);

    print $lg_seq, "\n";
    my $i = $start_i;
    my $segment;

    while ($i <= $lg_seq) {
	if ($i+2 >= $lg_seq) {
	    if ($begin_segment eq 'false') {
		$segment->{'stop'} = $i-1;
		$segment->{'stop_type'} = 'u';		
		    print OUTSEG $segment->{'start'}."_".$segment->{'start_type'}."..".$segment->{'stop_type'}."_".$segment->{'stop'}."\n";
	    }
	    $begin_segment = 'u';
	} else {
	    my $codon = substr($$r_seq, $i, 3);
	    if ($codon =~ /[^agct]/) {
		if ($begin_segment eq 'false') {
		    $segment->{'stop'} = $i-1;
		    $segment->{'stop_type'} = 'u';		
		    print OUTSEG $segment->{'start'}."_".$segment->{'start_type'}."..".$segment->{'stop_type'}."_".$segment->{'stop'}."\n";
		}
		$begin_segment = 'u';		
	    } elsif (defined $stops->{$codon}) {
		if ($begin_segment eq 'false') {
		    $segment->{'stop'} = $i-1;
		    $segment->{'stop_type'} = 'e';		
		    print OUTSEG $segment->{'start'}."_".$segment->{'start_type'}."..".$segment->{'stop_type'}."_".$segment->{'stop'}."\n";
		    $begin_segment = 'e';	    
		}
	    } elsif ($begin_segment ne 'false') {
		$segment = {};
		$segment->{'start'} = $i;
		$segment->{'start_type'} = $begin_segment;
		$begin_segment = 'false';
	    } 
	}
	$i += 3;    
    }
    
    close(OUTSEG) || die "could not close $filename open in write mode\n";
    return $filename;

}

sub parse_output {
    my $p_cds_threshold = shift @_;
    my $p_start_threshold = shift @_;    
    my $p_rbs_threshold = shift @_;    
    my $e_columns = shift @_;
    my $e_output = shift  @_;
    my $segments = shift @_;
    my $annotformat = shift @_;

    my @seqs_feat;
    my @frames = (1, 2, 3, -1, -2, -3);

    open(IN_E, "<$e_output") || die "could not open $e_output in read mode";

   
    my $e_line = <IN_E>;

    my $iseq = 0;
    while( $e_line && ($e_line =~ /^\#/) ) {
	$e_line = <IN_E>;
	$e_line = <IN_E>;

	my $features = [];
	my $orfs = ();
	my %seg;
	
	
	foreach my $frame (@frames) {
	    $seg{$frame} = {};
	}

	# open files where are written the segments containing potentials CDS
	open(IN_F1, "<$segments->[$iseq]->{'segments_stop'}->{'1'}");
	print $segments->[$iseq]->{'segments_stop'}->{'1'}, "\n";
	$seg{'1'}->{'handle'} = \*IN_F1;
	open(IN_F2, "<$segments->[$iseq]->{'segments_stop'}->{'2'}");
	$seg{'2'}->{'handle'} = \*IN_F2;
	open(IN_F3, "<$segments->[$iseq]->{'segments_stop'}->{'3'}");
	$seg{'3'}->{'handle'} = \*IN_F3;
	open(IN_R1, "<$segments->[$iseq]->{'segments_stop'}->{'-1'}");
	$seg{'-1'}->{'handle'} = \*IN_R1;
	open(IN_R2, "<$segments->[$iseq]->{'segments_stop'}->{'-2'}");
	$seg{'-2'}->{'handle'} = \*IN_R2;
	open(IN_R3, "<$segments->[$iseq]->{'segments_stop'}->{'-3'}");
	$seg{'-3'}->{'handle'} = \*IN_R3;
 
	foreach my $frame (@frames) {
	    $seg{$frame}->{'iseg'} = 0;
	    my $framehandle = $seg{$frame}->{'handle'};
	    $seg{$frame}->{'seg'} = {};
	    if ( $seg{$frame}->{'segline'} = <$framehandle> ) {
		$seg{$frame}->{'segline'} =~ /^([0-9]+)\_([eu])\.\.([eu])\_([0-9]+)/;
		$seg{$frame}->{'seg'}->{'begseg'} = $1;
		$seg{$frame}->{'seg'}->{'begseg_type'} = $2;
		$seg{$frame}->{'seg'}->{'endseg_type'} = $3;
		$seg{$frame}->{'seg'}->{'endseg'} = $4;
		$seg{$frame}->{'seg'}->{'p_cds'} = 0;
		$seg{$frame}->{'seg'}->{'starts'} = ();
		if ($frame < 0) {
		    $seg{$frame}->{'seg'}->{'strand'} = -1;
		} else {
		    $seg{$frame}->{'seg'}->{'strand'} = 1;
		}
	    } else {
		undef $seg{$frame};
	    }
	}
	
	my $ipos = 0;
	while($e_line && $e_line !~ /^$/ &&  $e_line !~ /^\#/ ) {	
	    my @e_line = split(/\s+/, $e_line);
	    
	    # change current segment if necessary

	    foreach my $frame (@frames) {
		if ( defined $seg{$frame} &&
		     $seg{$frame}->{'seg'}->{'endseg'} < $ipos ) {

		    if ($seg{$frame}->{'seg'}->{'endseg'} == 12853) {
			print "TITI $ipos\n";
		    }

		    if ($seg{$frame}->{'seg'}->{'p_cds'} > $p_cds_threshold) {
			push @$orfs, $seg{$frame}->{'seg'};
		    }

		    $seg{$frame}->{'iseg'}++;
		    $seg{$frame}->{'seg'} = {};
		    my $framehandle = $seg{$frame}->{'handle'};
		    if ( $seg{$frame}->{'segline'} = <$framehandle>) {
			$seg{$frame}->{'segline'} =~ /^([0-9]+)\_([eu])\.\.([eu])\_([0-9]+)/;
			$seg{$frame}->{'seg'}->{'begseg'} = $1;
			$seg{$frame}->{'seg'}->{'begseg_type'} = $2;
			$seg{$frame}->{'seg'}->{'endseg_type'} = $3;
			$seg{$frame}->{'seg'}->{'endseg'} = $4;
			$seg{$frame}->{'seg'}->{'p_cds'} = 0;
			$seg{$frame}->{'seg'}->{'starts'} = ();
			if ($frame < 0) {
			    $seg{$frame}->{'seg'}->{'strand'} = -1;
			} else {
			    $seg{$frame}->{'seg'}->{'strand'} = 1;
			}
		    } else {
			undef $seg{$frame};
		    }

		}
	    }

	    # process STRAND+ segments

	    my $begseg_pos = 1;
	    my $begseg_frame = ($ipos - $begseg_pos + 1)%3 + 1;
	    if (defined $seg{$begseg_frame} && 
		$seg{$begseg_frame}->{'seg'}->{'begseg'} == $ipos) {
		my $start = {};
		$start->{'cdsstart_pos'} = $ipos + 1;
		$start->{'cdsstart_postype'} = "LESSER";
		$start->{'cdsstart_proba'} = $e_line[$e_columns->{"CDS+_$begseg_pos"}];
		push @{$seg{$begseg_frame}->{'seg'}->{'starts'}}, $start;
		#print "TOTO+ $ipos\n";
	    }

	    my $start_pos = 3;
	    my $start_frame = ($ipos - $start_pos + 1)%3 + 1;
	    my $eproba = $e_line[$e_columns->{"START+"}];
	    if ($eproba > 0 && defined $seg{$start_frame}) {
		my $start = {};
		$start->{'cdsstart_pos'} = $ipos - 2 + 1;
		$start->{'cdsstart_postype'} = "EXACT";
		$start->{'cdsstart_proba'} = $eproba;
		push @{$seg{$start_frame}->{'seg'}->{'starts'}}, $start;		
	    }
	    
	    for( my $cds_pos = 1 ; $cds_pos <= 3 ; $cds_pos++ ) {
		my $frame = ($ipos - $cds_pos + 1)%3 + 1;
		my $eproba = $e_line[$e_columns->{"CDS+_$cds_pos"}];
		if ( defined $seg{$frame} &&
		     $eproba > $seg{$frame}->{'seg'}->{'p_cds'} && 
		     $seg{$frame}->{'seg'}->{'begseg'} <= $ipos ) {
		    $seg{$frame}->{'seg'}->{'p_cds'} = $eproba;
		}
	    }

	    # process STRAND- segments

	    my $endseg_pos = 1;
	    my $endseg_frame = -1 * (($ipos + $endseg_pos)%3 + 1);

	    if (defined $seg{$endseg_frame} && 
		$seg{$endseg_frame}->{'seg'}->{'endseg'} == $ipos) {

		my $start = {};
		$start->{'cdsstart_pos'} = $ipos + 1;
		$start->{'cdsstart_postype'} = "GREATER";
		$start->{'cdsstart_proba'} = $e_line[$e_columns->{"CDS-_$endseg_pos"}];
		push @{$seg{$endseg_frame}->{'seg'}->{'starts'}}, $start;
		#print "TOTO- $ipos\n";
	    }

	    $start_pos = 3;
	    $start_frame = -1 * (($ipos + $start_pos)%3 + 1);
	    $eproba = $e_line[$e_columns->{"START-"}];
	    if ($eproba > 0 && defined $seg{$start_frame}) {
		my $start = {};
		$start->{'cdsstart_pos'} = $ipos + 2 + 1;
		$start->{'cdsstart_postype'} = "EXACT";
		$start->{'cdsstart_proba'} = $eproba;
		push @{$seg{$start_frame}->{'seg'}->{'starts'}}, $start;		
	    }

	    for( my $cds_pos = 1 ; $cds_pos <= 3 ; $cds_pos++ ) {
		my $frame = -1 * (($ipos + $cds_pos)%3 + 1);
		my $eproba = $e_line[$e_columns->{"CDS-_$cds_pos"}];
		if (defined $seg{$frame} &&
		    $eproba > $seg{$frame}->{'seg'}->{'p_cds'} &&
		    $seg{$frame}->{'seg'}->{'begseg'} <= $ipos) {
		    $seg{$frame}->{'seg'}->{'p_cds'} = $eproba;
		}
	    }

	    if (defined $e_columns->{"RBS+"} 
		&& $e_line[$e_columns->{"RBS+"}] > $p_rbs_threshold) {
		&insert_RBS_direct($features, $ipos, $e_line[$e_columns->{"RBS+"}], $segments->[$iseq]->{'lgseq'});
	    }
	    if (defined $e_columns->{"RBS-"} 
		&& $e_line[$e_columns->{"RBS-"}] > $p_rbs_threshold) {
		&insert_RBS_opposite($features, $ipos, $e_line[$e_columns->{"RBS-"}], $segments->[$iseq]->{'lgseq'});
	    }

	    $e_line = <IN_E>;
	    $ipos++;
	}
	for my $frame (@frames) {
	    if (defined $seg{$frame} && 
		$seg{$frame}->{'seg'}->{'p_cds'} > $p_cds_threshold) {
		push @$orfs, $seg{$frame}->{'seg'};
	    }
	}

	close(IN_F1);
	close(IN_R1);
	close(IN_F2);
	close(IN_R2);
	close(IN_F3);
	close(IN_R3);

	&add_cds_to_features($p_start_threshold, $features, $orfs);
	my @sorted_features = sort by_startpos @$features;
	push @seqs_feat, \@sorted_features; 
	$iseq++;
    }

    close(IN_E);


    my $annot_file = $e_output;
    $annot_file =~ s/\.[^\.]+$//;
    $annot_file .= ".annot";

    &print_feat($annot_file, \@seqs_feat, $segments, $annotformat);
}
 
sub insert_RBS_direct {
    my $features = shift @_;
    my $ipos = shift @_;
    my $e_proba = shift @_;
    my $lg_seq = shift @_;

    my $rbs_feat = {};
    $rbs_feat->{'type_feat'} = 'RBS';
    $rbs_feat->{'strand'} = '+1';
    $rbs_feat->{'stoppos'} = $ipos+1;
    $rbs_feat->{'stoppos_type'} = 'EXACT';
    if ($ipos +1 - 13 >= 1) {
	$rbs_feat->{'startpos'} = $ipos+1-13;
	$rbs_feat->{'startpos_type'} = 'EXACT';
    } else {
	$rbs_feat->{'startpos'} = 1;
	$rbs_feat->{'startpos_type'} = 'LESSER';
    }
    $rbs_feat->{'qualifiers'} = {};
    $rbs_feat->{'qualifiers'}->{'rbs_proba'} = $e_proba;
    
    push @$features, $rbs_feat;
    
}

sub insert_RBS_opposite {
    my $features = shift @_;
    my $ipos = shift @_;
    my $e_proba = shift @_;
    my $lg_seq = shift @_;

    my $rbs_feat = {};
    $rbs_feat->{'type_feat'} = 'RBS';
    $rbs_feat->{'strand'} = '-1';
    $rbs_feat->{'startpos'} = $ipos+1;
    $rbs_feat->{'startpos_type'} = 'EXACT';
    if ($ipos +1 + 13 <= $lg_seq) {
	$rbs_feat->{'stoppos'} = $ipos+1+13;
	$rbs_feat->{'stoppos_type'} = 'EXACT';
    } else {
	$rbs_feat->{'stoppos'} = $lg_seq;
	$rbs_feat->{'stoppos_type'} = 'LESSER';
    }
    $rbs_feat->{'qualifiers'} = {};
    $rbs_feat->{'qualifiers'}->{'rbs_proba'} = $e_proba;
    
    push @$features, $rbs_feat;
    
}


sub add_cds_to_features {
    my $p_start_threshold = shift @_;
    my $features = shift @_;
    my $orfs = shift @_;

    foreach my $orf (@$orfs) {

	my $cds_feat = {};
	$cds_feat->{'qualifiers'} = {};
	$cds_feat->{'strand'} = $orf->{'strand'};
	$cds_feat->{'type_feat'} = 'CDS';

	#print '######', "\n";
	foreach my $key (keys %$orf) {
	    #print "KEY ", $key, " ", $orf->{$key}, "\n";
	}
       
	my @sorted_cdsstart = sort by_cdsstart_proba @{$orf->{'starts'}};
	
	if ($orf->{'strand'} == 1) {
	    if ($orf->{'endseg_type'} eq 'e') {
		$cds_feat->{'stoppos_type'} = 'EXACT';
		$cds_feat->{'stoppos'} = $orf->{'endseg'} +3 +1;
		$cds_feat->{'qualifiers'}->{'cds_proba'} = $orf->{'p_cds'};
	    } else {
		$cds_feat->{'stoppos_type'} = 'GREATER';
		$cds_feat->{'stoppos'} = $orf->{'endseg'} + 1;		    
		$cds_feat->{'qualifiers'}->{'cds_proba'} = $orf->{'p_cds'};		    
	    }	    
	    $cds_feat->{'startpos_type'} = $sorted_cdsstart[0]->{'cdsstart_postype'};
	    $cds_feat->{'startpos'} = $sorted_cdsstart[0]->{'cdsstart_pos'};
	    $cds_feat->{'qualifiers'}->{'cdsstart_proba'} = $sorted_cdsstart[0]->{'cdsstart_proba'};
	    $cds_feat->{'qualifiers'}->{'cdsstart_pos'} = $sorted_cdsstart[0]->{'cdsstart_pos'};	    
	} else {
	    if ($orf->{'begseg_type'} eq 'e') {
		$cds_feat->{'startpos_type'} = 'EXACT';
		$cds_feat->{'startpos'} = $orf->{'begseg'} -3 +1;
		$cds_feat->{'qualifiers'}->{'cds_proba'} = $orf->{'p_cds'};
	    } else {
		$cds_feat->{'startpos_type'} = 'LESSER';
		$cds_feat->{'startpos'} = $orf->{'begseg'} + 1;		    
		$cds_feat->{'qualifiers'}->{'cds_proba'} = $orf->{'p_cds'};		    
	    }	    
	    $cds_feat->{'stoppos_type'} = $sorted_cdsstart[0]->{'cdsstart_postype'};
	    $cds_feat->{'stoppos'} = $sorted_cdsstart[0]->{'cdsstart_pos'};
	    $cds_feat->{'qualifiers'}->{'cdsstart_proba'} = $sorted_cdsstart[0]->{'cdsstart_proba'};	    
	    $cds_feat->{'qualifiers'}->{'cdsstart_pos'} = $sorted_cdsstart[0]->{'cdsstart_pos'};	    
	}


	my $istart = 1;
	while ($istart < @sorted_cdsstart &&
	       $sorted_cdsstart[$istart]->{'cdsstart_proba'} > $p_start_threshold*$cds_feat->{'qualifiers'}->{'cdsstart_proba'}) {
	    
	    my $cdsstart_pos = $sorted_cdsstart[$istart]->{'cdsstart_pos'};
	    if ($sorted_cdsstart[$istart]->{'cdsstart_postype'} eq 'LESSER') {
		$cdsstart_pos = "<$cdsstart_pos";
	    } elsif ($sorted_cdsstart[$istart]->{'cdsstart_postype'} eq 'GREATER') {
		$cdsstart_pos = ">$cdsstart_pos";
	    }
	    $cds_feat->{'qualifiers'}->{'start'.chr(64 + $istart).'_pos'} = $cdsstart_pos;    
	    $cds_feat->{'qualifiers'}->{'start'.chr(64 + $istart).'_proba'} = $sorted_cdsstart[$istart]->{'cdsstart_proba'};	
	    $istart++;	    
	}

	push @$features, $cds_feat;
    }
    
}



sub print_feat {
    my $file = shift @_;
    my $seq_features = shift @_;
    my $segments = shift @_;
    my $annotformat = shift @_;

    open(OUT, ">$file") || die "could not open $file in write mode\n";

    if ($annotformat eq 'GBK') {
	while ( my $features = shift @$seq_features ) {
	    my $segment = shift @$segments;
	    my $seqname = $segment->{'comment'};
	    print OUT $seqname;
	    while (my $feat = shift @$features) {
		my $location_string = "";
		if ($feat->{'startpos_type'} eq 'LESSER') {
		$location_string .= "<";
	    } elsif  ($feat->{'startpos_type'} eq 'GREATER') {
		$location_string .= ">";
	    }
		$location_string .= $feat->{'startpos'};
		$location_string .= "..";
		if ($feat->{'stoppos_type'} eq 'LESSER') {
		    $location_string .= "<";
		} elsif  ($feat->{'stoppos_type'} eq 'GREATER') {
		    $location_string .= ">";
		}
		$location_string .= $feat->{'stoppos'};
		if ($feat->{'strand'} eq '-1') {
		    $location_string = "complement(".$location_string.")";
		}
		
		printf OUT "%5s%-15s%1s%-58s\n", "", $feat->{'type_feat'}, "", $location_string;
		
		my $type_qual;
		foreach $type_qual (sort keys(%{$feat->{'qualifiers'}})) {
		    printf OUT "%21s%-58s\n", "", "/".$type_qual."=\"".$feat->{'qualifiers'}->{$type_qual}."\"";
		}
		
	    }
	}
    } elsif ($annotformat eq 'GFF') {
	while ( my $features = shift @$seq_features ) {
	    my $segment = shift @$segments;
	    my $seqname = $segment->{'comment'};
	    if ($seqname =~ />(\S+)/) {
		$seqname = $1;
	    }
	    while (my $feat = shift @$features) {
		
		my $gffscore =".";
		if (defined $feat->{'qualifiers'}->{'cds_proba'}) {
		    $gffscore = $feat->{'qualifiers'}->{'cds_proba'};
		} else {
		    $gffscore = $feat->{'qualifiers'}->{'rbs_proba'};		    
		}
		my $gffstrand = "+";
		if ($feat->{'strand'} eq '-1') {
		    $gffstrand = "-";
		}
		my $gffframe = ".";
		if ($feat->{'type_feat'} eq 'CDS') {
		    $gffframe = 0;
		}


		print OUT $seqname, "\t", "SHOW", "\t", $feat->{'type_feat'}, "\t", $feat->{'startpos'}, "\t", $feat->{'stoppos'}, "\t", sprintf("%.3f\t", $gffscore), "\t", $gffstrand, "\t", $gffframe, "\t";
		if ($feat->{'startpos_type'} ne 'EXACT') {
		    print OUT "startpos_type", " ", $feat->{'startpos_type'}, " ; ";
		}
		if ($feat->{'stoppos_type'} ne 'EXACT') {
		    print OUT "stoppos_type", " ", $feat->{'stoppos_type'}, " ; ";
		}		
		my $type_qual;
		foreach $type_qual (sort keys(%{$feat->{'qualifiers'}})) {
		    print OUT $type_qual, " ", $feat->{'qualifiers'}->{$type_qual}, " ; ";
		}
		print OUT "\n";
	    }
	}	
    }
    close(OUT);
    
}


sub create_Eoutput_file {
    
    my $model = shift @_;
    my $e_columns = shift @_;

    my $line;
    my $state_id;
    my $key;

    my %output_keys;
    
    open(IN, "<$model") || die "cannot open the model file $model";
    
    my $istate = 0;
    $line = <IN>;
    while ($line) {
	if ($line =~ /\s*state_id:\s+([^\s]+)/) {
	    $state_id = $1;
	    if ($state_id =~ /\!([^\s]+)\!/) {
		$key = $1;
		if (! $output_keys{$key}) {
		    $output_keys{$key} = {};
		}
		$output_keys{$key}->{$state_id} = $istate;
	    }
	    $istate++;
	}
	$line = <IN>;
    }

    close(IN);

    my %parser_param;

    open(OUT, ">outputfromparse.desc");
    my $e_column = 0;
    foreach $key (keys %output_keys) {
	my $text = "";
	$e_columns->{$key} = $e_column;
	foreach $state_id (keys %{$output_keys{$key}}) {
	    $text .= " ; ".$state_id;
	}
	$text =~ s/^\s\;\s//;
	print OUT " ( ".$text." ) ";
	$e_column++;
    }
    close(OUT);

}

sub by_cdsstart_proba {
    if ($a->{'cdsstart_proba'} > $b->{'cdsstart_proba'}) {
	return -1;
    } elsif ($a->{'cdsstart_proba'} < $b->{'cdsstart_proba'}) {
	return 1;
    } else {
	return 0;
    }
}

sub by_startpos {
    if ($a->{'startpos'} < $b->{'startpos'}) {
	return -1;
    } elsif ($a->{'startpos'} > $b->{'startpos'}) {
	return 1;
    } else {
	return 0;
    }
}
