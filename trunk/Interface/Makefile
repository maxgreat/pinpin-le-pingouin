CC=javac
MAKE=make
JFLAGS=-classpath ../:. -implicit:none
OBJ=Interface.class InterfaceFabrique.class

# Décommentez l'interface que vous voulez utiliser
SUBDIRS=Console
# SUBDIRS=Graphique 

CLEANDIRS = $(SUBDIRS:%=clean-%)

all: $(OBJ) $(SUBDIRS)


%.class: %.java
	$(CC) $(JFLAGS) $<


.PHONY: subdirs $(SUBDIRS) clean
.PHONY: subdirs $(CLEANDIRS)


subdirs: $(SUBDIRS)

$(SUBDIRS):
	$(MAKE) -C $@

clean: $(CLEANDIRS)
	rm -rf *.class *~

$(CLEANDIRS):
	$(MAKE) -C $(@:clean-%=%) clean

