CC=javac
JAVAOBJ=$(wildcard *.java)
OBJ=$(patsubst %.java, %.class, $(JAVAOBJ))
SUBDIRS=Automates
JFLAGS=-classpath ../
CLEANDIRS = $(SUBDIRS:%=clean-%)

all: $(OBJ) $(JAVAOBJ)

%.class: %.java
	$(CC) $(JFLAGS) $<

subdirs: $(SUBDIRS)

$(SUBDIRS):
	$(MAKE) -C $@


.PHONY: clean
.PHONY: subdirs $(SUBDIRS) clean
.PHONY: subdirs $(CLEANDIRS)

clean: $(CLEANDIRS)
	rm -rf *.class *~

$(CLEANDIRS):
	$(MAKE) -C $(@:clean-%=%) clean
