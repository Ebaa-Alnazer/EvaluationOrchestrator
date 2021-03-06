package com.ebaa.alnazer.algorithm.evaluation.scenarios;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

import com.ebaa.alnazer.algorithm.algorithms.PlanningAlgorithm;
import com.ebaa.alnazer.algorithm.algorithms.UAShop;
import com.ebaa.alnazer.algorithm.execution.AlgorithmExecutionManager;
import com.ebaa.alnazer.problem.ProblemFileManager;
import freemarker.template.TemplateException;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Data
public class DiagonalProblemEvaluationScenario extends EvaluationScenario {
    private static final Logger log = LoggerFactory.getLogger(DiagonalProblemEvaluationScenario.class);
    private int startSize;
    private int endSize;
    private int step;
    private boolean hasShortcuts;
    private PlanningAlgorithm algorithm;

    @Builder
    public DiagonalProblemEvaluationScenario(int startSize, int endSize, int step, boolean hasShortcuts, PlanningAlgorithm algorithm, File outputDirectory) {
        super(outputDirectory);
        this.startSize = startSize;
        this.endSize = endSize;
        this.step = step;
        this.hasShortcuts = hasShortcuts;
        this.algorithm = algorithm;
    }

    @Override
    public void run() {
        try {
            File domainInput = getMoveContainerDomainFile();
            AlgorithmExecutionManager executor = AlgorithmExecutionManager
                    .builder()
                    .algorithm(algorithm)
                    .domainDescriptionFile(domainInput)
                    .executionDirectory(outputDirectory)
                    .removeIntermediaryFiles(true)
                    .isCSV(true)
                    .build();

            Files.createDirectories(outputDirectory.toPath());
            File problemFilePath = new File(outputDirectory, "/problem");
            File originalOutputFile = new File(outputDirectory, String.format("results-%s.csv", algorithm.getName()));
            File finalOutputFile = retrieveResults();
            try {
                for (int currentSize = startSize; currentSize <= endSize; currentSize += step) {
                    log.info(String.format("\n\nSize: %s\n*********\n", currentSize));

                    ProblemFileManager.generateDiagonalMoveContainerProblemFile(currentSize, hasShortcuts, problemFilePath);
                    executor.setProblemDescriptionFile(problemFilePath);
                    executor.executeAlgorithm();
                }
                this.enhanceOutput(originalOutputFile, finalOutputFile);
            } catch (IOException | TemplateException e) {
                log.error("Failed to generate diagonal problem input file!", e);
                throw new RuntimeException(e);
            } catch (Exception e) {
                log.error("Failed to execute the algorithm!", e);
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            log.error("Failed to create the output directory!", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void verifyInputs() throws IllegalArgumentException {
        super.verifyInputs();

        if (algorithm == null) {
            throw new IllegalArgumentException("Planning algorithm is not set!");
        }

        if (startSize < 4) {
            throw new IllegalArgumentException("startSize MUST be > 4!");
        }
        if (endSize < startSize || step <= 0 || step > endSize - startSize) {
            throw new IllegalArgumentException("Evaluation scenario arguments are invalid!");
        }
    }

    @Override
    public File retrieveResults() {
        return new File(outputDirectory, String.format("d-%s-%s-%s-%s.csv", algorithm.getName(), startSize, endSize, step));
    }

    @Override
    public int getIndexOfDurationColumnInOutput() {
        if (algorithm instanceof UAShop)
            return 2;
        return 1;
    }

    private void enhanceOutput(File original, File target) throws IOException {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(target, false))) {
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(original))) {
                bufferedWriter.write("sep=,\n");
                // insert header
                if (algorithm instanceof UAShop) {
                    bufferedWriter.write("grid size,phase1 duration, phase 2 duration, cost,plan length\n");
                } else {
                    bufferedWriter.write("grid size,duration, cost,plan length\n");
                }
                String current = bufferedReader.readLine();
                int currentSize = this.startSize;

                while (current != null) {
                    bufferedWriter.write(String.format("%s,%s\n", currentSize, current));
                    current = bufferedReader.readLine();
                    currentSize += step;
                }
            }
        } catch (IOException e) {
            log.error("An error occurred while injecting problem sizes in the output file..", e);
            throw new RuntimeException(e);
        }
        FileUtils.forceDelete(original);
    }

    @Override
    public String toString() {
        return String.format("diagonal-start%s-end%s-step%s-shortcut%s-algo_%s_", startSize, endSize, step,hasShortcuts, algorithm.toString());
    }
}
