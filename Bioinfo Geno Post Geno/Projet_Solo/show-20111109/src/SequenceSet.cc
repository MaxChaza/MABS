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
#include <iostream>
#include <fstream>
#include <cstring>
#include <cstdlib>
using namespace std;

#include "reading.h"
#include "SequenceSet.h"
#include "DnaCodingSeq.h"
#include "HomologCodingSeq.h"
#include "CodonCodingSeq.h"
#include "const.h"

#ifdef LOG
extern int loglevel;
extern ofstream logstream;
#endif /* LOG */

SequenceSet::SequenceSet(char* file)
{

#ifdef LOG
  if (loglevel >= 1)
    {
      logstream << "inside constructor SequenceSet" << endl 
		<< "filename : " << file 
		<< endl;
    }
#endif

  list<list<ObsSequence*> > lst_lst_seq;
  list<ObsSequence*>* current_lst_seq;
  list<char*> lst_seq_id;
  list<char*>::iterator il_seq_id;
  list<list<ObsSequence*> >::iterator il_lst_seq;
  list<ObsSequence*>::iterator il_seq;
  ObsSequence* current_seq; 


  CodingSeq* coding;
  ifstream ifile(file);
  char* seq_id;
  char* word;
  int i;

  if ( ! ifile.good() )
    {
      cerr << "Cannot open the sequence list file:" << endl
	   << "\t\"" << file << "\"" << endl;
      exit(1);
    }
  
  word = ReadWord(ifile);

#ifdef LOG
  if (loglevel >= 3)
    {
      logstream << "toto1: " << word << endl;
    }
#endif


  while ( (word != NULL) && strncmp(word, "seq_identifier:", 6) == 0 ) {
    delete word;
    word = ReadWord(ifile);
    seq_id = word;

#ifdef LOG
    if (loglevel >= 3)
      {
	logstream << "toto2: " << word << endl;
      }
#endif
    
      word = ReadWord(ifile);
      if ( (word != NULL) && strncmp(word, "seq_type:", 6) != 0 )
	{
	  cerr << "\"" << word 
	       << "\" found where \"seq_type:\" expected" << endl
	       << "\t in file \"" << file << "\"" << endl;
	  exit(1);
	}
      delete word;
      word = ReadWord(ifile);
      if ( word == NULL ) {
	cerr << "the seq type of \"" << seq_id << "\" not found" << endl;
	exit(1);
      }
      
      lst_seq_id.push_back(seq_id);
      if ( strncmp(word, "dna", 3) == 0 ) {

#ifdef LOG
	if (loglevel >= 3)
	  {
	    logstream << "DNA !" << endl;
	  }
#endif

	coding = new DnaCodingSeq;
      } else if ( strncmp(word, "homolog", 3) == 0 ) {

#ifdef LOG
	if (loglevel >= 3)
	  {
	    logstream << "HOMOLOG !" << endl; 
	  }
#endif

	coding = new HomologCodingSeq;
      }  else if ( strncmp(word, "codonusage", 6) == 0 ) {

#ifdef LOG
	if (loglevel >= 3)
	  {
	    logstream << "CODONUSAGE !" << endl; 
	  }
#endif

	coding = new CodonCodingSeq;
      } else {
	cerr << "Unknown seq type \"" << word << "\" (not dna nor homolog nor codonusage)" 
	     << endl;
	exit(1);
      }

      
      /*
       * Read the list of sequences
       */

      delete word;
      word = ReadWord(ifile);
      if ( (word != NULL) && strncmp(word, "seq_files", 7) == 0 ) {
	delete word;
	word = ReadWord(ifile);
	
	current_lst_seq = new list<ObsSequence*>;
	while ( (! ifile.eof() ) && 
		( word != NULL ) &&
		( strncmp(word, "seq_identifier:", 6) != 0 ) ) {
	  cerr << "seq_file: " << word << endl;
	  ifstream ifileseq(word);  
	  if ( ! ifileseq.good() ) {
	    cerr << "Invalid sequence file : \"" << word <<"\"" << endl;
	    exit(1);
	  }
	  
	  current_seq = new ObsSequence(word, 0, coding, ifileseq);
	  current_lst_seq->push_back(current_seq);
	  while( current_seq->YetAnotherSeqInTheSameFile() == true ) 
	    {
	      current_seq = new ObsSequence(word, 0, coding, ifileseq);
	      current_lst_seq->push_back(current_seq);	    
	    }
	  delete word;
	  word = ReadWord(ifile);
	}
	
	lst_lst_seq.push_back(*current_lst_seq);
	
      } else {
	cerr << "\"" << word 
	     << "\" found where \"seq_files:\" expected" << endl
	     << "\t in file \"" << file << "\"" << endl;
	exit(1);	      
      }
  }  
  
  
  ifile.close();
  
  /*
   * check if all seq_id have the same nbseq
   */
  
  if(lst_lst_seq.size() > 0) {
    il_lst_seq = lst_lst_seq.begin();
    _nbseq = il_lst_seq->size();
  } else {
    cerr << "no sequence found !" << endl;
    exit(1);
  }
 
  il_seq_id = lst_seq_id.begin();
  for(il_lst_seq = lst_lst_seq.begin() ; 
      il_lst_seq != lst_lst_seq.end() ; il_lst_seq++)
    {
      if ( (int)il_lst_seq->size() != _nbseq )
	{
	  cerr << "Sequence_id: \"" << *il_seq_id << "\" hasn't " 
	       << _nbseq << " sequences" << endl; 
	  exit(1);
	}
      il_seq_id++;
    }

  cout << "Number of sequences: " << _nbseq << endl; 

  /*
   * check if all seqs have the same length
   */
  
  int *lengths;
  lengths = new int [_nbseq];
  for(i=0 ; i<_nbseq ; i++) {
    lengths[i] = 0;
  }

  i = 0;
  il_seq_id = lst_seq_id.begin();
  for(il_lst_seq = lst_lst_seq.begin() ; 
      il_lst_seq != lst_lst_seq.end() ; il_lst_seq++) {
    
    for(il_seq = il_lst_seq->begin() ;
	il_seq != il_lst_seq->end() ; il_seq++) {
      if (lengths[i] == 0) {
	lengths[i] = (*il_seq)->Length();
      } else if (lengths[i] != (*il_seq)->Length()) {
	cerr << "length of the " << i << "th seq of " << *il_seq_id << " is " 
	     <<  (*il_seq)->Length() << " not " << lengths[i] << endl;
	cerr << "inconsistant lengths of the sequences !" << endl;
	exit(1);
      }
      i++;
    }
    i = 0;
    il_seq_id++;
  }
    
  delete [] lengths;


  
  /*
   * MAP SEQ ORDERS ...
   */
  
  il_seq_id = lst_seq_id.begin();
  
  map<int, ObsSequence**>* map_seq_by_order;
  for(il_lst_seq = lst_lst_seq.begin() ; 
      il_lst_seq != lst_lst_seq.end() ; il_lst_seq++)
    {
      ObsSequence** zero_order_seq;
      zero_order_seq = new ObsSequence*[_nbseq];
      il_seq = il_lst_seq->begin();
      for( i = 0 ; i < _nbseq ; i++ )
	{

#ifdef LOG
	  if (loglevel >= 3)
	    {
	      logstream << "zero_order_seq " << zero_order_seq[i] << endl;
	    }
#endif
	  
	  zero_order_seq[i] = *il_seq;
	  il_seq++;
	}
      
      map_seq_by_order = new map<int, ObsSequence**>;
      map_seq_by_order->insert(make_pair(0, zero_order_seq));
      _map_map_seq_by_id.insert(make_pair((string)(*il_seq_id), 
					  map_seq_by_order));
      il_seq_id++;
    }

  


#ifdef LOG
  if (loglevel >= 1)
    {
      logstream << "leaving SequenceSet constructor" << endl;
    }
#endif

}


SequenceSet::~SequenceSet()
{
#ifdef LOG
  if (loglevel >= 1)
    {
      logstream << "inside SequenceSet destructor" << endl;
    }
#endif

  map<string, map<int, ObsSequence**>* >::iterator im_map_seq_by_id;  
  map<int, ObsSequence**>::iterator im_seq_by_order;
  int i;

  for(im_map_seq_by_id = _map_map_seq_by_id.begin() ; 
      im_map_seq_by_id != _map_map_seq_by_id.end() ; im_map_seq_by_id++)
    {
      for(im_seq_by_order = im_map_seq_by_id->second->begin() ; 
	  im_seq_by_order != im_map_seq_by_id->second->end() ; 
	  im_seq_by_order++)
	{
	  for( i = 0 ; i < _nbseq ; i++ )
	    {
	      delete (im_seq_by_order->second)[i];	      
	    }
	  delete im_seq_by_order->second;
	}        
    }  

  
#ifdef LOG
  if (loglevel >= 1)
    {
      logstream << "leaving SequenceSet destructor" << endl;
    }
#endif

}

ObsSequence* SequenceSet::RetrieveObsSeq(char* seq_id, int order, 
					    int num_seq)
{
  //cerr << "Retrieve " << endl;
  if ( _map_map_seq_by_id.find((string)(seq_id)) != _map_map_seq_by_id.end() )
    {
      //cerr << "Known name" << endl;
      map<int, ObsSequence**>* map_seq_by_order;
      map_seq_by_order = _map_map_seq_by_id.find((string)(seq_id))->second;
      if ( map_seq_by_order->find(order) !=  map_seq_by_order->end() ) 
	{
	  //cerr << "Known order" << endl;
	  return((map_seq_by_order->find(order)->second)[num_seq]);
	} else {
	  //cerr << "Unknown order" << endl;
	  // create
	  ObsSequence** new_order_seq;
	  ObsSequence** zero_order_seq;
	  new_order_seq = new ObsSequence*[_nbseq];
	  zero_order_seq = map_seq_by_order->find(0)->second;
	  int i;
	  
	  for(i = 0; i < _nbseq; i++) 
	    {
	      new_order_seq[i] = new ObsSequence(zero_order_seq[i], order);
	      //cerr << "new_order_seq " << new_order_seq[i] << endl;
	    }
	  map_seq_by_order->insert(make_pair(order, new_order_seq));
	  return((map_seq_by_order->find(order)->second)[num_seq]);
	}
    } else {
      cerr << "Unknown sequence id: \"" << seq_id << "\"" << endl
	   << "couldn't be retrieved" << endl;
      exit(1);
    }
}

ObsSequence** SequenceSet::RetrieveObsSeq(char* seq_id, int order)
{
  if ( _map_map_seq_by_id.find((string)(seq_id)) != _map_map_seq_by_id.end() )
    {
      map<int, ObsSequence**>* map_seq_by_order;
      map_seq_by_order = _map_map_seq_by_id.find((string)(seq_id))->second;
      if ( map_seq_by_order->find(order) !=  map_seq_by_order->end() ) 
	{
	  return(map_seq_by_order->find(order)->second);
	} else {
	  // create
	  ObsSequence** new_order_seq;
	  ObsSequence** zero_order_seq;
	  new_order_seq = new ObsSequence*[_nbseq];
	  zero_order_seq = map_seq_by_order->find(0)->second;
	  int i;
	  for(i = 0; i < _nbseq; i++) 
	    {
	      new_order_seq[i] = new ObsSequence(zero_order_seq[i], order);
#ifdef LOG
	      if (loglevel >= 3)
		{
		  cerr << "new_order_seq " << new_order_seq[i] << endl;
		}
#endif
	    }
	  map_seq_by_order->insert(make_pair(order, new_order_seq));
	  return(map_seq_by_order->find(order)->second);
	}
    } else {
      cerr << "Unknown sequence id: \"" << seq_id << "\"" << endl
	   << "couldn't be retrieved" << endl;
      exit(1);
    }
}

CodingSeq* SequenceSet::RetrieveCodingSeq(char* seq_id)
{
  //cerr << "Retrieve CodingSeq" << endl;
  if ( _map_map_seq_by_id.find((string)(seq_id)) != _map_map_seq_by_id.end() )
    {
      //cerr << "Known name" << endl;
      map<int, ObsSequence**>* map_seq_by_order;
      map_seq_by_order = _map_map_seq_by_id.find((string)(seq_id))->second;
      ObsSequence** zero_order_seq;
      zero_order_seq = map_seq_by_order->find(0)->second;
      return(zero_order_seq[0]->CodingSeqObject());
    } else {
      cerr << "Unknown sequence id: \"" << seq_id << "\"" << endl
	   << "couldn't be retrieved" << endl;
      exit(1);
    }
  return(NULL);
}
