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

#ifndef CODONCODINGSEQ_H
#define CODONCODINGSEQ_H


#include <cstring>
using namespace std;

#include "CodingSeq.h"


class CodonCodingSeq : public CodingSeq
{
  
private :
  int** _borders_cc;
 int _nb_cc; 
 
public :
  CodonCodingSeq();
  ~CodonCodingSeq();
  int Coding0(char obsletter);
  int Coding(char *end_of_word, int order);
 char InvCoding0(int cod);
  bool ReadSeq(istream &ifile, int order, 
			     int** ret_obs, int *ret_length);
  bool ReadCodonUsageFile(istream &ifile, int order, 
					  int** ret_obs, int *ret_length,
					  bool* nextseq);
  
  int TypeSeq();
  char* SuffixSeq();
  int CodingWord0(char* obsword);
  void RandomPObsInit(double* pobs, int order, 
				 int nb_excepted_cod, 
				 int* excepted_cod);

  void MStepPObs(double* pobs, int order, double* count);

  void MStepPObsPseudoOrder(double* pobs, int pseudo_order, 
				       double* count,
				       int nb_excepted_cod, 
				       int* excepted_cod);

  void DeducePobsFromInfOrder(double* pobs, int order, 
					 int nb_excepted_cod, 
					 int* excepted_cod);
};

inline int CodonCodingSeq::TypeSeq() 
{
  return(2);
}


inline char* CodonCodingSeq::SuffixSeq()
{
  char* ret_type;
  ret_type = new char [strlen("codonusage")+1];
  strcpy(ret_type, "codonusage");

  return(ret_type);
}


#endif /* CODONCODINGSEQ_H */
