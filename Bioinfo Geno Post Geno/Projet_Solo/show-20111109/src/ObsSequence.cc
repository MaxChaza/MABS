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

#include <cctype>
#include <iostream>
#include <fstream>
#include <cstring>
#include <cstdlib>
#include <cmath>
#include <string>
using namespace std;


#include "ObsSequence.h"
#include "const.h"


#ifdef LOG
extern int loglevel;
extern ofstream logstream;
#endif /* LOG */


ObsSequence::ObsSequence(char* file, int order, CodingSeq* coding,
			 istream &ifile)
{

#ifdef LOG
  if (loglevel >= 1)
    {
      logstream << "inside constructor ObsSequence" << endl 
		<< "filename : " << file <<endl
		<< "order : " << order << " this : " << this
		<< endl;
    }
#endif

  _order = order;
  _coding = coding;
  _nmodal = _coding->Nmodal();
  _name = new char[strlen(file)+1];
  strcpy(_name, file);
  _yet_another_seq_in_the_same_file = _coding->ReadSeq(ifile, 
						       _order, &_obs, 
						       &_length);


#ifdef LOG
  if (loglevel >= 1) {
    logstream << "leaving ObsSequence constructor" << endl;
  }
#endif
  
}


ObsSequence::ObsSequence(ObsSequence *seq, int order)
{
  
#ifdef LOG
  if (loglevel >= 1)
    {
      logstream << "inside constructor ObsSequence" << endl 
		<< "order 0 sequence : " << seq << endl
		<< "order : " << order << " this " << this 
		<< endl;
    }
#endif


  int j;

  if(((*seq).Order()) != 0) 
    {
      cerr << "Invalid order for seq, order 0 expected for seq instead of :"
	   << (*seq).Order()
	   << endl;
      exit(1);
    }

  _order = order;
  _name = new char[strlen((*seq).Name())+1];
  _name = (*seq).Name(); 
  strcpy(_name, (*seq).Name());
  _length = (*seq).Length();
  _coding = (*seq)._coding;
  _nmodal = _coding->Nmodal();
  _yet_another_seq_in_the_same_file = (*seq)._yet_another_seq_in_the_same_file;

  //cerr << "Sequence " << _name << " order " << _order << endl;

  _obs = new int[_length];
  for(j=0 ; j<=order ; j++)
    {
      _obs[j] = _coding->Coding(seq->_obs + j, j);
    }
 
  for(j=order + 1 ; j<_length ; j++)
    {
      _obs[j] = _coding->Coding(seq->_obs + j, order);
      //cerr << _obs[j] << " ";
      //if ( j % 60 == 0 ) 
      //{
      //  cerr << endl;
      //}
    }
}


ObsSequence::~ObsSequence()
{ 

#ifdef LOG
  if (loglevel >= 1)
    {
      logstream << "inside destructor ObsSequence" 
		<< endl;
    }
#endif


 delete _obs;
}
