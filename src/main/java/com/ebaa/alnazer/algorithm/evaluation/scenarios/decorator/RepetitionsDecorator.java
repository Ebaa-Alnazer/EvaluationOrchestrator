package com.ebaa.alnazer.algorithm.evaluation.scenarios.decorator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.ebaa.alnazer.algorithm.evaluation.scenarios.EvaluationScenario;
import lombok.Data;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Data
public class RepetitionsDecorator extends ScenarioDecorator {
    private static final Logger log = LoggerFactory.getLogger(RepetitionsDecorator.class);
    private int repetitions;
    private int durationColIndex;

    public RepetitionsDecorator(EvaluationScenario scenario, int repetitions) {
        super(scenario);
        this.repetitions = repetitions;
        this.durationColIndex = scenario.getIndexOfDurationColumnInOutput();
    }

    @Override
    public void verifyInputs() throws IllegalArgumentException {
        this.decoratedScenario.verifyInputs();

        if (repetitions < 1) {
            throw new IllegalArgumentException("repetitions MUST be >= 1!");
        }
    }

    @Override
    public void run() {
        File output = retrieveResults();
        File[] outputFiles = new File[repetitions];
        try {
            for (int i = 0; i < repetitions; i++) {
                this.decoratedScenario.run();
                File tempCopy = new File(output.getParent(), String.format("%s-%s.csv",
                        output.getName().split("\\.")[0], i));
                FileUtils.copyFile(output, tempCopy);
                outputFiles[i] = tempCopy;
            }
            double[] averages = calculateAverageDurations(outputFiles);
            injectAverages(averages, output);
            Arrays.stream(outputFiles).forEach(File::delete);
        } catch (IOException e) {
            log.error("An error occurred while calculating average execution durations.", e);
            throw new RuntimeException(e);
        }
    }

    private double[] calculateAverageDurations(File[] outputFiles) {
        double[] result = null;

        for (File outputFile : outputFiles) {
            try (BufferedReader br = new BufferedReader(new FileReader(outputFile))) {
                List<String> allLines = br.lines().collect(Collectors.toList());
                // when we first learn how many records per output file there is, we create the result array!
                if (result == null) {
                    // the first line is headers
                    result = new double[allLines.size() - 2];
                }

                for (int j = 2; j < allLines.size(); j++) {
                    result[j - 2] += Double.parseDouble(
                            allLines.get(j).split(",")[durationColIndex]);
                }
            } catch (IOException e) {
                log.error("An error occurred while handling output files.", e);
                throw new RuntimeException(e);
            }
        }

        assert result != null;
        for (int j = 0; j < result.length; j++) {
            result[j] /= outputFiles.length;
        }

        return result;
    }

    private void injectAverages(double[] averages, File outputFile) throws IOException {
        StringBuilder newOutputSB = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(outputFile))) {
            List<String> allLines = br.lines().collect(Collectors.toList());
            newOutputSB.append(allLines.get(0));
            newOutputSB.append("\n");

            for (int i = 2; i < allLines.size(); i++) {
                String beforeAverage = Arrays.stream(allLines.get(i).split(",")).limit(durationColIndex).collect(Collectors.joining(","));
                String afterAverage = Arrays.stream(allLines.get(i).split(",")).skip(durationColIndex + 1).collect(Collectors.joining(","));
                newOutputSB.append(String.format("%s,%s,%s\n", beforeAverage, averages[i - 2], afterAverage));
            }
        }

        FileUtils.write(outputFile, newOutputSB.toString(), "UTF-8", false);
    }

    @Override
    public String toString() {
        return String.format("%sreps%s", decoratedScenario.toString(), repetitions);
    }
}
