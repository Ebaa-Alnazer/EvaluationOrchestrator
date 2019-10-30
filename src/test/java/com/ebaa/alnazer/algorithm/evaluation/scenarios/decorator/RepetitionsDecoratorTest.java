package com.ebaa.alnazer.algorithm.evaluation.scenarios.decorator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.ebaa.alnazer.algorithm.algorithms.PlanningAlgorithm;
import com.ebaa.alnazer.algorithm.algorithms.RiskAttitude;
import com.ebaa.alnazer.algorithm.algorithms.UAShop;
import com.ebaa.alnazer.algorithm.evaluation.scenarios.DiagonalProblemEvaluationScenario;
import org.junit.jupiter.api.Test;

class RepetitionsDecoratorTest {
    @Test
    public void testRepetition() throws IOException {
        final int START_SIZE = 5;
        final int END_SIZE = 20;
        final int STEP = 5;
        final int REPS = 3;
        final Path EXECUTION_PATH = Files.createTempDirectory("diagonal-evaluation-scenario-rep-");
        PlanningAlgorithm algorithm = UAShop.builder().resource(450).riskAttitude(RiskAttitude.RISK_SEEKING).build();
        DiagonalProblemEvaluationScenario scenario = DiagonalProblemEvaluationScenario
                .builder()
                .algorithm(algorithm)
                .startSize(START_SIZE)
                .endSize(END_SIZE)
                .step(STEP)
                .outputDirectory(new File(EXECUTION_PATH.toString()))
                .build();
        RepetitionsDecorator decorator = new RepetitionsDecorator(scenario, REPS);
        decorator.run();
    }
}