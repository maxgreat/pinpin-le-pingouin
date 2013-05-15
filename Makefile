CC=javac
MAKE=make
JFLAGS=-classpath . -implicit:none
EXEC=Gaufre
SUBDIRS=Interface Joueurs Arbitre
CLEANDIRS = $(SUBDIRS:%=clean-%)

all: $(EXEC)


%.class: %.java
	$(CC) $(JFLAGS) $<


Gaufre: $(SUBDIRS) Gaufre.class Gaufre.java


.PHONY: subdirs $(SUBDIRS) clean
.PHONY: subdirs $(CLEANDIRS)


subdirs: $(SUBDIRS)

$(SUBDIRS):
	$(MAKE) -C $@

clean: $(CLEANDIRS)
	rm -rf *.class *~

$(CLEANDIRS):
	$(MAKE) -C $(@:clean-%=%) clean

