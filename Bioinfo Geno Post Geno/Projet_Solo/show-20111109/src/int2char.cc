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
using namespace std;


#include "int2char.h"



char* int2char(int nint)
{
  int i;
  int nchar;
  int curr_i;
  char *return_char;

  i=1;
  while ( nint >= (int)pow((double)10, i) )
    {
      i++;
    }

  nchar = i;
  
  return_char = new char [nchar + 1];
  return_char[nchar] = '\0';
  for(i=nchar; i>0; i--)
    {
      curr_i = (int)(nint/pow((double)10, i-1));
      nint = nint % (int)pow((double)10, i-1);
      return_char[nchar - i] = 48 + curr_i;      
    }

  return(return_char);
  
}

