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
#include <cstring>
using namespace std;

#include "shortfilename.h"


char* shortfilename(char* longfilename)
{
  string name (longfilename);
  int slash;
  int point;
  char* short_name;
  char* tmp_name;
  
  slash = name.rfind(47);
    
  if( (slash < 0) || (slash > (int)name.size()) )
    {
      tmp_name = new char [(int)name.size() + 1];
      strcpy(tmp_name, longfilename);
    } else {
      tmp_name = new char [(int)name.size() - slash];
      for(int i = slash + 1 ; i < (int)name.size() ; i++)
	{
	  tmp_name[i - slash - 1] = longfilename[i];
	}
      tmp_name[(int)name.size() - slash - 1] = '\0';
    }
  
  string stmp_name (tmp_name);
  point = stmp_name.find_last_of('.');
  if( (point < 0) || (point > (int)stmp_name.size()) )
    {
      short_name = new char [(int)stmp_name.size()];
      strcpy(short_name, tmp_name);
      return(short_name);
    } else {
      short_name = new char [point + 1];
      for(int i = 0; i < point ; i++)
	{
	  short_name[i] = tmp_name[i];
	}
      short_name[point] = '\0';
      return(short_name);
    }
  
  
}

