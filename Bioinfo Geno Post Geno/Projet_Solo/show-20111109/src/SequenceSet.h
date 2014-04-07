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

#ifndef SEQUENCESET_H
#define SEQUENCESET_H

#include <map>
#include <string>
using namespace std;

#include "ObsSequence.h"
#include "CodingSeq.h"


class SequenceSet
{
private:
  int _nbseq;

  map<string, int> _map_type_by_id;
  map<string, map<int, ObsSequence**>* > _map_map_seq_by_id;  
  // embedded map is array of ObsSequence* by order

public:
  SequenceSet(char* file);
  ~SequenceSet(); 

  ObsSequence* RetrieveObsSeq(char* seq_id, int order,
						 int num_seq);
  ObsSequence** RetrieveObsSeq(char* seq_id, int order);
  
  CodingSeq* RetrieveCodingSeq(char* seq_id);
  
  inline int NbSeq();
  inline long MaxLength();
};





inline int SequenceSet::NbSeq()
{
  return(_nbseq);
}

inline long SequenceSet::MaxLength()
{
  long max_l = 0;
  
  map<string, map<int, ObsSequence**>* >::iterator im_map_seq_by_id;  
  map<int, ObsSequence**>::iterator im_seq_by_order;
  int i;

  im_map_seq_by_id = _map_map_seq_by_id.begin() ; 
  im_seq_by_order = im_map_seq_by_id->second->begin() ; 
  for( i = 0 ; i < _nbseq ; i++ )
    {
      if (max_l < (im_seq_by_order->second)[i]->Length())
	{
	  max_l = (im_seq_by_order->second)[i]->Length();	      
	}
    }
 
  return(max_l);
}

#endif /* SEQUENCESET_H */
