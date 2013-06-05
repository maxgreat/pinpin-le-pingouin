CC=javac
MAKE=make
JFLAGS=-classpath .:Sound/jl.jar -implicit:none
EXEC=Pingouin
SUBDIRS=Interface Joueurs Arbitre Utilitaires Network Sound
CLEANDIRS = $(SUBDIRS:%=clean-%)

all: $(EXEC)

jar: $(EXEC)
	jar cvmf MANIFEST.MF Pingouin.jar $(SUBDIRS) *.class *.java

%.class: %.java
	$(CC) $(JFLAGS) $<


$(EXEC): $(SUBDIRS) $(EXEC).class $(EXEC).java


.PHONY: subdirs $(SUBDIRS) clean
.PHONY: subdirs $(CLEANDIRS)


subdirs: $(SUBDIRS)

$(SUBDIRS):
	$(MAKE) -C $@

clean: $(CLEANDIRS)
	rm -rf *.class *~ *.jar

$(CLEANDIRS):
	$(MAKE) -C $(@:clean-%=%) clean

