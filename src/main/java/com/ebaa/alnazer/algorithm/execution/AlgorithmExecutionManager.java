package com.ebaa.alnazer.algorithm.execution;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import com.ebaa.alnazer.algorithm.algorithms.PlanningAlgorithm;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utilities.jshop2.InternalDomain;

@Builder
@Data
public class AlgorithmExecutionManager {
    private static final Logger log = LoggerFactory.getLogger(AlgorithmExecutionManager.class);
    private File domainDescriptionFile;
    private File problemDescriptionFile;
    private File executionDirectory;
    private boolean removeIntermediaryFiles;
    private boolean isCSV;
    private PlanningAlgorithm algorithm;

    private void ensureDirectoryExists() throws IOException {
        if (executionDirectory.isFile()) {
            throw new IllegalArgumentException("The specified execution directory refers to a file!");
        }

        if (!executionDirectory.exists()) {
            Files.createDirectory(executionDirectory.toPath());
            log.debug("A new execution directory is created at: " + executionDirectory.toPath());
        }
    }

    private void copyInputFilesToExecutionDirectory() throws IOException {
        if (!domainDescriptionFile.exists() || !problemDescriptionFile.exists()) {
            throw new FileNotFoundException("One of the input files is not found!");
        }

        if (!domainDescriptionFile.isFile() || !problemDescriptionFile.exists()) {
            throw new IllegalArgumentException("One of the specified input file is a directory!");
        }

        // if the file is already at the target directory, do nothing
        if (!domainDescriptionFile.getParentFile().equals(executionDirectory)) {
            File newFile = new File(executionDirectory, domainDescriptionFile.getName());
            FileUtils.copyFile(domainDescriptionFile, newFile);
            this.domainDescriptionFile = newFile;
            log.debug("The domain description file was copied to the execution directory: " + newFile.toPath());
        }

        // if the file is already at the target directory, do nothing
        if (!problemDescriptionFile.getParentFile().equals(executionDirectory)) {
            File newFile = new File(executionDirectory, problemDescriptionFile.getName());
            FileUtils.copyFile(problemDescriptionFile, newFile);
            this.problemDescriptionFile = newFile;
            log.debug("The problem description file was copied to the execution directory: " + newFile.toPath());
        }
    }

    private void createSources() throws Exception {
        InternalDomain.main(new String[] {domainDescriptionFile.getPath()});
        ArrayList<String> argsList = new ArrayList<>();
        argsList.add("-r");
        argsList.add(problemDescriptionFile.getPath());
        argsList.addAll(Arrays.asList(this.algorithm.getRequiredInputs()));
        argsList.add(isCSV ? "csv" : "txt");
        InternalDomain.main(argsList.toArray(new String[0]));
        log.debug("Sources were successfully created!");
    }

    /**
     * From https://stackoverflow.com/questions/21544446/how-do-you-dynamically-compile-and-load-external-java-classes
     */
    private void compileSources() throws IOException {
        /** Compilation Requirements *********************************************************************************************/
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

        if (compiler == null) {
            log.debug("Java Home: " + System.getProperty("java.home") + " should refer to a JDK installation!!");
            throw new RuntimeException("Could not compile sources. Reason: could not find a java compiler!");
        }

        StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null);

        // This sets up the class path that the compiler will use.
        // I've added the .jar file that contains the DoStuff interface within in it...
        List<String> optionList = new ArrayList<String>();
        // optionList.add("-classpath");
        // optionList.add(System.getProperty("java.class.path") + ";Libs/Utilities-JSHOP2-1.0-SNAPSHOT-jar-with-dependencies.jar");
        List<File> files = Arrays.asList(new File(executionDirectory, domainDescriptionFile.getName() + ".java"),
                new File(executionDirectory, problemDescriptionFile.getName() + ".java")
        );
        Iterable<? extends JavaFileObject> compilationUnit
                = fileManager.getJavaFileObjectsFromFiles(files);
        JavaCompiler.CompilationTask task = compiler.getTask(
                null,
                fileManager,
                diagnostics,
                optionList,
                null,
                compilationUnit);
        /********************************************************************************************* Compilation Requirements **/
        if (!task.call()) {
            log.debug("Compilation has failed!");
            for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()) {
                log.error(String.format("Error on line %d in %s%n",
                        diagnostic.getLineNumber(),
                        diagnostic.getSource().toUri()));
            }
            throw new RuntimeException("Could not compile the specified classes!");
        } else {
            log.debug("Compilation is successful!");
        }
        fileManager.close();
    }

    private Class<?> loadProblemClass() throws ClassNotFoundException, MalformedURLException {
        URL executionDir = executionDirectory.toURI().toURL();
        URLClassLoader classLoader = new URLClassLoader(new URL[] {executionDir});
        // Load the class from the classloader by name....
        return classLoader.loadClass("problem");
    }

    private void invokeAlgorithm(Class<?> problem) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        log.debug("Loading is successful! Running the algorithm...");
        Method main = problem.getMethod("main", String[].class);
        main.invoke(null, (Object) new String[0]);
        log.debug("Algorithm ran to completion!");
    }

    private void cleanup() {
        if (removeIntermediaryFiles) {
            log.debug("Cleaning up intermediary files");
            File[] allFiles = executionDirectory.listFiles();
            assert allFiles != null;
            List<File> toRemove = Arrays.stream(allFiles)
                    .filter(file -> file.getName().endsWith(".java")
                            || file.getName().endsWith(".class")
                            || file.getName().equals(domainDescriptionFile.getName() + ".txt"))
                    .collect(Collectors.toList());
            toRemove.forEach(File::delete);
        }
    }

    public void executeAlgorithm() throws Exception {
        ensureDirectoryExists();
        copyInputFilesToExecutionDirectory();
        createSources();
        compileSources();
        Class<?> problem = loadProblemClass();
        invokeAlgorithm(problem);
        cleanup();
    }
}
