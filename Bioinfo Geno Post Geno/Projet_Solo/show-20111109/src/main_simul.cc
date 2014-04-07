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

#include <iostream>
#include <fstream>
#include <cstring>
#include <cstdlib>
using namespace std;


#include "ObsSequence.h"
#include "SequenceSet.h"
#include "HiddenMarkovModel.h"
#include "const.h"
#include "time.h"
#include "Output.h"
#include "SelectModel.h"
#include "Param_Simul.h"




#ifdef LOG
int loglevel;
ofstream logstream("logfile.log");
#endif /* LOG */


int main(int argc, char *argv[])
{
  char* model_file = NULL;
  char* simul_file = NULL;
  char* seqlist_file = NULL;
  
  HiddenMarkovModel* my_hmm;
  Param_Simul* param_simul;
  
  int num_arg = 1;
  while (num_arg < argc)
    {
      
#ifdef LOG
      if ( (strcmp (argv[num_arg], "-log") == 0) && (num_arg + 1 < argc) )
	{
	  
	  num_arg++ ;
	  loglevel = (int)argv[num_arg][0] - 48 ;
	  cerr << "loglevel " << loglevel << endl;
	  num_arg++ ;
	  
	}
#endif /* LOG */
      
      
      if ( (strcmp (argv[num_arg], "-model") == 0) 
	   && (num_arg + 1 < argc) ) 
	{
	  
	  num_arg++;
	  model_file = argv[num_arg];
	  cerr << "model file name: " << model_file << endl;
	  num_arg++;
	  
	}  else if ( (strcmp (argv[num_arg], "-seq") == 0) 
		    && (num_arg + 1 < argc) ) {

	  num_arg++;
	  seqlist_file = argv[num_arg];
	  cerr << "seqlist file name: " << seqlist_file << endl;
	  num_arg++;	  

	} else if ( (strcmp (argv[num_arg], "-simul") == 0) 
		    && (num_arg + 1 < argc) ) {
	  
	  num_arg++;
	  simul_file = argv[num_arg];
	  cerr << "simul file name: " << simul_file << endl;
	  num_arg++;
	  
	  
	} else {
	  
	  cerr << "Usage : " << argv[0] << " "
	       << "-model <model_desc>" << " "
	       << "-simul <simul_parameters>" << " "
	       << "-seq <seq_list>" << " "
#ifdef LOG
	       << " " << "[-log <debug_level>]" 
#endif /* LOG */	    
	       << endl ;
	  
	  return (1) ;
	}
    }
  

  if( (model_file == NULL)
      || (simul_file == NULL)
      || (seqlist_file == NULL) )
    {
      cerr << "Usage : " << argv[0] << " "
	   << "-model <model_desc>" << " "
	   << "-simul <simul_parameters>" << " "
	   << "-seq <seq_list>" << " "
#ifdef LOG
	   << " " << "[-log <debug_level>]" 
#endif /* LOG */	    
	   << endl ;
      return(1);
    }

  
  srand(time(NULL));
  
  SequenceSet* seqset;
  
  seqset = new SequenceSet(seqlist_file);
  param_simul = new Param_Simul(simul_file);
  
  my_hmm = new HiddenMarkovModel(model_file, seqset);

  
  long lg;
  lg = param_simul->Length();

  my_hmm->HmmSimulation(lg);
  
  
  delete seqset;
  
  delete my_hmm;
  

#ifdef LOG
  logstream.close();
#endif 

  return(0);
}
