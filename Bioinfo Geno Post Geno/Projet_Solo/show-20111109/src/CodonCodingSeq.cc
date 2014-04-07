/*
 *  SHOW : A software to study DNA sequences using HMMs
 *  Copyright (C) 2003  Pierre NICOLAS
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

#include <config.h>

#include <cctype>
#include <iostream>
#include <fstream>
#include <cstring>
#include <cstdlib>
#include <cmath>
#include <string>
using namespace std;

#include "CodonCodingSeq.h"
#include "const.h"


#ifdef LOG
extern int loglevel;
extern ofstream logstream;
#endif /* LOG */

CodonCodingSeq::CodonCodingSeq()
{
  int i;

  _nmodal = 61;
  _nb_cc = 20;
  _borders_cc = new int* [_nb_cc];

  for(i=0; i<this->_nb_cc; i++) 
    {
      this->_borders_cc[i] = new int [2];
    }
  

  _borders_cc[0][0] = 0;
  _borders_cc[0][1] = 3;
  
  _borders_cc[1][0] = 4;
  _borders_cc[1][1] = 5;
  
  _borders_cc[2][0] = 6;
  _borders_cc[2][1] = 7;
  
  _borders_cc[3][0] = 8;
  _borders_cc[3][1] = 9;
      
  _borders_cc[4][0] = 10;
  _borders_cc[4][1] = 11;

  _borders_cc[5][0] = 12;
  _borders_cc[5][1] = 15;

  _borders_cc[6][0] = 16;
  _borders_cc[6][1] = 17;

  _borders_cc[7][0] = 18;
  _borders_cc[7][1] = 20;

  _borders_cc[8][0] = 21;
  _borders_cc[8][1] = 22;

  _borders_cc[9][0] = 23;
  _borders_cc[9][1] = 28;

  _borders_cc[10][0] = 29;
  _borders_cc[10][1] = 29;

  _borders_cc[11][0] = 30;
  _borders_cc[11][1] = 31;

  _borders_cc[12][0] = 32;
  _borders_cc[12][1] = 35;

  _borders_cc[13][0] = 36;
  _borders_cc[13][1] = 37;

  _borders_cc[14][0] = 38;
  _borders_cc[14][1] = 43;

  _borders_cc[15][0] = 44;
  _borders_cc[15][1] = 49;

  _borders_cc[16][0] = 50;
  _borders_cc[16][1] = 53;

  _borders_cc[17][0] = 54;
  _borders_cc[17][1] = 57;

  _borders_cc[18][0] = 58;
  _borders_cc[18][1] = 58;

  _borders_cc[19][0] = 59;
  _borders_cc[19][1] = 60;

}

CodonCodingSeq::~CodonCodingSeq()
{
  int i;

  for ( i = 0 ; i < _nb_cc ; i++ ) 
    {
      delete _borders_cc[i];
    }
  delete _borders_cc;

}


int CodonCodingSeq::CodingWord0(char* obsword)
{
  if (strncmp(obsword, "A1", 2) == 0) { return(0); }
  if (strncmp(obsword, "A2", 2) == 0) { return(1); }
  if (strncmp(obsword, "A3", 2) == 0) { return(2); }
  if (strncmp(obsword, "A4", 2) == 0) { return(3); }
  if (strncmp(obsword, "C1", 2) == 0) { return(4); }
  if (strncmp(obsword, "C2", 2) == 0) { return(5); }
  if (strncmp(obsword, "D1", 2) == 0) { return(6); }
  if (strncmp(obsword, "D2", 2) == 0) { return(7); }
  if (strncmp(obsword, "E1", 2) == 0) { return(8); }
  if (strncmp(obsword, "E2", 2) == 0) { return(9); }
  if (strncmp(obsword, "F1", 2) == 0) { return(10); }
  if (strncmp(obsword, "F2", 2) == 0) { return(11); }
  if (strncmp(obsword, "G1", 2) == 0) { return(12); }
  if (strncmp(obsword, "G2", 2) == 0) { return(13); }
  if (strncmp(obsword, "G3", 2) == 0) { return(14); }
  if (strncmp(obsword, "G4", 2) == 0) { return(15); }
  if (strncmp(obsword, "H1", 2) == 0) { return(16); }
  if (strncmp(obsword, "H2", 2) == 0) { return(17); }
  if (strncmp(obsword, "I1", 2) == 0) { return(18); }
  if (strncmp(obsword, "I2", 2) == 0) { return(19); }
  if (strncmp(obsword, "I3", 2) == 0) { return(20); }
  if (strncmp(obsword, "K1", 2) == 0) { return(21); }
  if (strncmp(obsword, "K2", 2) == 0) { return(22); }
  if (strncmp(obsword, "L1", 2) == 0) { return(23); }
  if (strncmp(obsword, "L2", 2) == 0) { return(24); }
  if (strncmp(obsword, "L3", 2) == 0) { return(25); }
  if (strncmp(obsword, "L4", 2) == 0) { return(26); }
  if (strncmp(obsword, "L5", 2) == 0) { return(27); }
  if (strncmp(obsword, "L6", 2) == 0) { return(28); }
  if (strncmp(obsword, "M1", 2) == 0) { return(29); }
  if (strncmp(obsword, "N1", 2) == 0) { return(30); }
  if (strncmp(obsword, "N2", 2) == 0) { return(31); }
  if (strncmp(obsword, "P1", 2) == 0) { return(32); }
  if (strncmp(obsword, "P2", 2) == 0) { return(33); }
  if (strncmp(obsword, "P3", 2) == 0) { return(34); }
  if (strncmp(obsword, "P4", 2) == 0) { return(35); }
  if (strncmp(obsword, "Q1", 2) == 0) { return(36); }
  if (strncmp(obsword, "Q2", 2) == 0) { return(37); }
  if (strncmp(obsword, "R1", 2) == 0) { return(38); }
  if (strncmp(obsword, "R2", 2) == 0) { return(39); }
  if (strncmp(obsword, "R3", 2) == 0) { return(40); }
  if (strncmp(obsword, "R4", 2) == 0) { return(41); }
  if (strncmp(obsword, "R5", 2) == 0) { return(42); }
  if (strncmp(obsword, "R6", 2) == 0) { return(43); }
  if (strncmp(obsword, "S1", 2) == 0) { return(44); }
  if (strncmp(obsword, "S2", 2) == 0) { return(45); }
  if (strncmp(obsword, "S3", 2) == 0) { return(46); }
  if (strncmp(obsword, "S4", 2) == 0) { return(47); }
  if (strncmp(obsword, "S5", 2) == 0) { return(48); }
  if (strncmp(obsword, "S6", 2) == 0) { return(49); }
  if (strncmp(obsword, "T1", 2) == 0) { return(50); }
  if (strncmp(obsword, "T2", 2) == 0) { return(51); }
  if (strncmp(obsword, "T3", 2) == 0) { return(52); }
  if (strncmp(obsword, "T4", 2) == 0) { return(53); }
  if (strncmp(obsword, "V1", 2) == 0) { return(54); }
  if (strncmp(obsword, "V2", 2) == 0) { return(55); }
  if (strncmp(obsword, "V3", 2) == 0) { return(56); }
  if (strncmp(obsword, "V4", 2) == 0) { return(57); }
  if (strncmp(obsword, "W1", 2) == 0) { return(58); }
  if (strncmp(obsword, "Y1", 2) == 0) { return(59); }
  if (strncmp(obsword, "Y2", 2) == 0) { return(60); }
  return(-1);
}


int CodonCodingSeq::Coding0(char obsletter)
{
  cerr << "CodonCodingSeq::Coding0 function must not be used when observation is codon usage"
       << endl;
  exit(1);
  return(-1);

}

char CodonCodingSeq::InvCoding0(int cod)
{
  cerr << "CodonCodingSeq::InvCoding0 function must not be used when observation is codon usage"
       << endl;
  exit(1);
  return('X');
  
}

int CodonCodingSeq::Coding(char *end_of_word, int order)
{
  cerr << "CodonCodingSeq::Coding(char*, ...) function"
       << "must not be used when observation is codon usage"
       << endl;
  exit(1);
  return(-1);
}

void CodonCodingSeq::MStepPObsPseudoOrder(double* pobs, int pseudo_order, 
					  double* count,
					  int nb_excepted_cod, 
					  int* excepted_cod)
{
  cerr << "PseudoOrder doesn't exists for CodonUsage"
       << endl;  
  exit(1);
}

void CodonCodingSeq::DeducePobsFromInfOrder(double* pobs, int order, 
					    int nb_excepted_cod, 
					    int* excepted_cod)
{
  cerr << "PseudoOrder doesn't exists for CodonUsage"
       << endl;
  exit(1);
}


void CodonCodingSeq::RandomPObsInit(double* pobs, int order, 
				    int nb_excepted_cod, 
				    int* excepted_cod)
{
  int i, j;
  double sum;
  
  if (order > 0 || nb_excepted_cod > 0) 
    {
      cerr << "order > 0 and excepted word are not allowed for Codon Usage"
	   << endl;
    }

  for ( i = 0 ; i < _nmodal ; i++ ) 
    {
      pobs[i] = (double)(rand()) / RAND_MAX;
    }
  
  for ( i = 0 ; i < _nb_cc ; i++ ) 
    {
      sum = 0;
      for ( j = _borders_cc[i][0] ; j <= _borders_cc[i][1] ; j++ )
	{
	  sum += pobs[j];
	}
      for ( j = _borders_cc[i][0] ; j <= _borders_cc[i][1] ; j++ )
	{
	  pobs[j] /= 2*sum;
	  pobs[j] += (double)1/(double)( 2*(_borders_cc[i][1] - 
					    _borders_cc[i][0] + 1));
	}
    }
}

void CodonCodingSeq::MStepPObs(double* pobs, int order, double* count)
{
  int i, j;
  double sum;
  
  if (order > 0) 
    {
      cerr << "order > 0 is not allowed for Codon Usage"
	   << endl;
    }

  for ( i = 0 ; i < _nb_cc ; i++ ) 
    {
      sum = 0;
      for ( j = _borders_cc[i][0] ; j <= _borders_cc[i][1] ; j++ )
	{
	  sum += count[j];
	}
      if (sum > 0) {
	for ( j = _borders_cc[i][0] ; j <= _borders_cc[i][1] ; j++ )
	  {
	    pobs[j] = count[j]/sum;
	  }
      }
    }
}


bool CodonCodingSeq::ReadSeq(istream &ifile, int order, 
			   int** ret_obs, int *ret_length)
{
#ifdef LOG
  if (loglevel >= 1)
    {
      logstream << "reading CodonCodingSeq" << endl 
		<< "ifile : " << ifile << endl
		<< "order : " << order << " this : " << this
		<< endl;
    }
#endif
  bool nextseq = false;
  if ( this->ReadCodonUsageFile(ifile, order, ret_obs, ret_length, &nextseq) 
       == true ) {
  } else {
    cerr << "Unknown file format for a DNA sequence in \"" << ifile 
	 << "\"" << endl;
    exit(1);
  }

  
  
#ifdef LOG
  if (loglevel >= 1) {
    logstream << "leaving ReadSeq" << endl;
  }
#endif
  

  return(nextseq);
}


bool CodonCodingSeq::ReadCodonUsageFile(istream &ifile, int order, 
				    int** ret_obs, int *ret_length, 
				    bool* nextseq)
{

  char line[LGSTRING];
  long i,ibloc;
  char c;
  char* word;
  int codword = 0;
  long length = 0;
  int **observ = NULL; /* observation for order=0 */
  int **temp;
  int *obs0; /* observation for order = 0 in one line*/

  *nextseq = false;
  
  ifile.get (c) ;
  if (c == '>') {
    ifile.putback(c);
    ifile.getline(line,LGSTRING);
  } else {
    cerr << "'>' expected" << endl ;
    cerr << c << " found" << endl;
    ifile.putback(c);
    return(false);
  }

  word = new char [256];
  while (ifile.eof () == 0 && (*nextseq == false) ) {
    
    ifile.get (c) ;
    while ( isspace(c) && !ifile.eof()) {
      ifile.get (c) ;
    }    
    
    if ( c == '>' ) {
      *nextseq = true;
    }
    
    ifile.putback(c);

    if (*nextseq == false && !ifile.eof() ) {
      
      ifile >> word;
      codword = CodingWord0(word);
      
      if(length%LGBLOC == 0) {
	if(length/LGBLOC == 0) {
	  observ = new int*[length/LGBLOC + 1];
	} else {
	  temp = new int*[length/LGBLOC + 1];
	  for(ibloc = 0 ; ibloc < (length/LGBLOC) ; ibloc++) {
	    temp[ibloc] = observ[ibloc];
	  }
	  delete observ;
	  observ = temp;
	}
	observ[length/LGBLOC] = new int[LGBLOC];
      }
      observ[length/LGBLOC][length%LGBLOC] = codword;
      length++;
    }
    
  }
  
 obs0 = new int[length];
  for(i=0 ; i<length ; i++) {
    obs0[i] = observ[i/LGBLOC][i%LGBLOC];
  }
  
  *ret_length = length;
  
  for(ibloc = 0 ; ibloc < length/LGBLOC; ibloc++) {
    delete observ[ibloc];
  }
  delete observ;
  
  *ret_obs = obs0;
  
  delete word;
  return(true);
}

