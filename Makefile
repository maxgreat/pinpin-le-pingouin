CC=javac
MAKE=make
JFLAGS=-classpath . -implicit:none
EXEC=Pingouin Serveur Client
SUBDIRS=Interface Joueurs Arbitre Utilitaires Network
CLEANDIRS = $(SUBDIRS:%=clean-%)

all: $(EXEC)


%.class: %.java
	$(CC) $(JFLAGS) $<

Pingouin: $(SUBDIRS) Pingouin.class Pingouin.java
Serveur: $(SUBDIRS) Serveur.class Serveur.java
Client: $(SUBDIRS) Client.class Client.java

.PHONY: subdirs $(SUBDIRS) clean
.PHONY: subdirs $(CLEANDIRS)


subdirs: $(SUBDIRS)

$(SUBDIRS):
	$(MAKE) -C $@

clean: $(CLEANDIRS)
	rm -rf *.class *~

$(CLEANDIRS):
	$(MAKE) -C $(@:clean-%=%) clean

