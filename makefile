goal:compil

compil:
	javac *.java
	javac commandLine/*.java

clear:
	rm *.class
	rm commandLine/*.class