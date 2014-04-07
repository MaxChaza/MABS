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

#include <cstring>
#include <cstdlib>
#include <list>
using namespace std;


#include "HmmTransitions.h"
#include "reading.h"
#include "const.h"




#ifdef LOG
extern int loglevel;
extern ofstream logstream;
#endif /* LOG */

HmmTransitions::HmmTransitions(istream &model_stream, char* state_id)
{
#ifdef LOG
  if (loglevel >=1) 
    {
      logstream << "Inside HmmTransitions constructor of " 
		<< this << endl << "with args "
		<< "model_stream: " << model_stream << " , " 
		<< "state_id: " << state_id << endl;
    }
#endif /* LOG */

  char* word;
  int current_type;
  double current_ptrans;

  list<char*> lst_type0_trans_state;
  list<char*> lst_tied_trans_state;
  list<double> lst_type0_trans_ptrans;
  list<char*> lst_type1_trans_state;
  list<double> lst_type1_trans_ptrans;
  list<double>* current_lst_ptrans;
  list<char*>* current_lst_state;
  list<char*>::iterator it_trans_state;
  list<double>::iterator it_trans_ptrans;

  _nb_trans = 0;
  _nb_trans_type0 = 0;
  _nb_trans_type1 = 0;
  _tied = false;
  _label = NULL;
  _state_id = new char [strlen(state_id)+1];
  strcpy(_state_id, state_id);
  
  word = ReadWord(model_stream);
  if ( (word != NULL) && (strncmp(word, "tied_to:", 6) == 0) )
    {
      delete word;
      _tied_to = ReadWord(model_stream);
      _tied = true;
      if (_tied_to == NULL) 
	{
	  cerr << "Error no label found following the key_word" 
	       << endl << "\"tied_to:\" in transitions definition of state "
	       << _state_id << endl;
	  exit(1);
	}
      current_lst_state = &lst_tied_trans_state;
      word = ReadWord(model_stream);     	  
      while ( (word != NULL) && (strncmp(word, "state:", 5) == 0) )
	{
	  delete word;
	  word = ReadWord(model_stream);     	  
	  if ( word != NULL )
	    {
	      current_lst_state->push_back(word);
	      _nb_trans++;
	      word = ReadWord(model_stream);     	  
	    } else {
	      cerr << "Error no state_id found following the key_word" 
		   << endl << "\"state:\" in the definition of the " 
		   << _nb_trans << "th " << "transition of state " 
		   << _state_id << " word " << word << " found" << endl;
	      exit(1);		  
	    }	      
	}
    } else {
      if ( (word != NULL) && (strncmp(word, "label:", 5) ==0) )
	{
	  _label = ReadWord(model_stream);
	  if (_label == NULL) 
	    {
	      cerr << "Error no label found following the key_word" 
		   << endl << "\"label:\" in transitions definition of state "
		   << _state_id << endl;
	      exit(1);
	    }

	  delete word;
 	  word = ReadWord(model_stream);     	  
	}
      while ( (word != NULL) && (strncmp(word, "type:", 4) == 0) )
	{
	  delete word;
	  _nb_trans++;
	  if ( ReadInt(model_stream, current_type) != true )
	    {
	      cerr << "integer not found following the keyword \"type:\""
		   << endl << "in the definition of the " << _nb_trans << "th "
		   << "transition of state " << _state_id << endl;
	      exit(1);
	    }
	  

	  
	  if ( current_type == 0 )
	    {
	      current_lst_state = &lst_type0_trans_state;
	      current_lst_ptrans = &lst_type0_trans_ptrans;
	    } else if ( current_type == 1 ) {
	      current_lst_state = &lst_type1_trans_state;
	      current_lst_ptrans = &lst_type1_trans_ptrans;	      
	    } else {
	      cerr << "Unknown type: " << current_type 
		   << endl << "in the definition of the " << _nb_trans << "th "
		   << "transition of state " << _state_id << endl;
	      exit(1);
	    }

	  word = ReadWord(model_stream);     	  
	  if ( (word != NULL) && (strncmp(word, "state:", 5) == 0) )
	    {
	      delete word;
	      word = ReadWord(model_stream);     	  
	      if ( word != NULL )
		{
		  current_lst_state->push_back(word);
		} else {
		  cerr << "Error no state_id found following the key_word" 
		       << endl << "\"state:\" in the definition of the " 
		       << _nb_trans << "th " << "transition of state " 
		       << _state_id << " word " << word << " found" << endl;
		  exit(1);		  
		}	      
	    } else {
	      cerr << "Error key_word \"state:\" not found" 
		   << " in the definition of the " 
		   << _nb_trans << "th " << "transition of state " 
		   << _state_id << " word " << word << " found" << endl;
	      exit(1);		  
	    }
	 
	  word = ReadWord(model_stream);     	  
	  if ( (word != NULL) && (strncmp(word, "ptrans:", 6) == 0) )
	    {
	      delete word;
	      if ( ReadDouble(model_stream, current_ptrans) == true )
		{
		  current_lst_ptrans->push_back(current_ptrans);
		} else {
		  cerr << "Error no real value found following the key_word" 
		       << endl << "\"ptrans:\" in the definition of the " 
		       << _nb_trans << "th " << "transition of state " 
		       << _state_id << endl;
		  exit(1);		  
		}	      
	    } else {
	      cerr << "Error key_word \"ptrans:\" not found" 
		   << " in the definition of the " 
		   << _nb_trans << "th " << "transition of state " 
		   << _state_id << endl;
	      exit(1);		  
	    }
	  word = ReadWord(model_stream);     	  
	}
    }
  
  if (_nb_trans == 0) 
    {
      cerr << "Error: no positive transition found" << endl
	   << " in the definition of the transitions of state " 
	   << _state_id << endl;
      exit(1);
    }

  if ( (word == NULL) || (strncmp(word, "END_TRANSITIONS", 15) != 0) ) 
    {
      cerr << "Unexpected end of observations," << endl 
	   << "keyword: \"END_OBSERVATIONS\" not found but " 
	   << word << " found in state " << _state_id <<  endl;
      exit(1);
    }
  delete word;

  _ptrans = new double [_nb_trans];
  _count = new double [_nb_trans];
  _to_state_id = new char* [_nb_trans];

  if (_tied != true )
    {
      _nb_trans_type0 = (int)lst_type0_trans_state.size();
      _nb_trans_type1 = (int)lst_type1_trans_state.size();
      
      int i = 0;
      it_trans_ptrans = lst_type1_trans_ptrans.begin();
      for ( it_trans_state = lst_type1_trans_state.begin() ;
	    it_trans_state != lst_type1_trans_state.end() ;
	    it_trans_state++ )
	{
	  _ptrans[i] = *it_trans_ptrans;
	  _to_state_id[i] = *it_trans_state;
	  it_trans_ptrans++;
	  i++;
	}
      
      it_trans_ptrans = lst_type0_trans_ptrans.begin();
      for ( it_trans_state = lst_type0_trans_state.begin() ;
	    it_trans_state != lst_type0_trans_state.end() ;
	    it_trans_state++ )
	{
	  _ptrans[i] = *it_trans_ptrans;
	  _to_state_id[i] = *it_trans_state;
	  it_trans_ptrans++;
	  i++;
	}
    } else if (_tied == true) {
      int i = 0;
      for ( it_trans_state = lst_tied_trans_state.begin() ;
	    it_trans_state != lst_tied_trans_state.end() ;
	    it_trans_state++ )
	{
	  _to_state_id[i] = *it_trans_state;
	  i++;
	}
      
    }
  
#ifdef LOG
  if (loglevel >=1) 
    {
      logstream << "leaving HmmTransitions constructor" << endl
		<< "of " << this << endl;
    }
#endif /* LOG */
}

HmmTransitions::~HmmTransitions()
{
#ifdef LOG
  if (loglevel >=1) 
    {
      logstream << "inside HmmTransitions destructor" << endl
		<< "of " << this << endl;
    }
#endif /* LOG */

  int itrans;
  
  delete _state_id;

  if ( _label != NULL )
    {
      delete _label;
    }
  
  if (_tied == true )
    {
      delete _tied_to;
    }

  for (itrans = 0 ; itrans < _nb_trans ; itrans++ )
    {
      delete _to_state_id[itrans];
    }
  delete _to_state_id;


  delete _to_state;

  if (_ptrans != NULL)
    {
      delete _ptrans;
    }
  
  if (_count != NULL)
    {
      delete _count;
    }

#ifdef LOG
  if (loglevel >=1) 
    {
      logstream << "leaving HmmTransitions destructor" << endl
		<< "of " << this << endl;
    }
#endif /* LOG */
}

char* HmmTransitions::Label()
{
  char* return_label;

  if (_label == NULL) 
    {
      return(NULL);
    }

  return_label = new char [strlen(_label)+1];
  strcpy(return_label, _label);
  
  return(return_label);
}


void HmmTransitions::Link(map<string, int> *map_state_by_id,
			  map<string, HmmTransitions* > *map_trans_by_id)
{
  
  int itrans;
  
#ifdef LOG
  if (loglevel >=4) 
    {
      logstream << "*************************" <<endl;
      logstream << "in HmmTransitions::Link()" << endl;
      logstream << "*************************" <<endl;
    }
#endif /* LOG */

  _to_state = new int [_nb_trans];

  for ( itrans = 0 ; itrans < _nb_trans ; itrans++ )
    {
      if ( map_state_by_id->find(_to_state_id[itrans]) 
	   == map_state_by_id->end() )
	{
	  cerr << "There is no state identifier " << _to_state_id[itrans] 
	       << endl
	       << "unknown identifier in transitions definition of " 
	       << "state " << _state_id << endl ;
	  exit(1);
	}
      _to_state[itrans] = map_state_by_id->find(_to_state_id[itrans])->second;
    }

  if(_tied != true)
    {
      return;
    }

  if ( map_trans_by_id->find(_tied_to) == map_trans_by_id->end() )
    {
      cerr << "There is no transitions labeled " << _tied_to << endl
	   << "unknown reference in transitions definition of " 
	   << "state " << _state_id << endl ;
      exit(1);
    }

  _primary_trans = 
    (HmmTransitions*)map_trans_by_id->find(_tied_to)->second;

  if (_nb_trans != _primary_trans->_nb_trans) 
    {
      cerr << "There is different number of transitions " 
	   << endl << "in these of state " <<  _state_id
	   << endl << "and these of state " << _primary_trans->_state_id
	   << endl << "tying impossible" << endl;
      exit(1);      
    } 

  _nb_trans_type0 = _primary_trans->_nb_trans_type0;
  _nb_trans_type1 = _primary_trans->_nb_trans_type1;

  int i;
  for ( i = 0 ; i < _nb_trans ; i++ )
    {
      _ptrans[i] = _primary_trans->_ptrans[i];
    }
}

void HmmTransitions::ResetCount()
{
  int i;
  for (i=0 ; i<_nb_trans ; i++) 
    {
      _count[i] = 0;
    }
}


void HmmTransitions::Print(ostream &ofile)
{
  int itrans;

  ofile << "\tBEGIN_TRANSITIONS" << endl;
  if (_tied == true) 
    {
      ofile << "\t\ttied_to: " << _tied_to << endl;
      for (itrans = 0 ; itrans < _nb_trans ; itrans++ )
	{
	  ofile << "\t\tstate: " << _to_state_id[itrans] << endl;
	}
    } else {
      if (_label != NULL) {
	ofile << "\t\tlabel: " << _label << endl;	
      }
      for (itrans = 0 ; itrans < _nb_trans ; itrans++ )
	{
	  if ( itrans < _nb_trans_type1 ) 
	    {
	      ofile << "\t\ttype: " << 1 << endl;
	    } else {
	      ofile << "\t\ttype: " << 0 << endl;
	    }
	  ofile << "\t\tstate: " << _to_state_id[itrans] << endl;
	  ofile << "\t\tptrans: " << _ptrans[itrans] << endl;
	}
    }

  ofile << "\tEND_TRANSITIONS" << endl;
}

void HmmTransitions::PreMStep()
{
  if ( _tied != true )
    {
      return;
    }
  
  int i;
  

  cerr << _primary_trans << " " << _state_id << endl; 
  for (i=0 ; i<_nb_trans ; i++) 
    {
      cerr << _primary_trans->_count[i] << " + " << _count[i] << endl;
      _primary_trans->_count[i] += _count[i];
    }
}

void HmmTransitions::MStep()
{
  if ( _tied == true )
    {
      return;
    }

  //cerr << "Inside MStep() HmmTransitions " << _state_id << endl;
  //cerr << "nb_type1 " << _nb_trans_type1 << endl;
  int i;
  double sum = 0;
  //cerr << " count " << endl;
  for (i=0 ; i<_nb_trans_type1 ; i++) 
    {
      //cerr << _count[i] << " " ;
      sum += _count[i];
    }  
  //cerr << endl;
  if (sum>0) {
    for (i=0 ; i<_nb_trans_type1 ; i++) 
      {
	_ptrans[i] = _count[i] / sum;
      } 
    sum = 0;
    for (i=_nb_trans_type1 ; i<_nb_trans ; i++) 
      {
	sum += _count[i];
      }  
    //cerr << " ptrans " << endl;
    for (i=0 ; i<_nb_trans_type1 ; i++) 
      {
	_ptrans[i] = (1-sum)*_ptrans[i];
	//cerr << _ptrans[i] << " " ;
      } 
    //cerr << endl;   
  } else {
    cout << "no update in HmmTransitions::MStep() due to zeros counts" << endl;
  }
}


void HmmTransitions::PostMStep()
{
  if ( _tied != true )
    {
      return;
    }
  
  int i;
  for (i=0 ; i<_nb_trans ; i++) 
    {
      _ptrans[i] = _primary_trans->_ptrans[i];
    }  
}


HmmTransitions::HmmTransitions(const HmmTransitions & templ)
{
#ifdef LOG
  if (loglevel >=1) 
    {
      logstream << "Inside HmmTransitions constructor of " 
		<< this << endl << "with args "
		<< "templ: " << &templ << endl;
    }
#endif /* LOG */
  
  int istate;

  _state_id = new char [strlen(templ._state_id)+1];
  strcpy(_state_id, templ._state_id);

  _nb_trans = templ._nb_trans;
  _nb_trans_type0 = templ._nb_trans_type0;
  _nb_trans_type1 = templ._nb_trans_type1;


  if ( templ._label != NULL )
    {
      _label = new char [strlen(templ._label)+1];
      strcpy(_label, templ._label);
    } else {
      _label = NULL;
    }

  _tied  = templ._tied;
  if ( _tied == true )
    {
      _tied_to  = new char [strlen(templ._tied_to)+1];
      strcpy(_tied_to, templ._tied_to);
      
      _primary_trans = templ._primary_trans;
    } else {
      _tied_to = NULL;
      _primary_trans = NULL;
    }
  
  
  _to_state_id = new char* [_nb_trans];
  _to_state = new int [_nb_trans];
  for ( istate = 0 ; istate < _nb_trans ; istate++ )
    {
      _to_state_id[istate] = 
	new char [strlen(templ._to_state_id[istate]) + 1 ];
      strcpy(_to_state_id[istate], templ._to_state_id[istate]);
      _to_state[istate] = templ._to_state[istate];
    }
  

  if (templ._ptrans != NULL)
    {
      _ptrans = new double [_nb_trans];
      for ( istate = 0 ; istate < _nb_trans ; istate++ )
	{
	  _ptrans[istate] = templ._ptrans[istate];
	}      
    } else {
      _ptrans = NULL;
    }

  if (templ._count != NULL)
    {
      _count = new double [_nb_trans];
      for ( istate = 0 ; istate < _nb_trans ; istate++ )
	{
	  _count[istate] = templ._count[istate];
	}      
    } else {
      _count = NULL;
    }

#ifdef LOG
  if (loglevel >=1) 
    {
      logstream << "leaving HmmTransitions constructor" << endl
		<< "of " << this << endl;
    }
#endif /* LOG */
}


int HmmTransitions::Simulation()
{
  double r;

  double sum_trans;
  int r_trans;

  r  = (double) rand() / (double) RAND_MAX; 
	  
  sum_trans = PTrans(0);
  r_trans = 0;
  while ( r > sum_trans ) 
    {
      r_trans++;
      sum_trans += PTrans(r_trans); 
    }
  return(ToState(r_trans));

}
