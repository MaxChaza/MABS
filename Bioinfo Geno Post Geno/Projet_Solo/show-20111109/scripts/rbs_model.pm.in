package rbs_model;

$rbs_model::RBSdirect_m0 = "
###################begin RBS+############################
BEGIN_STATE
state_id: rbs+_1
BEGIN_TRANSITIONS
	type: 0
	state: rbs+_2
	ptrans: 1
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
	label: rbs+_1
	type: 1
	order: 0
	pobs: 
		random
END_OBSERVATIONS
END_STATE

BEGIN_STATE
state_id: rbs+_2
BEGIN_TRANSITIONS
	type: 0
	state: rbs+_3
	ptrans: 1
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
	label: rbs+_2
	type: 1
	order: 0
	pobs: 
		random
END_OBSERVATIONS
END_STATE

BEGIN_STATE
state_id: rbs+_3
BEGIN_TRANSITIONS
	type: 0
	state: rbs+_4
	ptrans: 1
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
	label: rbs+_3
	type: 1
	order: 0
	pobs: 
		random
END_OBSERVATIONS
END_STATE

BEGIN_STATE
state_id: rbs+_4
BEGIN_TRANSITIONS
	type: 0
	state: rbs+_5
	ptrans: 1
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
	label: rbs+_4
	type: 1
	order: 0
	pobs: 
		random
END_OBSERVATIONS
END_STATE

BEGIN_STATE
state_id: rbs+_5
BEGIN_TRANSITIONS
	type: 0
	state: rbs+_6
	ptrans: 1
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
	label: rbs+_5
	type: 1
	order: 0
	pobs: 
		random
END_OBSERVATIONS
END_STATE

BEGIN_STATE
state_id: rbs+_6
BEGIN_TRANSITIONS
	type: 0
	state: rbs+_7
	ptrans: 1
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
	label: rbs+_6
	type: 1
	order: 0
	pobs: 
		random
END_OBSERVATIONS
END_STATE

BEGIN_STATE
state_id: rbs+_7
BEGIN_TRANSITIONS
	type: 0
	state: rbs+_8
	ptrans: 1
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
	label: rbs+_7
	type: 1
	order: 0
	pobs: 
		random
END_OBSERVATIONS
END_STATE

BEGIN_STATE
state_id: rbs+_8
BEGIN_TRANSITIONS
	type: 0
	state: rbs+_9
	ptrans: 1
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
	label: rbs+_8
	type: 1
	order: 0
	pobs: 
		random
END_OBSERVATIONS
END_STATE

BEGIN_STATE
state_id: rbs+_9
BEGIN_TRANSITIONS
	type: 0
	state: rbs+_10
	ptrans: 1
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
	label: rbs+_9
	type: 1
	order: 0
	pobs: 
		random
END_OBSERVATIONS
END_STATE

BEGIN_STATE
state_id: rbs+_10
BEGIN_TRANSITIONS
	type: 0
	state: rbs+_11
	ptrans: 1
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
	label: rbs+_10
	type: 1
	order: 0
	pobs: 
		random
END_OBSERVATIONS
END_STATE

BEGIN_STATE
state_id: rbs+_11
BEGIN_TRANSITIONS
	type: 0
	state: rbs+_12
	ptrans: 1
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
	label: rbs+_11
	type: 1
	order: 0
	pobs: 
		random
END_OBSERVATIONS
END_STATE

BEGIN_STATE
state_id: rbs+_12
BEGIN_TRANSITIONS
	type: 0
	state: rbs+_13
	ptrans: 1
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
	label: rbs+_12
	type: 1
	order: 0
	pobs: 
		random
END_OBSERVATIONS
END_STATE

BEGIN_STATE
state_id: rbs+_13
BEGIN_TRANSITIONS
	type: 0
	state: rbs+_14!RBS+!
	ptrans: 1
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
	label: rbs+_13
	type: 1
	order: 0
	pobs: 
		random
END_OBSERVATIONS
END_STATE

BEGIN_STATE
state_id: rbs+_14!RBS+!
BEGIN_TRANSITIONS
	type: 0
	state: rbs+_spacer_1
	ptrans: 1
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
	label: rbs+_14
	type: 1
	order: 0
	pobs: 
		random
END_OBSERVATIONS
END_STATE

BEGIN_STATE
state_id: rbs+_spacer_1
BEGIN_TRANSITIONS
	type: 1
	state: rbs+_spacer_2
	ptrans: 0.80
<Add transitions to START+_1 p=0.20>
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
	label: rbs+_spacer_obs
	type: 1
	order: 1
	pobs: 
		random
END_OBSERVATIONS
END_STATE

BEGIN_STATE
state_id: rbs+_spacer_2
BEGIN_TRANSITIONS
	type: 1
	state: rbs+_spacer_3
	ptrans: 0.80
<Add transitions to START+_1 p=0.20>
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
	tied_to: rbs+_spacer_obs
	type: 2
END_OBSERVATIONS
END_STATE

BEGIN_STATE
state_id: rbs+_spacer_3
BEGIN_TRANSITIONS
	type: 1
	state: rbs+_spacer_3
	ptrans: 0.5
<Add transitions to START+_1 p=0.50>
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
	tied_to: rbs+_spacer_obs
	type: 2
END_OBSERVATIONS
END_STATE
#####################end RBS+#########################
";

$rbs_model::RBSopposite_m0 = "
#####################begin RBS-#########################
BEGIN_STATE
state_id: rbs-_spacer_3
BEGIN_TRANSITIONS
	type: 1
	state: rbs-_spacer_3
	ptrans: 0.5
	type: 1
	state: rbs-_spacer_2
	ptrans: 0.5
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
	label: rbs-_spacer_obs
	type: 1
	order: 1
	pobs:
		random
END_OBSERVATIONS
END_STATE

BEGIN_STATE
state_id: rbs-_spacer_2
BEGIN_TRANSITIONS
	type: 0
	state: rbs-_spacer_1
	ptrans: 1
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
	tied_to: rbs-_spacer_obs
	type: 2
END_OBSERVATIONS
END_STATE

BEGIN_STATE
state_id: rbs-_spacer_1
BEGIN_TRANSITIONS
	type: 0
	state: rbs-_14!RBS-!
	ptrans: 1
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
	tied_to: rbs-_spacer_obs
	type: 2
END_OBSERVATIONS
END_STATE

BEGIN_STATE
state_id: rbs-_14!RBS-!
BEGIN_TRANSITIONS
	type: 0
	state: rbs-_13
	ptrans: 1
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
	tied_to: rbs+_14
	type: 3
END_OBSERVATIONS
END_STATE

BEGIN_STATE
state_id: rbs-_13
BEGIN_TRANSITIONS
	type: 0
	state: rbs-_12
	ptrans: 1
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
	tied_to: rbs+_13
	type: 3
END_OBSERVATIONS
END_STATE

BEGIN_STATE
state_id: rbs-_12
BEGIN_TRANSITIONS
	type: 0
	state: rbs-_11
	ptrans: 1
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
	tied_to: rbs+_12
	type: 3
END_OBSERVATIONS
END_STATE

BEGIN_STATE
state_id: rbs-_11
BEGIN_TRANSITIONS
	type: 0
	state: rbs-_10
	ptrans: 1
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
	tied_to: rbs+_11
	type: 3
END_OBSERVATIONS
END_STATE

BEGIN_STATE
state_id: rbs-_10
BEGIN_TRANSITIONS
	type: 0
	state: rbs-_9
	ptrans: 1
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
	tied_to: rbs+_10
	type: 3
END_OBSERVATIONS
END_STATE

BEGIN_STATE
state_id: rbs-_9
BEGIN_TRANSITIONS
	type: 0
	state: rbs-_8
	ptrans: 1
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
	tied_to: rbs+_9
	type: 3
END_OBSERVATIONS
END_STATE

BEGIN_STATE
state_id: rbs-_8
BEGIN_TRANSITIONS
	type: 0
	state: rbs-_7
	ptrans: 1
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
	tied_to: rbs+_8
	type: 3
END_OBSERVATIONS
END_STATE

BEGIN_STATE
state_id: rbs-_7
BEGIN_TRANSITIONS
	type: 0
	state: rbs-_6
	ptrans: 1
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
	tied_to: rbs+_7
	type: 3
END_OBSERVATIONS
END_STATE

BEGIN_STATE
state_id: rbs-_6
BEGIN_TRANSITIONS
	type: 0
	state: rbs-_5
	ptrans: 1
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
	tied_to: rbs+_5
	type: 3
END_OBSERVATIONS
END_STATE

BEGIN_STATE
state_id: rbs-_5
BEGIN_TRANSITIONS
	type: 0
	state: rbs-_4
	ptrans: 1
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
	tied_to: rbs+_5
	type: 3
END_OBSERVATIONS
END_STATE

BEGIN_STATE
state_id: rbs-_4
BEGIN_TRANSITIONS
	type: 0
	state: rbs-_3
	ptrans: 1
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
	tied_to: rbs+_4
	type: 3
END_OBSERVATIONS
END_STATE

BEGIN_STATE
state_id: rbs-_3
BEGIN_TRANSITIONS
	type: 0
	state: rbs-_2
	ptrans: 1
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
	tied_to: rbs+_3
	type: 3
END_OBSERVATIONS
END_STATE

BEGIN_STATE
state_id: rbs-_2
BEGIN_TRANSITIONS
	type: 0
	state: rbs-_1
	ptrans: 1
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
	tied_to: rbs+_2
	type: 3
END_OBSERVATIONS
END_STATE

BEGIN_STATE
state_id: rbs-_1
BEGIN_TRANSITIONS
<Add transitions to INTER>
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
	tied_to: rbs+_1
	type: 3
END_OBSERVATIONS
END_STATE
#####################begin RBS-#########################
";


$rbs_model::RBSdirect_m1 = "
###################begin RBS+############################
BEGIN_STATE
state_id: rbs+_1
BEGIN_TRANSITIONS
	type: 0
	state: rbs+_2
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
state_id: rbs+_2
BEGIN_TRANSITIONS
	type: 0
	state: rbs+_3
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
state_id: rbs+_3
BEGIN_TRANSITIONS
	type: 0
	state: rbs+_4
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
state_id: rbs+_4
BEGIN_TRANSITIONS
	type: 0
	state: rbs+_5
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
state_id: rbs+_5
BEGIN_TRANSITIONS
	type: 0
	state: rbs+_6
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
state_id: rbs+_6
BEGIN_TRANSITIONS
	type: 0
	state: rbs+_7
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
state_id: rbs+_7
BEGIN_TRANSITIONS
	type: 0
	state: rbs+_8
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
state_id: rbs+_8
BEGIN_TRANSITIONS
	type: 0
	state: rbs+_9
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
state_id: rbs+_9
BEGIN_TRANSITIONS
	type: 0
	state: rbs+_10
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
state_id: rbs+_10
BEGIN_TRANSITIONS
	type: 0
	state: rbs+_11
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
state_id: rbs+_11
BEGIN_TRANSITIONS
	type: 0
	state: rbs+_12
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
state_id: rbs+_12
BEGIN_TRANSITIONS
	type: 0
	state: rbs+_13
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
state_id: rbs+_13
BEGIN_TRANSITIONS
	type: 0
	state: rbs+_14!RBS+!
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
state_id: rbs+_14!RBS+!
BEGIN_TRANSITIONS
	type: 0
	state: rbs+_spacer_1
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
state_id: rbs+_spacer_1
BEGIN_TRANSITIONS
	type: 1
	state: rbs+_spacer_2
	ptrans: 0.80
<Add transitions to START+_1 p=0.20>
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
        label: rbs+_spacer_obs
	type: 1
	order: 1
	pobs: 
		random
END_OBSERVATIONS
END_STATE

BEGIN_STATE
state_id: rbs+_spacer_2
BEGIN_TRANSITIONS
	type: 1
	state: rbs+_spacer_3
	ptrans: 0.80
<Add transitions to START+_1 p=0.20>
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
	tied_to: rbs+_spacer_obs
	type: 2
END_OBSERVATIONS
END_STATE

BEGIN_STATE
state_id: rbs+_spacer_3
BEGIN_TRANSITIONS
	type: 1
	state: rbs+_spacer_3
	ptrans: 0.5
<Add transitions to START+_1 p=0.50>
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
	tied_to: rbs+_spacer_obs
	type: 2
END_OBSERVATIONS
END_STATE
#####################end RBS+#########################
";

$rbs_model::RBSopposite_m1 = "
#####################begin RBS-#########################
BEGIN_STATE
state_id: rbs-_spacer_3
BEGIN_TRANSITIONS
	type: 1
	state: rbs-_spacer_3
	ptrans: 0.5
	type: 1
	state: rbs-_spacer_2
	ptrans: 0.5
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
	label: rbs-_spacer_obs
	type: 1
	order: 1
	pobs:
		random
END_OBSERVATIONS
END_STATE

BEGIN_STATE
state_id: rbs-_spacer_2
BEGIN_TRANSITIONS
	type: 0
	state: rbs-_spacer_1
	ptrans: 1
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
	tied_to: rbs-_spacer_obs
	type: 2
END_OBSERVATIONS
END_STATE

BEGIN_STATE
state_id: rbs-_spacer_1
BEGIN_TRANSITIONS
	type: 0
	state: rbs-_14!RBS-!
	ptrans: 1
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
	tied_to: rbs-_spacer_obs
	type: 2
END_OBSERVATIONS
END_STATE

BEGIN_STATE
state_id: rbs-_14!RBS-!
BEGIN_TRANSITIONS
	type: 0
	state: rbs-_13
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
state_id: rbs-_13
BEGIN_TRANSITIONS
	type: 0
	state: rbs-_12
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
state_id: rbs-_12
BEGIN_TRANSITIONS
	type: 0
	state: rbs-_11
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
state_id: rbs-_11
BEGIN_TRANSITIONS
	type: 0
	state: rbs-_10
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
state_id: rbs-_10
BEGIN_TRANSITIONS
	type: 0
	state: rbs-_9
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
state_id: rbs-_9
BEGIN_TRANSITIONS
	type: 0
	state: rbs-_8
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
state_id: rbs-_8
BEGIN_TRANSITIONS
	type: 0
	state: rbs-_7
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
state_id: rbs-_7
BEGIN_TRANSITIONS
	type: 0
	state: rbs-_6
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
state_id: rbs-_6
BEGIN_TRANSITIONS
	type: 0
	state: rbs-_5
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
state_id: rbs-_5
BEGIN_TRANSITIONS
	type: 0
	state: rbs-_4
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
state_id: rbs-_4
BEGIN_TRANSITIONS
	type: 0
	state: rbs-_3
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
state_id: rbs-_3
BEGIN_TRANSITIONS
	type: 0
	state: rbs-_2
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
state_id: rbs-_2
BEGIN_TRANSITIONS
	type: 0
	state: rbs-_1
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
state_id: rbs-_1
BEGIN_TRANSITIONS
<Add transitions to INTER>
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
        type: 1
        order: 1
        pobs: 
                random
END_OBSERVATIONS
END_STATE
#####################begin RBS-#########################
";

$rbs_model::RBSdirect_double_m0 = "
###################begin RBS+############################
BEGIN_STATE
state_id: rbs+_1
BEGIN_TRANSITIONS
	type: 1
	state: rbs+_2*1
	ptrans: 0.5
	type: 1
	state: rbs+_2*2
	ptrans: 0.5
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
	label: rbs+_1
	type: 1
	order: 0
	pobs: 
		random
END_OBSERVATIONS
END_STATE

BEGIN_STATE
state_id: rbs+_2*1
BEGIN_TRANSITIONS
	type: 0
	state: rbs+_3*1
	ptrans: 1
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
	label: rbs+_2*1
	type: 1
	order: 0
	pobs: 
		random
END_OBSERVATIONS
END_STATE

BEGIN_STATE
state_id: rbs+_2*2
BEGIN_TRANSITIONS
	type: 0
	state: rbs+_3*2
	ptrans: 1
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
	label: rbs+_2*2
	type: 1
	order: 0
	pobs: 
		random
END_OBSERVATIONS
END_STATE

BEGIN_STATE
state_id: rbs+_3*1
BEGIN_TRANSITIONS
	type: 0
	state: rbs+_4*1
	ptrans: 1
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
	label: rbs+_3*1
	type: 1
	order: 0
	pobs: 
		random
END_OBSERVATIONS
END_STATE

BEGIN_STATE
state_id: rbs+_3*2
BEGIN_TRANSITIONS
	type: 0
	state: rbs+_4*2
	ptrans: 1
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
	label: rbs+_3*2
	type: 1
	order: 0
	pobs: 
		random
END_OBSERVATIONS
END_STATE

BEGIN_STATE
state_id: rbs+_4*1
BEGIN_TRANSITIONS
	type: 0
	state: rbs+_5*1
	ptrans: 1
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
	label: rbs+_4*1
	type: 1
	order: 0
	pobs: 
		random
END_OBSERVATIONS
END_STATE

BEGIN_STATE
state_id: rbs+_4*2
BEGIN_TRANSITIONS
	type: 0
	state: rbs+_5*2
	ptrans: 1
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
	label: rbs+_4*2
	type: 1
	order: 0
	pobs: 
		random
END_OBSERVATIONS
END_STATE

BEGIN_STATE
state_id: rbs+_5*1
BEGIN_TRANSITIONS
	type: 0
	state: rbs+_6*1
	ptrans: 1
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
	label: rbs+_5*1
	type: 1
	order: 0
	pobs: 
		random
END_OBSERVATIONS
END_STATE

BEGIN_STATE
state_id: rbs+_5*2
BEGIN_TRANSITIONS
	type: 0
	state: rbs+_6*2
	ptrans: 1
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
	label: rbs+_5*2
	type: 1
	order: 0
	pobs: 
		random
END_OBSERVATIONS
END_STATE


BEGIN_STATE
state_id: rbs+_6*1
BEGIN_TRANSITIONS
	type: 0
	state: rbs+_7*1
	ptrans: 1
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
	label: rbs+_6*1
	type: 1
	order: 0
	pobs: 
		random
END_OBSERVATIONS
END_STATE

BEGIN_STATE
state_id: rbs+_6*2
BEGIN_TRANSITIONS
	type: 0
	state: rbs+_7*2
	ptrans: 1
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
	label: rbs+_6*2
	type: 1
	order: 0
	pobs: 
		random
END_OBSERVATIONS
END_STATE

BEGIN_STATE
state_id: rbs+_7*1
BEGIN_TRANSITIONS
	type: 0
	state: rbs+_8*1
	ptrans: 1
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
	label: rbs+_7*1
	type: 1
	order: 0
	pobs: 
		random
END_OBSERVATIONS
END_STATE

BEGIN_STATE
state_id: rbs+_7*2
BEGIN_TRANSITIONS
	type: 0
	state: rbs+_8*2
	ptrans: 1
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
	label: rbs+_7*2
	type: 1
	order: 0
	pobs: 
		random
END_OBSERVATIONS
END_STATE

BEGIN_STATE
state_id: rbs+_8*1
BEGIN_TRANSITIONS
	type: 0
	state: rbs+_9*1
	ptrans: 1
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
	label: rbs+_8*1
	type: 1
	order: 0
	pobs: 
		random
END_OBSERVATIONS
END_STATE

BEGIN_STATE
state_id: rbs+_8*2
BEGIN_TRANSITIONS
	type: 0
	state: rbs+_9*2
	ptrans: 1
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
	label: rbs+_8*2
	type: 1
	order: 0
	pobs: 
		random
END_OBSERVATIONS
END_STATE


BEGIN_STATE
state_id: rbs+_9*1
BEGIN_TRANSITIONS
	type: 0
	state: rbs+_10*1
	ptrans: 1
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
	label: rbs+_9*1
	type: 1
	order: 0
	pobs: 
		random
END_OBSERVATIONS
END_STATE

BEGIN_STATE
state_id: rbs+_9*2
BEGIN_TRANSITIONS
	type: 0
	state: rbs+_10*2
	ptrans: 1
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
	label: rbs+_9*2
	type: 1
	order: 0
	pobs: 
		random
END_OBSERVATIONS
END_STATE

BEGIN_STATE
state_id: rbs+_10*1
BEGIN_TRANSITIONS
	type: 0
	state: rbs+_11*1
	ptrans: 1
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
	label: rbs+_10*1
	type: 1
	order: 0
	pobs: 
		random
END_OBSERVATIONS
END_STATE

BEGIN_STATE
state_id: rbs+_10*2
BEGIN_TRANSITIONS
	type: 0
	state: rbs+_11*2
	ptrans: 1
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
	label: rbs+_10*2
	type: 1
	order: 0
	pobs: 
		random
END_OBSERVATIONS
END_STATE

BEGIN_STATE
state_id: rbs+_11*1
BEGIN_TRANSITIONS
	type: 0
	state: rbs+_12*1
	ptrans: 1
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
	label: rbs+_11*1
	type: 1
	order: 0
	pobs: 
		random
END_OBSERVATIONS
END_STATE

BEGIN_STATE
state_id: rbs+_11*2
BEGIN_TRANSITIONS
	type: 0
	state: rbs+_12*2
	ptrans: 1
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
	label: rbs+_11*2
	type: 1
	order: 0
	pobs: 
		random
END_OBSERVATIONS
END_STATE

BEGIN_STATE
state_id: rbs+_12*1
BEGIN_TRANSITIONS
	type: 0
	state: rbs+_13*1
	ptrans: 1
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
	label: rbs+_12*1
	type: 1
	order: 0
	pobs: 
		random
END_OBSERVATIONS
END_STATE

BEGIN_STATE
state_id: rbs+_12*2
BEGIN_TRANSITIONS
	type: 0
	state: rbs+_13*2
	ptrans: 1
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
	label: rbs+_12*2
	type: 1
	order: 0
	pobs: 
		random
END_OBSERVATIONS
END_STATE

BEGIN_STATE
state_id: rbs+_13*1
BEGIN_TRANSITIONS
	type: 0
	state: rbs+_14!RBS+!*1
	ptrans: 1
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
	label: rbs+_13*1
	type: 1
	order: 0
	pobs: 
		random
END_OBSERVATIONS
END_STATE

BEGIN_STATE
state_id: rbs+_13*2
BEGIN_TRANSITIONS
	type: 0
	state: rbs+_14!RBS+!*2
	ptrans: 1
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
	label: rbs+_13*2
	type: 1
	order: 0
	pobs: 
		random
END_OBSERVATIONS
END_STATE

BEGIN_STATE
state_id: rbs+_14!RBS+!*1
BEGIN_TRANSITIONS
	type: 0
	state: rbs+_spacer_1
	ptrans: 1
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
	label: rbs+_14*1
	type: 1
	order: 0
	pobs: 
		random
END_OBSERVATIONS
END_STATE

BEGIN_STATE
state_id: rbs+_14!RBS+!*2
BEGIN_TRANSITIONS
	type: 0
	state: rbs+_spacer_1
	ptrans: 1
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
	label: rbs+_14*2
	type: 1
	order: 0
	pobs: 
		random
END_OBSERVATIONS
END_STATE

BEGIN_STATE
state_id: rbs+_spacer_1
BEGIN_TRANSITIONS
	type: 1
	state: rbs+_spacer_2
	ptrans: 0.80
<Add transitions to START+_1 p=0.20>
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
	label: rbs+_spacer_obs
	type: 1
	order: 1
	pobs: 
		random
END_OBSERVATIONS
END_STATE

BEGIN_STATE
state_id: rbs+_spacer_2
BEGIN_TRANSITIONS
	type: 1
	state: rbs+_spacer_3
	ptrans: 0.80
<Add transitions to START+_1 p=0.20>
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
	tied_to: rbs+_spacer_obs
	type: 2
END_OBSERVATIONS
END_STATE

BEGIN_STATE
state_id: rbs+_spacer_3
BEGIN_TRANSITIONS
	type: 1
	state: rbs+_spacer_3
	ptrans: 0.5
<Add transitions to START+_1 p=0.50>
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
	tied_to: rbs+_spacer_obs
	type: 2
END_OBSERVATIONS
END_STATE
#####################end RBS+#########################
";

$rbs_model::RBSopposite_double_m0 = "
#####################begin RBS-#########################
BEGIN_STATE
state_id: rbs-_spacer_3
BEGIN_TRANSITIONS
	type: 1
	state: rbs-_spacer_3
	ptrans: 0.5
	type: 1
	state: rbs-_spacer_2
	ptrans: 0.5
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
	label: rbs-_spacer_obs
	type: 1
	order: 1
	pobs:
		random
END_OBSERVATIONS
END_STATE

BEGIN_STATE
state_id: rbs-_spacer_2
BEGIN_TRANSITIONS
	type: 0
	state: rbs-_spacer_1
	ptrans: 1
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
	tied_to: rbs-_spacer_obs
	type: 2
END_OBSERVATIONS
END_STATE

BEGIN_STATE
state_id: rbs-_spacer_1
BEGIN_TRANSITIONS
	type: 1
	state: rbs-_14!RBS-!*1
	ptrans: 0.5
	type: 1
	state: rbs-_14!RBS-!*2
	ptrans: 0.5
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
	tied_to: rbs-_spacer_obs
	type: 2
END_OBSERVATIONS
END_STATE

BEGIN_STATE
state_id: rbs-_14!RBS-!*1
BEGIN_TRANSITIONS
	type: 0
	state: rbs-_13*1
	ptrans: 1
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
	tied_to: rbs+_14*1
	type: 3
END_OBSERVATIONS
END_STATE

BEGIN_STATE
state_id: rbs-_14!RBS-!*2
BEGIN_TRANSITIONS
	type: 0
	state: rbs-_13*2
	ptrans: 1
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
	tied_to: rbs+_14*2
	type: 3
END_OBSERVATIONS
END_STATE

BEGIN_STATE
state_id: rbs-_13*1
BEGIN_TRANSITIONS
	type: 0
	state: rbs-_12*1
	ptrans: 1
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
	tied_to: rbs+_13*1
	type: 3
END_OBSERVATIONS
END_STATE

BEGIN_STATE
state_id: rbs-_13*2
BEGIN_TRANSITIONS
	type: 0
	state: rbs-_12*2
	ptrans: 1
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
	tied_to: rbs+_13*2
	type: 3
END_OBSERVATIONS
END_STATE

BEGIN_STATE
state_id: rbs-_12*1
BEGIN_TRANSITIONS
	type: 0
	state: rbs-_11*1
	ptrans: 1
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
	tied_to: rbs+_12*1
	type: 3
END_OBSERVATIONS
END_STATE

BEGIN_STATE
state_id: rbs-_12*2
BEGIN_TRANSITIONS
	type: 0
	state: rbs-_11*2
	ptrans: 1
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
	tied_to: rbs+_12*2
	type: 3
END_OBSERVATIONS
END_STATE

BEGIN_STATE
state_id: rbs-_11*1
BEGIN_TRANSITIONS
	type: 0
	state: rbs-_10*1
	ptrans: 1
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
	tied_to: rbs+_11*1
	type: 3
END_OBSERVATIONS
END_STATE

BEGIN_STATE
state_id: rbs-_11*2
BEGIN_TRANSITIONS
	type: 0
	state: rbs-_10*2
	ptrans: 1
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
	tied_to: rbs+_11*2
	type: 3
END_OBSERVATIONS
END_STATE

BEGIN_STATE
state_id: rbs-_10*1
BEGIN_TRANSITIONS
	type: 0
	state: rbs-_9*1
	ptrans: 1
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
	tied_to: rbs+_10*1
	type: 3
END_OBSERVATIONS
END_STATE

BEGIN_STATE
state_id: rbs-_10*2
BEGIN_TRANSITIONS
	type: 0
	state: rbs-_9*2
	ptrans: 1
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
	tied_to: rbs+_10*2
	type: 3
END_OBSERVATIONS
END_STATE

BEGIN_STATE
state_id: rbs-_9*1
BEGIN_TRANSITIONS
	type: 0
	state: rbs-_8*1
	ptrans: 1
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
	tied_to: rbs+_9*1
	type: 3
END_OBSERVATIONS
END_STATE

BEGIN_STATE
state_id: rbs-_9*2
BEGIN_TRANSITIONS
	type: 0
	state: rbs-_8*2
	ptrans: 1
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
	tied_to: rbs+_9*2
	type: 3
END_OBSERVATIONS
END_STATE

BEGIN_STATE
state_id: rbs-_8*1
BEGIN_TRANSITIONS
	type: 0
	state: rbs-_7*1
	ptrans: 1
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
	tied_to: rbs+_8*1
	type: 3
END_OBSERVATIONS
END_STATE

BEGIN_STATE
state_id: rbs-_8*2
BEGIN_TRANSITIONS
	type: 0
	state: rbs-_7*2
	ptrans: 1
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
	tied_to: rbs+_8*2
	type: 3
END_OBSERVATIONS
END_STATE

BEGIN_STATE
state_id: rbs-_7*1
BEGIN_TRANSITIONS
	type: 0
	state: rbs-_6*1
	ptrans: 1
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
	tied_to: rbs+_7*1
	type: 3
END_OBSERVATIONS
END_STATE

BEGIN_STATE
state_id: rbs-_7*2
BEGIN_TRANSITIONS
	type: 0
	state: rbs-_6*2
	ptrans: 1
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
	tied_to: rbs+_7*2
	type: 3
END_OBSERVATIONS
END_STATE

BEGIN_STATE
state_id: rbs-_6*1
BEGIN_TRANSITIONS
	type: 0
	state: rbs-_5*1
	ptrans: 1
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
	tied_to: rbs+_5*1
	type: 3
END_OBSERVATIONS
END_STATE

BEGIN_STATE
state_id: rbs-_6*2
BEGIN_TRANSITIONS
	type: 0
	state: rbs-_5*2
	ptrans: 1
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
	tied_to: rbs+_5*2
	type: 3
END_OBSERVATIONS
END_STATE

BEGIN_STATE
state_id: rbs-_5*1
BEGIN_TRANSITIONS
	type: 0
	state: rbs-_4*1
	ptrans: 1
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
	tied_to: rbs+_5*1
	type: 3
END_OBSERVATIONS
END_STATE

BEGIN_STATE
state_id: rbs-_5*2
BEGIN_TRANSITIONS
	type: 0
	state: rbs-_4*2
	ptrans: 1
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
	tied_to: rbs+_5*2
	type: 3
END_OBSERVATIONS
END_STATE

BEGIN_STATE
state_id: rbs-_4*1
BEGIN_TRANSITIONS
	type: 0
	state: rbs-_3*1
	ptrans: 1
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
	tied_to: rbs+_4*1
	type: 3
END_OBSERVATIONS
END_STATE

BEGIN_STATE
state_id: rbs-_4*2
BEGIN_TRANSITIONS
	type: 0
	state: rbs-_3*2
	ptrans: 1
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
	tied_to: rbs+_4*2
	type: 3
END_OBSERVATIONS
END_STATE

BEGIN_STATE
state_id: rbs-_3*1
BEGIN_TRANSITIONS
	type: 0
	state: rbs-_2*1
	ptrans: 1
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
	tied_to: rbs+_3*1
	type: 3
END_OBSERVATIONS
END_STATE

BEGIN_STATE
state_id: rbs-_3*2
BEGIN_TRANSITIONS
	type: 0
	state: rbs-_2*2
	ptrans: 1
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
	tied_to: rbs+_3*2
	type: 3
END_OBSERVATIONS
END_STATE

BEGIN_STATE
state_id: rbs-_2*1
BEGIN_TRANSITIONS
	type: 0
	state: rbs-_1
	ptrans: 1
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
	tied_to: rbs+_2*1
	type: 3
END_OBSERVATIONS
END_STATE

BEGIN_STATE
state_id: rbs-_2*2
BEGIN_TRANSITIONS
	type: 0
	state: rbs-_1
	ptrans: 1
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
	tied_to: rbs+_2*2
	type: 3
END_OBSERVATIONS
END_STATE

BEGIN_STATE
state_id: rbs-_1
BEGIN_TRANSITIONS
<Add transitions to INTER>
END_TRANSITIONS
BEGIN_OBSERVATIONS
	seq: genomic_dna
	tied_to: rbs+_1
	type: 3
END_OBSERVATIONS
END_STATE
#####################begin RBS-#########################
";


1
