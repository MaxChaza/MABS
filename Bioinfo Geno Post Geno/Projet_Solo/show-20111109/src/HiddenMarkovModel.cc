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

#include <list>
#include <cmath>
#include <cstdlib>
#include <cstring>
using namespace std;

#include "HiddenMarkovModel.h"
#include "HmmObservations.h"
#include "reading.h"
#include "int2char.h"
#include "shortfilename.h"
#include "const.h"


#ifdef LOG
extern int loglevel;
extern ofstream logstream;
#endif /* LOG */

double HiddenMarkovModel::EMfit(int niter, Param_EM* param_em)
{
  int iter;
  double log_likelihood;
  double *log_likelihoods;

  log_likelihoods = new double [_nb_frag];
  
 
  _estep_segment = param_em->EStepSegment();
  _estep_overlap = param_em->EStepOverlap();
  

  if ( (_estep_segment == 0) || (_estep_overlap == 0) ) 
    {
      cerr << "em parameters not full in description file"
	   << endl;
      exit(1);
    }

  this->AllocPVect();

  for ( iter = 0 ; iter < niter ; iter++ )
    {
      this->EStep(false, log_likelihoods);
      this->MStep();      
    }
  log_likelihood = this->EStep(true, log_likelihoods);

  this->DeletePVect();
  
  delete [] log_likelihoods;

  return(log_likelihood);
}

double HiddenMarkovModel::EMfit(int niter, double eps, ostream* ofile, Param_EM* param_em)
{
  int iter;
  double log_likelihood = 1;
  double old_log_likelihood = 1;
  double diff;
  bool stop;
  double *log_likelihoods;
  int ifrag;

  log_likelihoods = new double [_nb_frag];
 

  _estep_segment = param_em->EStepSegment();
  _estep_overlap = param_em->EStepOverlap();
 

  if ( (_estep_segment == 0) || (_estep_overlap == 0) ) 
    {
      cerr << "incomplete em parameters in description file"
	   << endl;
      exit(1);
    }

  this->AllocPVect();

  iter = 0;
  stop = false;
  while ( (iter < niter) && (stop == false) )
    {
      old_log_likelihood = log_likelihood;
      log_likelihood = this->EStep(true, log_likelihoods);
      diff =  log_likelihood - old_log_likelihood;
      if ( ofile != NULL )
	{
	  if ( iter > 0 ) 
	    {
	      *ofile << "iter " << iter 
		     << " logl " << log_likelihood 
		     << " diff " << diff << endl; 
	      //cout << "TRACE iter " << iter 
	      //<< " logl " << log_likelihood 
	      //   << " diff " << diff << endl; 
	      //cout << "*****START OF MODEL******" << endl;
	      //this->Print(cout);
	      //cout << "*****END OF MODEL******" << endl;
	    } else {
	      *ofile << "iter " << iter 
		     << " logl " << log_likelihood << endl;
	      //cout << "TRACE iter " << iter 
	      //   << " logl " << log_likelihood << endl;
	      //cout << "*****START OF MODEL******" << endl;
	      //this->Print(cout);
	      //cout << "*****END OF MODEL******" << endl;
	    }
	  if ( (_nb_frag > 1) ) //&& (_is_bounded == true) ) 
	    {
	      for (ifrag = 0 ; ifrag < _nb_frag ; ifrag++) 
		{
		  *ofile << log_likelihoods[ifrag] << "\t";
		}
	      *ofile << endl;
	    }
	}

      if ( (old_log_likelihood < 0) && 
	   (diff < eps) )
	{
	  stop = true;
	} else {
	  this->MStep();
	}
      
      iter++;
    }

  if (stop == false) 
    {
      log_likelihood = this->EStep(true, log_likelihoods);
      *ofile << "iter " << iter 
	     << " logl " << log_likelihood << endl;
	  if ( (_nb_frag > 1) && (_is_bounded == true) ) 
	    {
	      for (ifrag = 0 ; ifrag < _nb_frag ; ifrag++) 
		{
		  for (ifrag = 0 ; ifrag < _nb_frag ; ifrag++) 
		    {
		      *ofile << log_likelihoods[ifrag] << "\t";
		    }
		  *ofile << endl;
		}
	    }
    }
  
  this->DeletePVect();
  delete [] log_likelihoods;

  return(log_likelihood);
}

void HiddenMarkovModel::MStep()
{
  int istate;
  int iobs;
  double sum_ps_init;


  /* 
   * Update _ps_init
   */

  sum_ps_init = 0;
  for ( istate = 0 ; istate < _nb_state ; istate++ )
    {
      _ps_init[istate] = _transitions[istate]->SumCount();
      sum_ps_init += _ps_init[istate];
    }
  for ( istate = 0 ; istate < _nb_state ; istate++ )
    {
      _ps_init[istate] /= sum_ps_init;
    }


  /*
   * Preprocessing M Step
   */

  for ( istate = 0 ; istate < _nb_state ; istate++ )
    {
      _transitions[istate]->PreMStep();
      for ( iobs = 0 ; iobs < _dim_obs ; iobs++ )
	{
	  _observations[istate][iobs]->PreMStep();
	}
    }

  /*
   * Processing M Step
   */

  for ( istate = 0 ; istate < _nb_state ; istate++ )
    {
      _transitions[istate]->MStep();
      for ( iobs = 0 ; iobs < _dim_obs ; iobs++ )
	{
	  _observations[istate][iobs]->MStep();
	}
    }

  /*
   * Postprocessing M Step
   */

  for ( istate = 0 ; istate < _nb_state ; istate++ )
    {
      _transitions[istate]->PostMStep();
      for ( iobs = 0 ; iobs < _dim_obs ; iobs++ )
	{
	  _observations[istate][iobs]->PostMStep();
	}
    }

}


double HiddenMarkovModel::EStep(bool loglikelihood, double *log_likelihoods)
{
  int num_frag;
  long lg_frag;
  long estep_start;
  long count_lmargin;
  int count_length;
  double logl;
  
  int istate;
  int iobs;
  
  logl = 0;

  if ( (_is_bounded == true) && (_seqset->MaxLength() > _estep_segment) ) 
    {
      cerr << "Using bounded model: " << endl
	   << "Length of the longest of the sequences must be inferior to estep_segment"
	   << endl;
      exit(1);
    }

  for ( istate = 0 ; istate < _nb_state ; istate++ )
    {
      _transitions[istate]->ResetCount();
      for ( iobs = 0 ; iobs < _dim_obs ; iobs++ )
	{
	  _observations[istate][iobs]->ResetCount();
	}
    }

  for(num_frag = 0; num_frag<_nb_frag; num_frag++)
    {
      if ( loglikelihood == true )
	{
	  log_likelihoods[num_frag] = 0;
	}
      estep_start = 0;
      this->SetSeqFrag(num_frag);
      
#ifdef LOG
      if (loglevel >=4) 
	{
	  logstream << "numfrag " << num_frag << endl;
	}
#endif

      lg_frag = _seqs[0]-> Length();
      if (lg_frag < _estep_segment)
	{
	  this->RestrictedEStep(0, lg_frag, 0, lg_frag, NULL);
	  if ( loglikelihood == true )
	    {
	      log_likelihoods[num_frag] += this->RestrictedLogl(0, lg_frag, 0, lg_frag);
	    }
	} else {

	  count_length = _estep_segment - _estep_overlap/2;
	  this->RestrictedEStep(0,
				_estep_segment, 0, 
				count_length, NULL);
	  if ( loglikelihood == true )
	    {
	      log_likelihoods[num_frag]  += this->RestrictedLogl(0,
					   _estep_segment, 0, 
					   count_length);
	      //cout << "************> " << logl << endl;
	    }


	  estep_start += _estep_segment - _estep_overlap;
	  count_lmargin = _estep_overlap/2;
	  count_length = _estep_segment - _estep_overlap;
	  while (estep_start + _estep_segment < lg_frag)
	    {
	      this->RestrictedEStep(estep_start,
				    _estep_segment, count_lmargin, 
				    count_length, NULL);
	      if ( loglikelihood == true )
		{
		  log_likelihoods[num_frag]  += this->RestrictedLogl(estep_start,
				    _estep_segment, count_lmargin, 
				    count_length);
		}
	      //cout << "************> " << logl << endl;
	      estep_start += _estep_segment - _estep_overlap;
	    }

	  
	  count_length = lg_frag - estep_start - count_lmargin;
	  this->RestrictedEStep(estep_start,
				lg_frag - estep_start, count_lmargin, 
				count_length, NULL);
	  
	  if ( loglikelihood == true )
	    {
	      log_likelihoods[num_frag]  += this->RestrictedLogl(estep_start,
					   lg_frag - estep_start, 
					   count_lmargin, 
					   count_length);
	      //cout << "************> " << logl << endl;
	    }
	  
	}
      logl += log_likelihoods[num_frag];
    }
  
  return(logl); // 0 if loglikelihood != true
}

double HiddenMarkovModel::RestrictedLogl(long estep_start, 
					 long estep_length,
					 long count_lmargin, 
					 long count_length)
{
  double logl;
  int istate;
  int iobs;
  long ipos;
  long t;

  double sum;
  double prod;

  logl = 0;

  ipos = estep_start + count_lmargin ; 
  for (t = count_lmargin ; t < count_lmargin + count_length ; t++)
    {
      this->SetSeqPos(ipos);     
      sum = 0;
      for ( istate = 0 ; istate <_nb_state ; istate++ )
	{
	  prod = _p_predic[t][istate];
	  for ( iobs = 0 ; iobs < _dim_obs ; iobs++ )
	    {
	      prod *= _observations[istate][iobs]->PObs();
	    }
	  sum += prod;
	}      
      //cout << ipos << " " << logl << " " << sum << " " << log((double)sum) << endl;
      logl += log((double)sum);
 
      ipos++;
    }

  if ( _is_bounded == true ) 
    {
      double p_predic_bound = 0;
      int itrans;

      for ( istate = 0 ; istate <_nb_state ; istate++ )
	{
	  for ( itrans = 0 ; itrans < _transitions[istate]->NbTrans() ;
		itrans++ )
	    {
	      if ( _transitions[istate]->ToState(itrans) == _bound_state ) 
		{
		  p_predic_bound += _p_filtra[t-1][istate] * 
		    _transitions[istate]->PTrans(itrans); 
		}
	    }
	}  
      logl += log((double)p_predic_bound);
    }
  return(logl);
}

void HiddenMarkovModel::RestrictedEStep(long estep_start, 
					long estep_length,
					long count_lmargin, 
					long count_length,
					Output* output)
{
  long ipos;
  int iobs;
  int istate;
  int itrans;
  long t;
  double sum;

 
#ifdef LOG
  if (loglevel >=2) 
    {
      logstream << "RestrictedEStep " << endl
       << "\t estep_start "  << estep_start << endl
       << "\t estep_length "  << estep_length << endl
       << "\t count_lmargin "  << count_lmargin << endl
       << "\t count_length "  << count_length << endl;
    }
#endif /* LOG */

  if (output != NULL) {
    output->CleanOutput();
  }
 		    
  
  t = 0;
  //cerr << "t[" << t <<"] predic ";
  if ( _is_bounded != true ) 
    {
      for ( istate = 0 ; istate <_nb_state ; istate++ )
	{
	  _p_predic[t][istate] = _ps_init[istate];
	  //cerr << _p_predic[t][istate] << " ";
	}
      //cerr << endl;
    } else {
      for ( istate = 0 ; istate <_nb_state ; istate++ )
	{
	  _p_predic[t][istate] = 0;
	}
      for ( itrans = 0 ; itrans < _transitions[_bound_state]->NbTrans() ;
	    itrans++ )
	{
	  _p_predic[t][_transitions[_bound_state]->ToState(itrans)] =
	    _transitions[_bound_state]->PTrans(itrans);
	}      
    }

  for ( ipos = estep_start ; ipos < estep_start + estep_length - 1; ipos++ )
    {
      this->SetSeqPos(ipos);      
      
      /*
       * filtration
       */

      sum = 0;
      //cerr << "t[" << t <<"] filtra ";
      for ( istate = 0 ; istate <_nb_state ; istate++ )
	{
	  _p_filtra[t][istate] = _p_predic[t][istate];
	  for ( iobs = 0 ; iobs < _dim_obs ; iobs++ )
	    {
	      _p_filtra[t][istate] *= _observations[istate][iobs]->PObs();
	    }
	  sum += _p_filtra[t][istate];
	}      
      for ( istate = 0 ; istate <_nb_state ; istate++ )
	{
	  _p_filtra[t][istate] /= sum;
	  //cerr << _p_filtra[t][istate] << " ";
	}
      //cerr << endl;

      /*
       * prediction
       */
      
      //cerr << "ipos " << ipos << endl;
      //cerr << "t[" << t+1 <<"] predic ";
      for ( istate = 0 ; istate <_nb_state ; istate++ )
	{
	  //cerr << _p_filtra[t][istate] << endl;
	  _p_predic[t+1][istate] = 0;
	}
      for ( istate = 0 ; istate <_nb_state ; istate++ )
	{
	  for ( itrans = 0 ; itrans < _transitions[istate]->NbTrans() ;
		itrans++ )
	    {
	      _p_predic[t+1][_transitions[istate]->ToState(itrans)] += 
		_p_filtra[t][istate]*_transitions[istate]->PTrans(itrans);
	    }
	  //cerr << _p_predic[t+1][istate] << " ";
	}      
      //cerr << endl;

      t++;
    }
  
  this->SetSeqPos(ipos);      
  
  /*
   * last filtration
   */
  
  sum = 0;
  //cerr << "t[" << t <<"] filtra ";
  for ( istate = 0 ; istate <_nb_state ; istate++ )
    {
      _p_filtra[t][istate] = _p_predic[t][istate];
      for ( iobs = 0 ; iobs < _dim_obs ; iobs++ )
	{
	  _p_filtra[t][istate] *= _observations[istate][iobs]->PObs();
	}
      sum += _p_filtra[t][istate];
    }      
  for ( istate = 0 ; istate <_nb_state ; istate++ )
    {
      _p_filtra[t][istate] /= sum;
      //cerr << _p_filtra[t][istate] << " ";
    }
  //cerr << endl;

  /*
   * first smoothing
   */
  
  if ( _is_bounded != true ) 
    {
      for ( istate = 0 ; istate <_nb_state ; istate++ )
	{
	  _p_smooth[t][istate] = _p_filtra[t][istate];
	}
    } else {
      double p_predic_bound = 0;

      for ( istate = 0 ; istate <_nb_state ; istate++ )
	{
	  for ( itrans = 0 ; itrans < _transitions[istate]->NbTrans() ;
		itrans++ )
	    {
	      if ( _transitions[istate]->ToState(itrans) == _bound_state ) 
		{
		  p_predic_bound += _p_filtra[t][istate] * 
		    _transitions[istate]->PTrans(itrans); 
		}
	    }
	}  
      
      for ( istate = 0 ; istate <_nb_state ; istate++ )
	{
	  _p_smooth[t][istate] = 0;
	}      
      for ( istate = 0 ; istate <_nb_state ; istate++ )
	{
	  for ( itrans = 0 ; itrans < _transitions[istate]->NbTrans() ;
		itrans++ )
	    {
	      if ( _transitions[istate]->ToState(itrans) == _bound_state ) 
		{
		  _p_smooth[t][istate] += (_transitions[istate]->PTrans(itrans) *
		    _p_filtra[t][istate]) / p_predic_bound ;
		  _transitions[istate]->Count(itrans, _p_smooth[t][istate]);
		}
	    }
	  // cerr << _p_smooth[t][istate] << " ";
	}  
      //cerr << endl;
    }
 
  /*
   * smoothing
   */

  t--;
  while ( t >= count_lmargin + count_length )
    {
      for ( istate = 0 ; istate <_nb_state ; istate++ )
	{
	  _p_smooth[t][istate] = 0;
	  for ( itrans = 0 ; itrans < _transitions[istate]->NbTrans() ;
		itrans++ )
	    {
	      if ( _p_predic[t+1][_transitions[istate]->ToState(itrans)] > 0) 
		{
		  _p_smooth[t][istate] += 
		    ( _transitions[istate]->PTrans(itrans) *
		      _p_smooth[t+1][_transitions[istate]->ToState(itrans)] ) /
		    _p_predic[t+1][_transitions[istate]->ToState(itrans)] ;	
		}
	    }      
	  _p_smooth[t][istate] *= _p_filtra[t][istate] ;
	}
      /*cerr << "t[" << t <<"] smooth ";
      for ( istate = 0 ; istate <_nb_state ; istate++ )
	{
	  cerr << _p_smooth[t][istate] << " ";
	}      
	cerr << endl;*/
      t--;
    }

  double count_val;
  if (output == NULL)
    {
      if ( estep_start + t == _seqs[0]->Length() - 2)
	{
	  this->SetSeqPos(estep_start + t + 1);      
	  for ( istate = 0 ; istate <_nb_state ; istate++ )
	    {
	      for ( iobs = 0 ; iobs < _dim_obs ; iobs++ )
		{
		  _observations[istate][iobs]->Count(_p_smooth[t+1][istate]);
		}
	    }
	}
      
      while ( t >= count_lmargin )
	{
	  this->SetSeqPos(estep_start + t);      
	  for ( istate = 0 ; istate <_nb_state ; istate++ )
	    {
	      _p_smooth[t][istate] = 0;
	      for ( itrans = 0 ; itrans < _transitions[istate]->NbTrans() ;
		    itrans++ )
		{
		  if ( _p_predic[t+1][_transitions[istate]->ToState(itrans)] > 0) 
		    {

		      count_val = (_transitions[istate]->PTrans(itrans)*
				   _p_filtra[t][istate]*
				   _p_smooth[t+1][_transitions[istate]->ToState(itrans)])/
			_p_predic[t+1][_transitions[istate]->ToState(itrans)];
		      _transitions[istate]->Count(itrans, count_val);
		      _p_smooth[t][istate] += count_val;
		    }
		}      
	      for ( iobs = 0 ; iobs < _dim_obs ; iobs++ )
		{
		  _observations[istate][iobs]->Count(_p_smooth[t][istate]);
		}
	    }
	  t--;
	}
    } else {
      if ( estep_start + t == _seqs[0]->Length() - 2)
	{
	  this->SetSeqPos(estep_start + t + 1);      
	  for ( istate = 0 ; istate <_nb_state ; istate++ )
	    {
	      for ( iobs = 0 ; iobs < _dim_obs ; iobs++ )
		{
		  _observations[istate][iobs]->Count(_p_smooth[t+1][istate]);
		}
	      output->Count(t + 1, 
			    istate, _p_smooth[t+1][istate]);
	    }
	}
      
      while ( t >= count_lmargin )
	{
	  this->SetSeqPos(estep_start + t);      
	  for ( istate = 0 ; istate <_nb_state ; istate++ )
	    {
	      _p_smooth[t][istate] = 0;
	      for ( itrans = 0 ; itrans < _transitions[istate]->NbTrans() ;
		    itrans++ )
		{
		  if ( _p_predic[t+1][_transitions[istate]->ToState(itrans)] > 0) 
		    {
		      count_val = (_transitions[istate]->PTrans(itrans)*
				   _p_filtra[t][istate]*
				   _p_smooth[t+1][_transitions[istate]->ToState(itrans)])/
			_p_predic[t+1][_transitions[istate]->ToState(itrans)];
		      _transitions[istate]->Count(itrans, count_val);
		      output->JoinCount(t, 
					istate, itrans, count_val);
		      _p_smooth[t][istate] += count_val;	
		    }
		}      
	      output->Count(t, istate, _p_smooth[t][istate]);

	      for ( iobs = 0 ; iobs < _dim_obs ; iobs++ )
		{
		  _observations[istate][iobs]->Count(_p_smooth[t][istate]);
		}
	    }
	  t--;
	}

    }


  while ( t >= 0 )
    {
      for ( istate = 0 ; istate <_nb_state ; istate++ )
	{
	  _p_smooth[t][istate] = 0;
	  for ( itrans = 0 ; itrans < _transitions[istate]->NbTrans() ;
		itrans++ )
	    {
	      if ( _p_predic[t+1][_transitions[istate]->ToState(itrans)] > 0 )
		{
		  _p_smooth[t][istate] += 
		    ( _transitions[istate]->PTrans(itrans) *
		      _p_smooth[t+1][_transitions[istate]->ToState(itrans)] ) /
		    _p_predic[t+1][_transitions[istate]->ToState(itrans)] ;
		}
	    }      
	  _p_smooth[t][istate] *= _p_filtra[t][istate] ;
	}
      t--;
    }
  if ( _is_bounded == true ) 
    {
      for ( itrans = 0 ; itrans < _transitions[_bound_state]->NbTrans() ;
	    itrans++ )
	{
	  _transitions[_bound_state]->Count(itrans, _p_smooth[0][_transitions[_bound_state]->ToState(itrans)]);
	}
    }
  

}
  

void HiddenMarkovModel::SetSeqFrag(int ifrag)
{
  int iseq;
  int iobs;
  int istate;
  
  
  //cerr << "SetSeqFrag " << this << endl;
  for ( iseq = 0 ; iseq < _nb_seqs ; iseq++ )
    {
      //cerr << "--seqs_frag0 " <<  _seqs_frag0[iseq][ifrag] << endl;
      _seqs[iseq] = _seqs_frag0[iseq][ifrag];
    }

  for ( istate = 0 ; istate < _nb_state ; istate++ )
    {
      for ( iobs = 0 ; iobs < _dim_obs ; iobs++ )
	{
	  _observations[istate][iobs]->SetFrag(ifrag);
	}
    }
  
  
}

void HiddenMarkovModel::RandInitPObs()
{
  int iobs;
  int istate;

#ifdef LOG
  if (loglevel >=1) 
    {
      logstream << "RandInitPObs()" << endl;
    }
#endif

  for ( istate = 0 ; istate < _nb_state ; istate++ )
    {
       for ( iobs = 0 ; iobs < _dim_obs ; iobs++ )
	{
	  _observations[istate][iobs]->RandInitPObs();
	}
    }
}

void HiddenMarkovModel::TiedInitPObs()
{
  int iobs;
  int istate;

#ifdef LOG
  if (loglevel >=1) 
    {
      logstream << "TiedInitPObs()" << endl;
    }
#endif

  for ( istate = 0 ; istate < _nb_state ; istate++ )
    {
      for ( iobs = 0 ; iobs < _dim_obs ; iobs++ )
	{
	  _observations[istate][iobs]->TiedInitPObs();
	}
    }
}


void HiddenMarkovModel::Print(ostream &ofile)
{
  int istate;
  int iobs;
  
  for ( istate = 0 ; istate < _nb_state ; istate++ )
    {
      ofile << "BEGIN_STATE" << endl;
      ofile << "state_id: " << _state_ids[istate] << endl;
      if ( istate != _bound_state ) 
	{
	  ofile << "ps_init: " << _ps_init[istate] << endl;
	}
      _transitions[istate]->Print(ofile);
      if ( istate != _bound_state ) 
	{
	  for(iobs = 0 ; iobs < _dim_obs ; iobs++)
	    {
	      _observations[istate][iobs]->Print(ofile);
	    }
	}
      ofile << "END_STATE" << endl;
    }        
}


void HiddenMarkovModel::Viterbi(Param_Vit* param_vit)
{
  int num_frag;
  long viterbi_start;
  long lg_frag;
  long store_length;
  long store_lmargin;
  int* path;
  int** phi;
  int t;

  if(this->Complete() == false)
    {
      this->RandInitPObs();
    }
  this->TiedInitPObs();

  _vit_segment = param_vit->VitSegment(); 
  _vit_overlap = param_vit->VitOverlap();

  if ( (_vit_segment == 0) || (_vit_overlap == 0) ) 
    {
      cerr << "vit parameters not full in description file"
	   << endl;
      exit(1);
    }


  if ( (_is_bounded == true) && (_seqset->MaxLength() > _vit_segment) ) 
    {
      cerr << "Using bounded models: " << endl 
	   << "length of the longest of the sequences must be inferior to vit_segment"
	   << endl;
      exit(1);
    }


  phi = new int* [_vit_segment];
  for(t = 0 ; t < _vit_segment ; t++)
    {
      phi[t] = new int [_nb_state];
    }
  
  char* tmp_prefix;
  char* out_filename;
  for(num_frag = 0 ; num_frag < _nb_frag ; num_frag++)
    {
      this->SetSeqFrag(num_frag);
      tmp_prefix = shortfilename(_seqs[0]->Name());
      out_filename = new char [strlen(tmp_prefix) + strlen(".vit") + 1];
      strcpy(out_filename, tmp_prefix);
      strcat(out_filename, ".vit");
      ofstream ofile(out_filename, ios::out); // erase pre-existing file
      ofile.close();
      delete [] tmp_prefix;
      delete [] out_filename;
    }

  for(num_frag = 0 ; num_frag < _nb_frag ; num_frag++)
    {
      viterbi_start = 0;
      this->SetSeqFrag(num_frag);
      lg_frag = _seqs[0]->Length();
      path = new int [lg_frag]; 
      if(lg_frag < _vit_segment)
	{
	  this->RestrictedViterbi(0, lg_frag, 0, lg_frag, path, phi);
	}
      else
	{
	  store_length = _vit_segment - _vit_overlap/2;
	  this->RestrictedViterbi(0, _vit_segment, 0, store_length, path, phi);

	  viterbi_start += _vit_segment - _vit_overlap;
	  store_lmargin = _vit_overlap/2;
	  store_length = _vit_segment - _vit_overlap;
	  cerr << lg_frag << endl;
	  while(viterbi_start + _vit_segment < lg_frag)
	    {
	      this->RestrictedViterbi(viterbi_start, _vit_segment,
				      store_lmargin, store_length,
				      path, phi);
	      viterbi_start += _vit_segment - _vit_overlap;
	    }
	  
	  store_length = lg_frag - viterbi_start - store_lmargin;
	  this->RestrictedViterbi(viterbi_start, lg_frag - viterbi_start,
				  store_lmargin, store_length,
				  path, phi);
	}
      /*     for(t = 0 ; t < lg_frag ; t++)
	{
	  cout << path[t] << " ";
	}
	cout << endl;*/

      PrintViterbi(path, num_frag);
    
      delete path;
    }
 
  for(t = 0 ; t < _vit_segment ; t++)
    {
      delete phi[t]; 
    }
  
  delete phi;

}


void HiddenMarkovModel::RestrictedViterbi(long viterbi_start,
					  long viterbi_length,
					  long store_lmargin,
				          long store_length,
				          int* path,
				          int** phi)
{
  int istate;
  double* current_p;
  double* past_p;
  double* i_p;
  int iobs;
  int t; // relative position in the sequence
  int itrans;
  int max_state;
  double max_prob;
 


#ifdef LOG
  if(loglevel >= 2)
    {
      logstream << "RestrictedViterbi " << endl
		<< "\t viterbi_start " << viterbi_start << endl
		<< "\t viterbi_length " << viterbi_length << endl
		<< "\t store_lmargin " << store_lmargin << endl
		<< "\t store_length " << store_length << endl;
    }
#endif /* LOG */


  // Initialisation

  this->SetSeqPos(viterbi_start);

  current_p = new double[_nb_state];
  past_p = new double[_nb_state];
  i_p = new double[_nb_state];

  if ( _is_bounded != true ) 
    {
      for(istate = 0 ; istate < _nb_state ; istate++)
	{
	  current_p[istate] = log((double)_ps_init[istate]);
	  
	  for(iobs = 0 ; iobs < _dim_obs ; iobs++)
	    {
	      current_p[istate] += log((double)_observations[istate][iobs]->PObs());
	    }
	}
    } else {
      for(istate = 0 ; istate < _nb_state ; istate++)
	{
	  current_p[istate] = log((double)0);
	}
      for ( itrans = 0 ; itrans < _transitions[_bound_state]->NbTrans() ;
	    itrans++ )
	{
	  current_p[_transitions[_bound_state]->ToState(itrans)] =
	    _transitions[_bound_state]->PTrans(itrans);
	}      
      for(istate = 0 ; istate < _nb_state ; istate++)
	{
	  for(iobs = 0 ; iobs < _dim_obs ; iobs++)
	    {
	      current_p[istate] += log((double)_observations[istate][iobs]->PObs());
	    }
	}
      
    }

  i_p = past_p;
  past_p = current_p;
  current_p = i_p;

  for(istate = 0 ; istate < _nb_state ; istate++)
    {
      current_p[istate] = 1;
    }

  // viterbi algorithm

  for(t = 1 ; t < viterbi_length  ; t++) 
    {
      this->SetSeqPos(t + viterbi_start); // absolute position in the sequence

      for(istate = 0 ; istate < _nb_state ; istate++)
	{
	  for(itrans = 0 ; itrans < _transitions[istate]->NbTrans() ; itrans++)
	    {
	      if( (current_p[_transitions[istate]->ToState(itrans)]
		  < past_p[istate] + log((double)_transitions[istate]->PTrans(itrans)) ) 
|| (current_p[_transitions[istate]->ToState(itrans)] > 0))		  
		{
		  current_p[_transitions[istate]->ToState(itrans)] = past_p[istate] + log((double)_transitions[istate]->PTrans(itrans));

		  phi[t][_transitions[istate]->ToState(itrans)] = istate;
		}
	    }
	}

      for(istate = 0 ; istate < _nb_state ; istate++)
	{
	  for(iobs = 0 ; iobs < _dim_obs ; iobs++)
	    {
	      current_p[istate] += log((double)_observations[istate][iobs]->PObs());
	    }
	}
      
      i_p = past_p;
      past_p = current_p;
      current_p = i_p;

      for(istate = 0 ; istate < _nb_state ; istate++)
	{
	  current_p[istate] = 1;
	}
      cout << "vit " << t << endl;
    }

  if ( _is_bounded != true ) 
    {
      max_state = 0;
      max_prob = past_p[0];
      for(istate = 1 ; istate < _nb_state ; istate++)
	{
	  if(past_p[istate] > max_prob)
	    {
	      max_prob = past_p[istate];
	      max_state = istate;
	    }
	}
    } else {
      max_state = -1;
      max_prob = log((double)0);

      for ( istate = 0 ; istate <_nb_state ; istate++ )
	{
	  for ( itrans = 0 ; itrans < _transitions[istate]->NbTrans() ;
		itrans++ )
	    {
	      if ( (_transitions[istate]->ToState(itrans) == _bound_state) 
		   && ( past_p[istate] + 
			log((double)_transitions[istate]->PTrans(itrans)) > max_prob )) 
		{
		  max_prob = past_p[istate] + 
		    log((double)_transitions[istate]->PTrans(itrans));
		  max_state = istate;
		}
	    }
	}  
      
      if (max_state < 0) {
	cerr << "No hidden path with positive probability"
	     << endl;
	exit(1);
      }
 
    }


  cout << "max_state" << endl;
  for(t = viterbi_length - 2 ; t >= (store_lmargin + store_length) ; t--)
    {
      cout << "! max_state " << max_state << endl;
      max_state = phi[t + 1][max_state];
    }

  
  t = (store_lmargin + store_length - 1);
  if( viterbi_length == store_length + store_lmargin) //ie if(lg_frag < _vit_segment)
    { 
      path[t + viterbi_start] = max_state; 
    }
  else
    {
      cout << " t " << t << " " << max_state << endl;
      max_state = phi[t + 1][max_state];
      path[t + viterbi_start] = max_state; 
    }

  for(t = (store_lmargin + store_length - 1) - 1 ; t >= store_lmargin ; t--)
    {
      max_state = phi[t + 1][max_state];
      path[t + viterbi_start] = max_state; 
    }
}


map<string, int>* HiddenMarkovModel::MapStateById()
{
  map<string, int>* temp_map_state_by_id;
  int istate;
  

  temp_map_state_by_id = new map<string, int>;

  for(istate = 0 ; istate < _nb_state ; istate++)
    {
      if((*temp_map_state_by_id).find((string)_state_ids[istate]) !=
	  (*temp_map_state_by_id).end() )
	{ 
	  cerr << "The state identidier: " << _state_ids[istate] 
	       << endl << "of state " << _state_ids 
	       << " is already used" << endl;
	  exit(1);
	}
      cerr << istate << " " << _state_ids[istate] << endl;
      (*temp_map_state_by_id).insert(make_pair((string)_state_ids[istate], 
				       istate));
    }
  return(temp_map_state_by_id);
}


void HiddenMarkovModel::AllocOutput(long int * lgmax, int*** join_idnum_to, 
				    int** join_nb_to, 
				    int*** join_count, 
				    int** count,
				    int *nb_state, Param_EM* param_em)
{ 
  int u;
  int i;


  if (param_em->EStepSegment() > _seqset->MaxLength() ) 
    {
      *lgmax = _seqset->MaxLength();
    } else {
      *lgmax = param_em->EStepSegment();
    }

  *nb_state = _nb_state;
  *join_nb_to = new int [_nb_state];
  *join_count = new int* [_nb_state];
  *join_idnum_to = new int* [_nb_state];
  *count = new int [_nb_state];

  for(u=0; u<_nb_state; u++)
    {
      (*count)[u] = -1;
      (*join_nb_to)[u] = _transitions[u]->NbTrans();
      (*join_count)[u] = new int [(*join_nb_to)[u]];
      (*join_idnum_to)[u] = new int [(*join_nb_to)[u]];
      for(i=0; i<(*join_nb_to)[u]; i++)
	{	  
	  (*join_count)[u][i] = -1;
	  (*join_idnum_to)[u][i] = _transitions[u]->ToState(i);
	}
    }
}

void HiddenMarkovModel::PrintEOutput(Output* output, Param_EM* param_em)
{
  int num_frag;
  long lg_frag;
  long estep_start;
  long count_lmargin;
  int count_length;
  
  int istate;
  int iobs;
  
  char* out_filename;
  char* tmp_prefix;
  
  _estep_segment = param_em->EStepSegment();
  _estep_overlap = param_em->EStepOverlap();
  
  if ( (_estep_segment == 0) || (_estep_overlap == 0) ) 
    {
      cerr << "em parameters not full in description file"
	   << endl;
      exit(1);
    }

  this->AllocPVect();

  for ( istate = 0 ; istate < _nb_state ; istate++ )
    {
      _transitions[istate]->ResetCount();
      for ( iobs = 0 ; iobs < _dim_obs ; iobs++ )
	{
	  _observations[istate][iobs]->ResetCount();
	}
    }

  for(num_frag = 0; num_frag<_nb_frag; num_frag++)
    {
      this->SetSeqFrag(num_frag);
      tmp_prefix = shortfilename(_seqs[0]->Name());
      out_filename = new char [strlen(tmp_prefix) + strlen(".e") + 1];
      strcpy(out_filename, tmp_prefix);
      strcat(out_filename, ".e");
      delete tmp_prefix;
      ofstream ofile(out_filename, ios::out); // erase pre-existing file
      ofile.close();
      delete out_filename;
    }

  for(num_frag = 0; num_frag<_nb_frag; num_frag++)
    {
      this->SetSeqFrag(num_frag);

      tmp_prefix = shortfilename(_seqs[0]->Name());
      out_filename = new char [strlen(tmp_prefix) + strlen(".e") + 1];
      strcpy(out_filename, tmp_prefix);
      strcat(out_filename, ".e");
      delete tmp_prefix;
      
      estep_start = 0;
      ofstream ofile(out_filename, ios::app);
      output->WriteOutputDefinition(ofile);

      lg_frag = _seqs[0]-> Length();
      //cerr << _seqs[0]-> Length() << endl;
      if (lg_frag < _estep_segment)
	{
	  this->RestrictedEStep(0, lg_frag, 0, lg_frag, output);
	  output->WriteOutputSeq(ofile, 0, lg_frag);
	} else {

	  count_length = _estep_segment - _estep_overlap/2;
	  this->RestrictedEStep(0,
				_estep_segment, 0, 
				count_length, output);
	  output->WriteOutputSeq(ofile, 0, count_length);
	  estep_start += _estep_segment - _estep_overlap;
	  count_lmargin = _estep_overlap/2;
	  count_length = _estep_segment - _estep_overlap;
	  while (estep_start + _estep_segment < lg_frag)
	    {
	      this->RestrictedEStep(estep_start,
				    _estep_segment, count_lmargin, 
				    count_length, output);
	      output->WriteOutputSeq(ofile, count_lmargin, count_lmargin + count_length);
	      estep_start += _estep_segment - _estep_overlap;
	    }

	  
	  count_length = lg_frag - estep_start - count_lmargin;
	  this->RestrictedEStep(estep_start,
				lg_frag - estep_start, count_lmargin, 
				count_length, output);
	  output->WriteOutputSeq(ofile, count_lmargin, count_lmargin + count_length);
	}
      ofile.close();
      delete out_filename;
    }

  this->DeletePVect();

}




void HiddenMarkovModel::PrintViterbi(int* path, int num_frag)
{
  long int ibase;
  int istate;
  long lg_frag;
  char* out_filename;
  char* tmp_prefix;

  tmp_prefix = shortfilename(_seqs[0]->Name());
  out_filename = new char [strlen(tmp_prefix) + strlen(".vit") + 1];
  strcpy(out_filename, tmp_prefix);
  strcat(out_filename, ".vit");
  delete tmp_prefix;

  

  ofstream ofile(out_filename, ios::app);

  ofile << "# viterbi reconstruction" << endl;

  ofile << "# " ;
  for(istate = 0 ; istate < _nb_state ; istate++)
    {
      ofile << istate << " : " ;
      ofile << "(" ;
      ofile << _state_ids[istate] ;
      ofile << ")" << "\t" ;
    } 
  ofile << endl;

  lg_frag = _seqs[0]->Length();
      
  for(ibase = 0 ; ibase < lg_frag ; ibase++)
    {
      ofile << path[ibase] << endl;
    }
  delete out_filename;
}


void  HiddenMarkovModel::HmmSimulation(long lg)
{
  
  double r;
  int* hidden_states_path;
  int* observations_path;
  long ipos;
  int iobs;
  int state_init;
  double sum_ps_init;
   
  hidden_states_path = new int [lg];
  observations_path = new int [lg];
 
  if(this->Complete() == false)
    {
      this->RandInitPObs();
    }
  this->TiedInitPObs();
  //initialisation

  this->SetSeqFrag(0);


  /*
   * Hidden State Path Simulation
   */

  r  = (double) rand() / (double) RAND_MAX;  
  state_init = 0;
  sum_ps_init = _ps_init[0];
  while(r > sum_ps_init)
    {
      state_init++;
      sum_ps_init += _ps_init[state_init];
    }
  hidden_states_path[0] = state_init;
 
  for(ipos = 1 ; ipos < lg ; ipos++)
	{
	  hidden_states_path[ipos] = 
	    _transitions[hidden_states_path[ipos-1]]->Simulation();
	}
  PrintStatesSimulation(hidden_states_path, lg);
  
     
  /*
   * Observations Paths Simulation
   */
  
  for(iobs = 0 ; iobs < _dim_obs ; iobs++)
    {
      for(ipos = 0 ; ipos < lg ; ipos++)
	{
	  _observations[hidden_states_path[ipos]]
	    [iobs]->ObsSimulation(ipos, observations_path);
	}
      _observations[0][iobs]->PrintObsSimulation(observations_path, 
						 lg, iobs);
    }    


  delete observations_path;
  delete hidden_states_path;  
}


void HiddenMarkovModel::PrintStatesSimulation(int* hidden_states_path,
					     long lg)
{
  long int ibase;
  int istate;
  char* out_filename;

  cerr << "dans print hiddenState" << endl;

  out_filename = new char [strlen("simulated.hidden_states") + 1];
  out_filename = "simulated.hidden_states";

  ofstream ofile(out_filename, ios::out);

  ofile << "# hidden states simulation" << endl;;

  ofile << "# " ;
  for(istate = 0 ; istate < _nb_state ; istate++)
    {
      ofile << istate << " : ";
      ofile << "(" ;
      ofile << _state_ids[istate] ;
      ofile << ")" << "\t" ;
    } 
  ofile << endl;

  for(ibase = 0 ; ibase < lg ; ibase++)
    {
      ofile << hidden_states_path[ibase] << endl;
    }

  ofile.close();
}






bool HiddenMarkovModel::Complete()
{
  int istate;
  int iobs;

  for(istate = 0 ; istate < _nb_state ; istate++)
    {
      for(iobs = 0 ; iobs < _dim_obs ; iobs++)
	{
	  if( (_observations[istate][iobs]->Complete()) == false )
	    {
	      return(false);
	    }
	}
    }
  return(true);
}
