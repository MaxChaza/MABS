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


#include <cmath>
#include <cstdlib>
#include <cstring>
#include <gsl/gsl_sys.h>
using namespace std;

#include "SelectModel.h"
#include "const.h"
#include "Param_EM.h"


#ifdef LOG
extern int loglevel;
extern ofstream logstream;
#endif /* LOG */


SelectModel::SelectModel(HiddenMarkovModel* model, Param_EM* param_em)
{
  

#ifdef LOG
  if (loglevel >=1) 
    {
      logstream << "Inside SelectModel constructor of " 
		<< this << endl;
    }
#endif /* LOG */
  _param_em = param_em;
  _niter_sel = param_em->NIterSel();
  _nb_sel = param_em->NbSel(); 
  _eps_sel = param_em->EpsSel(); 
  _best = -1;

  if ( model->Complete() == true )
    {
      _nb_sel = 1;
    }


  _array_model = new HiddenMarkovModel* [_nb_sel];
  for(int i = 0 ; i < _nb_sel ; i++)
    {
      _array_model[i] = new HiddenMarkovModel(*model);
      _array_model[i]->RandInitPObs(); 
      _array_model[i]->TiedInitPObs(); 
    }
   
 
#ifdef LOG
  if (loglevel >=1) 
    {
      logstream << "leaving SelectModel constructor" << endl
		<< "of " << this << endl;
    }
#endif /* LOG */


}


int SelectModel::Select(char* filename_prefix)
{

  double past_log;
  double current_log;
  int best;

  cerr << "Running from random parameters..." << endl;
 

  char* models_filename = NULL;
  char* likelihoods_filename = NULL;
  char* traces_filename = NULL;
 

  models_filename = new char [strlen(filename_prefix) 
			     + strlen(".select.models") + 1];

  

  strcpy(models_filename, filename_prefix);
  strcat(models_filename, ".select.models");
  ofstream models_stream(models_filename);

  
  likelihoods_filename = new char [strlen(filename_prefix) 
			     + strlen(".select.likelihoods") + 1];
  strcpy(likelihoods_filename, filename_prefix);
  strcat(likelihoods_filename, ".select.likelihoods");
  ofstream likelihoods_stream(likelihoods_filename);

 

  ofstream *traces_stream = NULL;
  if (_eps_sel > 0)
    {
      traces_filename = new char [strlen(filename_prefix) 
				 + strlen(".select.traces") + 1];
      strcpy(traces_filename, filename_prefix);
      strcat(traces_filename, ".select.traces");
      traces_stream = new ofstream(traces_filename);
    }

 

  best = -1;  
  past_log = 1; // real values are < 0
  for(int i = 0 ; i < _nb_sel ; i++)
    {
     
      if (_eps_sel < 0)
	{
	  current_log = _array_model[i]->EMfit(_niter_sel, _param_em);
	} else {
	  *traces_stream << "****************************************" << endl
			<< "model " << i << endl;	  
	  current_log = _array_model[i]->EMfit(_niter_sel, _eps_sel, 
					       traces_stream, _param_em);
	}
  
      likelihoods_stream << "model " << i << " " 
			 << "loglikelihood " << current_log << endl;
     
      models_stream << "*******************************************" << endl
		    << "model " << i << endl
		    << "loglikelihood " << current_log << endl
		    << "-------------------------------------------" << endl;
     _array_model[i]->Print(models_stream);
     
     
     

      if ( (past_log > 0) && (gsl_finite(current_log) == 1) )
	{
	  best = i; 
	  past_log = current_log;	  	  
	} else if( (gsl_finite(current_log) == 1) 
	  && (current_log > past_log) ) {
	  best = i;
	  past_log = current_log;
	}
    }


  if( best < 0 )
    {
      cerr << "warning : likelihood NaN or not finite ! " << endl;
      exit(1);
    }

  _best = best;
  likelihoods_stream << endl
		     << "best model found " << _best << " " 
		     << "loglikelihood " << past_log << endl;
  
  models_stream.close();
  likelihoods_stream.close();  
  if ( _eps_sel > 0 )
    {
      traces_stream->close();
    }

  delete models_filename;
  delete likelihoods_filename;
  if (traces_filename != NULL)
    {
      delete traces_filename;
    }
  
  return(best);
}
 






