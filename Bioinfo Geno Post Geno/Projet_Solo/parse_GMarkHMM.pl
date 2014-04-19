#!/usr/bin/perl -w

use strict;
use Getopt::Long;
#use Proc::ProcessTable;

my %params;
GetOptions(\%params, 'input=s','seqname=s','output=s', 'help');

usage() if 
        defined $params{help} || 
        ! defined $params{input}|| 
        ! defined $params{output}||
        ! defined $params{seqname}
        ;

sub usage {
print STDERR << "EOF";

   usage: $0  --help

    parameters:
      
       	--input           	specify input file name
       	
       	--seqname		  	specify sequence name

       	--output          	specify output filename
	
       	--help             	prints this help and exits

EOF
exit;
}

sub artem_form{
my ($inname,$outname,$seqn) = @_;
open (INP,"$inname".'.txt') || die ("Can't open file ".$inname.".txt");
open (OUT,">$outname".".txt") || die ("Can't open file ".$outname.".txt");

my $line;
my @split = ();
my %line_to_print;#Ma table de hashage contenant les éléments à printer
my $curentcds = 0; 

	while($line = <INP>){
	#print "Voici la ligne: ".$line."\n";
	if($.>2 && $line !~/^(\s)*$/){#On saute les 2 premières lignes et on vérifie que la ligne n'est pas vide
		chomp $line;
		@split = split(' ',$line);# On split la ligne (6 colonnes)
		$line_to_print{'seqname'} = $seqn;
		$line_to_print{'method'} = 'GenMarkHMM';
		
		if($split[1] eq '-'){$line_to_print{'strand'} = "-"; $line_to_print{'atgleft'}=$split[3]-2; $line_to_print{'atgright'}=$split[3];}
		else{ $line_to_print{'strand'} = "+"; $line_to_print{'atgright'}=$split[2]+2; $line_to_print{'atgleft'}=$split[2];}
		
		
		$line_to_print{'atg'} = 'ATG';#Je rajoute une info sur mon ATG
		
		$curentcds=$split[0];
		$line_to_print{'cds'} = 'CDS';
		$line_to_print{'cdsleft'} = $split[2];
		$line_to_print{'cdsright'} = $split[3];
			
		
		
		
		print OUT $line_to_print{'seqname'}."\t".$line_to_print{'method'}."\t".$line_to_print{'cds'}."\t".$line_to_print{'cdsleft'}."\t".$line_to_print{'cdsright'}."\t";
		print OUT "."."\t".$line_to_print{'strand'}."\t".'.'."\t".'gene GMHMM_CDS_'.$curentcds."\n";
		
		print OUT $line_to_print{'seqname'}."\t".$line_to_print{'method'}."\t".$line_to_print{'atg'}."\t".$line_to_print{'atgleft'}."\t".$line_to_print{'atgright'}."\t";
		print OUT "."."\t".$line_to_print{'strand'}."\t".'.'."\n";
		
		
		
		}
	}
	close(INP);
	close(OUT);
}



my $inp = $params{input};
my $outp = $params{output};
my $seqname = $params{seqname};
artem_form($inp, $outp, $seqname);
	
