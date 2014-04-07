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

#ifndef DNACODINGSEQ_H
#define DNACODINGSEQ_H

#include <cstring>
using namespace std;

#include "CodingSeq.h"


class DnaCodingSeq : public CodingSeq
{
  
private :

public :
  DnaCodingSeq();
  int Coding0(char obsletter);
  char InvCoding0(int cod);
  bool ReadSeq(istream &ifile, int order, 
			     int** ret_obs, int *ret_length);
 
  bool ReadGenBank(istream &ifile, int order, 
				 int** ret_obs, int *ret_length);
  int TypeSeq();
  char* SuffixSeq();
};

inline int DnaCodingSeq::TypeSeq() 
{
  return(0);
}


inline char* DnaCodingSeq::SuffixSeq()
{
  char* ret_type;
  ret_type = new char [strlen("dna")+1];
  strcpy(ret_type, "dna");

  return(ret_type);
}


#endif /* DNACODINGSEQ_H */
