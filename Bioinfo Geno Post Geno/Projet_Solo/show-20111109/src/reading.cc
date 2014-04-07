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
#include <cctype>
#include <cstdlib>
using namespace std;

#include "const.h"
#include "reading.h"


/* ........................................................................ */
/* ........ Fonction : Test a double on the initialisation file......... */
/* ........................................................................ */

bool  CheckDouble (istream& ifile)
{


  char c;
  char line[LGLINE];
  
  
 
  while(ifile.get(c) && ifile.good() )
    {
      if (isspace(c)) continue;
      if  (c != '#')
	{
	  if ( !isdigit(c) && (c!= '.' ))
	    {
	      ifile.putback(c);
	      return(false);
	    }
	  ifile.putback(c);
	  return(true);
	}
      else
	{
	  ifile.get(line, LGLINE, '\n');
	}
    }
  
  if (ifile.bad())
    {
      cerr << "Error in CheckDouble: bad istream" << endl; 
      exit(1);
    }
  if (ifile.eof())
    return(false);
  else
    return(true);
  
  
} /* end of CheckDouble */

/* ........................................................................ */
/* ........ Fonction : Read a double on the initialisation file ......... */
/* return false if an error occurs */
/* ........................................................................ */

bool ReadDouble (istream& ifile, double &real)
{


char c;
char line[LGLINE];



while(ifile.get(c) && ifile.good())
  {
    if (isspace(c)) continue;
    if  ( c != '#' )
      {
	if ( !isdigit(c) && (c!= '.' ))
	  {
	    cerr << 
	      "ReadDouble : on the initialisation file, "
		 << "a double is expected instead of "
		 << c << endl;
	    ifile.putback(c);
	    return(false);
	  }
	ifile.putback(c);
	ifile >> real;
	return(true);
      }
    else
      {
	ifile.get(line, LGLINE, '\n');
      }
  }

 if (ifile.bad())
   {
     cerr << "Error in ReadDouble : bad istream" << endl; 
     exit(1);
   }
 if (ifile.eof())
   return(false);
 else
   return(true);
 
 
} /* end of ReadDouble */




/* ........................................................................ */
/* ........ Fonction : Read a short on the initialisation file ......... */
/* return false if an error occurs */
/* ........................................................................ */

bool  ReadShort (istream& ifile, short &integer)
{


char c;
char line[LGLINE];



while(ifile.get(c) && ifile.good())
  {
    if (isspace(c)) continue;

    if  (c != '#')
      {
	if (!isdigit(c))
	  {
	    cerr << 
	      "ReadShort : on the initialisation file, "
		 << "an short is expected instead of "
		 << c << endl;
	    ifile.putback(c);
	    return(false);
	  }
	ifile.putback(c);
	ifile >> integer;
	return(true);
      }

    else
      {
	ifile.get(line, LGLINE, '\n');
      }
}

 if (ifile.bad())
   {
     cerr << "Error in ReadShort : bad istream" << endl; 
     exit(1);
   }
if (ifile.eof())
  return(false);
else
  return(true);
  

} /* end of ReadShort */


/* ........................................................................ */
/* ........ Fonction : Read an integer on the initialisation file ......... */
/* return false if an error occurs */
/* ........................................................................ */

bool ReadInt (istream& ifile, int &integer)
{


char c;
char line[LGLINE];



while(ifile.get(c) && ifile.good())
  {
    if (isspace(c)) continue;

    if  (c != '#')
      {
	if (!isdigit(c) && (c!= '-'))
	  {
	    cerr << 
	      "ReadInt : on the initialisation file, an integer is expected instead of "
		 << c << endl;
	    return(false);
	  }
	ifile.putback(c);
	ifile >> integer;
	return(true);
      }

    else
      {
	ifile.get(line, LGLINE, '\n');
      }
}

 if (ifile.bad())
   {
     cerr << "Error in ReadInt : bad istream" << endl; 
     exit(1);
   }

 if (ifile.eof())
   return(false);
 else
   return(true);
  

} /* end of ReadInt */

bool CheckInt (istream& ifile)
{


char c;
char line[LGLINE];



while(ifile.get(c) && ifile.good())
  {
    if (isspace(c)) continue;

    if  (c != '#')
      {
	if (!isdigit(c) && (c!= '-'))
	  {
	    cerr << 
	      "ReadInt : on the initialisation file, an integer is expected instead of "
		 << c << endl;
	    return(false);
	  }
	ifile.putback(c);
	return(true);
      }

    else
      {
	ifile.get(line, LGLINE, '\n');
      }
}

 if (ifile.bad())
   {
     cerr << "Error in CheckInt : bad istream" << endl; 
     exit(1);
   }

 if (ifile.eof())
   return(false);
 else
   return(true);
  

} /* end of CheckInt */


/* ........................................................................ */
/* ........ Fonction : Read a long integer on the initialisation file ......... */
/* return false if an error occurs */
/* ........................................................................ */

bool ReadLong (istream& ifile, long &integer)
{


char c;
char line[LGLINE];



while(ifile.get(c) && ifile.good())
  {
    if (isspace(c)) continue;

    if  (c != '#')
      {
	if (!isdigit(c))
	  {
	    cerr << 
	      "ReadLong : on the initialisation file, a long integer is expected instead of "
		 << c << endl;
	    return(false);
	  }
	ifile.putback(c);
	ifile >> integer;
	return(true);
      }

    else
      {
	ifile.get(line, LGLINE, '\n');
      }
}

 if (ifile.bad())
   {
     cerr << "Error in ReadInt : bad istream" << endl; 
     exit(1);
   }

 if (ifile.eof())
   return(false);
 else
   return(true);
  

} /* end of ReadLong */

/* ........................................................................ */
/* ........ Fonction : Read a word on the initialisation file ......... */
/*                     return NULL if end of file occurs                */
/* ........................................................................ */
char *  ReadWord (istream& ifile)
{


  char c;
  short i = 0 ;
  char* word; 
  //char temp_word[20]="                   ";
  char* temp_word;
  char line[LGLINE];

  temp_word = new char [LGMAX_WORD];

  
  while(ifile.good() && ifile.get(c))
    {
      if (isspace(c)) continue;
      
      if  (c != '#')
	{
	  temp_word[i++]=c;
	  while ( (i<LGMAX_WORD) && ifile.get(c))
	    {
	      if (!isspace(c))
		{
		  temp_word[i++]=c;
		}
	      else
		break;
	    }
	  temp_word[i]='\0';
	  word = new char[strlen(temp_word)+1];
	  strcpy(word, temp_word);
	  delete [] temp_word;
	  return word;
	}
      else
	{
	  ifile.get(line, LGLINE, '\n');
	}
    }

 if (ifile.bad())
   {
     cerr << "Error in ReadWord: bad istream" << endl; 
     exit(1);
   }

  if (ifile.eof())
    delete [] temp_word;
    return NULL;

  cerr << endl 
       << "warning char*  ReadWord (istream& ifile) with no return value" 
       << endl;
  delete [] temp_word;
  return NULL;

} /* end of ReadWord */


