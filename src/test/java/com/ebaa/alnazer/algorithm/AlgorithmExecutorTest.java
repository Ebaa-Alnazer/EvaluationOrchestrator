package com.ebaa.alnazer.algorithm;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import com.ebaa.alnazer.algorithm.algorithms.JShop2;
import com.ebaa.alnazer.algorithm.algorithms.RiskAttitude;
import com.ebaa.alnazer.algorithm.algorithms.UAShop;
import com.ebaa.alnazer.algorithm.execution.AlgorithmExecutionManager;
import org.junit.jupiter.api.Test;

class AlgorithmExecutorTest {
    @Test
    public void testExecuteAlgorithm() throws Exception {
        File domainInput = new File(
                Objects.requireNonNull(getClass().getClassLoader().getResource("domains/movecontainer")).getFile()
        );
        File problemInput = new File(
                Objects.requireNonNull(getClass().getClassLoader().getResource("problem")).getFile()
        );
        Path executionDirectory = Files.createTempDirectory("jshop2-execution");

        AlgorithmExecutionManager executor = AlgorithmExecutionManager.builder()
                .algorithm(new JShop2())
                .domainDescriptionFile(domainInput)
                .problemDescriptionFile(problemInput)
                .executionDirectory(executionDirectory.toFile())
                .removeIntermediaryFiles(true)
                .isCSV(true)
                .build();
        executor.executeAlgorithm();
        UAShop algo2 = UAShop.builder().resource(350).riskAttitude(RiskAttitude.RISK_SEEKING).build();
        executor.setAlgorithm(algo2);
        executor.executeAlgorithm();
    }
}