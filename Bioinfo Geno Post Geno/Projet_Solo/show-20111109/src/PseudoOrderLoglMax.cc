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

#include <iostream>
#include <gsl/gsl_vector.h>
#include <gsl/gsl_multimin.h>
#include <gsl/gsl_multiroots.h>
#include <gsl/gsl_errno.h>
#include <cmath>
using namespace std;

#include "PseudoOrderLoglMax.h"



void LoglMaximisation(CodingSeq* coding, int pseudo_order, int order, int* excepted_array,  double* count, double* pobs)
{
  cout << "LoglMaximisation" << endl;


  Lagrangian_params params;

  params.coding = coding;
  params.pseudo_order = pseudo_order;
  params.order = order;
  params.excepted_array = excepted_array;
  params.count = count;
  params.pseudo_order_pobs_start = (int)(pow((double)coding->Nmodal(), pseudo_order + 1) - 1)/(coding->Nmodal() - 1) - 1;
  params.order_pobs_start = (int)(pow((double)coding->Nmodal(), order + 1) - 1)/(coding->Nmodal() - 1) - 1;
  params.order_pobs_stop = (int)(pow((double)coding->Nmodal(), order + 2) - 1)/(coding->Nmodal() - 1) - 1;

  //cout << " params.pseudo_order_pobs_start " <<  params.pseudo_order_pobs_start << endl;
  //cout << " params.order_pobs_start " <<  params.order_pobs_start << endl;
  //cout << " params.order_pobs_stop " <<  params.order_pobs_stop << endl;


  //for ( int iword = 0 ; iword < params.order_pobs_stop ; iword++ ) 
  //{
  //cout << " ( " << iword << " " << params.excepted_array[iword] << " " 
  //<< params.count[iword] << " ) ";
  //}
  //cout << endl;

  gsl_vector *x;
  int dim_x = (int)pow((double)coding->Nmodal(), pseudo_order)*(coding->Nmodal() + 1);
  //cout << " dim_x " << dim_x << endl;
  params.dim_x = dim_x;
  x = gsl_vector_alloc(dim_x);
  // dim_x = Nb of parameters pow((double)coding->Nmodal(), pseudo_order + 1)
  // + Nb of constraints pow((double)coding->Nmodal(), pseudo_order + 1)

  // vector x initialisation
  double *sum_count;
  sum_count = new double [(int)pow((double)params.coding->Nmodal(), 
				   params.pseudo_order+1)];
  for ( int i = 0 ; i < (int)pow((double)params.coding->Nmodal(), 
				 params.pseudo_order+1) ; i++) {
    sum_count[i] = 0;
  }
  
  
  for ( int iword = params.order_pobs_start ;
	iword < params.order_pobs_stop ; iword++ ) 
    {
      sum_count[params.coding->SuffixCod(iword, order - pseudo_order) 
	       - params.pseudo_order_pobs_start] +=
	params.count[iword];
      //gsl_vector_set(x, i, pobs[i]);
      //cout << i << " " << pobs[i] << endl;
    }
  
  
  for(int i = 0 ; i < (int)pow((double)params.coding->Nmodal(), params.pseudo_order) ; 
      i++)
    {
      double sum = 0;
      for (int j = 0 ; j < params.coding->Nmodal() ; j++) 
	{
	  sum += sum_count[i*params.coding->Nmodal() + j];
	}
      for (int j = 0 ; j < params.coding->Nmodal() ; j++) 
	{
	  int k = i*params.coding->Nmodal() + j;
	  gsl_vector_set(x, k , sum_count[k]/sum);
	  //cout << "* " << k << " : " <<  sum_count[k]/sum << " = " <<  sum_count[k] <<"/" << sum << endl; 
	  gsl_vector_set(x, (int)pow((double)params.coding->Nmodal(), 
				     params.pseudo_order+1) + i, sum);
	}      
    }

  //cout << "x initialisation" << endl;
  //for (int i = 0 ; i < dim_x ; i++) {
  //cout << i << " " << gsl_vector_get(x, i) << endl;
  //}

  Logl(x, &params);

  gsl_vector *df;
  df = gsl_vector_alloc(dim_x);
  Lagrangian_df(x, &params, df);
  //cout << "Lagrangian_df " << endl;
  //for (int i = 0 ; i < dim_x ; i++) {
  //cout << i << " " << gsl_vector_get(df, i) << endl;
  //}


  //save original handler, set no abort mode
  gsl_error_handler_t * old_handler =  gsl_set_error_handler_off();

  gsl_multiroot_function my_func;
  gsl_multiroot_fsolver* my_solver;
  const gsl_multiroot_fsolver_type *T;
  int status1, status2, iter;

  my_func.f = &Lagrangian_df;
  my_func.n = dim_x;
  my_func.params = &params;

  T = gsl_multiroot_fsolver_dnewton;
  my_solver = gsl_multiroot_fsolver_alloc(T, dim_x);
  gsl_multiroot_fsolver_set(my_solver, &my_func, x);

  status1 = GSL_SUCCESS; 
  status2 = GSL_CONTINUE; 
  iter = 0;

  //cout << " GSL_SUCCESS " << GSL_SUCCESS << " GSL_CONTINUE " << GSL_CONTINUE << " GSL_EBADFUNC " << GSL_EBADFUNC 
  //<< " GSL_ENOPROG " << GSL_ENOPROG << endl;

  while (status1 == GSL_SUCCESS && status2 == GSL_CONTINUE && iter < 20) 
    {
      //cout << "*****iter****** " << iter << endl;
      status1 = gsl_multiroot_fsolver_iterate(my_solver);
      
      if (status1 == GSL_SUCCESS) {
	status2 = gsl_multiroot_test_residual(my_solver->f, 1e-7);
      }
      iter++;
      //cout << "x iter" << endl;
      //for (int i = 0 ; i < dim_x ; i++) {
      //cout << i << " " << gsl_vector_get(my_solver->x, i) << endl;
      //}

      Logl(my_solver->x, &params);
      //cout << "status1 " << status1 << " status2 " << status2 << endl;
    }

  //cout << "x final" << endl;
  //for (int i = 0 ; i < dim_x ; i++) {
  //cout << i << " " << gsl_vector_get(my_solver->x, i) << endl;
  //}

  Lagrangian_df(my_solver->x, &params, df);
  //cout << "Lagrangian_df " << endl;
  //for (int i = 0 ; i < dim_x ; i++) {
  //cout << i << " " << gsl_vector_get(df, i) << endl;
  //}
  
  int checkx = 1;
  for ( int i = 0 ;
	i < (int)pow((double)params.coding->Nmodal(), params.pseudo_order + 1) ; 
	i++ ) 
    {
      double proba;
      proba = gsl_vector_get(my_solver->x, i);
      if (gsl_finite(proba) == 0) {
	checkx = 0;
      } else if (proba > 1 || proba < 0) {
	checkx = 0;
      }
    }  
  
  if (checkx == 1 && status1 == GSL_SUCCESS) {
    // write in pobs the new estimates
    //cout << "updating pobs in PseudoOrderLoglMax" << endl;
    for ( int i = 0 ;
	  i < (int)pow((double)params.coding->Nmodal(), params.pseudo_order + 1) ; 
	  i++ ) 
      {
	//cout << pobs[params.pseudo_order_pobs_start + i] << " <- " << gsl_vector_get(my_solver->x, i) << endl;
	pobs[params.pseudo_order_pobs_start + i] = gsl_vector_get(my_solver->x, i);
      }
    for ( int i = 0 ;
	i < params.pseudo_order_pobs_start ; 
	  i+= params.coding->Nmodal() ) 
      {
	double sum = 0;
	for (int j = 0 ; j < params.coding->Nmodal() ; j++ ) {
	  sum += params.count[i+j];
	}
	for (int j = 0 ; j < params.coding->Nmodal() ; j++ ) {
	  //cout << pobs[i+j] << " <- " << params.count[i+j]/sum << endl;
	  pobs[i+j] = params.count[i+j]/sum;
	}
      }
  } else {
    //cout << "no pobs update in PseudoOrderLoglMax due to numerical exceptions" << endl;
  }
  
  gsl_multiroot_fsolver_free(my_solver);
  gsl_vector_free(x);
  gsl_vector_free(df);
  gsl_set_error_handler (old_handler);
}

double Logl(const gsl_vector* x, void* params)
{

  Lagrangian_params* params_i = (Lagrangian_params*)params;

  double logl = 0;

  int iword =  params_i->order_pobs_start ;
  while ( iword < params_i->order_pobs_stop) 
    {
      double denominator = 0;
      bool den_boolean = false;
      for (int i = 0 ; i < params_i->coding->Nmodal() ; i++) 
	{
	  if (params_i->excepted_array[iword + i] == 1) {
	    den_boolean = true;
	  } else {
	    denominator += gsl_vector_get(x,  params_i->coding->SuffixCod(iword + i, params_i->order - params_i->pseudo_order)
					  - params_i->pseudo_order_pobs_start);
	  }	
	}
     
      for (int i = 0 ; i < params_i->coding->Nmodal() ; i++) 
	{
	  if (params_i->excepted_array[iword + i] == 0) {
	    double pb;
	    pb = gsl_vector_get(x, params_i->coding->SuffixCod(iword + i, params_i->order - params_i->pseudo_order)
				- params_i->pseudo_order_pobs_start);
	    if (pb > 0) {
	      logl += params_i->count[iword+i]*log((double)pb);
	    }
	    
   	    if (den_boolean == true) {
	      logl += -params_i->count[iword+i]*log((double)denominator);
	    }
	  }	
	}
      
      iword += params_i->coding->Nmodal();
    }


  //cout << "Logl " << logl << endl; 
  return(logl);
}

	
int Lagrangian_df(const gsl_vector* x, void* params, 
				      gsl_vector* df)
{
  Lagrangian_params* params_i = (Lagrangian_params*)params;
  int iword;
  gsl_vector_set_zero(df);

  int xisfinite = 1;
  for( int i = 0 ; i < params_i->dim_x ; i++ ) {
    xisfinite *= gsl_finite(gsl_vector_get(x, i));
  }
  if (xisfinite != 1) {
    return(-1);
  }

  iword =  params_i->order_pobs_start ;
  while ( iword < params_i->order_pobs_stop) 
    {
      double denominator = 0;
      bool den_boolean = false;
      for (int i = 0 ; i < params_i->coding->Nmodal() ; i++) 
	{
	  if (params_i->excepted_array[iword + i] == 1) {
	    den_boolean = true;
	  } else {
	    denominator += gsl_vector_get(x,  params_i->coding->SuffixCod(iword + i, params_i->order - params_i->pseudo_order)
					  - params_i->pseudo_order_pobs_start);
	  }	
	}
     
      for (int i = 0 ; i < params_i->coding->Nmodal() ; i++) 
	{
	  if (params_i->excepted_array[iword + i] == 0) {
	    int df_pos =  params_i->coding->SuffixCod(iword + i, params_i->order - params_i->pseudo_order)
	      - params_i->pseudo_order_pobs_start;
	    double df_val = gsl_vector_get(df, df_pos);

	    double pb;
	    pb = gsl_vector_get(x, df_pos);
	    if (pb > 0) {
	      df_val += params_i->count[iword+i]/gsl_vector_get(x, df_pos);
	    }

   	    if (den_boolean == true) {
	      df_val += -params_i->count[iword+i]/denominator;
	    }

	    gsl_vector_set(df, df_pos, df_val);
	  }	
	}
      
      iword += params_i->coding->Nmodal();
    }

  // contraints terms
  for(int i = 0 ; i < (int)pow((double)params_i->coding->Nmodal(), params_i->pseudo_order) ; 
      i++)
    {
      double sum = 0;
      for (int j = 0 ; j < params_i->coding->Nmodal() ; j++) 
	{
	  int df_pos = i*params_i->coding->Nmodal() + j;
	  double df_val = gsl_vector_get(df, df_pos);
	  
	  df_val += -gsl_vector_get(x, (int)pow((double)params_i->coding->Nmodal(), params_i->pseudo_order+1) + i);
	  gsl_vector_set(df, df_pos, df_val);

	  sum += gsl_vector_get(x, df_pos);
	}
      gsl_vector_set(df, (int)pow((double)params_i->coding->Nmodal(), params_i->pseudo_order+1) + i, -(sum - 1));

    }

  return(GSL_SUCCESS);
}

