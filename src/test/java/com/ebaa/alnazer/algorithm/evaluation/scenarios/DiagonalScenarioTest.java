package com.ebaa.alnazer.algorithm.evaluation.scenarios;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.ebaa.alnazer.algorithm.algorithms.JShop2;
import com.ebaa.alnazer.algorithm.algorithms.PlanningAlgorithm;
import com.ebaa.alnazer.algorithm.algorithms.RiskAttitude;
import com.ebaa.alnazer.algorithm.algorithms.UAShop;
import org.junit.jupiter.api.Test;

public class DiagonalScenarioTest {
    @Test
    public void testDiagonalScenario() throws IOException {
        final int START_SIZE = 4;
        final int END_SIZE = 24;
        final int STEP = 4;
        final Path EXECUTION_PATH = Files.createTempDirectory("diagonal-evaluation-scenario-");
        PlanningAlgorithm algorithm = UAShop.builder().resource(350).riskAttitude(RiskAttitude.RISK_SEEKING).build();
       // PlanningAlgorithm algorithm= new JShop2();
       // PlanningAlgorithm algorithm = UAShop.builder().resource(28).riskAttitude(RiskAttitude.RISK_SEEKING).build();
        DiagonalProblemEvaluationScenario scenario = DiagonalProblemEvaluationScenario
                .builder()
                .algorithm(algorithm)
                .startSize(START_SIZE)
                .endSize(END_SIZE)
                .step(STEP)
                .outputDirectory(new File(EXECUTION_PATH.toString()))
                .build();
        scenario.run();
    }

    @Test
    public void testDiagonalScenarioWithShortcut() throws IOException {
        final int START_SIZE = 5;
        final int END_SIZE = 35;
        final int STEP = 5;
        final Path EXECUTION_PATH = Files.createTempDirectory("diagonal-evaluation-scenario-shortcut-");
        PlanningAlgorithm algorithm = UAShop.builder().resource(450).riskAttitude(RiskAttitude.RISK_SEEKING).build();
        DiagonalScenario scenario = DiagonalScenario
                .builder()
                .algorithm(algorithm)
                .startSize(START_SIZE)
                .endSize(END_SIZE)
                .step(STEP)
                .hasShortcuts(true)
                .outputDirectory(new File(EXECUTION_PATH.toString()))
                .build();
        scenario.run();
    }
}