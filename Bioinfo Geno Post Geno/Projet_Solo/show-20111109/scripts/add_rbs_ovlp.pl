#!/usr/local/bin/perl -w

use strict;

use lib 'NONE/libexec';
use overlaps_model;
use rbs_model;

my $prefix = "/usr/local";
my $exec_prefix = "${prefix}";
my $bindir = "${exec_prefix}/bin";
my $libexecdir = "${exec_prefix}/libexec";
my $datadir = "${prefix}/share";

my $outmodel;
my $inmodel;
my $rbsmodel;

if (@ARGV < 3) {
    die "Usage: add_rbs_ovlp.pl <inmodel> <outmodel> <rbsmodel>
rbsmodel: m0 | m1 | double_m0
**********************************************************\n";
} else {
    $inmodel = shift @ARGV;
    $outmodel = shift @ARGV;
    $rbsmodel = shift @ARGV;
}



my $model = [];

&ReadModel($model, $inmodel);
my $list_cod = &AddState($model);
&PrintModel($model, $outmodel);
&PrintAddedStates($outmodel, $list_cod, $rbsmodel);

################

sub PrintAddedStates {
    my $outmodel = shift @_;
    my $list_cod = shift @_;
    my $rbsmodel = shift @_;

    my $text;

    open(OUT, ">>$outmodel") || die "could not open to write $outmodel";

    
    if ($rbsmodel =~ /^m0$/) {
	$text = $rbs_model::RBSdirect_m0;
    } elsif ($rbsmodel =~ /^m1$/) {
	$text = $rbs_model::RBSdirect_m1;
    } elsif ($rbsmodel =~ /^double_m0$/) {
	$text = $rbs_model::RBSdirect_double_m0;
    }
    &replaceMarkUpByTransitions(\$text, 'START+_1', $list_cod->{'START+_1'});
    print OUT $text;
    

    if ($rbsmodel =~ /^m0$/) {
	$text = $rbs_model::RBSopposite_m0;
    } elsif ($rbsmodel =~ /^m1$/) {
	$text = $rbs_model::RBSopposite_m1;
    } elsif ($rbsmodel =~ /^double_m0$/) {
	$text = $rbs_model::RBSopposite_double_m0;
    }
    &replaceMarkUpByTransitions(\$text, 'INTER', $list_cod->{'INTER'});
    print OUT $text;

    $text = $overlaps_model::OVLP;
    &replaceMarkUpByTiedTransitions(\$text, 'CDS+_1', $list_cod->{'CDS+_1'},
				    'trans_start+_3');
    &replaceMarkUpByTiedTransitions(\$text, 'CDS+_3', $list_cod->{'CDS+_3'},
				    'trans_start+_3');
    &replaceMarkUpByTiedTransitions(\$text, 'CDS-_1', $list_cod->{'CDS-_1'},
				    'trans_stop-_1');
    &replaceMarkUpByTiedTransitions(\$text, 'CDS-_2', $list_cod->{'CDS-_2'},
				    'trans_stop-_1');
    &replaceMarkUpByTiedTransitions(\$text, 'CDS-_3', $list_cod->{'CDS-_3'},
				    'trans_stop-_1');
    print OUT $text;
    close(OUT);
}

sub AddState {
    my $model = shift @_;

    my $istate;
    my $trans_label_cod1direct = 'no';
    my $trans_label_cod2direct = 'no';
    my $trans_label_cod3direct = 'no';
    my $trans_label_cod3opposite = 'no';
    my $trans_label_cod2opposite = 'no';
    my $trans_label_cod1opposite = 'no';
    my $trans_label_stop3direct = 'no';

    my %list_cod;
    $list_cod{'CDS+_1'} = [];
    $list_cod{'CDS+_2'} = [];
    $list_cod{'CDS+_3'} = [];
    $list_cod{'CDS-_3'} = [];
    $list_cod{'CDS-_2'} = [];
    $list_cod{'CDS-_1'} = [];
    $list_cod{'START+_1'} = [];
    $list_cod{'INTER'} = [];
   
    for( $istate = 0 ; $istate < @$model ; $istate++ ) {
	if ($model->[$istate]->{'state_id'} =~ /\{CDS\+_1\}/) {
	    push @{$list_cod{'CDS+_1'}}, $model->[$istate]->{'state_id'};
	}
	if ($model->[$istate]->{'state_id'} =~ /\{CDS\+_2\}/) {
	    push @{$list_cod{'CDS+_2'}}, $model->[$istate]->{'state_id'};
	}
	if ($model->[$istate]->{'state_id'} =~ /\{CDS\+_3\}/) {
	    push @{$list_cod{'CDS+_3'}}, $model->[$istate]->{'state_id'};
	}
	if ($model->[$istate]->{'state_id'} =~ /\{CDS\-_1\}/) {
	    push @{$list_cod{'CDS-_1'}}, $model->[$istate]->{'state_id'};
	}
	if ($model->[$istate]->{'state_id'} =~ /\{CDS\-_2\}/) {
	    push @{$list_cod{'CDS-_2'}}, $model->[$istate]->{'state_id'};
	}
	if ($model->[$istate]->{'state_id'} =~ /\{CDS\-_3\}/) {
	    push @{$list_cod{'CDS-_3'}}, $model->[$istate]->{'state_id'};
	}
	if ($model->[$istate]->{'state_id'} =~ /\{INTER\}/) {
	    push @{$list_cod{'INTER'}}, $model->[$istate]->{'state_id'};
	}
	if ($model->[$istate]->{'state_id'} =~ /\{START\+_1\}/) {
	    push @{$list_cod{'START+_1'}}, $model->[$istate]->{'state_id'};
	}
    }

    for( $istate = 0 ; $istate < @$model ; $istate++ ) {
	if ($model->[$istate]->{'state_id'} =~ /\{INTER\}/) {
	    &AddTransTowardRBSdirect($model->[$istate]);
	}
	if ($model->[$istate]->{'state_id'} =~ /\{START\-\_1\}/) {
	    &AddTransTowardRBSopposite($model->[$istate]);
	}
	if ($model->[$istate]->{'state_id'} =~ /\{CDS\+_1\}/) {
	    &AddTransTowardOVLPdd($model->[$istate],  
				  \$trans_label_cod1direct);
	}
	if ($model->[$istate]->{'state_id'} =~ /\{CDS\-_3\}/) {
	    &AddTransTowardOVLPoo($model->[$istate],  
				  \$trans_label_cod3direct);
	}
	if ($model->[$istate]->{'state_id'} =~ /\{STOP\+_3\}/) {
	    &AddTransTowardOVLPdo($model->[$istate],  
				  \$trans_label_stop3direct);
	}
	if ($model->[$istate]->{'state_id'} =~ /\{START\+_3\}/) {
	    $model->[$istate]->{'label'} = 'trans_start+_3';
	}
	if ($model->[$istate]->{'state_id'} =~ /\{STOP\-_1\}/) {
	    $model->[$istate]->{'label'} = 'trans_stop-_1';
	}
    }

    return \%list_cod;
}


sub replaceMarkUpByTransitions {
    my $text = shift @_;
    my $key = shift @_;
    my $list_state = shift @_;
    
    my $new_trans = "";

    print $key, "\n";
    $key =~ s/([^A-Za-z0-9])/\\$1/g;
    print $key, "\n";
    my $totalp = 1;
    if ($$text =~ /\<[^\<]*Add[^\<]*$key[^\<]*p\=([0-9\.]+)[^\<]*\>/) {
	$totalp = $1;
	print "TOTO\n";
    } 

    my $nb_state = @$list_state;
    my $ptrans = $totalp/$nb_state;
    foreach my $state_id (@$list_state) {
	print $state_id, "\n";
	$new_trans .= "\ttype: 1\n\tstate: $state_id\n\tptrans: $ptrans\n";
    }    
    chomp($new_trans);
    print $new_trans, "\n";
    $$text =~ s/\<[^\<]*Add[^\<]*$key[^\<]*\>/$new_trans/g;
}

sub replaceMarkUpByTiedTransitions {
    my $text = shift @_;
    my $key = shift @_;
    my $list_state = shift @_;
    my $label = shift @_;

    my $new_trans = "\ttied_to: $label\n";

    print $key, "\n";
    $key =~ s/([^A-Za-z0-9])/\\$1/g;
 
    my $nb_state = @$list_state;

    foreach my $state_id (@$list_state) {
	print $state_id, "\n";
	$new_trans .= "\tstate: $state_id\n";
    }    
    chomp($new_trans);
    print $new_trans, "\n";
    $$text =~ s/\<[^\<]*Add[^\<]*$key[^\<]*\>/$new_trans/g;
}


sub AddTransTowardOVLPdo {
    my $state = shift @_;
    my $r_label = shift @_;

    if ($$r_label eq 'no') {
	$$r_label = 'yes';
	$state->{'label'} = 'trans_stop+_3';
       
	my $itrans;
	my $ptrans = 0;
	for ( $itrans = 0 ; $itrans < @{$state->{'transitions'}} ; 
	      $itrans++ ) {
	    if ( $state->{'transitions'}->[$itrans]->{'state'} 
		 =~ /\{INTER\}/ ) {
		$ptrans = $state->{'transitions'}->[$itrans]->{'ptrans'};
		$state->{'transitions'}->[$itrans]->{'ptrans'} = $ptrans*0.97;
		$state->{'transitions'}->[$itrans]->{'type'} = 1;
	    }
	}
	
	my $trans;
	
	$trans = {};
	$trans->{'type'} = 1;
	$trans->{'state'} = 'post_ovlp-_1';
	$trans->{'ptrans'} = $ptrans*0.01;    
	push @{$state->{'transitions'}}, $trans;

	$trans = {};
	$trans->{'type'} = 1;
	$trans->{'state'} = 'post_ovlp-_2';
	$trans->{'ptrans'} = $ptrans*0.01;    
	push @{$state->{'transitions'}}, $trans;

	$trans = {};
	$trans->{'type'} = 1;
	$trans->{'state'} = 'post_ovlp-_3';
	$trans->{'ptrans'} = $ptrans*0.01;    
	push @{$state->{'transitions'}}, $trans;

    } else {
	$state->{'tied_to'} = 'trans_stop+_3';
	
	my $trans;
	
	$trans = {};
	$trans->{'state'} = 'pre_ovlp-_1';
	push @{$state->{'transitions'}}, $trans;

	$trans = {};
	$trans->{'state'} = 'pre_ovlp-_2';
	push @{$state->{'transitions'}}, $trans;

	$trans = {};
	$trans->{'state'} = 'pre_ovlp-_3';
	push @{$state->{'transitions'}}, $trans;
	
    }
}



sub AddTransTowardOVLPoo {
    my $state = shift @_;
    my $r_label = shift @_;

    if ($$r_label eq 'no') {
	$$r_label = 'yes';
	$state->{'label'} = 'trans_cod-_3';
	
	my $itrans;
	my $ptrans = 0;
	for ( $itrans = 0 ; $itrans < @{$state->{'transitions'}} ; 
	      $itrans++ ) {
	    if ( $state->{'transitions'}->[$itrans]->{'state'} 
		 =~ /\{CDS\-\_2\}/ ) {
		$ptrans = $state->{'transitions'}->[$itrans]->{'ptrans'};
		$state->{'transitions'}->[$itrans]->{'ptrans'} = $ptrans*0.98;
		$state->{'transitions'}->[$itrans]->{'type'} = 1;
	    }
	}
	
	my $trans;
	
	$trans = {};
	$trans->{'type'} = 1;
	$trans->{'state'} = 'pre_ovlp-_2';
	$trans->{'ptrans'} = $ptrans*0.02;    
	push @{$state->{'transitions'}}, $trans;

    } else {
	$state->{'tied_to'} = 'trans_cod-_3';
	
	my $trans;
	
	$trans = {};
	$trans->{'state'} = 'pre_ovlp-_2';
	push @{$state->{'transitions'}}, $trans;

	
    }
}



sub AddTransTowardOVLPdd {
    my $state = shift @_;
    my $r_label = shift @_;

    if ($$r_label eq 'no') {
	$$r_label = 'yes';
	$state->{'label'} = 'trans_cod+_1';
	
	my $itrans;
	my $ptrans = 0;
	for ( $itrans = 0 ; $itrans < @{$state->{'transitions'}} ; 
	      $itrans++ ) {
	    if ( $state->{'transitions'}->[$itrans]->{'state'} 
		 =~ /\{CDS\+\_2\}/ ) {
		$ptrans = $state->{'transitions'}->[$itrans]->{'ptrans'};
		$state->{'transitions'}->[$itrans]->{'ptrans'} = $ptrans*0.99;
		$state->{'transitions'}->[$itrans]->{'type'} = 1;
	    }
	}
	
	my $trans;
	
	$trans = {};
	$trans->{'type'} = 1;
	$trans->{'state'} = 'pre_ovlp+_2';
	$trans->{'ptrans'} = $ptrans*0.01;    
	push @{$state->{'transitions'}}, $trans;
	
    } else {
	$state->{'tied_to'} = 'trans_cod+_1';

	my $trans;
	
	$trans = {};
	$trans->{'state'} = 'pre_ovlp+_2';
	push @{$state->{'transitions'}}, $trans;
    }
}

sub AddTransTowardRBSopposite {
    my $state = shift @_;

    my $itrans;
    my $ptrans = 0;
    for ( $itrans = 0 ; $itrans < @{$state->{'transitions'}} ; $itrans++ ) {
	if ( $state->{'transitions'}->[$itrans]->{'state'} =~ /\{INTER\}/ ) {
	    $ptrans = $state->{'transitions'}->[$itrans]->{'ptrans'};
	    $state->{'transitions'}->[$itrans]->{'ptrans'} = $ptrans/2;
	}
    }

    my $trans;

    $trans = {};
    $trans->{'type'} = 1;
    $trans->{'state'} = 'rbs-_spacer_3';
    $trans->{'ptrans'} = $ptrans/6;    
    push @{$state->{'transitions'}}, $trans;

    $trans = {};
    $trans->{'type'} = 1;
    $trans->{'state'} = 'rbs-_spacer_2';
    $trans->{'ptrans'} = $ptrans/6;    
    push @{$state->{'transitions'}}, $trans;

    $trans = {};
    $trans->{'type'} = 1;
    $trans->{'state'} = 'rbs-_spacer_1';
    $trans->{'ptrans'} = $ptrans/6;    
    push @{$state->{'transitions'}}, $trans;

}


sub AddTransTowardRBSdirect {
    my $state = shift @_;

    my $itrans;
    my $ptrans = 0;
    for ( $itrans = 0 ; $itrans < @{$state->{'transitions'}} ; $itrans++ ) {
	if ( $state->{'transitions'}->[$itrans]->{'state'} =~ /{\START\+_1\}/ ) {
	    $ptrans = $state->{'transitions'}->[$itrans]->{'ptrans'};
	    $state->{'transitions'}->[$itrans]->{'ptrans'} = $ptrans/2;
	}
    }

    my $trans = {};
    $trans->{'type'} = 1;
    $trans->{'state'} = 'rbs+_1';
    $trans->{'ptrans'} = $ptrans/2;
    
    push @{$state->{'transitions'}}, $trans;
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


