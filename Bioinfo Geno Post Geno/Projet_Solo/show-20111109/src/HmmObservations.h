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

#ifndef HMMOBSERVATIONS_H
#define HMMOBSERVATIONS_H

#include <iostream>
#include <fstream>
#include <string>
#include <list>
#include <map>
using namespace std;

#include "ObsSequence.h"
#include "SequenceSet.h"

class HmmObservations
{
protected :
  int _nmodal;  // number of modalities 

  int _nb_obs;
  double *_pobs;  // array containing P( Yt = obs | St = u )
  double *_count; 
  // array containing sum [ 1_{Yt = obs} P( St = u | Y_0^lg ) ] 

  char *_label;
  char *_state_id;
  char *_seq_id;

  char* _tied_to;
  int _order;      
  // Markov order of the obs chain conditionnaly on hidden chain
  int _pseudo_order; 
  // in the case where length of excepted word is equal to initial _order + 2
  // (and not _order + 1) the _order is set to length - 1 and _pseudo_order 
  // to the initial _order


  int _type;  // known types are 0, 1, 2, 3, 4 

  /*
   *                   TYPES PROPERTIES
   * 0: fixed (not estimated)
   * 1: estimated
   * 2: identical-tied to one other
   * 3: reverse_complementary-tied to one other
   * 4: self complementary (estimated)
   */

  ObsSequence **_seq_frag0;
  ObsSequence *_seq;

  HmmObservations *_primary_obs;
  // if not tied == NULL 

  list<char*> _excepted;
  // forbidden obs words

  int _nb_excepted_cod;
  // number of forbidden obs words (after coding)

  int *_excepted_cod;
  // forbidden encoded obs words

  CodingSeq *_coding; 

private :
  
  bool ReadAndCheckPObs(istream &model_stream);  
  
public :
  
  ~HmmObservations();
  
  HmmObservations(istream &ifile, int nmodal);
  HmmObservations(HmmObservations & templ);
  HmmObservations(HmmObservations & templ, bool bounded);
  HmmObservations(istream &model_stream, 
				   char *state_id, char *seq_id,
				   SequenceSet *seqset);
  int Class();
  double PObs(long ipos);
  double PObs();
  void Count(long ipos, double pb);
  void Count(double pb);
  void ResetCount();
  void Print(ostream &ifile);
  void PreMStep();
  void MStep();
  void PostMStep();
  char* Label();
  char* ObsSeqId();
  void RandInitPObs();
  void TiedInitPObs(); 
  void SetFrag(int ifrag);
  ObsSequence** Seq();
  void Link(map<string, HmmObservations* >* 
			     map_obs_by_id, SequenceSet* seqset);
  void ObsSimulation(int ipos, int* path);
  inline bool Complete();
  void PrintObsSimulation(int* observations_path, 
					   long lg, int id_num);
  
  int Order();
};


inline bool HmmObservations::Complete()
{
  if(_pobs == NULL) 
    {
      return(false);
    } else {
      return(true);
    }
}
inline int HmmObservations::Class()
{
  return(0); // HmmObservations
}

inline int HmmObservations::Order()
{
  return(_seq->Order());
}

inline double HmmObservations::PObs(long ipos)
{
 return(_pobs[_seq->Obs(ipos)]);
}

inline double HmmObservations::PObs()
{
  //cerr << "_seq " <<  _seq << " _seq->Obs() " << _seq->Obs() << endl;
  return(_pobs[_seq->Obs()]);
}

inline void HmmObservations::Count(long ipos, double pb)
{
  _count[_seq->Obs(ipos)] += pb;
}

inline void HmmObservations::Count(double pb)
{
  if (pb < 0) {
    cout << "Warning pb = " << pb << " in " << _state_id << endl;
  }
  _count[_seq->Obs()] += pb;
}

#endif  /* HMMOBSERVATIONS_H */
