#!/usr/bin/perl -w

use strict;
use Getopt::Long;
#use Proc::ProcessTable;

my %params;
GetOptions(\%params, 'input=s', 'feature=s','output=s', 'help');

usage() if 
        defined $params{help} || 
        ! defined $params{input}|| 
        ! defined $params{output}|| 
        ! defined $params{feature};

sub usage {
print STDERR << "EOF";

   usage: $0  --help

    parameters:
      
       	--input           specify input file name

       	--output          specify output filename

	--feature		sepcify a feature type (RBS, promoteur, terminateur)
       
       	--help             prints this help and exits

EOF
exit;
}



sub artem_form{
my ($inname,$outname,$feature) = @_;
open (INP,"$inname".".txt") || die ("Can't open file ".$inname.".txt");
open (OUT,">$outname".".txt") || die ("Can't open file ".$outname.".txt");

my $line;
my @split = ();
my @line_to_print = ();
my @bornes=();
my $note;

	while($line = <INP>){
	print "Voici la ligne: ".$line."\n";
	chomp $line;
		if($line =~ /^>/){
			@split = split(':',$line);# On split le >BS09819:[347,378] en deux
			$line_to_print[0] = substr($split[0],1,length($split[0])-1);#On récupère le nom de la seq
			$line_to_print[1] = 'Patscan';
			$line_to_print[2] = $feature;
			@bornes = split(',',$split[1]);
			$bornes[0] =~ s/\[//g; #remplace [ par rien
			$bornes[1]=~ s/\]//g;
			if ($bornes[0]>$bornes[1]){
				$line_to_print[3] = $bornes[1];
				$line_to_print[4] = $bornes[0];
			}
			else{
				$line_to_print[3] = $bornes[0];
				$line_to_print[4] = $bornes[1];
			}
			$line_to_print[5] = ".";
			if($bornes[0] > $bornes[1]){ $line_to_print[6] = "-";}
			else{ $line_to_print[6]="+";}
			$line_to_print[7] = ".";
			foreach my $val (@line_to_print){
				print OUT $val."\t";
			}
		}
		else{
			if(length($line) > 0){
				$note = "note \"".$line."\"";
				print OUT $note."\n";
			}
		}
	}
}



my $inp = $params{input};
my $outp = $params{output};
my $feat = $params{feature};
artem_form($inp, $outp, $feat);