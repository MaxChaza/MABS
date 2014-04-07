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
#include <cstring>
#include <cstdlib>
using namespace std;

#include "HiddenMarkovModel.h"
#include "HmmObservations.h"
#include "reading.h"
#include "Param_EM.h"
#include "Param_Vit.h"
#include "const.h"


#ifdef LOG
extern int loglevel;
extern ofstream logstream;
#endif /* LOG */


HiddenMarkovModel::HiddenMarkovModel(char* model_filename, 
				     SequenceSet *seqset)			     
{
#ifdef LOG
  if (loglevel >=1) 
    {
      logstream << "Inside HiddenMarkovModel constructor of " 
		<< this << endl << "with arg "
		<< "filename: " << model_filename << endl;
    }
#endif /* LOG */

  _seqset = seqset;
  _bound_state = -1;
  char* word;
  ifstream model_stream(model_filename);

  list<double> lst_ps_init;
  double current_ps_init;

  list<char*> lst_state_ids;
  char* current_id;
  
  list<HmmTransitions*> lst_state_trans;
  HmmTransitions* current_trans;
  

  list<list<HmmObservations*>*> lst_lst_state_obs;
  list<HmmObservations*>* current_lst_state_obs;
  HmmObservations* current_state_obs;

  if (! model_stream.good())
    {
      cerr << "Invalid model file : \"" << model_filename <<"\"" << endl;
      exit(1);      
    }

  _model_filename = new char[strlen(model_filename) + 1];
  strcpy(_model_filename, model_filename);  
 
  _nb_state = 0;
  _is_bounded = false;

  word = ReadWord(model_stream);

  while ( (word != NULL) && strncmp(word, "BEGIN_STATE", 11)==0 )
    {
      _nb_state++;	  	  
      
      /*
       * Read hidden state
       */
      
#ifdef LOG
      if (loglevel >=2) 
	{
	  logstream << "Building the " << _nb_state << "th "  
		    << "hidden state"; 
	}
#endif /* LOG */	  
      
      delete word;
      word = ReadWord(model_stream);      
      // read state associated identifier
      if ( (word != NULL) && strncmp(word, "state_id:", 8)==0 )
	{
	  delete word;
	  word = ReadWord(model_stream);
	  if (word != NULL)
	    {
	      current_id = word;
	    } else {
	      cerr << "no state_id given following " 
		   << "\"state_id:\" keyword in the " 
		   << _nb_state << "th state " << endl;
	      exit(1);		
	    }
	} else {
	  cerr << "keyword: \"state_id:\" waited in the " 
	       << _nb_state << "th state where \""
	       << word << "\" found" << endl;
	  exit(1);
	}
      lst_state_ids.push_back(current_id);

	
      word = ReadWord(model_stream);
      // read state associated ps_init
      if ( (word != NULL) && (strncmp(word, "ps_init:", 6)==0) )
	{
	  delete word;
	  if ( ReadDouble(model_stream, current_ps_init) == false )
	    {
	      cerr << "no value for ps_init in state following " 
		   << "\"ps_init:\" keyword in state: " << current_id 
		   << endl;
	      exit(1);
	    }	    
	  if (current_ps_init < 0) 
	    {
	      cerr << "Invalid negative value for ps_init: " 
		   << current_ps_init << " in state: " << current_id 
		   << endl;
	      exit(1);
	    } else if (current_ps_init > 1) {
	      cerr << "Invalid greater than 1 value for ps_init: " 
		   << current_ps_init << " in state: " << current_id 
		   << endl;
	      exit(1);		
	    }
	  word = ReadWord(model_stream);
	} else {
	  current_ps_init = -1; 
	}
      lst_ps_init.push_back(current_ps_init);
	
	
      // read state associated transitions
      if ( (word != NULL) && (strncmp(word, "BEGIN_TRANSITIONS", 16)==0) )
	{
	  delete word;
	  current_trans = new HmmTransitions(model_stream, current_id);	    
	} else {
	  cerr << "Invalid key_word: \""<<  word 
	       << "\" found in state " << current_id << endl
	       << "where BEGIN_TRANSITIONS expected" << endl;
	  exit(1);
	}
      lst_state_trans.push_back(current_trans);

	
      // read state associated observations
      current_lst_state_obs = new list<HmmObservations*>;
      word = ReadWord(model_stream);
      
      while ( (word != NULL) && (strncmp(word, "BEGIN_OBSERVATIONS", 16)==0) ) {
	delete word;
	word = ReadWord(model_stream);
	if ( (word != NULL) && 
	     (strncmp(word, "seq:", 4)==0) ) {
	  delete word;
	} else {
	  cerr << "Invalid key_word: \""<<  word 
	       << "\" found in observation definition of state " 
	       << current_id << endl
	       << "where \"seq:\" expected" << endl;
	  exit(1);
	}
	
	char *seq_id;
	seq_id = ReadWord(model_stream);
	if ( seq_id != NULL ) {
	  current_state_obs = new HmmObservations(model_stream,
						  current_id,
						  seq_id,
						  seqset);
	  current_lst_state_obs->push_back(current_state_obs);
	  word = ReadWord(model_stream);
	}
      }
      
      if ( strcmp(current_id, "bound") == 0 ) {
	if ( _is_bounded == false ) 
	  {
	    cerr << "IS BOUNDED" << endl;
	    _is_bounded = true;
	    if ( current_lst_state_obs->size() != 0 ) 
	      {
		cerr << "observations could not be defined in \"bound\" state" << endl;
		exit(1);
	      }
	  } else {
	    cerr << "only one state named \"bound\" is allowed" << endl;
	    exit(1);
	  }
      }
      
      if ( (current_lst_state_obs->size() == 0) 
	   && (strcmp(current_id, "bound") !=0) )
	{
	  cerr << "Invalid key_word: \""<<  word 
	       << "\" found in state " << current_id << endl
	       << "where BEGIN_OBSERVATIONS expected" << endl;
	  exit(1);
	}

      lst_lst_state_obs.push_back(current_lst_state_obs);
      
      
      if ( (word == NULL) || (strncmp(word, "END_STATE", 8) !=0) )
	{
	  cerr << "Invalid key_word: \""<<  word 
	       << "\" found in state " << current_id << endl
	       << "where END_STATE expected" << endl;
	  exit(1);	  
	}
      delete word;
      word = ReadWord(model_stream);
    } 

  if ( (_nb_state == 0) || ( (_nb_state == 1) && ( _is_bounded == true) ) ) 
    {
      cerr << "incorrect model file: no hidden state defined" << endl;
      exit(1);      
    }
  model_stream.close();
  
  if ( (_nb_state != (int)lst_ps_init.size()) || 
       (_nb_state != (int)lst_state_ids.size()) ||
       (_nb_state != (int)lst_state_trans.size()) ||
       (_nb_state != (int)lst_lst_state_obs.size()) )
    {
#ifdef LOG
      if (loglevel >=0) 
	{
	  logstream << "Error: Inconsitent list lengths in " << this << endl;
	}
#endif /* LOG */      
      exit(1);
    }

  _state_ids = new char* [_nb_state];
  _ps_init = new double [_nb_state];
  _transitions = new HmmTransitions* [_nb_state];
  _observations = new HmmObservations** [_nb_state];

  list<double>::iterator it_ps_init;
  list<char*>::iterator it_state_ids;
  list<HmmTransitions*>::iterator it_state_trans;
  list<list<HmmObservations*>*>::iterator it_lst_state_obs;
  list<HmmObservations*>::iterator it_state_obs;

  _dim_obs = 0;
  
  it_state_ids = lst_state_ids.begin();
  it_lst_state_obs = lst_lst_state_obs.begin(); 
  while ( ( strcmp(*it_state_ids, "bound") == 0 ) && 
	  ( it_lst_state_obs != lst_lst_state_obs.end() ) ) 
    {
      it_state_ids++;
      it_lst_state_obs++;
   }
  
  _dim_obs = (*(it_lst_state_obs))->size();
 
#ifdef LOG
      if (loglevel >=0) 
	{
	  logstream << "dim_obs: " << _dim_obs << endl;
	}
#endif /* LOG */      
      
      
      it_ps_init = lst_ps_init.begin();
  it_state_ids = lst_state_ids.begin();
  it_state_trans = lst_state_trans.begin();
  it_lst_state_obs = lst_lst_state_obs.begin();

  int istate;
  int iobs;
  for( istate = 0 ; istate < _nb_state; istate++ )
    {
      _ps_init[istate] = *it_ps_init;
      _state_ids[istate] = *it_state_ids;
      _transitions[istate] = *it_state_trans;
#ifdef LOG
      if (loglevel >=0) 
	{
	  logstream << "NbTrans : " << _transitions[istate]->NbTrans() << endl;
	}
#endif /* LOG */      
      
      if ( strcmp(*it_state_ids, "bound") == 0 )
	{
	  _bound_state = istate;
	} else if ( _dim_obs != (int)(*it_lst_state_obs)->size() ) {
	  cerr << "Number of observation defined in state " 
	       << _state_ids[istate] << " is not " << _dim_obs << endl;
	  exit(1);
	} else {
	  _observations[istate] = new HmmObservations* [_dim_obs];
	  it_state_obs = (*it_lst_state_obs)->begin();
	  for (iobs = 0 ; iobs < _dim_obs ; iobs++ )
	    {
	      _observations[istate][iobs] = *it_state_obs;
	      it_state_obs++;
	    }
	}
      
      it_ps_init++;
      it_state_ids++;
      it_state_trans++;
      it_lst_state_obs++;
    }

  if ( _is_bounded == true ) 
    {
      _observations[_bound_state] = new HmmObservations* [_dim_obs];
      istate = 0;
      while ( istate == _bound_state ) 
	{
	  istate++;
	}
      for ( iobs = 0 ; iobs < _dim_obs ; iobs++ ) 
	{
	  _observations[_bound_state][iobs] = 
	    new HmmObservations( *_observations[istate][iobs], true );
	}
    }
  this->Link();

  this->ObsCheck();

  this->PsInitCheck();

  
  
  _estep_segment = 0;
  _estep_overlap = 0;
   

  
  _vit_segment = 0;
  _vit_overlap = 0;
    
  
  _nb_frag = _seqset->NbSeq();

  _p_predic = NULL;
  _p_filtra = NULL;
  _p_smooth = NULL;  
 


#ifdef LOG
  if (loglevel >=1) 
    {
      logstream << "leaving HiddenMarkovModel constructor" << endl
		<< "of " << this << endl;
    }
#endif /* LOG */
}


void HiddenMarkovModel::AllocPVect()
{

  _p_predic = new double* [_estep_segment];
  _p_filtra = new double* [_estep_segment];
  _p_smooth = new double* [_estep_segment];

  int ipos;

  for ( ipos = 0 ; ipos < _estep_segment ; ipos++ )
    {
      _p_predic[ipos] = new double [_nb_state];
      _p_filtra[ipos] = new double [_nb_state];
      _p_smooth[ipos] = new double [_nb_state];
    }

}

void HiddenMarkovModel::DeletePVect()
{
  int ipos;

  for ( ipos = 0 ; ipos < _estep_segment ; ipos++ )
    {
      delete _p_predic[ipos];
      delete _p_filtra[ipos];
      delete _p_smooth[ipos];
    }

  delete _p_predic;
  delete _p_filtra;
  delete _p_smooth;  

  _p_predic = NULL;
  _p_filtra = NULL;
  _p_smooth = NULL;  
}

void HiddenMarkovModel::Link()
{
  int istate;
  int iobs;
  char* current_label;
  ObsSequence** current_obsseq;

#ifdef LOG
  if (loglevel >=4) 
    {
      logstream << "in HiddenMarkovModel::Link()" << endl;
    }
#endif 

  map<string, HmmObservations* > map_obs_by_id;
  map<string, HmmTransitions* > map_trans_by_id;
  map<string, int>* map_state_by_id;
  map<ObsSequence**, bool> map_obsseq;
  map<ObsSequence**, bool>::iterator it_obsseq;

  map_state_by_id = this->MapStateById();

  for(istate = 0 ; istate < _nb_state ; istate++)
    {
      current_label = _transitions[istate]->Label();
      if (current_label != NULL) 
	{
	  if ( map_trans_by_id.find((string)current_label) !=
	       map_trans_by_id.end() )
	    {
	      cerr << "The label: " << current_label << " found " 
		   << endl
		   << "in the " 
		   << "transitions of the state " 
		   << endl << _state_ids 
		   << " is already used" << endl;
	      exit(1);
	    }
	  map_trans_by_id.insert(make_pair((string)current_label, 
					 _transitions[istate]));
	}
      
      for ( iobs = 0 ; iobs < _dim_obs ; iobs++ )
	{
	  current_label = _observations[istate][iobs]->Label();
	  //cerr << "current_label " << current_label << endl;
	  if (current_label != NULL) 
	    {
	      if ( map_obs_by_id.find((string)current_label) !=
		   map_obs_by_id.end() )
		{
		  cerr << "The label: " << current_label << " found " 
		       << endl
		       << "in the " << iobs 
		       << "th observation of the state " 
		       << endl << _state_ids 
		       << " is already used" << endl;
		  exit(1);
		}
	      map_obs_by_id.insert(make_pair((string)current_label, 
					     _observations[istate][iobs]));
	    }
	}
    }
  
  for ( istate = 0 ; istate < _nb_state ; istate++ )
    {
      _transitions[istate]->Link(map_state_by_id, &map_trans_by_id);
      for ( iobs = 0 ; iobs < _dim_obs ; iobs++ )
	{
	  _observations[istate][iobs]->Link(&map_obs_by_id, _seqset);
	  current_obsseq = _observations[istate][iobs]->Seq();
	  if ( map_obsseq.find(current_obsseq) ==
	       map_obsseq.end() )
	    {
	      map_obsseq.insert(make_pair(current_obsseq, true));
	      //cerr << " current_obsseq " << current_obsseq << endl;
	    }
	}
    }  

  //cerr << "toto " << endl;
  _nb_seqs = map_obsseq.size();
  //cerr << "Number of ObsSeq = " << _nb_seqs << endl;
  _seqs = new ObsSequence* [_nb_seqs];
  _seqs_frag0 = new ObsSequence** [_nb_seqs];
  //_seqs_frag0 = new ObsSequence* [_nb_seqs];
  
  int iseq;
  it_obsseq = map_obsseq.begin();
  for (iseq = 0 ; iseq < _nb_seqs ; iseq++ )
    {
      _seqs[iseq] = NULL;
      _seqs_frag0[iseq] = it_obsseq->first;
      //cerr << _seqs_frag0[iseq] << endl;
      it_obsseq++;
    }

#ifdef LOG
  if (loglevel >=4) 
    {
      logstream << "end of HiddenMarkovModel::Link()" << endl;
    }
#endif 

}


void HiddenMarkovModel::ObsCheck()
{
  int iobsseq;
  int istate;
  map<string, bool> map_obsseq_identifier;
  map<string, bool>::iterator it_obsseq_identifier;
  char* current_seq_id;

  for ( istate = 0 ; istate < _nb_state ; istate++ )
    {
      for ( iobsseq = 0 ; iobsseq < _dim_obs ; iobsseq++ )
	{
	  current_seq_id = _observations[istate][iobsseq]->ObsSeqId();
	  if (map_obsseq_identifier.find(current_seq_id) 
	      != map_obsseq_identifier.end() )
	    {
	      cerr << "the same sequence: " << current_seq_id 
		   << " cannot be modelized with " << endl
		   << "2 observation model in state " 
		   << _state_ids[istate] << endl;
	      exit(1);
	    } else {
	      map_obsseq_identifier.insert(make_pair((string)current_seq_id,
						     true));
	    }
	}
      
      for (it_obsseq_identifier = map_obsseq_identifier.begin() ;
	   it_obsseq_identifier != map_obsseq_identifier.end() ;
	   it_obsseq_identifier++)
	{
	  map_obsseq_identifier.erase(it_obsseq_identifier);
	}
      
    }
  
  for ( iobsseq = 0 ; iobsseq < _dim_obs ; iobsseq++ )
    {
      current_seq_id = _observations[0][iobsseq]->ObsSeqId();      
      map_obsseq_identifier.insert(make_pair((string)current_seq_id,
					     true));
    }

  for ( istate = 1 ; istate < _nb_state ; istate++ )
    {
      for ( iobsseq = 0 ; iobsseq < _dim_obs ; iobsseq++ )
	{
	  current_seq_id = _observations[istate][iobsseq]->ObsSeqId();
	  if (map_obsseq_identifier.find(current_seq_id) 
	      == map_obsseq_identifier.end() )
	    {
	      cerr << "modelized sequences with observation models " << endl
		   << "in state: " << _state_ids[istate] << endl
		   << "are not compatible with those of the previous states "
		   << endl;
	      exit(1);
	    }
	}
    }
  
}


void HiddenMarkovModel::PsInitCheck()
{
  int nb_ps_uninit;
  double sum;
  int istate;

  nb_ps_uninit = 0;
  sum = 0;

  for ( istate = 0 ; istate < _nb_state ; istate++ )
    {
      if ( _ps_init[istate] < 0 - PRECISION )
	{
	  nb_ps_uninit++;
	} else if ( _ps_init[istate] < 0 ) {
	  _ps_init[istate] = 0;
	} else {
	  sum += _ps_init[istate];
	}
    }
  
  if ( nb_ps_uninit > 0 ) 
    {
      for ( istate = 0 ; istate < _nb_state ; istate++ )
	{
	  _ps_init[istate] = 1/(double)_nb_state;
	}      
    } else {
      for ( istate = 0 ; istate < _nb_state ; istate++ )
	{
	  _ps_init[istate] /= sum;
	}      
    }

}


HiddenMarkovModel::HiddenMarkovModel(const HiddenMarkovModel & templ)
{
#ifdef LOG
  if (loglevel >=1) 
    {
      logstream << "Inside HiddenMarkovModel copy constructor of " 
		<< this << endl << "with arg "
		<< "templ: " << &templ << endl;
    }
#endif /* LOG */

  int istate;
  int iobs;
  int iseq;

  _model_filename = new char[strlen(templ._model_filename) + 1];
  strcpy(_model_filename, templ._model_filename);

  _seqset = templ._seqset;
  _is_bounded = templ._is_bounded;
  _bound_state = templ._bound_state;
  
  _nb_state = templ._nb_state;
  _dim_obs = templ._dim_obs;

  _state_ids = new char* [_nb_state];

  _ps_init = new double [_nb_state];
  _transitions = new HmmTransitions* [_nb_state];  
  _observations = new HmmObservations** [_nb_state];

  for ( istate = 0 ; istate < _nb_state ; istate++ )
    {
      _state_ids[istate] = new char [ strlen(templ._state_ids[istate]) + 1 ];
      strcpy(_state_ids[istate], templ._state_ids[istate]);

      _ps_init[istate] = templ._ps_init[istate];
      _transitions[istate] = new HmmTransitions(*templ._transitions[istate]);
      _observations[istate] = new HmmObservations* [_dim_obs];
      for ( iobs = 0 ; iobs < _dim_obs ; iobs++ )
	{
	  _observations[istate][iobs] = 
	    new HmmObservations(*((HmmObservations*)templ._observations[istate][iobs]));
	  
	}
    }  
  
  _p_predic = NULL; 
  _p_filtra = NULL; 
  _p_smooth = NULL; 
  
  _estep_segment = templ._estep_segment;
  _estep_overlap = templ._estep_overlap;

  _vit_segment = templ._vit_segment;
  _vit_overlap = templ._vit_overlap;

  _nb_seqs = templ._nb_seqs;
  _nb_frag = templ._nb_frag;

  _seqs_frag0 = new ObsSequence ** [_nb_seqs];
  _seqs = new ObsSequence * [_nb_seqs];
  for (iseq = 0 ; iseq < _nb_seqs ; iseq++ )
    {
      _seqs[iseq] = NULL;
      _seqs_frag0[iseq] = templ._seqs_frag0[iseq];
    }

  this->Link();

#ifdef LOG
  if (loglevel >=1) 
    {
      logstream << "leaving HiddenMarkovModel constructor of " 
		<< this << endl << "with arg "
		<< "templ: " << &templ << endl;
    }
#endif /* LOG */
}


HiddenMarkovModel::~HiddenMarkovModel()
{
#ifdef LOG
  if (loglevel >=1) 
    {
      logstream << "inside HiddenMarkovModel destructor of " 
		<< this << endl;
    }
#endif /* LOG */

  int istate;
  int iobs;
  
  delete _model_filename;

  for ( istate = 0 ; istate < _nb_state ; istate++ )
    {
      delete _state_ids[istate];
      delete _transitions[istate];
      for (iobs = 0 ; iobs < _dim_obs ; iobs++ )
	{
	  delete _observations[istate][iobs];
	}
      delete _observations[istate];
    }
  delete _state_ids;
  delete _transitions;
  delete _observations;

  delete _ps_init;


#ifdef LOG
  if (loglevel >=1) 
    {
      logstream << "leaving HiddenMarkovModel destructor of " 
		<< this << endl;
    }
#endif /* LOG */
}
