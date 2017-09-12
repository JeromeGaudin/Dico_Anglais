but:compil

JAVAFILES = DicoEnglish.class \
	References.class \
	FichierLire.class \
	FichierModifier.class \
	Option.class \
	Console.class \
	CommandeConsole.class \


FLAGS =

Xlint:
	ifeq (FLAGS, null)
		FLAGS = -Xlint
	endif
	compil:

ifndef FLAGS
	FLAGS =
endif

References.class: References.java
	javac $(FLAGS) References.java

FichierLire.class: FichierLire.java References.class
	javac $(FLAGS) FichierLire.java

FichierModifier.class: FichierModifier.java References.class
	javac $(FLAGS) FichierModifier.java

Option.class: Option.java
	javac $(FLAGS) Option.java

Console.class: Console.java Option.class FichierLire.class FichierModifier.class
	javac $(FLAGS) Console.java

CommandeConsole.class: CommandeConsole.java Console.class References.class
	javac $(FLAGS) CommandeConsole.java

DicoEnglish.class: DicoEnglish.java Console.class
	javac $(FLAGS) DicoEnglish.java

compil: $(JAVAFILES)

clean:
	rm -f $(JAVAFILES)