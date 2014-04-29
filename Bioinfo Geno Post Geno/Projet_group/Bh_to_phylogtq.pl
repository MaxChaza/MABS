#!/usr/bin/perl -w

use strict;
use Getopt::Long;
#use Proc::ProcessTable;

my %params;
GetOptions(\%params, 'input=s','output=s', 'help');

usage() if 
        defined $params{help} || 
		#! defined $params{output}||
        ! defined $params{input}
        ;

sub usage {
print STDERR << "EOF";

   usage: $0  --help

    parameters:
      
       	--input           	specify input file name
       	
       	--help             	prints this help and exits

EOF
exit;
}
sub list_query_genes{
	my ($s_ref, @list_sr ) = @_;
	my @list_s = grep{$_ ne $s_ref} @list_sr; 
	my @list_genes=();
	my $line;
	foreach my $value (@list_s){
	print $value."\n";
		open (QS,"./genomes_coli/$s_ref"."_"."$value".".txt") || die ("Can't open file ".$s_ref."_".$value.".txt");
		#On va parcourir tous les fichiers pour compter le nombres de gènes totaux de la query présents dans les autres souches
		while($line= <QS>){
			if($.>1 ){
				chomp $line;
				my @split = split("\t",$line);#'\t' ou "\t" ?
				my $gene = $split[0];
				if ( "@list_genes" !~ /$gene/) {
					unshift(@list_genes,$gene);
				}
			}
		}
		close(QS);
	}

	return (@list_genes);
}

sub bh_to_phylo{
    my ($s_ref, $list_sr,$genes_r) = @_;#les listes des gènes et des souches sont passé par référence
    my @list_s = grep{$_ ne $s_ref} @$list_sr; 


    my $lineQS;
    my $lineSQ;
    my $linePARQ;
    my $linePARS;

    my $val;
    my $cle;
    my $row = 0;
    my $col= 0;
    # $t[$row][$col] = "\t";

    my %hash = ();

    foreach my $souche (@list_s){
            foreach my $gene (@$genes_r){
                    print $gene."\n";
                    $hash{$gene}{$souche} = "0";
            }
    }

    foreach my $cle (keys(%hash)){
            foreach my $val (keys %{$hash{$cle}}){
                    print "souche $cle gène $val : val = $hash{$cle}{$val} \n";
            }
    }
    #open (OUT,"<$output".".txt") || die ("Can't open file ".$output.".txt");

    foreach my $value (@list_s){
            #$col++;
            #$t[$row][$col] = $value;
            open (QS,"./genomes_coli/$s_ref"."_"."$value".".txt") || die ("Can't open file ".$s_ref."_".$value.".txt");
            open (SQ,"./genomes_coli/$value"."_"."$s_ref".".txt") || die ("Can't open file ".$value."_".$s_ref.".txt");
            open (PARQ,"./genomes_coli/$s_ref"."_"."$s_ref".".txt") || die ("Can't open file ".$s_ref."_".$s_ref.".txt");
            open (PARS,"./genomes_coli/$value"."_"."$value".".txt") || die ("Can't open file ".$value."_".$value.".txt");

            while($lineQS= <QS>){
                    if($.>1 ){
                            chomp $lineQS;
                            my @splitQS = split("\t",$lineQS);#'\t' ou "\t" ?
                            my $queryQS = $splitQS[0];
                            my $subjQS = $splitQS[1];
                            my $scoreQS = pop(@splitQS);

                            while($lineSQ = <SQ>){
                                if($.>1 ){
                                    chomp $lineSQ;
                                    my @splitSQ = split("\t",$lineSQ);#'\t' ou "\t" ?
                                    my $querySQ = $splitSQ[0];
                                    my $subjSQ = $splitSQ[1];
                                    if(($subjQS eq $querySQ) && ($subjSQ eq $queryQS)){#Il faut que l'on retrouve dans l'autre fichier le Best Hit pour faire un BBH
                                            while($linePARQ = <PARQ>){#On regarde si le score des paralogues est supérieur
                                                if($.>1 ){
                                                    chomp $linePARQ;
                                                    my @splitPARQ = split("\t",$linePARQ);#'\t' ou "\t" ?
                                                    my $queryPARQ = $splitPARQ[0];
                                                    if($queryPARQ eq $queryQS){#si je retrouve mon query
                                                            my $scorePARQ = pop(@splitPARQ);#on récup le score
                                                            if($scoreQS >= $scorePARQ){#On compare les scores
                                                                    while($linePARS = <PARS>){#On regarde si le score des paralogues est supérieur
                                                                        if($.>1 ){
                                                                            chomp $linePARS;
                                                                            my @splitPARS = split("\t",$linePARS);#'\t' ou "\t" ?
                                                                            my $queryPARS = $splitPARS[0];
                                                                            if($queryPARS eq $querySQ){#si je retrouve mon subject
                                                                                    my $scorePARS = pop(@splitPARS);#on récup le score
                                                                                    if($scoreQS >= $scorePARS){#On compare les scores (si sup, on est sur un BBH)
                                                                                            $hash{$queryQS}{$value} ="1";
                                                                                    }
                                                                            }
                                                                        }
                                                                    }
                                                            }
                                                    }
                                                }
                                            }
                                    }
                                }
                            }	
                    }
            }
    }
}





my @list_SR = ("EcolA", "EcolB", "EcolD", "EcolL", "EcolM", "EcolR", "EcolU", "EcolX");#liste de toutes les souches
my $nom_souche_ref = $params{input};
#my $out = $params{output};#matrice présence basence output
my @list_R = &list_query_genes($nom_souche_ref,@list_SR);#la liste de TOUS mes gènes du génome de référence

&bh_to_phylo($nom_souche_ref,\@list_SR,\@list_R);


#for $family ( keys %HoH ) {
 #   print "$family: ";
 #   for $role ( keys %{ $HoH{$family} } ) {
 #        print "$role=$HoH{$family}{$role} ";
 #   }
#    print "\n";
#}