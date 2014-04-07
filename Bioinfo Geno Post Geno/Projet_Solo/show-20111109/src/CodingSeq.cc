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
#include <string>
using namespace std;

#include "CodingSeq.h"
#include "PseudoOrderLoglMax.h"
#include "const.h"

#ifdef LOG
extern int loglevel;
extern ofstream logstream;
#endif /* LOG */


int CodingSeq::Coding(int  *end_of_word, int order)
{
  int cod;
  int i;

  cod = *end_of_word;

  for(i=1 ; i<=order ; i++)
    {
      if(*(end_of_word - i) == -1) return(cod);
      else
	{
	  cod += *(end_of_word - i)*(int)pow((double)_nmodal,i) + (int)pow((double)_nmodal,i);
	}
    }
  return(cod);
}

int CodingSeq::LWord(int cod)
{
  int lword;

  // cod >= (_nmodal^lword - 1) / (_nmodal - 1) - 1
  // i.e. lword = floor{ log[(cod + 1)(_nmodal - 1) + 1] / log[_nmodal] }

  lword = (int)floor( log((double) ( cod + 1 ) * ( _nmodal - 1 ) + 1 ) / log((double)_nmodal )  
		      + 0.00001);
  
  //cout << "cod " << cod << " lword " << lword << endl;

  return lword;
}


int CodingSeq::Coding(char  *end_of_word, int order)
{
  int cod;
  int i;

  cod = this->Coding0(*end_of_word);
  for(i=1 ; i<=order ; i++)
    {
      if(this->Coding0(*(end_of_word - i)) == -1) return(cod);
      else
	{
	  cod += this->Coding0(*(end_of_word - i))*
	    (int)pow((double)_nmodal,i) + (int)pow((double)_nmodal,i);
	}
    }
  return(cod);
}

int CodingSeq::SuffixCod(int cod)
{
  // return the code of the word corresponding to the word of code cod
  // whitout its first letter
  // ex if cod = TGA return code of GA

  int codbis;
  int codsuffixbis;
  int codsuffix;
  int lword = this->LWord(cod);

  // cod >= (_nmodal^lword - 1) / (_nmodal - 1) - 1
  codbis = cod - (int) ((pow((double)_nmodal, lword) - 1) / (_nmodal - 1) - 1);

  //cout << codbis << endl;

  codsuffixbis = (int)fmod(codbis, pow((double)_nmodal, lword-1));

  codsuffix = codsuffixbis + (int) ((pow((double)_nmodal, lword-1) - 1) / (_nmodal - 1) - 1);
  //cout << codsuffix << endl;
    
    
  return(codsuffix);
}

int CodingSeq::SuffixCod(int cod, int prefix_lgth) 
{
  int codsuffix = cod;
  for (int i = 0 ; i < prefix_lgth ; i++) {
    codsuffix = this->SuffixCod(codsuffix);
  }
  return(codsuffix);
}


void CodingSeq::DeducePobsFromInfOrder(double* pobs, int order, 
				       int nb_excepted_cod, 
				       int* excepted_cod)
{
  int iobs;
  double sum;
  int i;
  
  int nb_obs;
  int nb_obs_inf;

  nb_obs = (int) ((pow((double)_nmodal, order+2) - 1) / (_nmodal - 1) - 1);
  nb_obs_inf = (int) ((pow((double)_nmodal, order+1) - 1) / (_nmodal - 1) - 1);

  //cout << "order " << order << endl;
  //cout << "nb_obs " << nb_obs << endl;
  //cout << "nb_obs_inf " << nb_obs_inf << endl;

  for ( int iobs = nb_obs_inf ; iobs < nb_obs ; iobs++ ) 
    {
      pobs[iobs] = pobs[this->SuffixCod(iobs)];
    }		      
  for( iobs = 0 ; iobs < nb_excepted_cod ; iobs++ ) 
    {
      pobs[excepted_cod[iobs]] = 0;
    }
 
  // rescale
  iobs = 0;
  while ( iobs < nb_obs - _nmodal )
    {
      sum = 0;
      for ( i = 0 ; i < _nmodal ; i++ ) 
	{
	  sum += pobs[iobs+i];
	}
      for ( i = 0 ; i < _nmodal ; i++ ) 
	{
	  pobs[iobs+i] /= sum;
	  //cout << pobs[iobs+i]  << " x ";
	}
      //cout << endl;
      iobs += _nmodal;
    }		  
  
}

void CodingSeq::DeducePobsFromInfOrder(double* pobs, 
				       int pseudo_order, 
				       int order, 
				       int nb_excepted_cod, 
				       int* excepted_cod)
{
  int iorder;
  for (iorder = pseudo_order+1 ; iorder <= order ; iorder++) {
    this->DeducePobsFromInfOrder(pobs, iorder, nb_excepted_cod, excepted_cod);
  }
}


void CodingSeq::MStepPObsPseudoOrder1(double* pobs, int order, 
				     double* count,
				     int nb_excepted_cod, 
				     int* excepted_cod)
{
  //cout << "CodingSeq::MStepPObsPseudoOrder" << endl;
  
  int pre_nb_obs;
  int i, j, k;
  int iword;
  double sum;

  pre_nb_obs = 0;
  
  for( i = 0 ; i < order - 1 ; i++ )
    {
      pre_nb_obs += (int)pow((double)_nmodal, i+1);
    }
  
  iword = 0;
  while ( iword < pre_nb_obs )
    {
      sum = 0;
      for ( i = 0  ; i < _nmodal ; i++ )
	{
	  sum += count[iword+i];	  
	} 
      if (sum > 0) {
	for ( i = 0  ; i < _nmodal ; i++ )
	  {
	    pobs[iword+i] = count[iword+i] / sum;	  
	  } 
      }
      iword += _nmodal;
    }

  double *t_pobs;
  double *t_count;
  int *excepted;
  int *t_excepted;
  int nb_suffix;
  int t_i;

  pre_nb_obs += (int)pow((double)_nmodal, order);
  nb_suffix = (int)pow((double)_nmodal, order - 1);
  
  //cout << "nb_suffix " << nb_suffix << endl;
  //cout << "pre_nb_obs " << pre_nb_obs << endl;
  excepted  = new int [ (int) ( (pow((double)_nmodal, order+2) - 1) / (_nmodal - 1) - 1) ];
  t_excepted = new int [_nmodal*_nmodal];
  t_count = new double [_nmodal*_nmodal];
  t_pobs = new double [_nmodal*_nmodal];
  
  for ( i = 0 ; i < (int) ( (pow((double)_nmodal, order+2) - 1) / 
			    (_nmodal - 1) - 1) ; i++ ) 
    {
      excepted[i] = 0;
    }
  for (i = 0 ; i < nb_excepted_cod  ; i++) 
    {
      excepted[excepted_cod[i]] = 1;
    }

  for (i = 0 ; i < nb_suffix ; i++) 
    {
      t_i = 0;
      for (j = 0 ; j < _nmodal ; j++) 
	{
	  for (k = 0 ; k < _nmodal ; k++) 
	    {
	      //cout << t_i << " " << pre_nb_obs + (j*nb_suffix + i)*_nmodal + k 
	      //<< endl;
	      t_excepted[t_i] = excepted[pre_nb_obs + (j*nb_suffix + i)*_nmodal 
					+ k];
	      t_count[t_i] = count[pre_nb_obs + (j*nb_suffix + i)*_nmodal + k];
	      t_i++;
	    }
	}
      
      //LoglMaximisation(_nmodal, t_excepted,  t_count, t_pobs);
      t_i = 0;
      for (j = 0 ; j < _nmodal ; j++) 
	{
	  for (k = 0 ; k < _nmodal ; k++) 
	    {
	      pobs[pre_nb_obs + (j*nb_suffix + i)*_nmodal + k] = t_pobs[t_i];
	      cout << t_i << " " << pre_nb_obs + (j*nb_suffix + i)*_nmodal + k << " - " << t_pobs[t_i] << endl;
	      t_i++;
	    }
	}
    }
  
  delete [] excepted;
  delete [] t_excepted;
  delete [] t_count;
  delete [] t_pobs;
}


void CodingSeq::MStepPObsPseudoOrder(int pseudo_order, int order, 
				     double* pobs, 
				     double* count,
				     int nb_excepted_cod, 
				     int* excepted_cod)
{
  int *excepted_array;
  int nb_obs = (int) ( (pow((double)_nmodal, order+2) - 1) / (_nmodal - 1) - 1);

  excepted_array  = new int [ nb_obs ];
  
  for (int i = 0 ; i < nb_obs  ; i++) 
    {
      excepted_array[i] = 0;
    } 
  
  for (int i = 0 ; i < nb_excepted_cod  ; i++) 
    {
      cout << "M except " << excepted_cod[i] << endl;
      excepted_array[excepted_cod[i]] = 1;
    }

  cout << "toto0 " << excepted_array[76] << endl;
  LoglMaximisation(this, pseudo_order, order, excepted_array,  count, pobs);

  delete [] excepted_array;
}


bool CodingSeq::ReadFasta(istream &ifile, int order, int** ret_obs, int *ret_length, bool *nextseq)
{

  char line[LGSTRING];
  long i,ibloc;
  char c;
  int codletter = 0;
  long length = 0;
  int **observ = NULL; /* observation for order=0 */
  int **temp;
  int *obs0; /* observation for order = 0 in one line*/
  int j;

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

  while (ifile.eof () == 0 && (*nextseq == false) ) {
    
    ifile.get (c) ;
    if ( c == '>' ) {
      *nextseq = true;
      ifile.putback(c);
    }
    if (!isspace(c) && (*nextseq == false)) {
      codletter = Coding0(c);
      
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
      observ[length/LGBLOC][length%LGBLOC] = codletter;
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
  
  *ret_obs = new int[length];
  for(j=0 ; j<=order ; j++) {
    (*ret_obs)[j] = Coding(obs0 + j, j);
  }
  
  for(j=order + 1 ; j<length ; j++) {
    (*ret_obs)[j] = Coding(obs0 + j, order);
  }
  
  delete obs0;
  return(true);
}

void CodingSeq::RandomPObsInit(double* pobs, int order, int nb_excepted_cod, 
			       int* excepted_cod)
{
  int nb_obs;
  int i, j, excepted;
  double sum;

  //cerr << "RandomPObsInit " << endl;

  nb_obs = 0;
  
  for( i = 0 ; i <= order ; i++ )
    {
      nb_obs += (int)pow((double)_nmodal, i+1);
    }
  

  pobs[-1] = 1; // corresponding to unknown observation

  for ( i = 0 ; i < nb_obs ; i++ ) 
    {
      pobs[i] = (double)(rand()) / RAND_MAX;
    }
  
  i = 0;
  while ( i < nb_obs )
    {
      sum = 0;
      for( j = 0 ; j < _nmodal ; j++ )
	{
	  sum += pobs[i+j];
	}
      for( j = 0 ; j < _nmodal ; j++ )
	{
	  pobs[j+i] /= 2*sum;
	  pobs[j+i] += (double)1/(double)(2*_nmodal);
	}      
      i += _nmodal;
    }  

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


void CodingSeq::MStepPObs(double* pobs, int order, double* count)
{
  int nb_obs;
  int iword, i;
  double sum;

  //cerr << "MStepPObs " << endl;

  nb_obs = 0;
  
  for( i = 0 ; i <= order ; i++ )
    {
      nb_obs += (int)pow((double)_nmodal, i+1);
    }
  
  iword = 0;
  while ( iword < nb_obs )
    {
      sum = 0;
      for ( i = 0  ; i < _nmodal ; i++ )
	{
	  sum += count[iword+i];	  
	} 
      if (sum > 0) {
	for ( i = 0  ; i < _nmodal ; i++ )
	  {
	    pobs[iword+i] = count[iword+i] / sum;	  
	    //cerr << pobs[iword+i] << " ";
	  } 
      }
      iword += _nmodal;
    }
  //cerr << endl;

}

CodingSeq::~CodingSeq()
{
}
