MAKE=make
CC=javac
JAVAOBJ=$(wildcard *.java)
OBJ=$(patsubst %.java, %.class, $(JAVAOBJ))
JFLAGS=-classpath ../:.
SUBDIRS=Regles
CLEANDIRS=$(SUBDIRS:%=clean-%)

all: $(OBJ) $(JAVAOBJ) $(SUBDIRS)

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
