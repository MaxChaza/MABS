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
#include <cmath>
#include <cstdlib>
#include <string>
using namespace std;

#include "HomologCodingSeq.h"
#include "const.h"


#ifdef LOG
extern int loglevel;
extern ofstream logstream;
#endif /* LOG */

HomologCodingSeq::HomologCodingSeq()
{
  _nmodal = 7;
  /*
    _nmodal = 7 -> 
    0: first pos in codon (+) strand
    1: second "
    2: third  "
    3: no similarity 
    4: first pos in codon (-) strand
    5: second "
    6: third  "
  */
}


int HomologCodingSeq::Coding0(char obsletter)
{
  int codchar;
  
  switch(obsletter)
    {
      case '0' : codchar = 0; break;
      case '1' : codchar = 1; break;
      case '2' : codchar = 2; break;
      case '3' : codchar = 3; break;
      case '4' : codchar = 4; break;
      case '5' : codchar = 5; break;
      case '6' : codchar = 6; break;
      default : codchar = -1; break;  
    }
    
  return(codchar);

}

char HomologCodingSeq::InvCoding0(int cod)
{
  char ret_char;

  switch(cod)
    {
      case 0 : ret_char = '0'; break;
      case 1 : ret_char = '1'; break;
      case 2 : ret_char = '2'; break;
      case 3 : ret_char = '3'; break;
      case 4 : ret_char = '4'; break;
      case 5 : ret_char = '5'; break;
      case 6 : ret_char = '6'; break;
      default : ret_char = 'X'; break;  
    }
    
  return(ret_char);
  
}

bool HomologCodingSeq::ReadSeq(istream &ifile, int order, 
			   int** ret_obs, int *ret_length)
{
#ifdef LOG
  if (loglevel >= 1)
    {
      logstream << "inside constructor DnaObsSequence" << endl 
		<< "ifile : " << ifile <<endl
		<< "order : " << order << " this : " << this
		<< endl;
    }
#endif

  bool nextseq;
  
  if ( this->ReadFasta(ifile, order, ret_obs, ret_length, &nextseq) == true ) {
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


void HomologCodingSeq::RandomPObsInit(double* pobs, int order, 
				      int nb_excepted_cod, 
				      int* excepted_cod)
{
  
  int nb_obs;
  int i, j, excepted;
  double sum;

  cerr << "RandomPObsInit " << endl;
  
  if (order == 0) {
    this->CodingSeq::RandomPObsInit(pobs, order, 
				    nb_excepted_cod, 
				    excepted_cod);
    return;
  }

  if (order > 1) {
    cerr << "order > 1 not implemented for homologies data" << endl;
    exit(1);
  }

  nb_obs = 0;
  
  for( i = 0 ; i <= order ; i++ )
    {
      nb_obs += (int)pow((double)_nmodal, i+1);
    }
  

  pobs[-1] = 1; // corresponding to unknown observation

  for ( i = 0 ; i < _nmodal ; i++ ) 
    {
      pobs[i] = (double)(rand()) / RAND_MAX;
    }

  for( i = _nmodal ; i < nb_obs ; i++ ) 
    {
      pobs[i] = 0;
    }
  
  pobs[16] = 1; // 1 -> 2
  pobs[21] = 1; // 2 -> 0 

  pobs[8] = (double)(rand()) / RAND_MAX;
  pobs[10] = (double)(rand()) / RAND_MAX;
  sum = pobs[8] + pobs[10];
  pobs[8] = 1.0/4.0 + (1.0/2.0)*(pobs[8]/sum);  // 0 -> 1
  pobs[10] = 1.0/4.0 + (1.0/2.0)*(pobs[10]/sum);  // 0 -> 3

  pobs[29] = (double)(rand()) / RAND_MAX;
  pobs[31] = (double)(rand()) / RAND_MAX;
  pobs[32] = (double)(rand()) / RAND_MAX;
  sum = pobs[29] + pobs[31] + pobs[32];
  pobs[29] = 1.0/6.0 + (1.0/3.0)*(pobs[29]/sum);  // 3 -> 1
  pobs[31] = 1.0/6.0 + (1.0/3.0)*(pobs[32]/sum);  // 3 -> 3
  pobs[32] = 1.0/6.0 + (1.0/3.0)*(pobs[33]/sum);  // 3 -> 4

  pobs[41] = 1; // 4 -> 6
  pobs[54] = 1; // 6 -> 5

  pobs[45] = (double)(rand()) / RAND_MAX;
  pobs[46] = (double)(rand()) / RAND_MAX;
  sum = pobs[45] + pobs[46];
  pobs[45] = 1.0/4.0 + (1.0/2.0)*(pobs[45]/sum);  // 5 -> 3
  pobs[46] = 1.0/4.0 + (1.0/2.0)*(pobs[46]/sum);  // 5 -> 4

  i = 0;
  while ( i < nb_obs )
    {
      sum = 0;
      for( j = 0 ; j < _nmodal ; j++ )
	{
	  for(excepted = 0 ; excepted < nb_excepted_cod ; excepted++)
	    {
	      if ( excepted_cod[excepted] == i+j )
		{
		  pobs[i+j] = 0;
		}
	    }
	  sum += pobs[i+j];
	}      
      for( j = 0 ; j < _nmodal ; j++ )
	{
	  pobs[j+i] /= sum;
	}      
      i += _nmodal;
    }  

}

void HomologCodingSeq::MStepPObs(double* pobs, int order, double* count)
{
  double sum;
  int i;

  if (order == 0) {
    this->CodingSeq::MStepPObs(pobs, order, count);
    return;
  }
  
  if (order > 1) {
    cerr << "order > 1 not implemented for homologies data" << endl;
    exit(1);
  }

  sum = 0;
  for ( i = 0 ; i < _nmodal ; i++ ) 
    {
      sum += count[i];
    }
  for ( i = 0 ; i < _nmodal ; i++ ) 
    {
      pobs[i] = count[i] / sum;
    }

  sum = count[8] + count[10];
  if (sum == 0) 
    {
      pobs[8] = 1;
      pobs[10] = 0;
    } else {
      pobs[8] = count[8] / sum;
      pobs[10] = count[10] / sum;
    }
  
  sum = count[45] + count[46];
  if (sum == 0) 
    {
      pobs[46] = 1;
      pobs[45] = 0;
    } else {
      pobs[45] = count[45] / sum;
      pobs[46] = count[46] / sum;
    }

  sum = count[30] + count[31] + count[32];
  if (sum == 0) 
    {
      pobs[31] = 1;
      pobs[29] = 0;
      pobs[32] = 0;
    } else {
      pobs[29] = count[29] / sum;
      pobs[31] = count[31] / sum;
      pobs[32] = count[32] / sum;
    }    
}
