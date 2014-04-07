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
using namespace std;


#include "Param_EM.h"
#include "reading.h"



#ifdef LOG
extern int loglevel;
extern ofstream logstream;
#endif /* LOG */

Param_EM::Param_EM(char* em_file)
{
  ifstream ifile(em_file);
  char* word;
  bool iverif_estep_segment = false;
  bool iverif_epsi = false;
  bool iverif_niter = false;
  bool iverif_estep_overlap = false;
  bool iverif_niter_sel = false;
  bool iverif_nb_sel = false;
  bool iverif_eps_sel = false;

#ifdef LOG
  if (loglevel >=1) 
    {
      logstream << "Inside Param_EM constructor of " 
		<< this << endl << "with arg "
		<< "filename: " << em_file << endl;
    }
#endif /* LOG */


  if( ifile.fail() == 1 )
    {
      cerr << "Error: bad file \"" << em_file 
	   << "\"" << endl;
      exit(1);
    }

  word = ReadWord(ifile);
  while ( ( ifile.eof() != 1 ) && (word != NULL) )
    {
      if (strncmp(word, "estep_segment:", 13) == 0)
	{
	  ReadLong (ifile, _estep_segment);
	  iverif_estep_segment = true;
	  cout << "estep_segment: " << _estep_segment << endl;
	} 
      else if (strncmp(word, "estep_overlap:", 13) == 0) 
	{
	  ReadLong (ifile, _estep_overlap );
	  iverif_estep_overlap = true;
	  cout << "estep_overlap: " << _estep_overlap << endl; 
	} 
      else if ( (strncmp(word, "niter:", 5) == 0) 
		&& (strncmp(word, "niter_sel:", 9) != 0)) 
	{
	  ReadInt (ifile, _niter);
	  iverif_niter = true;
	  cout << "niter: " << _niter << endl;
	} 
      else if ( (strncmp(word, "eps:", 3) == 0) 
		&& (strncmp(word, "eps_sel:", 7) != 0) )
	{
	  ReadDouble (ifile, _epsi);
	  iverif_epsi = true;
	  cout << "eps: " << _epsi << endl;
	} 
      else if (strncmp(word, "niter_sel:", 9) == 0) 
	{
	  ReadInt (ifile, _niter_sel);
	  iverif_niter_sel = true; 
	  cout << "niter_sel: " << _niter_sel << endl;
	} 
      else if (strncmp(word, "nb_sel:", 6) == 0) 
	{
	  ReadInt (ifile, _nb_sel);
	  iverif_nb_sel = true;
	  cout << "nb_sel: " << _nb_sel << endl;
	} 
      else if (strncmp(word, "eps_sel:", 7) == 0) 
	{
	  ReadDouble (ifile, _eps_sel);
	  iverif_eps_sel = true;
	  cout << "eps_sel: " << _eps_sel << endl;
	} 
      else 
	{
	  cerr << "Error: Unknown key word \"" << word << "\" in "
	       << "em description file" << endl;
	  exit(1);
	}
      delete word;
      word = ReadWord(ifile);
    }
  


  if ( (iverif_estep_segment!=true) || (iverif_estep_overlap!=true) 
       || (iverif_niter!=true) || (iverif_epsi!=true) )
    {
      cerr << "Error: incorrect em description file \""
	   << em_file << "\"" << endl;
      if (iverif_estep_segment != true)
	{
	  cerr << "-> No estep_segment value" << endl;
	}    
      if (iverif_estep_overlap != true)
	{
	  cerr << "-> No estep_overlap value" << endl;
	}    
      if (iverif_epsi != true)
	{
	  cerr << "-> No epsi value" << endl;
	}    
      if (iverif_niter != true)
	{
	  cerr << "-> No niter value" << endl;
	}    
      exit(1);
    }

  if ( (iverif_niter_sel != true) 
       || (iverif_nb_sel != true) 
       || (iverif_eps_sel != true) )
    {
     
      if ( iverif_niter_sel != true )
	{
	  _niter_sel = 10; 
	}
      if ( iverif_nb_sel != true )
	{
	  _nb_sel = 0; 
	}
      if ( iverif_eps_sel != true )
	{
	  _eps_sel = -1; 
	}
    }
#ifdef LOG
  if (loglevel >=1) 
    {
      logstream << "leaving Param_EM constructor" << endl
		<< "of " << this << endl;
    }
#endif /* LOG */

}

