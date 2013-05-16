CC=javac
JAVAOBJ=$(wildcard *.java)
OBJ=$(patsubst %.java, %.class, $(JAVAOBJ))
JFLAGS=-classpath ../../

all: $(OBJ) $(JAVAOBJ)

%.class: %.java
	$(CC) $(JFLAGS) $<


.PHONY: clean

clean:
	rm -rf *.class *~
