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
open (INP,"$inname") || die ("Can't open file ".$inname.".txt");
open (OUT,">$outname".".txt") || die ("Can't open file ".$outname.".txt");

my $line;
my @split = ();
my %line_to_print;#Ma table de hashage contenant les éléments à printer
my $curentcds = 0; 
my $reg = 0;
my $atg = 0;
my $cdsPossibility=1;

	while($line = <INP>){
	#print "Voici la ligne: ".$line."\n";
	if($.>3 && $line !~/^(\s)*$/){#On saute les 3 premières lignes et on vérifie que la ligne n'est pas vide
		chomp $line;
		@split = split(' ',$line);# On split la ligne (7 colonnes)
		$line_to_print{'seqname'} = $seqn;
		$line_to_print{'method'} = 'GenMark';
		
		if($split[2] eq 'complement'){$line_to_print{'strand'} = "-";}
		else{ $line_to_print{'strand'} = "+";}
		
		$line_to_print{'cds'} = 'CDS';
		$line_to_print{'cdsleft'} = $split[0];
		$line_to_print{'cdsright'} = $split[1];
		$line_to_print{'cdsprob'} = $split[5];
		
		if($split[6] !~ m/\.{4}/){#Si j'ai une info dans le champ start prob (!= '....') cela signifie que l'on a un ATG
			$line_to_print{'atg'} = 'ATG';#Je rajoute une info sur mon ATG
			if($split[2] eq 'complement'){$line_to_print{'atgleft'} = int($split[1])-2;$line_to_print{'atgright'} = int($split[1]);}#Si brin -
			else{$line_to_print{'atgleft'} = $split[0];$line_to_print{'atgright'} = int($split[0]) + 2;}#Si brin +
			if(int($split[6]) > 0){ $line_to_print{'atgprob'} = $split[6];}#Si la proba est > 0 on met la start prob
			else{$line_to_print{'atgprob'} = $split[5];}#sinon on met la proba moyenne
			$atg = 1;	
		}
		
		
		
		if(($line_to_print{'strand'}=~m/\+/) && ($reg != $split[1])){#Si on change de codon et qu'on est sur le brin +
			$curentcds += 1;#On change donc de cds
			$reg = $split[1];#On change la région
			$cdsPossibility = 1;#On remet à un le compteur des cds pour cette région
		}
		if(($line_to_print{'strand'}=~m/\-/) && ($reg != $split[0])){#Si on change de codon et qu'on est sur le brin -
			$curentcds += 1;
			$reg = $split[0];
			$cdsPossibility = 1;
		}
		
		print OUT $line_to_print{'seqname'}."\t".$line_to_print{'method'}."\t".$line_to_print{'cds'}."\t".$line_to_print{'cdsleft'}."\t".$line_to_print{'cdsright'}."\t";
		print OUT $line_to_print{'cdsprob'}."\t".$line_to_print{'strand'}."\t".'.'."\t".'gene GM_CDS_'.$curentcds.'.'.$cdsPossibility."\n";
		
		if($atg == 1){# S'il y a un ATG
			print OUT $line_to_print{'seqname'}."\t".$line_to_print{'method'}."\t".$line_to_print{'atg'}."\t".$line_to_print{'atgleft'}."\t".$line_to_print{'atgright'}."\t";
			print OUT $line_to_print{'atgprob'}."\t".$line_to_print{'strand'}."\t".'.'."\n";
		}
		
		$cdsPossibility += 1;
		
		
		}
	}
	close(INP);
	close(OUT);
}



my $inp = $params{input};
my $outp = $params{output};
my $seqname = $params{seqname};
artem_form($inp, $outp, $seqname);
	
