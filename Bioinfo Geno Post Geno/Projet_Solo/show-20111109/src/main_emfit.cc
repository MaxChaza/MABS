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
#include "Param_EM.h"
#include "Param_Vit.h"
#include "shortfilename.h"



#ifdef LOG
int loglevel = 0;
ofstream logstream("logfile.log");
#endif /* LOG */


int main(int argc, char *argv[])
{
  SequenceSet *seqset;
  Param_EM* param_em;
  SelectModel* model; 
  HiddenMarkovModel* my_hmm;
  Output* output;

  char* model_file = NULL;
  char* em_file = NULL;
  char* seqlist_file = NULL;
  char* output_file = NULL;

  unsigned int rseed = time(NULL);

  int num_arg = 1;
  while (num_arg < argc)
    {
      
#ifdef LOG
      if ( (strcmp (argv[num_arg], "-log") == 0) && (num_arg + 1 < argc) )
	{

	  num_arg++ ;
	  loglevel = (int)argv[num_arg][0] - 48 ;
	  cout << "loglevel " << loglevel << endl;
	  num_arg++ ;

	} else 
#endif /* LOG */
	  
      if ( (strcmp (argv[num_arg], "-model") == 0) 
	   && (num_arg + 1 < argc) ) 
	{
	  
	  num_arg++;
	  model_file = argv[num_arg];
	  cout << "model file name: " << model_file << endl;
	  num_arg++;
	
	} else if ( (strcmp (argv[num_arg], "-em") == 0) 
		    && (num_arg + 1 < argc) ) {
	  
	  num_arg++;
	  em_file = argv[num_arg];
	  cout << "em file name: " << em_file << endl;
	  num_arg++;

	} else if ( (strcmp (argv[num_arg], "-seq") == 0) 
		    && (num_arg + 1 < argc) ) {

	  num_arg++;
	  seqlist_file = argv[num_arg];
	  cerr << "seqlist file name: " << seqlist_file << endl;
	  num_arg++;	  

	} else if ( (strcmp (argv[num_arg], "-output") == 0) 
		    && (num_arg + 1 < argc) ) {
	  	  
	  num_arg++;
	  output_file = argv[num_arg];
	  cout << "output description file name: " << output_file
	       << endl;
	  num_arg++;	  	  
    
	}  else if ( (strcmp (argv[num_arg], "-rseed") == 0) 
		    && (num_arg + 1 < argc) ) {
	  	  
	  num_arg++;
	  rseed = atoi(argv[num_arg]);
	  cout << "random number generator seed set to: " << rseed
	       << endl;
	  num_arg++;	  	  
	} else {
	  
	  cerr << "Usage : " << argv[0] << " "
	       << "-model <model_desc>" << " "
	       << "-em <em_parameters>" << " "
	       << "-seq <seq_list>" << " "
	       << "[-output <ouput_desc>]"
	       << "[-rseed <rseed>]"
#ifdef LOG
	       << " " << "[-log <debug_level>]" 
#endif /* LOG */	    
	       << endl ;

	  return (1) ;
	}
    }


  if( (model_file == NULL)
      || (em_file == NULL)
      || (seqlist_file == NULL) )
    {
      cerr << "Usage : " << argv[0] << " "
	   << "-model <model_desc>" << " "
	   << "-em <em_parameters>" << " "
	   << "-seq <seq_list>" << " "
	   << "[-output <ouput_desc>]"
	   << "[-rseed <rseed>]"
#ifdef LOG
	       << " " << "[-log <debug_level>]" 
#endif /* LOG */	    
	       << endl ;
      return(1);
    }
     
  cout << "rseed: " << rseed << endl;
  srand(rseed);

 
  seqset = new SequenceSet(seqlist_file);
  param_em = new Param_EM(em_file);

  my_hmm = new HiddenMarkovModel(model_file, seqset);
  HiddenMarkovModel* best_hmm;

  int niter = param_em->NIter();
  double epsi = param_em->Epsi();
  

  

  char* otrace_file;
  char* omodel_file;

  char* tmp_char;
  tmp_char = shortfilename(seqlist_file);
  //cerr << "long file name " << seqlist_file << endl;
  //cerr << "short file name " << tmp_char << endl;

  otrace_file = new char [strlen(tmp_char) + strlen(".trace") + 1];
  strcpy(otrace_file, tmp_char);
  strcat(otrace_file, ".trace");
  cout << "trace file name: " << otrace_file << endl;

  omodel_file = new char [strlen(tmp_char) + strlen(".model") + 1];
  strcpy(omodel_file, tmp_char);
  strcat(omodel_file, ".model");
  //cerr << "model file name " << omodel_file << endl;


  
  if (param_em->NbSel() > 0) 
    {
      model = new SelectModel(my_hmm, param_em);
      model->Select(tmp_char);
      best_hmm = model->Best();
    } else {
      if (my_hmm->Complete() != true)
	{
	  cerr << "Model in " << model_file 
	       << " is not fully defined" << endl
	       << "you must specify selection parameters in " 
	       << em_file << endl;
	  exit(1);
	}
      my_hmm->TiedInitPObs();
      best_hmm = my_hmm;
    }

  if (niter > 0) 
    {
      ofstream otracestream(otrace_file);
      best_hmm->EMfit(niter, epsi, &otracestream, param_em); 
      otracestream.close();
      ofstream omodelstream(omodel_file);
      best_hmm->Print(omodelstream);
      omodelstream.close();
    }


  if (output_file != NULL) 
    {
      output = new Output(output_file);
      cerr << "output created" << endl;
      (*output).CheckAndSetStateIdNum(my_hmm);
      (*output).AllocOutput(my_hmm, param_em);
      cerr << "output checked" << endl;
      best_hmm->PrintEOutput(output, param_em);
    }
  
 
  delete seqset;

  delete my_hmm;
  delete tmp_char;
 

#ifdef LOG
  logstream.close();
#endif 

  return(0);
}
