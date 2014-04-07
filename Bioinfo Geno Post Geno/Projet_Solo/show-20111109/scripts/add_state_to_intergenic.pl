#!/usr/local/bin/perl -w

use strict;

my $prefix = "/usr/local";
my $exec_prefix = "${prefix}";
my $bindir = "${exec_prefix}/bin";
my $libexecdir = "${exec_prefix}/libexec";
my $datadir = "${prefix}/share";

my $outmodel;
my $inmodel;
my $seqfastafile;
my $statename;
my $markovorder;

if (@ARGV < 5) {
    die "Usage: Add_StateToIntergenic.pl <inmodel> <outmodel> <seqfastafile> <statename> <markovorder>
**********************************************************\n";
} else {
    $inmodel = shift @ARGV;
    $outmodel = shift @ARGV;
    $seqfastafile = shift @ARGV;
    $statename = shift @ARGV;
    $markovorder = shift @ARGV;
}

#print $Model::RBSdirect;


my $model = [];

my $r_pobs = &ComputeStateComposition($seqfastafile, $markovorder);
&ReadModel($model, $inmodel);
&AddState_ToIntergenic($model, $statename, $markovorder, $r_pobs);
&PrintModel($model, $outmodel);

################

sub ComputeStateComposition {
    my $seqfastafile = shift @_;
    my $markovorder = shift @_;

    my @seqs;
    open(IN_SEQ, "<$seqfastafile") || die "couldn't open in read mode $seqfastafile\n";
    my $seqline = <IN_SEQ>;
    while( $seqline && $seqline !~ /^>/ ) {
	$seqline = <IN_SEQ>;
    }
    while( $seqline && $seqline =~ /^>/ ) {
	$seqline = <IN_SEQ>;
	my $seq;
	while( $seqline && $seqline !~ /^>/ ) {
	    $seq .= $seqline;
	    $seqline = <IN_SEQ>;
	}
	$seq =~ s/\s//g;
	$seq =~ tr/A-Z/a-z/;
	push @seqs, $seq;
    }
    close(IN_SEQ);


    my $nb_seq = @seqs;
    #print "$nb_seq\n";

    my $nbwords = (4**($markovorder + 2) - 1)/(4 - 1) - 1;
    #print $nbwords, "\n";
    my @counts;
    for (my $i = 0 ; $i < $nbwords ; $i++) {
	$counts[$i] = 0;
    }

    for(my $iseq = 0; $iseq < $nb_seq ; $iseq++) {
	my $lgseq = length($seqs[$iseq]);
	for(my $ipos = 0; $ipos < $lgseq; $ipos++) {
	    my $lgword = 1;
	    while ( ($lgword <= $markovorder + 1) &&
		    ($ipos + $lgword <= $lgseq) ) {
		my $word = substr($seqs[$iseq], $ipos, $lgword);
		$lgword++;
		if ($word !~ /[^agct]/) {
		    my $cod = &word2cod($word); 
		    #print "$cod\n";
		    $counts[$cod]++;
		}
	    }
	}
    }

    
    my $iword = 0;
    while ($iword + 4 <= $nbwords) {
	my $sum = 0;
	for(my $i = 0 ; $i < 4 ; $i++) {
	    $sum += $counts[$iword + $i];
	}
	for(my $i = 0 ; $i < 4 ; $i++) {
	    $counts[$iword + $i] /= $sum;
	}
	$iword += 4;
    }
    
    for (my $i = 0 ; $i < $nbwords ; $i++) {
	#print " ($i - ".$counts[$i].") ";
    }
    
    return \@counts;
}

sub word2cod {
    my $word = shift @_;
    
    my @word = split(//, $word);
    my $lgword = @word;
    
    my %alphabet = ('a' => 0, 'g' => 1, 'c' => 2, 't' => 3 );
    
    my $cod = (4**$lgword - 1)/(4 - 1) - 1;
    for( my $i = 0 ; $i < $lgword ; $i++ ) {
	$cod += 4**($lgword - $i - 1)*$alphabet{$word[$i]};
    }
    
    
    return $cod;
}

sub AddState_ToIntergenic {
    my $model = shift @_;
    my $statename = shift @_;
    my $markovorder = shift @_;
    my $r_pobs = shift @_;

    my $istate;   

    my $intergenic;

    for( $istate = 0 ; $istate < @$model ; $istate++ ) {
	if ($model->[$istate]->{'state_id'} =~ /\{INTER\}$/) {
	    $intergenic = $model->[$istate];
	} 
    }

    if (!defined $intergenic) {
	die "key {INTER} not found in model definition, unable to add state to intergenic\n"; 
    }

    my $itrans;
    my $ptrans;
    
    for ( $itrans = 0 ; $itrans < @{$intergenic->{'transitions'}} ; $itrans++ ) {
	$intergenic->{'transitions'}->[$itrans]->{'ptrans'} *= 0.9999 ;
    }

    my %newtransition;
    $newtransition{'state'} = $statename;
    $newtransition{'ptrans'} = 0.0001;
    $newtransition{'type'} = 1;
    push @{$intergenic->{'transitions'}}, \%newtransition; 
	  
    my $seqname;
    if ($intergenic->{'observations'} =~ /seq:\s*(\S+)\s/) {
	$seqname = $1;
    }

    my %newstate;
    $newstate{'state_id'} = $statename;
    $newstate{'transitions'} = [];

    $newstate{'transitions'}->[0] = {};
    $newstate{'transitions'}->[0]->{'state'} = $statename;
    $newstate{'transitions'}->[0]->{'type'} = 0;
    $newstate{'transitions'}->[0]->{'ptrans'} = 0.999;

    $newstate{'transitions'}->[1] = {};
    $newstate{'transitions'}->[1]->{'state'} = $intergenic->{'state_id'};
    $newstate{'transitions'}->[1]->{'type'} = 0;
    $newstate{'transitions'}->[1]->{'ptrans'} = 0.001;
    


    $newstate{'observations'} = "\tseq: $seqname\n\ttype: 0\n\torder: $markovorder\n\tpobs:";
    my $iobs = 0;
    for (my $order = 0 ; $order <= $markovorder ; $order++) {
	$newstate{'observations'} .= "\n";
	for (my $k = 0 ; $k < 4**$order ; $k++) {
	    my $pobs = join(" ", @{$r_pobs}[$iobs, $iobs+1, $iobs+2, $iobs+3]);
	    $newstate{'observations'} .= "\t\t$pobs\n";
	    $iobs += 4;
	}
    }


    push @$model, \%newstate;


}

sub ReadModel{
    my $model = shift @_;
    my $inmodel = shift @_;
    
    print "Read Model\n";
    
    my $state = 'no';
    my $transitions = 'no';
    my $tied_transitions = 'no';
    my $observations = 'no';

    open(IN, "<$inmodel") || die "could not open to read $inmodel";
    my $line = <IN>;
    
    while ( $line ) {
	$line =~ s/\#[\W\w]+$//;
	if ($line =~ /BEGIN_STATE/) {
	    $state = 'yes';
	} elsif ($line =~ /BEGIN_TRANSITIONS/) {
	    $transitions = 'yes';
	} elsif ($line =~ /BEGIN_OBSERVATIONS/) {
	    $observations = 'yes';
	} elsif ($line =~ /END_STATE/) {
	    $state = 'no';
	} elsif ($line =~ /END_TRANSITIONS/) {
	    $transitions = 'no';
	} elsif ($line =~ /END_OBSERVATIONS/) {
	    $observations = 'no';
	} elsif ( ($state eq 'yes') 
		  && ($transitions eq 'no') 
		  && ($observations eq 'no') ) {
	    if ($line =~ /state_id:\s*(\S+)/) {
		my %state;
		$state{'state_id'} = $1;
		$state{'transitions'} = [];
		$state{'observations'} = "";
		push @$model, \%state;
	    }
	} elsif ( $transitions eq 'yes' ) {
	    if ($line =~ /label:\s*(\S+)/) {
		$model->[@$model-1]->{'label'} = $1;
	    } elsif ($line =~ /tied_to:\s*(\S+)/) {
		$model->[@$model-1]->{'tied_to'} = $1;		
	    } elsif ( $model->[@$model-1]->{'tied_to'} 
		      && ($line =~ /state:\s*(\S+)/) ) {
		my %transition;
		$transition{'state'} = $1;
		push @{$model->[@$model-1]->{'transitions'}}, \%transition;
	    } elsif (!$model->[@$model-1]->{'tied_to'} 
		     && ($line =~ /type:\s*(\S+)/) ) {
		my %transition;
		$transition{'type'} = $1;
		push @{$model->[@$model-1]->{'transitions'}}, \%transition;
	    } elsif (!$model->[@$model-1]->{'tied_to'} 
		     && ($line =~ /state:\s*(\S+)/) ) {
		my $itrans = @{$model->[@$model-1]->{'transitions'}};
		$model->[@$model-1]->{'transitions'}->[$itrans-1]->{'state'} 
		= $1;
	    } elsif (!$model->[@$model-1]->{'tied_to'} 
		      && ($line =~ /ptrans:\s*(\S+)/) ) {
		my $itrans = @{$model->[@$model-1]->{'transitions'}};
		$model->[@$model-1]->{'transitions'}->[$itrans-1]->{'ptrans'} 
		= $1;
	    } 
	} elsif ( $observations eq 'yes' ) {
	    $model->[@$model-1]->{'observations'} .= $line;
	}
		
	$line = <IN>;
    }
    
    close(IN);
}


sub PrintModel{
    my $model = shift @_;
    my $outmodel = shift @_;

    print "Print Model\n";

    open(OUT, ">$outmodel") || die "could not open to write $outmodel";

    my $state;
    my $trans;

    while ( $state = shift @$model ) {
	print OUT "BEGIN_STATE\n";
	print OUT "state_id: ", $state->{'state_id'}, "\n";
	
	print OUT "BEGIN_TRANSITIONS\n";
	if ($state->{'tied_to'}) {
	    print OUT "\ttied_to: ", $state->{'tied_to'}, "\n";
	    while ($trans = shift @{$state->{'transitions'}}) {
		print OUT "\tstate: ", $trans->{'state'}, "\n";
	    }
	} elsif ($state->{'label'}) {
	    print OUT "\tlabel: ", $state->{'label'}, "\n";
	}
	if ( !$state->{'tied_to'} ) {
	    while ($trans = shift @{$state->{'transitions'}}) {
		print OUT "\ttype: ", $trans->{'type'}, "\n";
		print OUT "\tstate: ", $trans->{'state'}, "\n";
		print OUT "\tptrans: ", $trans->{'ptrans'}, "\n";
	    }
	}
	print OUT "END_TRANSITIONS\n";	

	print OUT "BEGIN_OBSERVATIONS\n";
	print OUT $state->{'observations'};
	print OUT "END_OBSERVATIONS\n";

	print OUT "END_STATE\n";
    }

    close(OUT);
    
}


