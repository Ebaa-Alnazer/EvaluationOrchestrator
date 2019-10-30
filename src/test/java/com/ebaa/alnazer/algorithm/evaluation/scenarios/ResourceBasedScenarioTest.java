package com.ebaa.alnazer.algorithm.evaluation.scenarios;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.ebaa.alnazer.algorithm.algorithms.RiskAttitude;
import com.ebaa.alnazer.problem.movecontainer.model.Position;
import javafx.geometry.Pos;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResourceBasedScenarioTest {

    @Test
    public void testResourceBasedScenario() throws IOException {
        final Path EXECUTION_PATH = Files.createTempDirectory("resource-evaluation-scenario-");
        final int SIZE = 20;
        final double RES_STEP = 2.0;
        final int RES_STEPS = 50;
        final Position CENTER = Position.builder().x((SIZE - 1) / 2).y((SIZE - 1) / 2).build();
        final Position CORNER = Position.builder().x(SIZE - 1).y(SIZE - 1).build();
        final Position TRUCK = Position.builder().x(0).y(0).build();
        final Position CONTAINER = Position.builder().x(0).y(0).build();
        final Position TARGET = CENTER;

        final RiskAttitude ATTITUDE = RiskAttitude.RISK_SEEKING;

        ResourceBasedScenario scenario = ResourceBasedScenario
                .builder()
                .containerPosition(CONTAINER)
                .targetPosition(TARGET)
                .truckPosition(TRUCK)
                .size(SIZE)
                .steps(RES_STEPS)
                .resourceStep(RES_STEP)
                .riskAttitude(ATTITUDE)
                .outputDirectory(EXECUTION_PATH.toFile())
                .build();
        scenario.run();
    }

}