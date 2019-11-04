package com.ebaa.alnazer.algorithm.evaluation.scenarios;

import com.ebaa.alnazer.algorithm.algorithms.PlanningAlgorithm;
import com.ebaa.alnazer.algorithm.algorithms.RiskAttitude;
import com.ebaa.alnazer.algorithm.execution.AlgorithmExecutionManager;
import com.ebaa.alnazer.problem.ProblemFileManager;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import lombok.Builder;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Data
public class SmallResourceScenario extends BasicProblemEvaluationScenario {

    @Builder
    public SmallResourceScenario(int startSize, int endSize, int step, boolean hasShortcuts, PlanningAlgorithm algorithm, File outputDirectory) {
        super(startSize, endSize, step, hasShortcuts, algorithm, outputDirectory);
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

                    ProblemFileManager.generateSmallResourceMoveContainerProblemFile(currentSize, hasShortcuts, problemFilePath);
                    executor.setProblemDescriptionFile(problemFilePath);
                    executor.executeAlgorithm();
                }
                enhanceOutput(originalOutputFile, finalOutputFile);
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
}
