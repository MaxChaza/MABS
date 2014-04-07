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
#include <cstdlib>
#include <fstream>
#include <cstring>
#include <cmath>
#include <string>
using namespace std;

#include "DnaCodingSeq.h"
#include "const.h"


#ifdef LOG
extern int loglevel;
extern ofstream logstream;
#endif /* LOG */

DnaCodingSeq::DnaCodingSeq()
{
  _nmodal = 4;
  /*
    A
    G
    C
    T
  */
}

int DnaCodingSeq::Coding0(char obsletter)
{
  int codchar;
  
  switch(obsletter)
    {
      case 'a' : codchar = 0; break;
      case 'A' : codchar = 0; break;
      case 'g' : codchar = 1; break;
      case 'G' : codchar = 1; break;
      case 'c' : codchar = 2; break;
      case 'C' : codchar = 2; break;
      case 't' : codchar = 3; break;
      case 'T' : codchar = 3; break;
      default : codchar = -1; break;  
    }
    
  return(codchar);

}

char DnaCodingSeq::InvCoding0(int cod)
{
  char ret_char;

  switch(cod)
    {
      case 0 : ret_char = 'A'; break;
      case 1 : ret_char = 'G'; break;
      case 2 : ret_char = 'C'; break;
      case 3 : ret_char = 'T'; break;
      default : ret_char = 'X'; break;  
    }
    
  return(ret_char);
  
}


bool DnaCodingSeq::ReadSeq(istream &ifile, int order, 
			   int** ret_obs, int *ret_length)
{
#ifdef LOG
  if (loglevel >= 1)
    {
      logstream << "reading DnaCodingSeq" << endl 
		<< "ifile : " << ifile << endl
		<< "order : " << order << " this : " << this
		<< endl;
    }
#endif
  bool nextseq;
  
  if ( this->ReadGenBank(ifile, order, ret_obs, ret_length) == true ) {
    nextseq = false;
  } else if ( this->ReadFasta(ifile, order, ret_obs, ret_length, &nextseq) 
	      == true ) {
  } else {
    cerr << "Unknown file format for a DNA sequence in \"" << ifile 
	 << "\"" << endl;
    exit(1);
  }

  
  
#ifdef LOG
  if (loglevel >= 1) {
    logstream << "leaving ReadSeq" << endl;
  }
#endif
  

  return(nextseq);
}


bool DnaCodingSeq::ReadGenBank(istream &ifile, int order, 
			 int** ret_obs, int *ret_length)
{
  char line[LGSTRING];
  long i,ibloc;
  char c;
  int codletter = 0;
  long length = 0;
  int **observ = NULL; /* observation for order=0 */
  int **temp;
  int *obs0; /* observation for order = 0 in one line*/
  int j;
  bool slash;

  
  //ifile.getline(line,LGSTRING);
  for (j=0; j<5; j++) {
    ifile.get(line[j]); 
  }
  line[5] = '\0';
  if ( strncmp(line, "LOCUS",5 ) != 0 ) {
    for (j=4; j>=0; j--) {
      ifile.putback(line[j]); 
    }    
    return(false);
  } else {
    while ( (ifile.eof () == 0) && (strncmp(line, "ORIGIN",6 ) != 0) ) {
      ifile.getline(line,LGSTRING);
    }
  }
  
  if (strncmp(line, "ORIGIN",6 ) != 0) {
    return(false);
  }

  //ifile.getline(line,LGSTRING); // skip the origin line
  slash = false;
  while ( (ifile.eof () == 0) && (slash == false) ) {
    
    ifile.get (c) ;
    if ( c == '/' ) {
      slash = true;
    } else if ( !isspace(c) && !isdigit(c) ) {
      codletter = Coding0(c);
      
      if(length%LGBLOC == 0) {
	if(length/LGBLOC == 0) {
	  observ = new int*[length/LGBLOC + 1];
	} else {
	  temp = new int*[length/LGBLOC + 1];
	  for(ibloc = 0 ; ibloc < (length/LGBLOC) ; ibloc++) {
	    temp[ibloc] = observ[ibloc];
	  }
	  delete observ;
	  observ = temp;
	}
	observ[length/LGBLOC] = new int[LGBLOC];
      }
      observ[length/LGBLOC][length%LGBLOC] = codletter;
      //cerr << codletter ;
      length++;
    }    
  }
  
  if (slash == false) {
    cerr << "Warning: missing // at the end of the GenBank format file" << endl;
  }
  
  obs0 = new int[length];
  for(i=0 ; i<length ; i++) {
    obs0[i] = observ[i/LGBLOC][i%LGBLOC];
  }
  
  *ret_length = length;
  
  for(ibloc = 0 ; ibloc < length/LGBLOC + 1; ibloc++) {
    delete observ[ibloc];
  }
  delete observ;
  
  *ret_obs = new int[length];
  for(j=0 ; j<=order ; j++) {
    (*ret_obs)[j] = Coding(obs0 + j, j);
  }
  
  for(j=order + 1 ; j<length ; j++) {
    (*ret_obs)[j] = Coding(obs0 + j, order);
  }

  delete obs0;
  return(true);
}
