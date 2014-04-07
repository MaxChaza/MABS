# Makefile.in generated automatically by automake 1.4-p6 from Makefile.am

# Copyright (C) 1994, 1995-8, 1999, 2001 Free Software Foundation, Inc.
# This Makefile.in is free software; the Free Software Foundation
# gives unlimited permission to copy and/or distribute it,
# with or without modifications, as long as this notice is preserved.

# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY, to the extent permitted by law; without
# even the implied warranty of MERCHANTABILITY or FITNESS FOR A
# PARTICULAR PURPOSE.


SHELL = /bin/sh

srcdir = .
top_srcdir = ..

prefix = /usr/local
exec_prefix = ${prefix}

bindir = ${exec_prefix}/bin
sbindir = ${exec_prefix}/sbin
libexecdir = ${exec_prefix}/libexec
datadir = ${prefix}/share
sysconfdir = ${prefix}/etc
sharedstatedir = ${prefix}/com
localstatedir = ${prefix}/var
libdir = ${exec_prefix}/lib
infodir = ${prefix}/info
mandir = ${prefix}/man
includedir = ${prefix}/include
oldincludedir = /usr/include

DESTDIR =

pkgdatadir = $(datadir)/show
pkglibdir = $(libdir)/show
pkgincludedir = $(includedir)/show

top_builddir = ..

ACLOCAL = aclocal-1.4
AUTOCONF = autoconf
AUTOMAKE = automake-1.4
AUTOHEADER = autoheader

INSTALL = /usr/bin/install -c
INSTALL_PROGRAM = ${INSTALL} $(AM_INSTALL_PROGRAM_FLAGS)
INSTALL_DATA = ${INSTALL} -m 644
INSTALL_SCRIPT = ${INSTALL}
transform = s,x,x,

NORMAL_INSTALL = :
PRE_INSTALL = :
POST_INSTALL = :
NORMAL_UNINSTALL = :
PRE_UNINSTALL = :
POST_UNINSTALL = :
AWK = gawk
CC = gcc
CP = /bin/cp
CXX = g++
GSLCONFIG = /usr/bin/gsl-config
LN_S = ln -s
MAKEINFO = makeinfo
MKDIR = /bin/mkdir
MODULEDIR = NONE/libexec
PACKAGE = show
PERL = /usr/local/bin/perl
VERSION = 20061029

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

CXXFLAGS = `/usr/bin/gsl-config --cflags` -O2
LDFLAGS = `/usr/bin/gsl-config --libs`

DISTCLEANFILES = *~
mkinstalldirs = $(SHELL) $(top_srcdir)/mkinstalldirs
CONFIG_HEADER = config.h
CONFIG_CLEAN_FILES = 
PROGRAMS =  $(bin_PROGRAMS)


DEFS = -DHAVE_CONFIG_H -I. -I$(srcdir) -I.
CPPFLAGS = 
LIBS = -lm 
show_emfit_OBJECTS =  CodingSeq.o CodonCodingSeq.o DnaCodingSeq.o \
HiddenMarkovModel.o HiddenMarkovModel_const.o HmmObservations.o \
HmmTransitions.o HomologCodingSeq.o int2char.o ObsSequence.o Output.o \
PseudoOrderLoglMax.o reading.o SelectModel.o SequenceSet.o \
shortfilename.o Param_EM.o main_emfit.o
show_emfit_LDADD = $(LDADD)
show_emfit_DEPENDENCIES = 
show_emfit_LDFLAGS = 
show_simul_OBJECTS =  CodingSeq.o CodonCodingSeq.o DnaCodingSeq.o \
HiddenMarkovModel.o HiddenMarkovModel_const.o HmmObservations.o \
HmmTransitions.o HomologCodingSeq.o int2char.o ObsSequence.o Output.o \
PseudoOrderLoglMax.o reading.o SelectModel.o SequenceSet.o \
shortfilename.o Param_Simul.o main_simul.o
show_simul_LDADD = $(LDADD)
show_simul_DEPENDENCIES = 
show_simul_LDFLAGS = 
show_viterbi_OBJECTS =  CodingSeq.o CodonCodingSeq.o DnaCodingSeq.o \
HiddenMarkovModel.o HiddenMarkovModel_const.o HmmObservations.o \
HmmTransitions.o HomologCodingSeq.o int2char.o ObsSequence.o Output.o \
PseudoOrderLoglMax.o reading.o SelectModel.o SequenceSet.o \
shortfilename.o Param_Vit.o main_viterbi.o
show_viterbi_LDADD = $(LDADD)
show_viterbi_DEPENDENCIES = 
show_viterbi_LDFLAGS = 
CXXCOMPILE = $(CXX) $(DEFS) $(INCLUDES) $(AM_CPPFLAGS) $(CPPFLAGS) $(AM_CXXFLAGS) $(CXXFLAGS)
CXXLD = $(CXX)
CXXLINK = $(CXXLD) $(AM_CXXFLAGS) $(CXXFLAGS) $(LDFLAGS) -o $@
CFLAGS = -g -O2
COMPILE = $(CC) $(DEFS) $(INCLUDES) $(AM_CPPFLAGS) $(CPPFLAGS) $(AM_CFLAGS) $(CFLAGS)
CCLD = $(CC)
LINK = $(CCLD) $(AM_CFLAGS) $(CFLAGS) $(LDFLAGS) -o $@
DIST_COMMON =  ./stamp-h.in Makefile.am Makefile.in config.h.in


DISTFILES = $(DIST_COMMON) $(SOURCES) $(HEADERS) $(TEXINFOS) $(EXTRA_DIST)

TAR = gtar
GZIP_ENV = --best
SOURCES = $(show_emfit_SOURCES) $(show_simul_SOURCES) $(show_viterbi_SOURCES)
OBJECTS = $(show_emfit_OBJECTS) $(show_simul_OBJECTS) $(show_viterbi_OBJECTS)

all: all-redirect
.SUFFIXES:
.SUFFIXES: .S .c .cc .o .s
$(srcdir)/Makefile.in: Makefile.am $(top_srcdir)/configure.in $(ACLOCAL_M4) 
	cd $(top_srcdir) && $(AUTOMAKE) --gnu --include-deps src/Makefile

Makefile: $(srcdir)/Makefile.in  $(top_builddir)/config.status
	cd $(top_builddir) \
	  && CONFIG_FILES=$(subdir)/$@ CONFIG_HEADERS= $(SHELL) ./config.status


config.h: stamp-h
	@if test ! -f $@; then \
		rm -f stamp-h; \
		$(MAKE) stamp-h; \
	else :; fi
stamp-h: $(srcdir)/config.h.in $(top_builddir)/config.status
	cd $(top_builddir) \
	  && CONFIG_FILES= CONFIG_HEADERS=src/config.h \
	     $(SHELL) ./config.status
	@echo timestamp > stamp-h 2> /dev/null
$(srcdir)/config.h.in: $(srcdir)/stamp-h.in
	@if test ! -f $@; then \
		rm -f $(srcdir)/stamp-h.in; \
		$(MAKE) $(srcdir)/stamp-h.in; \
	else :; fi
$(srcdir)/stamp-h.in: $(top_srcdir)/configure.in $(ACLOCAL_M4) 
	cd $(top_srcdir) && $(AUTOHEADER)
	@echo timestamp > $(srcdir)/stamp-h.in 2> /dev/null

mostlyclean-hdr:

clean-hdr:

distclean-hdr:
	-rm -f config.h

maintainer-clean-hdr:

mostlyclean-binPROGRAMS:

clean-binPROGRAMS:
	-test -z "$(bin_PROGRAMS)" || rm -f $(bin_PROGRAMS)

distclean-binPROGRAMS:

maintainer-clean-binPROGRAMS:

install-binPROGRAMS: $(bin_PROGRAMS)
	@$(NORMAL_INSTALL)
	$(mkinstalldirs) $(DESTDIR)$(bindir)
	@list='$(bin_PROGRAMS)'; for p in $$list; do \
	  if test -f $$p; then \
	    echo "  $(INSTALL_PROGRAM) $$p $(DESTDIR)$(bindir)/`echo $$p|sed 's/$(EXEEXT)$$//'|sed '$(transform)'|sed 's/$$/$(EXEEXT)/'`"; \
	     $(INSTALL_PROGRAM) $$p $(DESTDIR)$(bindir)/`echo $$p|sed 's/$(EXEEXT)$$//'|sed '$(transform)'|sed 's/$$/$(EXEEXT)/'`; \
	  else :; fi; \
	done

uninstall-binPROGRAMS:
	@$(NORMAL_UNINSTALL)
	list='$(bin_PROGRAMS)'; for p in $$list; do \
	  rm -f $(DESTDIR)$(bindir)/`echo $$p|sed 's/$(EXEEXT)$$//'|sed '$(transform)'|sed 's/$$/$(EXEEXT)/'`; \
	done

.c.o:
	$(COMPILE) -c $<

.s.o:
	$(COMPILE) -c $<

.S.o:
	$(COMPILE) -c $<

mostlyclean-compile:
	-rm -f *.o core *.core

clean-compile:

distclean-compile:
	-rm -f *.tab.c

maintainer-clean-compile:

show_emfit: $(show_emfit_OBJECTS) $(show_emfit_DEPENDENCIES)
	@rm -f show_emfit
	$(CXXLINK) $(show_emfit_LDFLAGS) $(show_emfit_OBJECTS) $(show_emfit_LDADD) $(LIBS)

show_simul: $(show_simul_OBJECTS) $(show_simul_DEPENDENCIES)
	@rm -f show_simul
	$(CXXLINK) $(show_simul_LDFLAGS) $(show_simul_OBJECTS) $(show_simul_LDADD) $(LIBS)

show_viterbi: $(show_viterbi_OBJECTS) $(show_viterbi_DEPENDENCIES)
	@rm -f show_viterbi
	$(CXXLINK) $(show_viterbi_LDFLAGS) $(show_viterbi_OBJECTS) $(show_viterbi_LDADD) $(LIBS)
.cc.o:
	$(CXXCOMPILE) -c $<

tags: TAGS

ID: $(HEADERS) $(SOURCES) $(LISP)
	list='$(SOURCES) $(HEADERS)'; \
	unique=`for i in $$list; do echo $$i; done | \
	  awk '    { files[$$0] = 1; } \
	       END { for (i in files) print i; }'`; \
	here=`pwd` && cd $(srcdir) \
	  && mkid -f$$here/ID $$unique $(LISP)

TAGS:  $(HEADERS) $(SOURCES) config.h.in $(TAGS_DEPENDENCIES) $(LISP)
	tags=; \
	here=`pwd`; \
	list='$(SOURCES) $(HEADERS)'; \
	unique=`for i in $$list; do echo $$i; done | \
	  awk '    { files[$$0] = 1; } \
	       END { for (i in files) print i; }'`; \
	test -z "$(ETAGS_ARGS)config.h.in$$unique$(LISP)$$tags" \
	  || (cd $(srcdir) && etags $(ETAGS_ARGS) $$tags config.h.in $$unique $(LISP) -o $$here/TAGS)

mostlyclean-tags:

clean-tags:

distclean-tags:
	-rm -f TAGS ID

maintainer-clean-tags:

distdir = $(top_builddir)/$(PACKAGE)-$(VERSION)/$(subdir)

subdir = src

distdir: $(DISTFILES)
	@for file in $(DISTFILES); do \
	  d=$(srcdir); \
	  if test -d $$d/$$file; then \
	    cp -pr $$d/$$file $(distdir)/$$file; \
	  else \
	    test -f $(distdir)/$$file \
	    || ln $$d/$$file $(distdir)/$$file 2> /dev/null \
	    || cp -p $$d/$$file $(distdir)/$$file || :; \
	  fi; \
	done
info-am:
info: info-am
dvi-am:
dvi: dvi-am
check-am: all-am
check: check-am
installcheck-am:
installcheck: installcheck-am
all-recursive-am: config.h
	$(MAKE) $(AM_MAKEFLAGS) all-recursive

install-exec-am: install-binPROGRAMS
install-exec: install-exec-am

install-data-am:
install-data: install-data-am

install-am: all-am
	@$(MAKE) $(AM_MAKEFLAGS) install-exec-am install-data-am
install: install-am
uninstall-am: uninstall-binPROGRAMS
uninstall: uninstall-am
all-am: Makefile $(PROGRAMS) config.h
all-redirect: all-am
install-strip:
	$(MAKE) $(AM_MAKEFLAGS) AM_INSTALL_PROGRAM_FLAGS=-s install
installdirs:
	$(mkinstalldirs)  $(DESTDIR)$(bindir)


mostlyclean-generic:

clean-generic:

distclean-generic:
	-rm -f Makefile $(CONFIG_CLEAN_FILES)
	-rm -f config.cache config.log stamp-h stamp-h[0-9]*
	-test -z "$(DISTCLEANFILES)" || rm -f $(DISTCLEANFILES)

maintainer-clean-generic:
mostlyclean-am:  mostlyclean-hdr mostlyclean-binPROGRAMS \
		mostlyclean-compile mostlyclean-tags \
		mostlyclean-generic

mostlyclean: mostlyclean-am

clean-am:  clean-hdr clean-binPROGRAMS clean-compile clean-tags \
		clean-generic mostlyclean-am

clean: clean-am

distclean-am:  distclean-hdr distclean-binPROGRAMS distclean-compile \
		distclean-tags distclean-generic clean-am

distclean: distclean-am

maintainer-clean-am:  maintainer-clean-hdr maintainer-clean-binPROGRAMS \
		maintainer-clean-compile maintainer-clean-tags \
		maintainer-clean-generic distclean-am
	@echo "This command is intended for maintainers to use;"
	@echo "it deletes files that may require special tools to rebuild."

maintainer-clean: maintainer-clean-am

.PHONY: mostlyclean-hdr distclean-hdr clean-hdr maintainer-clean-hdr \
mostlyclean-binPROGRAMS distclean-binPROGRAMS clean-binPROGRAMS \
maintainer-clean-binPROGRAMS uninstall-binPROGRAMS install-binPROGRAMS \
mostlyclean-compile distclean-compile clean-compile \
maintainer-clean-compile tags mostlyclean-tags distclean-tags \
clean-tags maintainer-clean-tags distdir info-am info dvi-am dvi check \
check-am installcheck-am installcheck all-recursive-am install-exec-am \
install-exec install-data-am install-data install-am install \
uninstall-am uninstall all-redirect all-am all installdirs \
mostlyclean-generic distclean-generic clean-generic \
maintainer-clean-generic clean mostlyclean distclean maintainer-clean


# Tell versions [3.59,3.63) of GNU make to not export all variables.
# Otherwise a system limit (for SysV at least) may be exceeded.
.NOEXPORT:
