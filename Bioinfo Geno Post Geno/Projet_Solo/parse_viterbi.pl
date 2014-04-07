#!/usr/bin/perl -w

use strict;

my $rep = '/home/fichant/MasterBioinfo/TD_HMM/2010/';

my @motif_liste = ( 'sigma-35+', 'sigma-10+' );

opendir(DIR, $rep) or warn "Cannot open $rep: $!";
my @liste_of_files = grep /\.vit$/, sort(readdir DIR);
if ( scalar @liste_of_files == 0 ) {
	print "pas de fichier trouve!\n";
	exit(1);
}

close(DIR);

my $tp_sigma_35 = 0;
my $tp_sigma_10 = 0;
my $tp_sigma_35alt = 0;
my $tp_sigma_10alt = 0;
my $tot = 0;

# boucle sur les fichiers resultats (*.vit)
foreach my $file ( @liste_of_files ) {
	my($name) = $file =~ /(.+)\.vit/;
	$file = $rep . $file;
	open(FILE,"$file") or die("open: $file\n$!");

	my $pos = 0; # position dans la sequence
	my %pattern = ();
	my %motif_coord = ();
	my($code, $motif);
	while(<FILE>) {
	
		chop();
		if ( /^\# \d/ ) { # new hit
			# extraction des codes/motifs
			my(@list_of_pairs) = split(/\t/); # les pairs sont separees par des tabuations
			foreach my $pair ( @list_of_pairs ) {
				($code, $motif) = split(/ \: /, $pair); # separateur ' : '
				# eliminer les () et les nombres pour obtenir les motifs a 
				# chaque position, exemple :sigma-35+_1 a la position 1
				$motif =~ /\((.+)_\d+\)/; 
				$pattern{$code} = $1;
				#print "$code $pattern{$code}\n";
			}
		} elsif ( /^\d/ ) {
			# prediction, sous la forme d'un entier, correspondant a $code extrait dans l'entete, 
			# pour chaque position de la sequence
			$code = $_;
			$pos++;
			# enregistrer le debut fin de chaque motif, 
			# si le motif a une longueur de 1, debut=fin, 
			# sinon fin est la position de la derniere occurence de ce motif
			if ( defined $motif_coord{$pattern{$code}} ) {
				$motif_coord{$pattern{$code}}{End} = $pos;
			} else {
				$motif_coord{$pattern{$code}}{Start} = $pos;
				$motif_coord{$pattern{$code}}{End} = $pos;
			}
		}
	}
	close(FILE);
	
	# rechercher les positions attendues des motifs dans les sequences annotees
	$file = $rep . $name . '.dna';
	open(FILE,"$file") or die("open: $file\n$!");
	my @info = ();
	while(<FILE>) {
		chop();
		if ( /^>/ ) {
			# la ligne commencant par > contient 
			# le nom du gene, 
			# le debut des motifs 
			# et la longueur du linker
			(@info) = split(/\s+/);
			last;
		}
	}
	# si on predit un sigma-35alt sur le brin +
	if ( defined $motif_coord{'sigma-35alt+'}{Start} ) {
		# on calcule l'ecart (absolue) entre les positions predites et attendues
		if ( abs($motif_coord{'sigma-35alt+'}{Start} - $info[3]) >= 3 || abs($motif_coord{'sigma-10alt+'}{Start} - $info[5]) >= 3 ){
			my $linker_p = $motif_coord{'sigma-10alt+'}{Start} - $motif_coord{'sigma-35alt+'}{End} - 1;
			my $linker_o = $info[5] - ( $info[3] + 5 ) - 1;
			# on imprime les erreurs avec les tailles des linker predits attendus
			print "alt $name\t$motif_coord{'sigma-35alt+'}{Start} $info[3] $info[2] $motif_coord{'sigma-10alt+'}{Start} $info[5] $info[4] $linker_p $linker_o\n";
		}
		$tp_sigma_35alt++ if ( abs($motif_coord{'sigma-35alt+'}{Start} - $info[3]) < 3 );
		$tp_sigma_10alt++ if ( abs($motif_coord{'sigma-10alt+'}{Start} - $info[5]) < 3 );
	}
	# si on predit un sigma-35 sur le brin +
	if ( defined $motif_coord{'sigma-35+'}{Start} ) {
		# on calcule l'ecart (absolue) entre les positions predites et attendues
		if ( abs($motif_coord{'sigma-35+'}{Start} - $info[3]) >= 3 || abs($motif_coord{'sigma-10+'}{Start} - $info[5]) >= 3 ) {
			my $linker_p = $motif_coord{'sigma-10+'}{Start} - $motif_coord{'sigma-35+'}{End} - 1;
			my $linker_o = $info[5] - ( $info[3] + 5 ) - 1;
			# on imprime les erreurs avec les tailles des linker predits attendus
			print "$name\t$motif_coord{'sigma-35+'}{Start} $info[3] $info[2] $motif_coord{'sigma-10+'}{Start} $info[5] $info[4] $linker_p $linker_o\n";
		}
		$tp_sigma_35++ if ( abs($motif_coord{'sigma-35+'}{Start} - $info[3]) < 3 );
		$tp_sigma_10++ if ( abs($motif_coord{'sigma-10+'}{Start} - $info[5]) < 3 );
	}
	$tot++;
	close(FILE);
}
print "\nStatistiques pour un ecart < 3\n";
printf ("sigma_35 %5.2f sigma_10  %5.2f \n", $tp_sigma_35*100/$tot, $tp_sigma_10*100/$tot);
printf ("sigma_35alt %5.2f sigma_10alt  %5.2f \n", $tp_sigma_35alt*100/$tot, $tp_sigma_10alt*100/$tot);
printf ("sigma_35tot %5.2f sigma_10tot  %5.2f \n", ($tp_sigma_35 + $tp_sigma_35alt)*100/$tot, ($tp_sigma_10 + $tp_sigma_10alt)*100/$tot);
