bin_PROGRAMS = show_emfit show_simul show_viterbi
COMMONSOURCES = CodingSeq.h CodingSeq.cc \
                CodonCodingSeq.h CodonCodingSeq.cc \
                const.h \
                DnaCodingSeq.h DnaCodingSeq.cc \
                HiddenMarkovModel.h HiddenMarkovModel.cc \
                HiddenMarkovModel_const.cc \
                HmmObservations.h HmmObservations.cc \
                HmmTransitions.h HmmTransitions.cc \
                HomologCodingSeq.h HomologCodingSeq.cc \
                int2char.h int2char.cc \
		ObsSequence.h ObsSequence.cc \
                Output.h Output.cc \
                PseudoOrderLoglMax.h PseudoOrderLoglMax.cc \
                reading.h reading.cc \
                SelectModel.h SelectModel.cc \
                SequenceSet.h SequenceSet.cc \
                shortfilename.h shortfilename.cc

show_emfit_SOURCES = $(COMMONSOURCES) Param_EM.h Param_EM.cc main_emfit.cc
show_simul_SOURCES = $(COMMONSOURCES) Param_Simul.h Param_Simul.cc main_simul.cc
show_viterbi_SOURCES = $(COMMONSOURCES) Param_Vit.h Param_Vit.cc main_viterbi.cc

CXXFLAGS=`@GSLCONFIG@ --cflags` -O2
LDFLAGS=`@GSLCONFIG@ --libs`

DISTCLEANFILES=*~
