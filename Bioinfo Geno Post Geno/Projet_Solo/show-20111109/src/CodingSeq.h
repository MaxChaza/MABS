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

#ifndef CODINGSEQ_H
#define CODINGSEQ_H

#include <iostream>
#include <fstream>
using namespace std;


class CodingSeq
{
  
protected :

  int _nmodal;

 virtual void DeducePobsFromInfOrder(double* pobs, int order, 
				     int nb_excepted_cod, 
						 int* excepted_cod);
public :

  virtual ~CodingSeq();
 
  int Coding(int *end_of_word, int order);

  virtual int Coding(char *end_of_word, int order);

  int SuffixCod(int cod);
  int SuffixCod(int cod, int prefix_lgth);

  int LWord(int cod);

  virtual int Coding0(char obsletter) = 0;

  virtual char InvCoding0(int cod) = 0;

  bool ReadFasta(istream &ifile, int order, 
		 int** ret_obs, int *ret_length, bool *nextseq);

  virtual bool ReadSeq(istream &ifile, int order, 
		       int** ret_obs, int *ret_length) = 0;
  
  virtual void RandomPObsInit(double* pobs, int order, 
			      int nb_excepted_cod, 
			      int* excepted_cod);

  virtual void MStepPObs(double* pobs, int order, double* count);

  virtual void MStepPObsPseudoOrder1(double* pobs, int pseudo_order, 
				     double* count, 
				     int nb_excepted_cod,  
				     int* excepted_cod);
  
  virtual void MStepPObsPseudoOrder(int pseudo_order, int order, 
				    double* pobs, 
				    double* count,
				    int nb_excepted_cod, 
				    int* excepted_cod);



  virtual void DeducePobsFromInfOrder(double* pobs, int pseudo_order,
				      int order, 
				      int nb_excepted_cod, 
				      int* excepted_cod);
  
  int Nmodal();
  
  virtual int TypeSeq() = 0;

  virtual char* SuffixSeq() = 0;
};

inline int CodingSeq::Nmodal()
{
  return(_nmodal);
}

#endif /* CODINGSEQ_H */
