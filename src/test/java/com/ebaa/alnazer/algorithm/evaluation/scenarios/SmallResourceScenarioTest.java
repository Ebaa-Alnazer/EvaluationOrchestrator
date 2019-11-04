package com.ebaa.alnazer.algorithm.evaluation.scenarios;

import com.ebaa.alnazer.algorithm.algorithms.JShop2;
import com.ebaa.alnazer.algorithm.algorithms.PlanningAlgorithm;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class SmallResourceScenarioTest {
    @Test
    public void testSmallResourceScenario() throws IOException {
        final int START_SIZE = 4;
        final int END_SIZE = 15;
        final int STEP = 1;
        final Path EXECUTION_PATH = Files.createTempDirectory("smallResource-evaluation-scenario-");
        //PlanningAlgorithm algorithm = UAShop.builder().resource(450).riskAttitude(RiskAttitude.RISK_SEEKING).build();
        PlanningAlgorithm algorithm= new JShop2();
        // PlanningAlgorithm algorithm = UAShop.builder().resource(28).riskAttitude(RiskAttitude.RISK_SEEKING).build();
        SmallResourceScenario scenario = SmallResourceScenario
                .builder()
                .algorithm(algorithm)
                .startSize(START_SIZE)
                .endSize(END_SIZE)
                .step(STEP)
                .outputDirectory(new File(EXECUTION_PATH.toString()))
                .build();
        scenario.run();
    }
}
