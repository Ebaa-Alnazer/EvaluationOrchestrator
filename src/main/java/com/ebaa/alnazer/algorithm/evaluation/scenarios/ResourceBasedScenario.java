package com.ebaa.alnazer.algorithm.evaluation.scenarios;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.ebaa.alnazer.algorithm.algorithms.JShop2;
import com.ebaa.alnazer.algorithm.algorithms.RiskAttitude;
import com.ebaa.alnazer.algorithm.algorithms.UAShop;
import com.ebaa.alnazer.algorithm.execution.AlgorithmExecutionManager;
import com.ebaa.alnazer.problem.ProblemFileManager;
import com.ebaa.alnazer.problem.movecontainer.model.MoveContainerProblem;
import com.ebaa.alnazer.problem.movecontainer.model.Position;
import com.ebaa.alnazer.problem.movecontainer.model.Shortcut;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Data
public class ResourceBasedScenario extends EvaluationScenario {
    private static final Logger log = LoggerFactory.getLogger(ResourceBasedScenario.class);
    private int size;
    private Position truckPosition;
    private Position containerPosition;
    private Position targetPosition;
    private Shortcut shortcut;
    private double resourceStep;
    private int steps;
    private RiskAttitude riskAttitude;

    @Builder
    public ResourceBasedScenario(File outputDirectory, int size, Position truckPosition, Position containerPosition,
                                 Position targetPosition, Shortcut shortcut, double resourceStep, int steps, RiskAttitude riskAttitude) {
        super(outputDirectory);
        this.size = size;
        this.truckPosition = truckPosition;
        this.containerPosition = containerPosition;
        this.targetPosition = targetPosition;
        this.shortcut = shortcut;
        this.resourceStep = resourceStep;
        this.steps = steps;
        this.riskAttitude = riskAttitude;
    }

    @Override
    public void verifyInputs() throws IllegalArgumentException {
        super.verifyInputs();

        if (steps <= 0 || resourceStep <= 0) {
            throw new IllegalArgumentException("resourceStep and # of steps MUST be > 1");
        }

        if (size <= 0) {
            throw new IllegalArgumentException("Problem size MUST be > 0");
        }

        if (truckPosition == null || containerPosition == null || targetPosition == null) {
            throw new IllegalArgumentException("Some required positions are not set!");
        }
    }

    @Override
    public File retrieveResults() {
        return new File(outputDirectory, String.format("r-%s-%s-%s-%s.csv", UAShop.NAME, size, steps, resourceStep));
    }

    @Override
    public int getIndexOfDurationColumnInOutput() {
        return 2;
    }

    @Override
    public void run() {
        try {
            File domainInput = getMoveContainerDomainFile();
            Files.createDirectories(outputDirectory.toPath());
            File problemFilePath = new File(outputDirectory, "/problem");
            List<Position> containers = Collections.singletonList(containerPosition);
            List<Shortcut> shortcuts = new ArrayList<>();

            if (shortcut != null) {
                shortcuts.add(shortcut);
            }

            MoveContainerProblem problem = MoveContainerProblem
                    .builder()
                    .size(size)
                    .truck(truckPosition)
                    .target(targetPosition)
                    .containers(containers)
                    .shortcuts(shortcuts)
                    .build();
            ProblemFileManager.generateMoveContainerProblemFile(problem, problemFilePath);
            AlgorithmExecutionManager executor = AlgorithmExecutionManager
                    .builder()
                    .algorithm(new JShop2())
                    .problemDescriptionFile(problemFilePath)
                    .domainDescriptionFile(domainInput)
                    .executionDirectory(outputDirectory)
                    .removeIntermediaryFiles(true)
                    .isCSV(true)
                    .build();
            executor.executeAlgorithm();
            // we get the starting value of the resource from the naive amount we get from JSHOP2 execution.
            double resourceStart = this.getNaiveResourceAmount();
            String msg = "\nNaive Cost: " + resourceStart
                    + "\n******************";
            log.info(msg);
            double currentResource = resourceStart;
            UAShop algorithm = UAShop.builder().riskAttitude(riskAttitude).build();

            for (int i = 0; i < steps; i++) {
                msg = "\nResource: " + currentResource +
                        "\n****************";
                log.info(msg);
                algorithm.setResource(currentResource);
                executor.setAlgorithm(algorithm);
                executor.executeAlgorithm();
                currentResource -= resourceStep;
            }

            File originalOutputFile = new File(outputDirectory, String.format("results-%s.csv", UAShop.NAME));
            File finalOutputFile = retrieveResults();
            enhanceOutput(originalOutputFile, finalOutputFile);
        } catch (Exception e) {
            log.error("An error occurred when trying to run the scenario!", e);
            throw new RuntimeException(e);
        }
    }

    private double getNaiveResourceAmount() throws IOException {
        File originalOutputFile = new File(outputDirectory, String.format("results-%s.csv", JShop2.NAME));
        List<String> content = Files.readAllLines(originalOutputFile.toPath());
        // clear the file so the results are not mixed up with the useful ones!
        if (!originalOutputFile.delete()) {
            throw new RuntimeException("Unable to delete the naive output file after reading it!");
        }

        if (content.size() != 1) {
            throw new RuntimeException("Wrong number of lines in csv file!");
        }

        String[] dataItems = content.get(0).split(",");

        if (dataItems.length != 3) {
            throw new RuntimeException("Wrong number of data elements in csv file!");
        }

        // return the plan cost
        return Double.parseDouble(dataItems[1]);
    }

    @Override
    public String toString() {
        return String.format("resourceBased-size%s-resSize%s-resStep%s-riskAttitude%s-truckPos%s-containerPos%s-targetPos%s", size, steps, resourceStep, riskAttitude, truckPosition,containerPosition,targetPosition);
    }

    private void enhanceOutput(File original, File target) throws IOException {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(target, false))) {
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(original))) {
                bufferedWriter.write("sep=,\n");
                // insert header
                bufferedWriter.write("grid size,phase1 duration,phase 2 duration,cost,plan length\n");
                String current = bufferedReader.readLine();

                while (current != null) {
                    bufferedWriter.write(String.format("%s,%s\n", size, current));
                    current = bufferedReader.readLine();
                }
            }
        } catch (IOException e) {
            log.error("An error occurred while injecting problem sizes in the output file..", e);
            throw new RuntimeException(e);
        }
        FileUtils.forceDelete(original);
    }
}
