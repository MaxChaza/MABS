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

#include <string>
#include <list>
#include <cstdlib>
#include <map>
using namespace std;


#include "Output.h"
#include "const.h"
#include "HiddenMarkovModel.h"


#ifdef LOG
extern int loglevel;
extern ofstream logstream;
#endif /* LOG */

/*--------------------------------------------------------*/
void Output::WriteOutputDefinition(ostream & o_output)
{
 
  list<list<string> * >::iterator kind1_it;
  list<string>::iterator inter_kind1_it;
  list<list<string**> * >::iterator kind2_it;
  list<string**>:: iterator inter_kind2_it;


  int i;
  o_output << "# " ;

  for (kind1_it=(_prob_out_kind1).begin(); 
       kind1_it!=(_prob_out_kind1).end();
       kind1_it++)
    {
      o_output << "( " ;
      i = 0;
      for(inter_kind1_it=(**kind1_it).begin();
	  inter_kind1_it!=(**kind1_it).end();
	  inter_kind1_it++)
	{
	  if( i != (int)((**kind1_it).size()) - 1 )
	    {
	      o_output << *inter_kind1_it << " ; " ;
	    } else {
	      o_output << *inter_kind1_it << " ) " ;
	    }
	  i++;
	}
    }
  o_output << endl;

  
  o_output << "# " ;
  for (kind2_it=(_prob_out_kind2).begin(); 
       kind2_it!=(_prob_out_kind2).end();
       kind2_it++)
    {
      o_output << "( " ;
      
      i = 0;
      for(inter_kind2_it=(**kind2_it).begin();
	  inter_kind2_it!=(**kind2_it).end();
	  inter_kind2_it++)
	{
	  if ( i != (int)(**kind2_it).size() - 1)
	    {
	      o_output << *((*inter_kind2_it)[0]) << " -> " 
		       <<   *((*inter_kind2_it)[1]) << " ; ";
	    } else {
	      o_output << *((*inter_kind2_it)[0]) << " -> " 
		       <<   *((*inter_kind2_it)[1]) << " ) ";
	    }
	  i++;
	}
    }
  o_output << endl;
  
  
}


/*--------------------------------------------------------------------*/
bool Output::CheckAndSetStateIdNum(HiddenMarkovModel* model)
{
  map<string, int>* map_id_num;  
  list<list<string> * >::iterator kind1_it;
  list<string>::iterator inter_kind1_it;
  list<list<string**> * >::iterator kind2_it;
  list<string**>:: iterator inter_kind2_it;
  bool control_link = true;
  int i;
  int j;
  int u;

  map_id_num = (*model).MapStateById();
  
  _array_out_kind1 = new int* [(_prob_out_kind1).size()];
  _nb_state_out_kind1 = new int [(_prob_out_kind1).size()];
  _array_out_kind2 = new int** [(_prob_out_kind2).size()];
  _nb_state_out_kind2 = new int [(_prob_out_kind2).size()];
  
  i=0;
  for (kind1_it=(_prob_out_kind1).begin(); 
       kind1_it!=(_prob_out_kind1).end();
       kind1_it++)
    {
      _array_out_kind1[i] = new int [ (**kind1_it).size() ];
      _nb_state_out_kind1[i] = (**kind1_it).size();
      j = 0;
      for(inter_kind1_it=(**kind1_it).begin();
	  inter_kind1_it!=(**kind1_it).end();
	  inter_kind1_it++)
	{
	  if((*map_id_num).find(*inter_kind1_it) != (*map_id_num).end() )
	    {
	      u = (*((*map_id_num).find(*inter_kind1_it))).second;
	      _array_out_kind1[i][j] = u;
	    } else {
	      cerr << "Error: Unknown state \"" << *inter_kind1_it 
		   << "\" used in output definition" << endl;
	      control_link = false;
	    }
	  j++;
	}
      i++;
    }

  i=0;
  for (kind2_it=(_prob_out_kind2).begin(); 
       kind2_it!=(_prob_out_kind2).end();
       kind2_it++)
    {
      _array_out_kind2[i] = new int* [ (**kind2_it).size() ];
      _nb_state_out_kind2[i] = (**kind2_it).size();
      j = 0;
      for(inter_kind2_it=(**kind2_it).begin();
	  inter_kind2_it!=(**kind2_it).end();
	  inter_kind2_it++)
	{
	  _array_out_kind2[i][j] = new int [2];
	  //cerr << *((*inter_kind2_it)[0]) << endl;
	  //cerr << *((*inter_kind2_it)[1]) << endl;
	  if ((*map_id_num).find( *((*inter_kind2_it)[0]) ) 
	      != (*map_id_num).end() )
	    {
	      u = (*((*map_id_num).find( *((*inter_kind2_it)[0]) ))).second;
	      _array_out_kind2[i][j][0] = u;
	    } else {
	      cerr << "Error: Unknown state \"" << *((*inter_kind2_it)[0])
		   << "\" used in output definition" << endl;
	      control_link = false;
	    }
	  if ((*map_id_num).find( *((*inter_kind2_it)[1])  ) 
	      != (*map_id_num).end() )
	    {
	      u = (*((*map_id_num).find( *((*inter_kind2_it)[1])  ))).second;
	      _array_out_kind2[i][j][1] = u;
	    } else {
	      cerr << "Error: Unknown state \"" << *((*inter_kind2_it)[1])
		   << "\" used in output definition" << endl;
	      control_link = false;
	    }
	  j++;
	}
      i++;
    }

  return(control_link);
}



/*--------------------------------------------------------*/
Output::Output(char* output_desc_file)
{
  ifstream ifile(output_desc_file);

  //cerr << "trace 1" << endl;
  if( ifile.fail()==1 )
    {
      cerr << "Error: bad file \"" << output_desc_file
	   << "\"" << endl;
      exit(1);
    }

  //cerr << "trace 2" << endl;

  while(ifile.eof() != 1)
    {
      this->ReadLine(ifile);
    }

  //cerr << "trace 3" << endl;

  _nb_prob_out = (_prob_out_kind1).size() + (_prob_out_kind2).size();
  //this->_output = new double* [_nb_prob_out];

}


void Output::ReadLine(istream & ifile)
{
  char current_line [LGLINE+1];
  string *current_string;
  int begin_comment;
  int begin_def_out;
  int end_def_out;
  string *current_def_out;

  //ifile >> word;
  //cerr << "Word: " << word << endl; 

  ifile.getline(current_line, LGLINE);
  current_string = new string(current_line);

#ifdef LOG
  if (loglevel >=1) 
    {
      logstream << "line1 : " << (*current_string) << endl;
      //cerr << "line2 : " << current_line << endl;
    }
#endif /* LOG */
  
  begin_comment = (*current_string).find('#');
  //cerr << "begin_comment : " << begin_comment << endl;
  if ( (begin_comment < (int)(*current_string).size()) && (begin_comment >= 0) )
    {
      (*current_string).erase(begin_comment);
    }

  //cerr << "hello" << endl;

  begin_def_out = (*current_string).find('(');
  end_def_out = (*current_string).find(')');

  while ( ( begin_def_out < (int)(*current_string).size() ) &&
       ( end_def_out < (int)(*current_string).size() ) && 
       (begin_def_out < end_def_out) )
    {
      current_def_out = new string(*current_string);

#ifdef LOG
      if (loglevel >=1) 
	{
	  logstream << "beg : " << begin_def_out << endl;
	  logstream << "end : " << end_def_out << endl;
	  logstream << "current_def_out : " << (*current_def_out) << endl;
	}
#endif /* LOG */

      begin_def_out = (*current_def_out).find('(');
      (*current_def_out).erase((*current_def_out).begin(), 
			     (*current_def_out).begin()+begin_def_out+1);
      
#ifdef LOG
      if (loglevel >=1) 
	{
	  cerr << "current_def_out : " << (*current_def_out) << endl;
	}
#endif /* LOG */
      
      end_def_out = (*current_def_out).find(')');
      (*current_def_out).erase((*current_def_out).begin()+end_def_out, 
			       (*current_def_out).end());
      
#ifdef LOG
      if (loglevel >=1) 
	{
	  cerr << "current_def_out : " << (*current_def_out) << endl;
	}
#endif /* LOG */
      
      end_def_out = (*current_string).find(')');
      begin_def_out = (*current_string).find('(');
      (*current_string).erase((*current_string).begin()+begin_def_out, 
			    (*current_string).begin()+end_def_out+1);
      
      
#ifdef LOG
      if (loglevel >=1) 
	{
	  cerr << "current_string : " << (*current_string) << endl;
	}
#endif /* LOG */

      if ( (*current_def_out).find("->") < (*current_def_out).size() )
	{
	  
#ifdef LOG
	  if (loglevel >=1) 
	    {
	      cerr << "(*current_def_out) 2 " <<  (*current_def_out) << endl;
	    }
#endif /* LOG */
	  
	  this->ReadOutputKind2((*current_def_out));
	} else {
	  
#ifdef LOG
	  if (loglevel >=1) 
	    {
	      cerr << "(*current_def_out) 1 " <<  (*current_def_out) << endl;
	    }
#endif /* LOG */
	  
	  this->ReadOutputKind1((*current_def_out));
	}
      begin_def_out = (*current_string).find('(');
      end_def_out = (*current_string).find(')');

#ifdef LOG
      if (loglevel >=1) 
	{
	  cerr << "beg : " << begin_def_out << endl;
	  cerr << "end : " << end_def_out << endl;
	}
#endif /* LOG */
      
    }
}

void Output::ReadOutputKind1(string & current_output)
{
  list<string>* l_output;
  l_output = new list<string>;
  int first_delim;
  string current_state;
  string current_prob;
  
  current_output.insert(current_output.end(), ';');

#ifdef LOG
  if (loglevel >=1) 
    {
      logstream << "current_output : " << current_output << endl;
    }
#endif /* LOG */

  first_delim = current_output.find(';');
#ifdef LOG
  if (loglevel >=1) 
    {
      logstream << "first_delim : " << first_delim << endl;
    }
#endif /* LOG */

  
  while ( ( first_delim < (int)current_output.size() ) &&  
	  ( first_delim >= 0) )
    {
#ifdef LOG
      if (loglevel >=1) 
	{
	  logstream << "first delim : " << first_delim << endl;
	}
#endif /* LOG */
      current_prob = current_output;
#ifdef LOG
      if (loglevel >=1) 
	{
	  logstream << "current_prob 1 : " << current_prob << endl;
	}
#endif /* LOG */
      current_prob = 
	current_prob.erase(first_delim);
      
#ifdef LOG
      if (loglevel >=1) 
	{
	  logstream << "current_prob 2 : " << current_prob << endl;
	}
#endif /* LOG */
      current_state = current_prob;
      CleanString(current_state);
      if (current_state.size() > 0)
	{
	  (*l_output).push_back(current_state);
	} else {
	  cerr << "Error: in output description file" << endl;
	  exit(1);
	}
      
      
      current_output.erase(current_output.begin(), 
			     current_output.begin()+first_delim+1);	  
      first_delim = current_output.find(';');
    }

  if ( (*l_output).size() > 0 )
    {
      (_prob_out_kind1).push_back(l_output);
    } else {
      cerr << "Error: in ReadOutputKind1 - " << current_output << endl;
    }
}
 
void Output::ReadOutputKind2(string & current_output)
{
  string** l_trans;
  list<string**> * l_output;
  l_output = new list<string**>;
  int first_delim;
  string current_state1;
  string current_state2;
  string current_prob;


  current_output.insert(current_output.end(), ';');
  //cerr << "current_output : " << current_output << endl;
  first_delim = current_output.find(';');
    
  while ( (first_delim < (int)current_output.size()) && 
	  (first_delim >= 0))
    {
      current_prob = current_output;
      current_prob = 
	current_prob.erase((int)current_output.find(';'));
      //cerr << "current_prob : " << current_prob << endl;
      if (  current_prob.find("->") > current_prob.size() )
	{
	  cerr << "Error: all probas must be of the same kind" << endl;
	  exit(1);
	}	      
      
      current_state1 = current_prob;
      current_state2 = current_prob;
      current_state1.erase((int)current_state1.find("->"));
      //cerr << "current_state1: " << current_state1 << endl;

      current_state2.erase(current_state2.begin(), 
			   current_state2.begin() + 
			   current_state2.find("->") + 2 );	      
      //cerr << "current_state2: " << current_state2 << endl;
      
      CleanString(current_state1);
      CleanString(current_state2);
      l_trans = new string* [2];
      l_trans[0] = new string (current_state1);
      l_trans[1] = new string (current_state2);
      (*l_output).push_back(l_trans);
	
      
      //cerr << "current_output : " << current_output << endl;
      current_output.erase(current_output.begin(), 
			     current_output.begin()+first_delim+1);	  
      //cerr << "current_output : " << current_output << endl;
      first_delim = current_output.find(';');
    }

  if ( (*l_output).size() > 0 )
    {
      (_prob_out_kind2).push_back(l_output);
    } else {
      cerr << "Error: in ReadOutputKind2 - " << current_output << endl;
    }
}
 
  



void Output::CleanString(string & current_string)
{
  //cerr << "deb clean string : #" << current_string <<  "#" << endl;
  while ( ( (int)current_string.find_first_of(" \t") == 0)
	  && (current_string.size() > 0) )
    {
      //cerr << "erase first" << endl;
      current_string.erase(current_string.begin());    
    }
  while ( ( (int)(current_string.find_last_of(" \t")) == 
	   ((int)current_string.size() - 1) )
	  && (current_string.size() > 0) )
    {
      //cerr << "erase last" << endl;
      current_string.erase(current_string.find_last_of(" \t")); 
    }
  //cerr << "end clean string : #" << current_string << "#" << endl;
}

/*--------------------------------------------------------*/
void Output::AllocOutput(HiddenMarkovModel* model, Param_EM* param_em)
{
  int i;
  int u;
  int v;
  int j;
  int k;
  int ibase;

  (*model).AllocOutput(&_lgmax,
		       &_join_idnum_to, 
		       &_join_nb_to, 
		       &_join_count, 
		       &_count, &_nb_states, param_em); 
  
  cerr << "Output::AllocOutput Lgmax: " << _lgmax << endl;
  _output = new double* [_lgmax];
  for(ibase=0; ibase<_lgmax; ibase++)
    {
      _output[ibase] = new double [_nb_prob_out];
    }

  for(i=0; i<(int)(_prob_out_kind1).size(); i++)
    {
      for(j=0; j<(_nb_state_out_kind1)[i]; j++)
	{
	  u = (_array_out_kind1)[i][j];
	  if ( (_count)[u] == -1 )
	    {
	      (_count)[u] = i;
	    } else {
	      cerr << "Error: a same state cannot be used in 2 output" << endl;
	      exit(1);
	    }	  
	}
    }

  for(i=0; i<(int)(_prob_out_kind2).size(); i++)
    {
      for(j=0; j<(_nb_state_out_kind2)[i]; j++)
	{
	  u = (_array_out_kind2)[i][j][0];
	  v = (_array_out_kind2)[i][j][1];
	  for(k=0; k<(_join_nb_to)[v]; k++)
	    {
	      if ( (_join_idnum_to)[v][k] == u )
		{
		  if ( (_join_count)[v][k] == -1)
		    {
		      (_join_count)[v][k] = 
			(_prob_out_kind1).size() + i;
		    } else {
		      cerr << "Error: a same join probability cannot be used in 2 output" 
			   << endl;
		      exit(1);
		    }
		}
	    }
	}      
    }
  

}
