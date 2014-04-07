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

#ifndef HMMTRANSITIONS_H
#define HMMTRANSITIONS_H

#include <iostream>
#include <fstream>
#include <map>
#include <string>
using namespace std;

class HmmTransitions
{

private:
  char* _state_id;

  int _nb_trans;
  int _nb_trans_type0;
  int _nb_trans_type1;
  
  char* _label; // necessary only when the transition is referenced
  // as primary by one other

  HmmTransitions* _primary_trans;
  char* _tied_to; // NULL if _tied is false
  bool _tied; // true only if the transition is defined as
  // tied to one other (called primary). 
  // In this case the primary transition is identified by a label 
  // _tied_to and primary._tied must be false

  char** _to_state_id;
  int* _to_state;
  double* _ptrans;
  double* _count;
  

public:
  HmmTransitions(istream &ifile, char* state_id);
  HmmTransitions(const HmmTransitions & templ);
  virtual ~HmmTransitions();
  void ResetCount();
  void Print(ostream &ofile);
  void PreMStep();
  void MStep();
  void PostMStep();  
  char* Label();
  void Link(map<string, int> *map_state_by_id,
			    map<string, HmmTransitions* > *map_trans_by_id);

  inline int ToState(int itrans);
  inline int NbTrans();
  inline double PTrans(int itrans);
  inline void Count(int i, double p);
  inline double SumCount();
  int Simulation();
};

inline double HmmTransitions::SumCount()
{
  double sum = 0;
  for (int itrans=0 ; itrans < _nb_trans ; itrans++)
    {
      sum += _count[itrans];
    }
  return(sum);
}

inline int HmmTransitions::NbTrans()
{
  return(_nb_trans);
}

inline int HmmTransitions::ToState(int itrans)
{
  return(_to_state[itrans]);
}

inline double HmmTransitions::PTrans(int itrans)
{
  return(_ptrans[itrans]);
}

inline void HmmTransitions::Count(int i, double p)
{
  //cerr << "count state: " << _state_id << " p " << p << endl;
  _count[i] += p;
}

#endif /* HMMTRANSITIONS_H */
