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

#ifndef PSEUDOORDERLOGLMAX_H
#define PSEUDOORDERLOGLMAX_H

#include <gsl/gsl_vector.h>
using namespace std;

#include "CodingSeq.h"



typedef struct {
  CodingSeq* coding;
  int pseudo_order;
  int order;
  int pseudo_order_pobs_start;
  int order_pobs_start;
  int order_pobs_stop;
  int dim_x;
  int* excepted_array;
  double* count;
} Lagrangian_params;

void LoglMaximisation(CodingSeq* coding, int pseudo_order, int order, int* excepted_array,  double* count, double* pobs);

double Logl(const gsl_vector* x, void* params);

int Lagrangian_df(const gsl_vector* x, void* params, 
		  gsl_vector* df);

#endif /* PSEUDOORDERLOGLMAX_H */
