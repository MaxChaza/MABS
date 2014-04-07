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

#ifndef PARAM_VIT_H
#define PARAM_VIT_H


class Param_Vit
{
private:
  long _vit_segment;
  long _vit_overlap;
  
public:
  Param_Vit(char* vit_file);

  inline long VitSegment();
  inline long VitOverlap();
 
  
};

inline long Param_Vit::VitSegment()
{
  return( _vit_segment);
}

inline long Param_Vit::VitOverlap()
{
  return( _vit_overlap);
}

#endif /* PARAM_VIT_H */
