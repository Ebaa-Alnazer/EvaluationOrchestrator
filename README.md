# Evaluation Orchestrator

This project uses Maven. One of the dependencies is a JAR file located in the folder called `libs`.
Before trying to build this project, you should add this JAR file to the local Maven repository by executing the following command while being at the root directory of the project:
```
mvn install:install-file -Dfile=.\libs\Utilities-JSHOP2-1.0-SNAPSHOT-jar-with-dependencies.jar -DgroupId=utilities.jshop2 -DartifactId=jshop2 -Dversion=1.0 -Dpackaging=jar -DgeneratePom=true
```
