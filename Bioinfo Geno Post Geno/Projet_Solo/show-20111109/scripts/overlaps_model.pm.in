package overlaps_model;


$overlaps_model::OVLP = "
#####################begin PREOVLP+#####################
BEGIN_STATE
state_id: pre_ovlp+_2
	BEGIN_TRANSITIONS
		type: 1
		state: pre_ovlp+_3
		ptrans: 0.33
		type: 1
		state: ovlp2_start+_1
		ptrans: 0.33
		type: 1
		state: short_overlap+-_1
		ptrans: 0.34
	END_TRANSITIONS
	BEGIN_OBSERVATIONS
		seq: genomic_dna
		type: 1
		order: 0
		pobs:
		    random
	END_OBSERVATIONS
END_STATE
BEGIN_STATE
state_id: pre_ovlp+_3
	BEGIN_TRANSITIONS
		type: 0
		state: pre_ovlp+_1
		ptrans: 1
	END_TRANSITIONS
	BEGIN_OBSERVATIONS
		seq: genomic_dna
		type: 1
		order: 0
		pobs:
		    random
                excepted: TAG TGA TAA
	END_OBSERVATIONS
END_STATE
BEGIN_STATE
state_id: pre_ovlp+_1
	BEGIN_TRANSITIONS
		type: 0
		state: ovlp1_start+_1
		ptrans: 1
	END_TRANSITIONS
	BEGIN_OBSERVATIONS
		seq: genomic_dna
		type: 1
		order: 0
		pobs:
		    random
	END_OBSERVATIONS
END_STATE
#####################end PREOVLP+#####################

#####################begin POSTOVLP+#####################
BEGIN_STATE
state_id: post_ovlp+_2
	BEGIN_TRANSITIONS
<Add transitions to CDS+_3>
	END_TRANSITIONS
	BEGIN_OBSERVATIONS
		seq: genomic_dna
		type: 1
		order: 0
		pobs:
		    random
	END_OBSERVATIONS
END_STATE
BEGIN_STATE
state_id: post_ovlp+_3
	BEGIN_TRANSITIONS
<Add transitions to CDS+_1>
	END_TRANSITIONS
	BEGIN_OBSERVATIONS
		seq: genomic_dna
		type: 1
		order: 0
		pobs:
		    random
                excepted: TAG TGA TAA
	END_OBSERVATIONS
END_STATE
#####################end POSTOVLP+#####################

#####################begin PREOVLP-#####################
BEGIN_STATE
state_id: pre_ovlp-_2
	BEGIN_TRANSITIONS
		type: 1
		state: pre_ovlp-_1
		ptrans: 0.33
		type: 1
		state: ovlp2_stop-_3*1
		ptrans: 0.33
		type: 1
		state: ovlp2_stop-_3*2
		ptrans: 0.34
	END_TRANSITIONS
	BEGIN_OBSERVATIONS
		seq: genomic_dna
		type: 1
		order: 0
		pobs:
		    random
	END_OBSERVATIONS
END_STATE
BEGIN_STATE
state_id: pre_ovlp-_1
	BEGIN_TRANSITIONS
		type: 0
		state: pre_ovlp-_3
		ptrans: 1
	END_TRANSITIONS
	BEGIN_OBSERVATIONS
		seq: genomic_dna
		type: 1
		order: 0
		pobs:
		    random
                excepted: CTA TTA TCA
	END_OBSERVATIONS
END_STATE
BEGIN_STATE
state_id: pre_ovlp-_3
	BEGIN_TRANSITIONS
		type: 1
		state: ovlp1_stop-_3*1
		ptrans: 0.5
		type: 1
		state: ovlp1_stop-_3*2
		ptrans: 0.5
	END_TRANSITIONS
	BEGIN_OBSERVATIONS
		seq: genomic_dna
		type: 1
		order: 0
		pobs:
		    random
	END_OBSERVATIONS
END_STATE
#####################end PREOVLP-#####################


#####################begin POSTOVLP-#####################
BEGIN_STATE
state_id: post_ovlp-_2
	BEGIN_TRANSITIONS
<Add transitions to CDS-_1>
	END_TRANSITIONS
	BEGIN_OBSERVATIONS
		seq: genomic_dna
		type: 1
		order: 0
		pobs:
		    random
	END_OBSERVATIONS
END_STATE
BEGIN_STATE
state_id: post_ovlp-_3
	BEGIN_TRANSITIONS
<Add transitions to CDS-_2>
	END_TRANSITIONS
	BEGIN_OBSERVATIONS
		seq: genomic_dna
		type: 1
		order: 0
		pobs:
		    random
	END_OBSERVATIONS
END_STATE
BEGIN_STATE
state_id: post_ovlp-_1
	BEGIN_TRANSITIONS
<Add transitions to CDS-_3>
	END_TRANSITIONS
	BEGIN_OBSERVATIONS
		seq: genomic_dna
		type: 1
		order: 0
		pobs:
		    random
                excepted: TTA TCA CTA
	END_OBSERVATIONS
END_STATE
#####################end POSTOVLP-#####################


#####################begin OVLP1_START+#####################
BEGIN_STATE
state_id: ovlp1_start+_1
	BEGIN_TRANSITIONS
		type: 0
		state: ovlp1_start+_2
		ptrans: 1
	END_TRANSITIONS
	BEGIN_OBSERVATIONS
		seq: genomic_dna
		label: ovlp1_start+_1
		type: 1
		order: 0
		pobs:
			 0.555365 0.175689 0 0.268945

	END_OBSERVATIONS
END_STATE
BEGIN_STATE
state_id: ovlp1_start+_2
	BEGIN_TRANSITIONS
		type: 0
		state: ovlp1_start+_3!START+!
		ptrans: 1
	END_TRANSITIONS
	BEGIN_OBSERVATIONS
		seq: genomic_dna
		type: 0
		order: 0
		pobs:
			 0 0 0 1

	END_OBSERVATIONS
END_STATE
BEGIN_STATE
state_id: ovlp1_start+_3!START+!
	BEGIN_TRANSITIONS
		type: 0
		state: ovlp1_coding+_2;1
		ptrans: 1 
	END_TRANSITIONS
	BEGIN_OBSERVATIONS
		seq: genomic_dna
		type: 0
		order: 0
		pobs:
			 0 1 0 0

	END_OBSERVATIONS
END_STATE
################end OVLP1_START+#########################

################begin OVLP1_CODING+#####################
BEGIN_STATE
state_id: ovlp1_coding+_2;1
	BEGIN_TRANSITIONS
		type: 0
		state: ovlp1_coding+_3;2
		ptrans: 1
	END_TRANSITIONS
	BEGIN_OBSERVATIONS
		seq: genomic_dna
		type: 1
		order: 1
		pobs:
			random
	END_OBSERVATIONS
END_STATE
BEGIN_STATE
state_id: ovlp1_coding+_3;2
	BEGIN_TRANSITIONS
		type: 1
		state: ovlp1_coding+_1;3
		ptrans: 0.95
		type: 1
		state: ovlp1_stop+_1
		ptrans: 0.05
	END_TRANSITIONS
	BEGIN_OBSERVATIONS
		seq: genomic_dna
		type: 1
		order: 1
		pobs:
			random
		excepted: 
			TGA
			TAG
			TAA
	END_OBSERVATIONS
END_STATE
BEGIN_STATE
state_id: ovlp1_coding+_1;3
	BEGIN_TRANSITIONS
		type: 0
		state: ovlp1_coding+_2;1
		ptrans: 1
	END_TRANSITIONS
	BEGIN_OBSERVATIONS
		seq: genomic_dna
		type: 1
		order: 1
		pobs:
			random
		excepted: 
			TGA
			TAG
			TAA
	END_OBSERVATIONS
END_STATE
##################end OVLP1_CODING+##############################


###################begin OVLP1_STOP+####################
BEGIN_STATE
state_id: ovlp1_stop+_1
	BEGIN_TRANSITIONS
		type: 1
		state: ovlp1_stop+_2*1
		ptrans: 0.766363
		type: 1
		state: ovlp1_stop+_2*2
		ptrans: 0.233637
	END_TRANSITIONS
	BEGIN_OBSERVATIONS
		seq: genomic_dna
		type: 0
		order: 0
		pobs:
			 0 0 0 1

	END_OBSERVATIONS
END_STATE
BEGIN_STATE
state_id: ovlp1_stop+_2*1
ps_init: 0.000581329
	BEGIN_TRANSITIONS
		type: 1
		state: ovlp1_stop+_3*1
		ptrans: 0.31387
		type: 1
		state: ovlp1_stop+_3*2
		ptrans: 0.68613
	END_TRANSITIONS
	BEGIN_OBSERVATIONS
		seq: genomic_dna
		type: 0
		order: 0
		pobs:
			 1 0 0 0

	END_OBSERVATIONS
END_STATE
BEGIN_STATE
state_id: ovlp1_stop+_2*2
	BEGIN_TRANSITIONS
		type: 0
		state: ovlp1_stop+_3*2
		ptrans: 1
	END_TRANSITIONS
	BEGIN_OBSERVATIONS
		seq: genomic_dna
		type: 0
		order: 0
		pobs:
			 0 1 0 0

	END_OBSERVATIONS
END_STATE
BEGIN_STATE
state_id: ovlp1_stop+_3*1
	BEGIN_TRANSITIONS
		type: 0
		state: post_ovlp+_3
		ptrans: 1
	END_TRANSITIONS
	BEGIN_OBSERVATIONS
		seq: genomic_dna
		type: 0
		order: 0
		pobs:
			 0 1 0 0

	END_OBSERVATIONS
END_STATE
BEGIN_STATE
state_id: ovlp1_stop+_3*2
	BEGIN_TRANSITIONS
		type: 0
		state: post_ovlp+_3
		ptrans: 1
	END_TRANSITIONS
	BEGIN_OBSERVATIONS
		seq: genomic_dna
		type: 0
		order: 0
		pobs:
			 1 0 0 0

	END_OBSERVATIONS
END_STATE
########################end STOP+######################
#############end END_OVERLAP1++

########BEGIN_OVERLAP2++
#####################begin OVLP2_START+#####################
BEGIN_STATE
state_id: ovlp2_start+_1
	BEGIN_TRANSITIONS
		type: 0
		state: ovlp2_start+_2
		ptrans: 1
	END_TRANSITIONS
	BEGIN_OBSERVATIONS
		seq: genomic_dna
		type: 1
		order: 0
		pobs:
			 0.555365 0.175689 0 0.268945
                excepted: TGA TAA TAG
	END_OBSERVATIONS
END_STATE
BEGIN_STATE
state_id: ovlp2_start+_2
	BEGIN_TRANSITIONS
		type: 0
		state: ovlp2_start+_3!START+!
		ptrans: 1
	END_TRANSITIONS
	BEGIN_OBSERVATIONS
		seq: genomic_dna
		type: 0
		order: 0
		pobs:
			 0 0 0 1

	END_OBSERVATIONS
END_STATE
BEGIN_STATE
state_id: ovlp2_start+_3!START+!
	BEGIN_TRANSITIONS
		type: 1
		state: ovlp2_coding+_3;1
		ptrans: 0.5 
		type: 1
		state: ovlp2_stop+_3*2
		ptrans: 0.5
	END_TRANSITIONS
	BEGIN_OBSERVATIONS
		seq: genomic_dna
		type: 0
		order: 0
		pobs:
			 0 1 0 0

	END_OBSERVATIONS
END_STATE
################end OVLP2_START+#########################

################begin OVLP2_CODING+#####################
BEGIN_STATE
state_id: ovlp2_coding+_3;1
	BEGIN_TRANSITIONS
		type: 1
		state: ovlp2_coding+_1;2
		ptrans: 0.95
		type: 1
		state: ovlp2_stop+_1
		ptrans: 0.05
	END_TRANSITIONS
	BEGIN_OBSERVATIONS
		seq: genomic_dna
		type: 1
		order: 1
		pobs:
			random
		excepted: 
			TGA
			TAG
			TAA
	END_OBSERVATIONS
END_STATE
BEGIN_STATE
state_id: ovlp2_coding+_1;2
	BEGIN_TRANSITIONS
		type: 0
		state: ovlp2_coding+_2;3
		ptrans: 1
	END_TRANSITIONS
	BEGIN_OBSERVATIONS
		seq: genomic_dna
		type: 1
		order: 1
		pobs:
			random
	END_OBSERVATIONS
END_STATE
BEGIN_STATE
state_id: ovlp2_coding+_2;3
	BEGIN_TRANSITIONS
		type: 0
		state: ovlp2_coding+_3;1
		ptrans: 1
	END_TRANSITIONS
	BEGIN_OBSERVATIONS
		seq: genomic_dna
		type: 1
		order: 1
		pobs:
			random
		excepted: 
			TGA
			TAG
			TAA
	END_OBSERVATIONS
END_STATE
##################end OVLP2_CODING+##############################


###################begin OVLP2_STOP+####################
BEGIN_STATE
state_id: ovlp2_stop+_1
	BEGIN_TRANSITIONS
		type: 1
		state: ovlp2_stop+_2*1
		ptrans: 0.766363
		type: 1
		state: ovlp2_stop+_2*2
		ptrans: 0.233637
	END_TRANSITIONS
	BEGIN_OBSERVATIONS
		seq: genomic_dna
		type: 0
		order: 0
		pobs:
			 0 0 0 1

	END_OBSERVATIONS
END_STATE
BEGIN_STATE
state_id: ovlp2_stop+_2*1
ps_init: 0.000581329
	BEGIN_TRANSITIONS
		type: 1
		state: ovlp2_stop+_3*1
		ptrans: 0.31387
		type: 1
		state: ovlp2_stop+_3*2
		ptrans: 0.68613
	END_TRANSITIONS
	BEGIN_OBSERVATIONS
		seq: genomic_dna
		type: 0
		order: 0
		pobs:
			 1 0 0 0

	END_OBSERVATIONS
END_STATE
BEGIN_STATE
state_id: ovlp2_stop+_2*2
	BEGIN_TRANSITIONS
		type: 0
		state: ovlp2_stop+_3*2
		ptrans: 1
	END_TRANSITIONS
	BEGIN_OBSERVATIONS
		seq: genomic_dna
		type: 0
		order: 0
		pobs:
			 0 1 0 0

	END_OBSERVATIONS
END_STATE
BEGIN_STATE
state_id: ovlp2_stop+_3*1
	BEGIN_TRANSITIONS
		type: 0
		state: post_ovlp+_2
		ptrans: 1
	END_TRANSITIONS
	BEGIN_OBSERVATIONS
		seq: genomic_dna
		type: 0
		order: 0
		pobs:
			 0 1 0 0

	END_OBSERVATIONS
END_STATE
BEGIN_STATE
state_id: ovlp2_stop+_3*2
	BEGIN_TRANSITIONS
		type: 0
		state: post_ovlp+_2
		ptrans: 1
	END_TRANSITIONS
	BEGIN_OBSERVATIONS
		seq: genomic_dna
		type: 0
		order: 0
		pobs:
			 1 0 0 0

	END_OBSERVATIONS
END_STATE
########################end STOP+######################
#############end END_OVERLAP2++

########################begin SHORT_OVERLAP+-##########

BEGIN_STATE
state_id: short_overlap+-_1
	BEGIN_TRANSITIONS
		type: 0
		state: short_overlap+-_2
		ptrans: 1
	END_TRANSITIONS
	BEGIN_OBSERVATIONS
		seq: genomic_dna
		type: 1
		order: 0
		pobs:
			 0 0 0.5 0.5

	END_OBSERVATIONS
END_STATE

BEGIN_STATE
state_id: short_overlap+-_2
	BEGIN_TRANSITIONS
		type: 0
		state: short_overlap+-_3
		ptrans: 1
	END_TRANSITIONS
	BEGIN_OBSERVATIONS
		seq: genomic_dna
		type: 0
		order: 0
		pobs:
			 0 0 0 1

	END_OBSERVATIONS
END_STATE

BEGIN_STATE
state_id: short_overlap+-_3
	BEGIN_TRANSITIONS
		type: 0
		state: short_overlap+-_4
		ptrans: 1
	END_TRANSITIONS
	BEGIN_OBSERVATIONS
		seq: genomic_dna
		type: 0
		order: 0
		pobs:
			 1 0 0 0

	END_OBSERVATIONS
END_STATE

BEGIN_STATE
state_id: short_overlap+-_4
	BEGIN_TRANSITIONS
		type: 0
		state: post_ovlp-_2
		ptrans: 1
	END_TRANSITIONS
	BEGIN_OBSERVATIONS
		seq: genomic_dna
		type: 1
		order: 0
		pobs:
			 0.5 0.5 0 0

	END_OBSERVATIONS
END_STATE
########################end SHORT_OVERLAP+-##########

########################begin OVLP1-
BEGIN_STATE
state_id: ovlp1_stop-_3*1
	BEGIN_TRANSITIONS
		type: 1
		state: ovlp1_stop-_2*1
		ptrans: 0.266341
		type: 1
		state: ovlp1_stop-_2*2
		ptrans: 0.733659
	END_TRANSITIONS
	BEGIN_OBSERVATIONS
		seq: genomic_dna
		type: 0
		order: 0
		pobs:
			 0 0 0 1

	END_OBSERVATIONS
END_STATE
BEGIN_STATE
state_id: ovlp1_stop-_3*2
	BEGIN_TRANSITIONS
		type: 0
		state: ovlp1_stop-_2*2
		ptrans: 1
	END_TRANSITIONS
	BEGIN_OBSERVATIONS
		seq: genomic_dna
		type: 0
		order: 0
		pobs:
			 0 0 1 0

	END_OBSERVATIONS
END_STATE
BEGIN_STATE
state_id: ovlp1_stop-_2*1
	BEGIN_TRANSITIONS
		type: 0
		state: ovlp1_stop-_1
		ptrans: 1
	END_TRANSITIONS
	BEGIN_OBSERVATIONS
		seq: genomic_dna
		type: 0
		order: 0
		pobs:
			 0 0 1 0

	END_OBSERVATIONS
END_STATE
BEGIN_STATE
state_id: ovlp1_stop-_2*2
	BEGIN_TRANSITIONS
		type: 0
		state: ovlp1_stop-_1
		ptrans: 1
	END_TRANSITIONS
	BEGIN_OBSERVATIONS
		seq: genomic_dna
		type: 0
		order: 0
		pobs:
			 0 0 0 1

	END_OBSERVATIONS
END_STATE
BEGIN_STATE
state_id: ovlp1_stop-_1
	BEGIN_TRANSITIONS
		type: 0
		state: ovlp1_coding-_2;3
		ptrans: 1
	END_TRANSITIONS
	BEGIN_OBSERVATIONS
		seq: genomic_dna
		type: 0
		order: 0
		pobs:
			 1 0 0 0

	END_OBSERVATIONS
END_STATE
##################end OVLP1_STOP-########################
#####################begin OVLP1_CODING-####################
BEGIN_STATE
state_id: ovlp1_coding-_3;1
	BEGIN_TRANSITIONS
		type: 0
		state: ovlp1_coding-_2;3
		ptrans: 1
	END_TRANSITIONS
	BEGIN_OBSERVATIONS
		seq: genomic_dna
		type: 1
		order: 1
		pobs:
			random
		excepted: 
			TCA
			TTA
			CTA
	END_OBSERVATIONS
END_STATE
BEGIN_STATE
state_id: ovlp1_coding-_2;3
	BEGIN_TRANSITIONS
		type: 1
		state: ovlp1_coding-_1;2
		ptrans: 1
	END_TRANSITIONS
	BEGIN_OBSERVATIONS
		seq: genomic_dna
		type: 1
		order: 1
		pobs:
			random
	END_OBSERVATIONS
END_STATE
BEGIN_STATE
state_id: ovlp1_coding-_1;2
	BEGIN_TRANSITIONS
		type: 1
		state: ovlp1_coding-_3;1
		ptrans: 0.95
		type: 1 
		state: ovlp1_start-_3!START-!
		ptrans: 0.05
	END_TRANSITIONS
	BEGIN_OBSERVATIONS
		seq: genomic_dna
		type: 1
		order: 1
		pobs:
			random
		excepted: 
			TCA
			TTA
			CTA
	END_OBSERVATIONS
END_STATE
######################end OVLP1_CODING3-###################
######################begin OVLP1_START-###################
BEGIN_STATE
state_id: ovlp1_start-_3!START-!
	BEGIN_TRANSITIONS
		type: 0
		state: ovlp1_start-_2
		ptrans: 1
	END_TRANSITIONS
	BEGIN_OBSERVATIONS
		seq: genomic_dna
		type: 0
		order: 0
		pobs:
			 0 0 1 0
	END_OBSERVATIONS
END_STATE
BEGIN_STATE
state_id: ovlp1_start-_2
	BEGIN_TRANSITIONS
		type: 0
		state: ovlp1_start-_1
		ptrans: 1
	END_TRANSITIONS
	BEGIN_OBSERVATIONS
		seq: genomic_dna
		type: 0
		order: 0
		pobs:
			 1 0 0 0

	END_OBSERVATIONS
END_STATE
BEGIN_STATE
state_id: ovlp1_start-_1
	BEGIN_TRANSITIONS
		type: 0
		state: post_ovlp-_1
		ptrans: 1
	END_TRANSITIONS
	BEGIN_OBSERVATIONS
		seq: genomic_dna
		tied_to: ovlp1_start+_1
		type: 3
	END_OBSERVATIONS
END_STATE
###################end OVLP1_START-###########################

###################begin OVLP2-
BEGIN_STATE
state_id: ovlp2_stop-_3*1
	BEGIN_TRANSITIONS
		type: 1
		state: ovlp2_stop-_2*1
		ptrans: 0.25
		type: 1
		state: ovlp2_stop-_2*2
		ptrans: 0.25
		type: 1
		state: ovlp2_start-_3!START-!
		ptrans: 0.5
	END_TRANSITIONS
	BEGIN_OBSERVATIONS
		seq: genomic_dna
		type: 0
		order: 0
		pobs:
			 0 0 0 1

	END_OBSERVATIONS
END_STATE
BEGIN_STATE
state_id: ovlp2_stop-_3*2
	BEGIN_TRANSITIONS
		type: 0
		state: ovlp2_stop-_2*2
		ptrans: 1
	END_TRANSITIONS
	BEGIN_OBSERVATIONS
		seq: genomic_dna
		type: 0
		order: 0
		pobs:
			 0 0 1 0

	END_OBSERVATIONS
END_STATE
BEGIN_STATE
state_id: ovlp2_stop-_2*1
	BEGIN_TRANSITIONS
		type: 0
		state: ovlp2_stop-_1
		ptrans: 1
	END_TRANSITIONS
	BEGIN_OBSERVATIONS
		seq: genomic_dna
		type: 0
		order: 0
		pobs:
			 0 0 1 0

	END_OBSERVATIONS
END_STATE
BEGIN_STATE
state_id: ovlp2_stop-_2*2
	BEGIN_TRANSITIONS
		type: 0
		state: ovlp2_stop-_1
		ptrans: 1
	END_TRANSITIONS
	BEGIN_OBSERVATIONS
		seq: genomic_dna
		type: 0
		order: 0
		pobs:
			 0 0 0 1

	END_OBSERVATIONS
END_STATE
BEGIN_STATE
state_id: ovlp2_stop-_1
	BEGIN_TRANSITIONS
                tied_to: trans_ovlp2_coding-_2;1
		state: ovlp2_coding-_1;3
		state: ovlp2_coding-_1;3bis
	END_TRANSITIONS
	BEGIN_OBSERVATIONS
		seq: genomic_dna
		type: 0
		order: 0
		pobs:
			 1 0 0 0

	END_OBSERVATIONS
END_STATE
##################end OVLP2_STOP-########################
#####################begin OVLP2_CODING3-####################
BEGIN_STATE
state_id: ovlp2_coding-_3;2
	BEGIN_TRANSITIONS
		type: 0
		state: ovlp2_coding-_2;1
		ptrans: 1
	END_TRANSITIONS
	BEGIN_OBSERVATIONS
		seq: genomic_dna
		type: 1
		order: 1
		pobs:
			random
	END_OBSERVATIONS
END_STATE
BEGIN_STATE
state_id: ovlp2_coding-_2;1
	BEGIN_TRANSITIONS
                label: trans_ovlp2_coding-_2;1
		type: 1
		state: ovlp2_coding-_1;3
		ptrans: 0.8
		type: 1
		state: ovlp2_coding-_1;3bis
		ptrans: 0.2
	END_TRANSITIONS
	BEGIN_OBSERVATIONS
		seq: genomic_dna
		type: 1
		order: 1
		pobs:
			random
		excepted: 
			TCA
			TTA
			CTA
	END_OBSERVATIONS
END_STATE
BEGIN_STATE
state_id: ovlp2_coding-_1;3
	BEGIN_TRANSITIONS
		type: 0
		state: ovlp2_coding-_3;2
		ptrans: 1
	END_TRANSITIONS
	BEGIN_OBSERVATIONS
		seq: genomic_dna
		type: 1
		order: 1
		pobs:
			random
		excepted: 
			TCA
			TTA
			CTA
	END_OBSERVATIONS
END_STATE
BEGIN_STATE
state_id: ovlp2_coding-_1;3bis
	BEGIN_TRANSITIONS
		type: 0
		state: ovlp2_start-_3!START-!
		ptrans: 1
	END_TRANSITIONS
	BEGIN_OBSERVATIONS
		seq: genomic_dna
		type: 1
		order: 0
		pobs:
			random
		excepted: 
			TCA
			TTA
			CTA
                        AAT AGT ACT ATT
                        GAT GGT GCT GTT
                        CAT CGT CCT CTT
                        TAT TGT TCT TTT
	END_OBSERVATIONS
END_STATE
######################end OVLP2_CODING3-###################
######################begin OVLP2_START-###################
BEGIN_STATE
state_id: ovlp2_start-_3!START-!
	BEGIN_TRANSITIONS
		type: 0
		state: ovlp2_start-_2
		ptrans: 1
	END_TRANSITIONS
	BEGIN_OBSERVATIONS
		seq: genomic_dna
		type: 0
		order: 0
		pobs:
			 0 0 1 0

	END_OBSERVATIONS
END_STATE
BEGIN_STATE
state_id: ovlp2_start-_2
	BEGIN_TRANSITIONS
		type: 0
		state: ovlp2_start-_1
		ptrans: 1
	END_TRANSITIONS
	BEGIN_OBSERVATIONS
		seq: genomic_dna
		type: 0
		order: 0
		pobs:
			 1 0 0 0

	END_OBSERVATIONS
END_STATE
BEGIN_STATE
state_id: ovlp2_start-_1
	BEGIN_TRANSITIONS
		type: 0
		state: post_ovlp-_2
		ptrans: 1
	END_TRANSITIONS
	BEGIN_OBSERVATIONS
		seq: genomic_dna
		type: 1
		order: 0
		pobs:
			 0.268945 0  0.175689 0.555365 

	END_OBSERVATIONS
END_STATE
###################end OVLP2_START-###########################
";

1
