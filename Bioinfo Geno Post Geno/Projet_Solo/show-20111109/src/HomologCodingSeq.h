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

#ifndef HOMOLOGCODINGSEQ_H
#define HOMOLOGCODINGSEQ_H


#include <cstring>
using namespace std;

#include "CodingSeq.h"

class HomologCodingSeq : public CodingSeq
{
  
private :

public :
  HomologCodingSeq();
  int Coding0(char obsletter);
  char InvCoding0(int cod);
  bool ReadSeq(istream &ifile, int order, 
			  int** ret_obs, int *ret_length);

  int TypeSeq(); 
  char* SuffixSeq();
  void RandomPObsInit(double* pobs, int order, 
					 int nb_excepted_cod, 
					 int* excepted_cod);

  void MStepPObs(double* pobs, int order, double* count);
};


inline int HomologCodingSeq::TypeSeq() 
{
  return(1);
}


inline char* HomologCodingSeq::SuffixSeq()
{
  char* ret_type;
  ret_type = new char [strlen("homolog")+1];
  strcpy(ret_type, "homolog");

  return(ret_type);
}

#endif /* HOMOLOGCODINGSEQ_H */
