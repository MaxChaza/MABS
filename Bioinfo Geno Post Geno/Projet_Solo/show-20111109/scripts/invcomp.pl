#!/usr/local/bin/perl -w

use strict;

my $prefix = "/usr/local";
my $exec_prefix = "${prefix}";
my $bindir = "${exec_prefix}/bin";
my $libexecdir = "${exec_prefix}/libexec";
my $datadir = "${prefix}/share";

my $inseqfastafile;
my $outseqfastafile;

if (@ARGV < 2) {
    die "Usage: invcomp.pl <inseqfastafile> <outseqfastafile>
**********************************************************\n";
} else {
    $inseqfastafile = shift @ARGV;
    $outseqfastafile = shift @ARGV;
}

my @seqs;
open(IN_SEQ, "<$inseqfastafile") || die "couldn't open in read mode $inseqfastafile\n";
my $seqline = <IN_SEQ>;
while( $seqline && $seqline !~ /^>/ ) {
    $seqline = <IN_SEQ>;
}
while( $seqline && $seqline =~ /^>/ ) {
    my $comment = $seqline;
    $seqline = <IN_SEQ>;
    my $seq;
    while( $seqline && $seqline !~ /^>/ ) {
	$seq .= $seqline;
	$seqline = <IN_SEQ>;
    }
    $seq =~ s/\s//g;
    $seq =~ tr/A-Z/a-z/;
    my $sequence = {};
    $sequence->{'seq'} = $seq;
    $sequence->{'comment'} = $comment;
    push @seqs, $sequence;
}
close(IN_SEQ);


my $nb_seq = @seqs;

foreach my $sequence (@seqs) {
    my @splitseq = split(//,$sequence->{'seq'});
    @splitseq = reverse @splitseq;
    my $seq = join("", @splitseq);
    $seq =~ tr/AGCTagct/TCGAtcga/;
    $sequence->{'invcompseq'} = $seq;
}


open(OUT_SEQ, ">$outseqfastafile") || die "couldn't open in write mode $outseqfastafile\n";

foreach my $sequence (@seqs) {
    my $newcomment = $sequence->{'comment'};
    $newcomment =~ s/\n$/ \(invcomp\)\n/;
    print OUT_SEQ $newcomment;
    my $seq = $sequence->{'invcompseq'};
    $seq =~ s/(\w{60})/$1\n/g;
    print OUT_SEQ $seq, "\n";
}

close(OUT_SEQ);


