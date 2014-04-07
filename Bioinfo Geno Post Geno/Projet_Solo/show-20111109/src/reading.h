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

#ifndef READING_H
#define READING_H

#include <iostream>
#include <fstream>


char*  ReadWord (istream& ifile);
bool ReadShort (istream& ifile, short &integer);
bool ReadInt (istream& ifile, int &integer);
bool CheckInt (istream& ifile);
bool CheckDouble (istream& ifile);
bool ReadDouble (istream& ifile, double &real);
bool ReadLong (istream& ifile, long &integer);



#endif /* READING_H */
