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

#ifndef PARAM_EM_H
#define PARAM_EM_H


class Param_EM
{
private:
  long _estep_segment;
  long _estep_overlap;
  int _niter;
  double _epsi;
  int _niter_sel;
  int _nb_sel;
  double _eps_sel;
  
public:
  Param_EM(char* em_file);

  inline long EStepSegment();
  inline long EStepOverlap();
  inline double Epsi();
  inline int NIter();
  inline int NIterSel();
  inline int NbSel();
  inline double EpsSel();
  
};

inline long Param_EM::EStepSegment()
{
  return( _estep_segment);
}

inline long Param_EM::EStepOverlap()
{
  return( _estep_overlap);
}

inline double Param_EM::Epsi()
{
  return(_epsi);
}

inline int Param_EM::NIter()
{
  return(_niter);
}

inline int Param_EM::NIterSel()
{
  return(_niter_sel);
}

inline int Param_EM::NbSel()
{
  return(_nb_sel);
}

inline double Param_EM::EpsSel()
{
  return(_eps_sel);
}

#endif /* PARAM_EM_H */
