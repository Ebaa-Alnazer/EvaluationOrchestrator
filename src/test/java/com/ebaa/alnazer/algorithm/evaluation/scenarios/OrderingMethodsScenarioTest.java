package com.ebaa.alnazer.algorithm.evaluation.scenarios;

import com.ebaa.alnazer.algorithm.algorithms.JShop2;
import com.ebaa.alnazer.algorithm.algorithms.PlanningAlgorithm;
import com.ebaa.alnazer.algorithm.algorithms.RiskAttitude;
import com.ebaa.alnazer.algorithm.algorithms.UAShop;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class OrderingMethodsScenarioTest {

    @Test
    public void testOrederingMethodsScenarioWithShortcut() throws IOException {
        final int START_SIZE = 5;
        final int END_SIZE = 35;
        final int STEP = 5;
        final Path EXECUTION_PATH = Files.createTempDirectory("orderingMethods-evaluation-scenario-shortcut-");
        PlanningAlgorithm algorithm = UAShop.builder().resource(450).riskAttitude(RiskAttitude.RISK_SEEKING).build();
        OrderingMethodsScenario scenario = OrderingMethodsScenario
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
