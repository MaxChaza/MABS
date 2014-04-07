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
#include <iostream>
#include <fstream>
#include <cstring>
#include <cstdlib>
using namespace std;


#include "HmmObservations.h"
#include "reading.h"
#include "const.h"
#include "int2char.h"


#ifdef LOG
extern int loglevel;
extern ofstream logstream;
#endif /* LOG */

HmmObservations::HmmObservations(istream &model_stream,
				       char* state_id,
				       char* seq_id,
				       SequenceSet *seqset)
{
#ifdef LOG
  if (loglevel >=1) 
    {
      logstream << "Inside HmmObservations constructor of " 
		<< this << endl << "with arg "
		<< "model_stream: " << model_stream << endl;
    }
#endif /* LOG */

  _label = NULL;
  _tied_to = NULL;
  _pobs = NULL;  
  _count = NULL;
  _nb_excepted_cod = 0;
  _state_id = new char [strlen(state_id)+1];
  strcpy(_state_id, state_id);

  _seq_id = new char [strlen(seq_id)+1];
  strcpy(_seq_id, seq_id);
  _coding = seqset->RetrieveCodingSeq(seq_id);
  _nmodal = _coding->Nmodal();
  cout << "NMODAL " << _nmodal << endl;
  _seq_frag0 = NULL;
  _seq = NULL;
  _pseudo_order = -1;

  char* word;
  
  
#ifdef LOG
  logstream << "before tied_to " << endl;
#endif
  word = ReadWord(model_stream);
  if ( (word != NULL) && (strncmp(word, "tied_to:", 6) == 0) )
    {
      delete word;
      _tied_to = ReadWord(model_stream);
      word = ReadWord(model_stream);
      if (_tied_to == NULL) 
	{
	  cerr << "Error no label found following the key_word" 
	       << endl << "\"tied_to:\" in observation definition of state "
	       << _state_id << endl;
	  exit(1);
	}
    } else if ( (word != NULL) && (strncmp(word, "label:", 5) ==0) ) {
      _label = ReadWord(model_stream);
      if (_label == NULL) 
	{
	  cerr << "Error no label found following the key_word" 
	       << endl << "\"label:\" in observation definition of state "
	       << _state_id << endl;
	  exit(1);
	}
      delete word;
      word = ReadWord(model_stream);     	  
    }
    
#ifdef LOG
  logstream  << "before type " << endl;
#endif
  if ( (word != NULL) && (strncmp(word, "type:", 4) == 0) )
    {
      delete word;
      if ( ReadInt(model_stream, _type) != true )
	{
	  cerr << "integer not found following the keyword \"type:\""
	       << endl << "in the definition of the observation "
	       << "of state " << _state_id << endl;
	  exit(1);
	} else if ( (_type > 4) || (_type < 0) ) {
	      cerr << "Unknown type: " << _type 
		   << endl << "in the definition of the "
		   << "observation of state " << _state_id << endl;
	      exit(1);	  
	}
    } else {
      cerr << "keyword \"type:\" not found in the definition " 
	   << "of the observation "
	   << "of state " << _state_id << endl
	   << "word: \"" << word << "\" found " << endl;
      exit(1);
    }


#ifdef LOG
  logstream  << "before type 2 || 3" << endl;
#endif
  if ( (_type == 2) || (_type == 3) ) 
    {
    if ( _tied_to == NULL ) 
      {
	cerr << "type 2 and 3 correspond to tied observation, " << endl
	     << "keyword \"tied_to:\" not found in the definition " << endl
	     << "of type " << _type << " observation of state " 
	     << _state_id << endl;
	exit(1);
      }
    }

  word = ReadWord(model_stream);     	  
  if ( (_type == 0) || (_type == 1) || (_type == 4) )
    {
      cout << "(_type == 0) || (_type == 1) || (_type == 4)" << endl;
      if ( (word != NULL) && (strncmp(word, "order:", 5)==0) )
	{
	  delete word;
	  if ( ReadInt(model_stream, _order) != true )
	    {
	      cerr << "integer not found following the keyword \"order:\""
		   << endl << "in the definition of the observation "
		   << "of state " << _state_id << endl;
	      exit(1);
	    } else if ( _order < 0 ) {
	      cerr << "order must be positive or null" 
		   << endl << _order << " found in the definition " 
		   << "of the type " << _type << " observation "
		   << "of state " << _state_id << endl;
	      exit(1);
	    }
	  cout << "order " << _order << endl;
	  cout << "nmodal " << _nmodal << endl;
	  _nb_obs = 0;
	  int i;
	  for( i = 0 ; i <= _order ; i++ )
	    {
	      _nb_obs += (int)pow((double)_nmodal, i+1);
	    }
	  //_count = new double [_nb_obs + 1];
	  //_count += 1;
	  //_seq_frag0 = seqset->RetrieveObsSeq(_seq_id, _order);
	} else {
	  cerr << "keyword \"order:\" not found in the definition " 
	       << "of the type " << _type << " observation "
	       << "of state " << _state_id << endl
	       << "word: \"" << word << "\" found " << endl;
	  exit(1);	  
	}

      word = ReadWord(model_stream);     	  
      cout << word << endl;
      if(strncmp(word, "pobs:", 4) != 0 )
	{
	  cerr << "Invalid keyword \""<< word 
	       << "\" where \"pobs:\" expected  in the definition " 
	       << "of the type " << _type << " observation "
	       << "of state " << _state_id
	       << endl; 
	  exit(1);
	} else {
	  delete word;
	}

      cout << "apres pobs" << endl;
      if ( (CheckDouble(model_stream) != true) && 
	   ( (_type==1) || (_type==4) )  ) 
	{
	  word = ReadWord(model_stream);     	  
	  if  ( (word == NULL) || (strncmp(word, "random", 6) != 0) )
	    {
	      cerr << "Invalid keyword \""<< word 
		   << "\" where \"random\" or double expected  "
		   << "in the definition " 
		   << "of the type " << _type << " observation "
		   << "of state " << _state_id
		   << endl;
	      exit(1);
	    } else {
	      delete word;
	      _pobs = NULL; //cerr << "NULL" << endl;
	    }
	} else if (CheckDouble(model_stream) != true) {
	  cerr << "double expected after the keyword  \"pobs:\"" << endl
	       << "in the definition " 
	       << "of the type " << _type << " observation "
	       << "of state " << _state_id
	       << endl;
	  exit(1);
	} else if ( this->ReadAndCheckPObs(model_stream) != true ) {
	  cerr << "Error in reading pobs in the definition " 
	       << "of the type " << _type << " observation "
	       << "of state " << _state_id
	       << endl;  
	  exit(1);
	}
      cout << "apres readandcheck" << endl;

      word = ReadWord(model_stream);
      if ( (word != NULL) && (strncmp(word, "excepted:", 6) ==0) 
	   && ((_type == 1) || (_type == 4)) )
	{
	  if (_type == 4) 
	    {
	      cerr << "Error: excepted word in self-complementary state "
		   << "are not currently implemented" << endl;
	      exit(1);
	    }
	  delete word;

	  //int lg_max_excepted = 0;
	  word = ReadWord(model_stream);		    
	  while (strncmp(word, "END_OBSERVATIONS", 16) != 0)
	    {
	      // forbidden words case...
	      (_excepted).push_back(word);
	      word = ReadWord(model_stream);		    
	    }
		      
	  
	  if ( (_excepted).size() > 0)
	    {
	      list<char*>::iterator it_excep;
	      it_excep=(_excepted).begin();
	      int lg_excepted_word = (int)strlen(*it_excep);

	      _nb_excepted_cod = 0;
	      for(it_excep=(_excepted).begin(); 
		  it_excep!=(_excepted).end(); it_excep++)
		{
		  if ( (int)strlen(*it_excep) != lg_excepted_word ) 
		    {
		      cerr << "Error: all excepted word must be the same length "
			   << endl;
		      exit(1);		      
		    }
		  _nb_excepted_cod++;
		}	
	      
	      if ( lg_excepted_word >= _order + 1 ) 
		{
		  _excepted_cod = new int [_nb_excepted_cod];
		  
		  int i = 0;
		  for(it_excep=(_excepted).begin(); 
		      it_excep!=(_excepted).end(); it_excep++)
		    {
		      _excepted_cod[i] = _coding->Coding(*it_excep + 
							 strlen(*it_excep) - 1, 
							 lg_excepted_word - 1);
		      cout << "i " << i << " " << _excepted_cod[i] << endl;
		      i++;
		    }
		}


	      if ( lg_excepted_word > _order + 1) 
		{
		  _pseudo_order = _order;
		  _order = lg_excepted_word - 1;
		  int new_nb_obs = ((int)pow((double)_nmodal, _order + 2) - 1)/(int)(_nmodal - 1) - 1;
		  
		  if ( _pobs != NULL ) 
		    {
		      double *new_pobs;
		      new_pobs = new double [new_nb_obs + 1];
		      new_pobs = new_pobs + 1;
		      
		      for ( int iobs = -1 ; iobs < _nb_obs ; iobs++ ) 
			{
			  new_pobs[iobs] = _pobs[iobs];
			}
		      _pobs = _pobs - 1;
		      delete [] _pobs;
		      _pobs = new_pobs;
		      cout << _pobs << endl;
		      _coding->DeducePobsFromInfOrder(_pobs, _pseudo_order, 
						      _order, 
						      _nb_excepted_cod, 
						      _excepted_cod);
		      cout << _pobs << endl;
		    }
		  _nb_obs = new_nb_obs;
		} else if (lg_excepted_word != _order + 1) {
		  cerr << "Error: excepted word length must be compatible "
		       << "with observation order (i.e > order)" << endl;
		  exit(1);
		}
	      	      
	    }
	  

	  
	}
      
    }

  if( (word == NULL) || strncmp(word, "END_OBSERVATIONS", 15) !=0 ) 
    {
      cerr << "Unexpected end of observations," << endl 
	   << "keyword: \"END_OBSERVATIONS\" not found but " 
	   << word << " found" << endl;
    }
  delete word;

 
  if ( (_type == 0) || (_type == 1) || (_type == 4) )
    {
      _count = new double [_nb_obs + 1];
      _count += 1;
      _seq_frag0 = seqset->RetrieveObsSeq(_seq_id, _order);      
    }

#ifdef LOG
  if (loglevel >= 3) {
    logstream << "_nmodal = " << _nmodal << " "
	      << "_nb_obs = " << _nb_obs << " "
	      << "_pobs = " << _pobs << " " 
	      << "_count = " << _count << " "
	      << "_label = " << _label << " "
	      << "_state_id = " << _state_id << " "
	      << "_seq_id = " << _seq_id << " "
	      << "_tied_to = " << _tied_to << " " 
	      << "_order = " << _order << endl;
    
  }

  if (loglevel >=1) 
    {
      logstream << "leaving HmmObservations constructor" << endl
		<< "of " << this << endl;
    }
#endif /* LOG */  
}


bool HmmObservations::ReadAndCheckPObs(istream &model_stream)
{
  int j;
  int i;
  double sum;

	  
  cout << "ReadAndCheckPObs toto _pobs " << _pobs << " " << _nb_obs << endl;
  _pobs = new double [_nb_obs + 1];
  cout << "_pobs " << _pobs << endl;
  _pobs = _pobs+1;
  _pobs[-1] = 1; // corresponding to unknown observation
  
  cout << "apres alloc nb_obs " << _nb_obs << endl;
  for ( i = 0 ; i < _nb_obs ; i++ ) 
    {
      cout << i << endl;
      if ( ReadDouble(model_stream, _pobs[i]) != true )
	{
	  cerr << _nb_obs << " double values expected following "
	       << "the keyword  \"pobs:\"" 
	       << endl
	       << "in the definition " 
	       << "of the type " << _type << " observation "
	       << "of state " << _state_id
	       << endl;	
	  exit(1);
	}
    }
  
  i = 0;
  while ( i < _nb_obs )
    {
      sum = 0;
      for( j = 0 ; j < _nmodal ; j++ )
	{
	  if ( _pobs[j+i] <  0 )
	    {
	      if ( _pobs[j+i] <  0 - PRECISION )
		{
		  cerr << "Error: Negative probability value " 
		       << _pobs[j+i] << endl;
		  return(false);
		} else {
		  _pobs[j+i] = 0;
		}
	    }
	  if ( _pobs[j+i] >  1 )
	    {
	      if ( _pobs[j+i] >  1 + PRECISION )
		{
		  cerr << "Error: greater than 1 probability value " 
		       << _pobs[j+i] << endl;
		  return(false);
		} else {
		  _pobs[j+i] = 1;
		}
	    }
	  sum += _pobs[i+j];
	}
      if ( (sum < 1 - PRECISION) || (sum > 1 + PRECISION) )
	{
	  cerr << "Error: sum of probas values from " << i << " to " 
	       << i+_nmodal - 1 << " must be 1, " 
	       << sum << " found" << endl;
	  return(false);	  
	} else {
	  for( j = 0 ; j < _nmodal ; j++ )
	    {
	      _pobs[j+i] /= sum;
	    }
	}
      i += _nmodal;
    }
  
  return(true); 
}

void HmmObservations::TiedInitPObs()
{
  int iobs;
  
  if ( (_type != 2) && (_type != 3) ) 
    {
      return;
    }
  

  cerr << "TIED INIT" << endl; 

  if (_type == 2)
    {
      for ( iobs = 0 ; iobs < _nb_obs ; iobs++ ) 
	{
	  _pobs[iobs] = _primary_obs->_pobs[iobs];
	}      
    }
  if (_type == 3)
    {
      if (_order != 0)
	{
	  cerr << "sorry complementary tide observations of order > 0 are currently not supported" << endl;
	  exit(1);
	}
      for ( iobs = 0 ; iobs < _nb_obs ; iobs++ ) 
	{
	  _pobs[iobs] = _primary_obs->_pobs[_nb_obs - iobs -1];
	}      
    }
  
}

void HmmObservations::RandInitPObs()
{
  if ( (_pobs != NULL) || (_type == 2) || (_type == 3) )
    {
      return;
    }

#ifdef LOG
  if (loglevel >=1) 
    {
      logstream << "RandInitPobs  " << _state_id << " " << this << endl;
    }
#endif

  _pobs = new double [_nb_obs + 1];
  _pobs = _pobs+1;
  //cerr << "_pobs " << _pobs << endl;
  _pobs[-1] = 1; // corresponding to unknown observation

  if ( _pseudo_order < 0 ) 
    {
      _coding->RandomPObsInit(_pobs, _order, _nb_excepted_cod, _excepted_cod);
    } else {
      _coding->RandomPObsInit(_pobs, _pseudo_order, 0 , NULL);
      _coding->DeducePobsFromInfOrder(_pobs, _pseudo_order, 
				      _order, _nb_excepted_cod, 
				      _excepted_cod);
      for (int i = 0 ; i < _nb_obs ; i++) {
	cout << i << " - " << _pobs[i] << endl;
      }
    }
}


void HmmObservations::ResetCount()
{
  int i;
  for(i=-1 ; i<_nb_obs ; i++) { _count[i] = 0; }  
}

char* HmmObservations::Label()
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

void HmmObservations::Link(map<string, HmmObservations* > *map_obs_by_id,
			      SequenceSet* seqset)
{
  if ( (_type == 0) || (_type == 1) || (_type == 4) )
    {
      return;
    }
  
  if ( map_obs_by_id->find(_tied_to) == map_obs_by_id->end() )
    {
      cerr << "There is no observation labeled " << _tied_to << endl
	   << "unknown reference in observation definition of " 
	   << "state " << _state_id << endl ;
      exit(1);
    }

  _primary_obs = (HmmObservations*)map_obs_by_id->find(_tied_to)->second;
  _order = _primary_obs->_order;
  _nb_obs = _primary_obs->_nb_obs;
  _coding = _primary_obs->_coding;
  _seq_frag0 = seqset->RetrieveObsSeq(_seq_id, _order);
  cerr << " _seq_frag0 " << _seq_frag0 << endl;

  _pobs = new double [_nb_obs + 1];
  _pobs += 1;
  _pobs[-1] = 1;
  _count = new double [_nb_obs + 1];
  _count += 1;
}


char* HmmObservations::ObsSeqId()
{
  char* return_seq_id;
  return_seq_id = new char [strlen(_seq_id) + 1];
  strcpy(return_seq_id, _seq_id);
  
  return(return_seq_id);
}
  

ObsSequence** HmmObservations::Seq()
{
  return((ObsSequence**)_seq_frag0);
}

void HmmObservations::SetFrag(int ifrag)
{
  _seq = _seq_frag0[ifrag];
}

void HmmObservations::Print(ostream &ofile)
{
  int iword;
  int iord;
  int i;
  int j;
  int print_order;

  if (_pseudo_order < 0) {
    print_order = _order;
  } else {
    print_order = _pseudo_order;
  }

  ofile << "\tBEGIN_OBSERVATIONS" << endl;
  ofile << "\t\tseq: " << _seq_id << endl;
  if (_label != NULL)
    {
      ofile << "\t\tlabel: " << _label << endl;
    } else if (_tied_to != NULL) {
      ofile << "\t\ttied_to: " << _tied_to << endl;
    }
  ofile << "\t\ttype: " << _type << endl;
  if (_tied_to == NULL) {
    ofile << "\t\torder: " << print_order << endl;
    ofile << "\t\tpobs:" << endl;

    iword = 0;
    iord = 0;
    for ( iord = 0 ; iord <= print_order ; iord++)
      {
	for ( i = 0 ; i < (int)pow((double)_nmodal, iord) ; i++ )
	  {
	    ofile << "\t\t\t" ;
	    for ( j = 0 ; j < _nmodal ; j++ )
	      {
		ofile << " " << _pobs[iword] ;	
		iword++;
	      }
	    ofile << endl;
	  }
	ofile << endl;
      }
    if ( _excepted.size() > 0 )
      {
	list<char*>::iterator it_excepted;
	ofile << "\t\texcepted: " << endl;	
	for ( it_excepted  = _excepted.begin() ; 
	      it_excepted  != _excepted.end() ; it_excepted++ )
	  {
	    ofile << "\t\t\t" << *it_excepted << endl;
	  }
      }
  } 
  
  ofile << "\tEND_OBSERVATIONS" << endl;
}


void HmmObservations::PreMStep()
{
  if ( (_type == 0) || (_type == 1) ||  (_type == 4) )
    {
      return;
    }

  if ( _type == 2  )
    {
      int iobs;
      cerr << "identic state " << _state_id << endl;
      for ( iobs = 0 ; iobs < _nb_obs ; iobs++ )
	{
	  cerr << _primary_obs->_count[iobs] << " + " 
	       << _count[iobs] << " : ";
	  _primary_obs->_count[iobs] += _count[iobs] ;
	}
     cerr << endl;
    }
  if ( _type == 3 )
    {
      if ( _order != 0 )
	{
	  cerr << "reverse complementary tied states of order > 0 are not yet allowed ! sorry..." << endl;
	  exit(1);
	}
      int iobs;
      cerr << "complement state " << _state_id << endl;
      for ( iobs = 0 ; iobs < _nb_obs ; iobs++ )
	{
	  cerr << _primary_obs->_count[iobs] << " + " 
	       << _count[_nb_obs - iobs -1] << " : ";
	  _primary_obs->_count[iobs] += _count[_nb_obs - iobs - 1] ;
	}
     cerr << endl;
    }

  if ( _type == 4 )
    {
      cerr << "self complement states are not yet allowed ! sorry..." << endl;
      exit(1);      
    }
}


void HmmObservations::MStep()
{
  int iord;
  int iword;
  int i;
  int j;
  int shift;
  //double sum;

  if ( (_type != 1) && (_type != 4) )
    {
      return;
    }

  if ( _type == 4 )
    {
      // inv-complementary add counts
    }



  // deduce n-1count 
  iword = _nb_obs - 1;
  for ( iord = _order ; iord > 0 ; iord-- )
    {
      shift = (int)pow((double)_nmodal, iord+1);
      for ( i = 0  ; i < _nmodal ; i++ )
	{
	  for ( j = 0 ; j < (int)pow((double)_nmodal, iord) ; j++ )
	    {
	      _count[iword - shift] += _count[iword];
	      // cerr << iword - shift << " += " << iword << endl;
	      iword--;
	    }
	  shift -= (int)pow((double)_nmodal, iord);
	}
    }

  //cerr << "pobs state " << _state_id << endl;
  //cerr << "_order " << _order << endl;
  //cerr << "_pseudo_order " << _pseudo_order << endl;
  //cout << "COUNT " << _count[60] << endl;
  
  if (_pseudo_order < 0) 
    {
      _coding->MStepPObs(_pobs, _order, _count);
    } else {
      _coding->MStepPObsPseudoOrder(_pseudo_order, _order, _pobs, _count,
				    _nb_excepted_cod, 
				    _excepted_cod);    
      _coding->DeducePobsFromInfOrder(_pobs, _pseudo_order, _order, 
				      _nb_excepted_cod, 
				      _excepted_cod);
    }



#ifdef LOG
  if (loglevel >=2) 
    {

      logstream << "count state " << _state_id << endl;
      iword = 0;
      while ( iword < _nb_obs )
	{
	  logstream << _count[iword] << " ";
	  iword++;
	} 

      logstream << endl;
      
    }
#endif /* LOG */
  
}


void HmmObservations::PostMStep()
{
  if ( (_type == 0) || (_type == 1) ||  (_type == 4) )
    {
      return;
    }

  if ( _type == 2 )
    {
      int iobs;
      for ( iobs = 0 ; iobs < _nb_obs ; iobs++ )
	{
	  _pobs[iobs] = _primary_obs->_pobs[iobs];
	}
    }

  if ( _type == 3 )
    {
      if ( _order != 0 )
	{
	  cerr << "reverse complementary tied states of order > 0 are not yet allowed ! sorry..." << endl;
	  exit(1);
	}
      int iobs;
      for ( iobs = 0 ; iobs < _nb_obs ; iobs++ )
	{
	   _pobs[_nb_obs - iobs - 1] = _primary_obs->_pobs[iobs];
	}
    }

#ifdef LOG
  if (loglevel >=3) 
    {
      logstream << "PostMStep() state  " << _state_id << endl;
      int iobs;
      for ( iobs = 0 ; iobs < _nb_obs ; iobs++ )
	{
	   logstream << _pobs[iobs] << " ";
	}
      logstream << endl;
    }
#endif

}



HmmObservations::~HmmObservations()
{
#ifdef LOG
  if (loglevel >=1) 
    {
      logstream << "inside HmmObservations destructor" << endl
		<< "of " << this << endl;
    }
#endif /* LOG */

  if (_label != NULL )
    {
      delete _label;
    }

  delete _state_id;
  delete _seq_id;
  
  if (_pobs != NULL)
    {
      _pobs -= 1;
      delete _pobs;
    }
  if (_count != NULL)
    {
      _count -= 1;
      delete _count;
    }

 if ( _nb_excepted_cod > 0 )
   {
     delete _excepted_cod;
   }
 
 if ( _excepted.size() > 0 )
   {
     list<char*>::iterator it_excepted;

     for ( it_excepted = _excepted.begin() ; 
	   it_excepted != _excepted.end() ;
	   it_excepted++ )
       {
	 delete *it_excepted;
       }    
   }

 if ( _tied_to != NULL )
   {
     delete _tied_to;
   }

#ifdef LOG
  if (loglevel >=1) 
    {
      logstream << "leaving HmmObservations destructor" << endl
		<< "of " << this << endl;
    }
#endif /* LOG */  
}



HmmObservations::HmmObservations(HmmObservations & templ)
{
#ifdef LOG
  if (loglevel >=1) 
    {
      logstream << "Inside HmmObservations constructor of " 
		<< this << endl << "with arg "
		<< "templ: " << &templ << endl;
    }
#endif /* LOG */

  int iobs;

  _nmodal = templ._nmodal;
  _coding = templ._coding;

  if (templ._label != NULL)
    {
      _label = new char [strlen(templ._label) + 1];
      strcpy(_label, templ._label);
    } else {
      _label = NULL;
    }


  _state_id = new char [strlen(templ._state_id) + 1];
  strcpy(_state_id, templ._state_id);
  _seq_id = new char [strlen(templ._seq_id) + 1];
  strcpy(_seq_id, templ._seq_id);
 
  _order = templ._order; 
  _pseudo_order = templ._pseudo_order;
  _nb_obs = templ._nb_obs;

  if (templ._pobs != NULL)
    {
      _pobs = new double [_nb_obs + 1];
      _pobs += 1;
      for (iobs = -1 ; iobs < _nb_obs ; iobs++ )
	{
	  _pobs[iobs] = templ._pobs[iobs]; 
	}
    } else {
      _pobs = NULL;
    }

  if (templ._count != NULL)
    {
      _count = new double [_nb_obs + 1];
      _count += 1;
      for (iobs = -1 ; iobs < _nb_obs ; iobs++ )
	{
	  _count[iobs] = templ._count[iobs]; 
	}
    } else {
      _count = NULL;
    }


  _type = templ._type;
  

  
  _seq_frag0 = templ._seq_frag0;
  _seq = NULL;

  if ( templ._tied_to != NULL )
    {
      _tied_to = new char [strlen(templ._tied_to) + 1];
      strcpy(_tied_to, templ._tied_to);
      //_primary_obs = templ._primary_obs;
      _primary_obs = NULL;
    } else {
      _tied_to = NULL;
      _primary_obs = NULL;
    }

  if (templ._excepted.size() > 0)
    {
      list<char*>::iterator it_excepted;
      char* word;

      for ( it_excepted = templ._excepted.begin() ; 
	    it_excepted != templ._excepted.end() ;
	    it_excepted++ )
	{
	  word = new char [strlen(*it_excepted) + 1];
	  strcpy(word, *it_excepted);
	  _excepted.push_back(word);
	}
      

    }

  _nb_excepted_cod = templ._nb_excepted_cod;
  
  _excepted_cod = new int [_nb_excepted_cod];
  for(int i=0 ; i<_nb_excepted_cod ; i++)
    {
      _excepted_cod[i] = templ._excepted_cod[i];
    }

#ifdef LOG
  if (loglevel >= 3) {
    logstream << "_nmodal = " << _nmodal << " "
	      << "_nb_obs = " << _nb_obs << " "
	      << "_pobs = " << _pobs << " " 
	      << "_count = " << _count << " "
	      << "_label = " << _label << " "
	      << "_state_id = " << _state_id << " "
	      << "_seq_id = " << _seq_id << " "
	      << "_tied_to = " << _tied_to << " " 
	      << "_order = " << _order << endl;
    
  }
  
  if (loglevel >=1) 
    {
      logstream << "leaving HmmObservations constructor" << endl
		<< "of " << this << endl;
    }
#endif /* LOG */  
}


HmmObservations::HmmObservations(HmmObservations & templ, bool bounded)
{
#ifdef LOG
  if (loglevel >=1) 
    {
      logstream << "Inside Bound HmmObservations constructor of " 
		<< this << endl << "with arg "
		<< "templ: " << &templ << endl;
    }
#endif /* LOG */

  if (bounded != true) {
    cerr << "bound must be true" << endl;
    exit(1);
  }

  int iobs;

  _nmodal = templ._nmodal;
  _coding = templ._coding;

  _label = NULL;


  _state_id = new char [strlen("bound") + 1];
  strcpy(_state_id, "bound");
  _seq_id = new char [strlen(templ._seq_id) + 1];
  strcpy(_seq_id, templ._seq_id);
 
  _order = templ._order; 

  _nb_obs = templ._nb_obs;

  _pobs = new double [_nb_obs + 1];
  _pobs += 1;
  for (iobs = -1 ; iobs < _nb_obs ; iobs++ )
    {
      _pobs[iobs] = 0; 
    }

  _count = new double [_nb_obs + 1];
  _count += 1;
  for (iobs = -1 ; iobs < _nb_obs ; iobs++ )
    {
      _count[iobs] = 0; 
    }
  
  
  _type = 0;
  

  
  _seq_frag0 = templ._seq_frag0;
  _seq = NULL;

  _tied_to = NULL;
  _primary_obs = NULL;
  
  _nb_excepted_cod = 0;
  
  _excepted_cod = NULL;

#ifdef LOG
  if (loglevel >= 3) {
    logstream << "_nmodal = " << _nmodal << " "
	      << "_nb_obs = " << _nb_obs << " "
	      << "_pobs = " << _pobs << " " 
	      << "_count = " << _count << " "
	      << "_label = " << _label << " "
	      << "_state_id = " << _state_id << " "
	      << "_seq_id = " << _seq_id << " "
	      << "_tied_to = " << _tied_to << " " 
	      << "_order = " << _order << endl;
    
  }
  
  if (loglevel >=1) 
    {
      logstream << "leaving HmmObservations constructor" << endl
		<< "of " << this << endl;
    }
#endif /* LOG */  
}


void HmmObservations::ObsSimulation(int ipos, int* observations_path)
{
  double r;
  int k;
  int cod;
  

  int simul_order;
  double sum_pobs;
  int prefix_word;

  r = (double) rand() / (double) RAND_MAX;
  
  if ( ipos >= _order )
    {
      simul_order = _order;
    } else {
      simul_order = ipos;
    }
  
  prefix_word = 0;
  for(k = 1 ; k <= simul_order ; k++)
    {
      prefix_word += (int)pow((double)_nmodal, k) 
	+ observations_path[ipos - k] * (int)pow((double)_nmodal, k);
    }
  
  cod = 0;
  sum_pobs = _pobs[prefix_word];
  while ( (r > sum_pobs) && (cod < _nmodal) )
    {
      cod++;
      prefix_word++;
      sum_pobs += _pobs[prefix_word];
    }

  observations_path[ipos] = cod;
}


void HmmObservations::PrintObsSimulation(int* observations_path, 
					   long lg, int id_num)
{
  long ibase;
  char* out_filename;
  char* num_char;
  char* obs_suffix;

  cerr << "dans printObsSimulation" << endl;
  

  obs_suffix = _coding->SuffixSeq();
  num_char = int2char(id_num);
  out_filename = new char [strlen("simulated_") + strlen(num_char) 
			  + strlen(".") + strlen(obs_suffix) + 1];
  strcpy(out_filename, "simulated_");
  strcat(out_filename, num_char);
  strcat(out_filename, ".");
  strcat(out_filename, obs_suffix);
  delete num_char;

  ofstream ofile(out_filename, ios::out);

  ofile << "> " << obs_suffix << " observations simulation" << endl;
  delete obs_suffix;

  int i = 0;
  for(ibase = 0 ; ibase < lg ; ibase++)
    {
      i += 1;
      ofile << _coding->InvCoding0(observations_path[ibase]);
      if( (i == 60) || (ibase == lg - 1) )
	{
	  ofile << endl;
	  i = 0;
	}
    }
  
  ofile.close();

}


