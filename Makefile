CC=javac
MAKE=make
JFLAGS=-classpath . -implicit:none
EXEC=Pingouin
SUBDIRS=Interface Joueurs Arbitre Utilitaires
CLEANDIRS = $(SUBDIRS:%=clean-%)

all: $(EXEC)


%.class: %.java
	$(CC) $(JFLAGS) $<


$(EXEC): $(SUBDIRS) $(EXEC).class $(EXEC).java


.PHONY: subdirs $(SUBDIRS) clean
.PHONY: subdirs $(CLEANDIRS)


subdirs: $(SUBDIRS)

$(SUBDIRS):
	$(MAKE) -C $@

clean: $(CLEANDIRS)
	rm -rf *.class *~

$(CLEANDIRS):
	$(MAKE) -C $(@:clean-%=%) clean

