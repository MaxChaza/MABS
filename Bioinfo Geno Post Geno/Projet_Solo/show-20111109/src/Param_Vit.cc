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

#include "Param_Vit.h"
#include "reading.h"



#ifdef LOG
extern int loglevel;
extern ofstream logstream;
#endif /* LOG */


Param_Vit::Param_Vit(char* vit_file)
{
  ifstream ifile(vit_file);
  char* word;
  bool iverif_vit_segment = false;
  bool iverif_vit_overlap = false;

#ifdef LOG
  if (loglevel >=1) 
    {
      logstream << "Inside Param_Vit constructor of " 
		<< this << endl << "with arg "
		<< "filename: " << vit_file << endl;
    }
#endif /* LOG */

 
  if( ifile.fail() == 1 )
    {
      cerr << "Error: bad file \"" << vit_file 
	   << "\"" << endl;
      exit(1);
    }

  word = ReadWord(ifile);
  while ( ( ifile.eof() != 1 ) && (word != NULL) )
    {
      if (strncmp(word, "vit_segment:", 11) == 0)
	{
	  ReadLong (ifile, _vit_segment);
	  iverif_vit_segment = true;
	} else if (strncmp(word, "vit_overlap:", 11) == 0) {
	  ReadLong (ifile, _vit_overlap );
	  iverif_vit_overlap = true;
	} else {
	  cerr << "Error: Unknown key word \"" << word << "\" in "
	       << "vit description file" << endl;
	  exit(1);
	}
      delete word;
      word = ReadWord(ifile);
    }
  
  if ( (iverif_vit_segment!=true) 
       || (iverif_vit_overlap!=true) )  
    {
      cerr << "Error: incorrect vit description file \""
	   << vit_file << "\"" << endl;
      if (iverif_vit_segment != true)
	{
	  cerr << "-> No vit_segment value" << endl;
	}    
      if (iverif_vit_overlap != true)
	{
	  cerr << "-> No vit_overlap value" << endl;
	}    
      exit(1);
    }
#ifdef LOG
  if (loglevel >=1) 
    {
      logstream << "leaving Param_Vit constructor" << endl
		<< "of " << this << endl;
    }
#endif /* LOG */

}

