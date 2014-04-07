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

#ifndef OBSSEQUENCE_H
#define OBSSEQUENCE_H

#include <iostream>
using namespace std;

#include "CodingSeq.h"

class ObsSequence
{
protected :
  
  int* _obs;
  int _length;
  int _nmodal;
  char* _name;
  long _ipos;
  CodingSeq* _coding;
  int _order;
  bool _yet_another_seq_in_the_same_file;

public :
  
  ObsSequence(char* file, int order, CodingSeq* coding, 
			   istream &ifile);
  ObsSequence(ObsSequence *seq, int order);
  virtual ~ObsSequence();
  inline int Obs(long t);
  inline int Obs();
  inline void SetPos(long t);
  inline int Length();
  inline char* Name();
  inline void Print(ostream &ofile, long ipos);
  inline int Pos();
  inline int Order();
  inline CodingSeq* CodingSeqObject();
  inline bool YetAnotherSeqInTheSameFile(); 
};

inline CodingSeq* ObsSequence::CodingSeqObject()
{
  return(_coding);
}

inline int ObsSequence::Order()
{
  return(_order);
}

inline int ObsSequence::Obs(long t)
{
  return(_obs[t]);
}

inline int ObsSequence::Obs()
{
  return(_obs[_ipos]);
}


inline int ObsSequence::Length()
{
  return(_length);
}

inline char* ObsSequence::Name()
{
  return(_name);
}


inline void ObsSequence::SetPos(long t)
{
  _ipos = t;
}

inline void ObsSequence::Print(ostream &ofile, long ipos)
{
  ofile << "ipos" << ipos << " " << _obs[ipos] << endl;
}


inline int ObsSequence::Pos()
{
  return(_ipos);
}

inline bool ObsSequence::YetAnotherSeqInTheSameFile()
{
  return(_yet_another_seq_in_the_same_file);
}


#endif /* OBSSEQUENCE_H */
