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

#ifndef OUTPUT_H
#define OUTPUT_H


#include<list>
#include<string>
#include<iostream>
#include<fstream>
using namespace std;

#include "Param_EM.h"


class HiddenMarkovModel;


class Output
{

 private:

  int _nb_prob_out;
  list<list<string> * > _prob_out_kind1;
  list<list<string**> * > _prob_out_kind2;
  int** _array_out_kind1;
  int* _nb_state_out_kind1;
  int*** _array_out_kind2;
  int* _nb_state_out_kind2;

  long int _lgmax;

  int** _join_idnum_to; 
  int* _join_nb_to;
  int _nb_states;

  int** _join_count;
  int* _count;

  double** _output;

  void ReadLine(istream & ifile);
  void ReadOutputKind1(string & current_output);
  void ReadOutputKind2(string & current_output);
  void CleanString(string & current_string); 


 public:

  Output(char* output_desc_file); 
  bool CheckAndSetStateIdNum(HiddenMarkovModel* model);
  inline void CleanOutput();
  inline void JoinCount(long ibase, int v, int u, double p);
  inline void Count(long ibase, int u, double p);  
  void WriteOutputDefinition(ostream & ofile);

  void AllocOutput(HiddenMarkovModel* model, Param_EM* param_em);

  inline void WriteOutputSeq(ostream & ofile, long int begin, long int end);
  
};




/*----------------------------------------------------------*/

inline void Output::JoinCount(long ibase, int v, int i, double p)
{
  if ( _join_count[v][i] != -1 )
    {
       _output[ibase][_join_count[v][i]] += p;
    }
}

inline void Output::Count(long ibase, int u, double p)
{
  //cout << ibase << " " << _ofile->good() << endl;
  if ( _count[u] != -1 )
    {
      _output[ibase][_count[u]] += p;
    }
}


inline void Output::CleanOutput()
{
  int i;
  long int ibase;

  for (i=0; i<_nb_prob_out; i++)
    {
      for(ibase=0; ibase<_lgmax; ibase++)
	{
	  _output[ibase][i] = 0;
	}
    }
}

inline void Output::WriteOutputSeq(ostream & ofile, long int begin, long int end)
{
  int i;
  long int ibase;

  for (ibase = begin; ibase< end; ibase++)
    {
      //cerr << ibase << " " << _ofile->good() << endl;
      for (i=0; i<_nb_prob_out; i++)
	{
	  ofile << _output[ibase][i] << "\t"; 
	}  
      ofile << endl;
    }  
}


#endif /* OUTPUT_H */
