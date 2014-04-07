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

#ifndef HIDDEN_MARKOV_MODEL_H
#define HIDDEN_MARKOV_MODEL_H


#include<iostream>
#include<fstream>
using namespace std;

#include "HmmTransitions.h"
#include "HmmObservations.h"
#include "SequenceSet.h"
#include "ObsSequence.h"
#include "Param_EM.h"
#include "Param_Vit.h"

#include "Output.h"


class HiddenMarkovModel
{
private:
  char* _model_filename;
  char** _state_ids;
  SequenceSet *_seqset;

  //ObsSequence **_seqs_frag0;
  //ObsSequence **_seqs;
  ObsSequence ***_seqs_frag0;
  ObsSequence **_seqs;
  int _nb_seqs;
  int _nb_frag;
  
  int _nb_state;
  int _dim_obs;

  double *_ps_init;
  HmmTransitions ** _transitions;
  HmmObservations *** _observations;
  
  
  bool _is_bounded;
  int _bound_state;

  // used in M step
  //HmmObservations * _primary_obs;
  //HmmObservations * _secondary_obs;
  
  // used in E step
  double **_p_predic;      // P( St = u | Y_0^t-1 )
  double **_p_filtra;      // P( St = u | Y_0^t )
  double **_p_smooth;      // P( St = u | Y_0^lg-1 ) 
  
  long _estep_segment;
  long _estep_overlap;

  long _vit_segment;
  long _vit_overlap;


  
protected: 
  void RestrictedEStep(long estep_start, 
					  long estep_length,
					  long count_lmargin, 
					  long count_length,
					  Output* output);

  double RestrictedLogl(long estep_start, 
					   long estep_length,
					   long count_lmargin, 
					   long count_length);
  void Link();
  void ObsCheck();
  void PsInitCheck();

   void SetSeqFrag(int ifrag);
  inline void SetSeqPos(long ipos);
  void RestrictedViterbi(long viterbi_start,
					    long viterbi_length,
					    long store_lmargin,
					    long store_length,
					    int* path,
					    int** phi);
  void AllocPVect();
  void DeletePVect();
  double EStep(bool loglikelihood, double *log_likelihoods);
  void MStep();
  void PrintStatesSimulation(int* hidden_states_path,
					       long lg);
  
public:
  HiddenMarkovModel(char* model_filename, 
				       SequenceSet *seqset);

  HiddenMarkovModel(const HiddenMarkovModel &);

  virtual ~HiddenMarkovModel();
  void RandInitPObs();
  void TiedInitPObs();
  void Print(ostream &ofile);
  void PrintEOutput(Output* output, Param_EM* param_em);
  void Viterbi(Param_Vit* param_vit);
  double EMfit(int niter, Param_EM* param_em);
  double EMfit(int niter, double eps, ostream* ofile, Param_EM* param_em);
  map<string, int>* MapStateById();
  void AllocOutput(long int * lgmax, int*** join_idnum_to, 
				      int** join_nb_to, 
				      int*** join_count, 
				      int** count,
				      int *nb_states, Param_EM* param_em);
  void PrintViterbi(int* path, int num_frag);
  void HmmSimulation(long lg);
  //void PrintObservationSimulation(int* observation, long lg, int iobs);
  bool Complete();
 
};
 

inline void HiddenMarkovModel::SetSeqPos(long ipos)
{
  int iseq;
  
  for( iseq = 0 ; iseq < _nb_seqs ; iseq++ )
    {
      _seqs[iseq]->SetPos(ipos);
    }
}



#endif /* HIDDEN_MARKOV_MODEL_H */
