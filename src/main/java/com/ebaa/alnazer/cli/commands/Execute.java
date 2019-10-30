package com.ebaa.alnazer.cli.commands;

import java.io.File;

import com.ebaa.alnazer.algorithm.execution.AlgorithmExecutionManager;
import com.ebaa.alnazer.algorithm.algorithms.JShop2;
import com.ebaa.alnazer.algorithm.algorithms.PlanningAlgorithm;
import com.ebaa.alnazer.algorithm.algorithms.RiskAttitude;
import com.ebaa.alnazer.algorithm.algorithms.UAShop;
import picocli.CommandLine;

@CommandLine.Command(name = "execute", description = "executes the planning algorithm")
public class Execute implements Runnable {
    @CommandLine.Spec
    CommandLine.Model.CommandSpec spec;

    @CommandLine.Option(names = {"-d", "--domain"}, paramLabel = "DOMAIN-FILE", description = "the file that describes the domain")
    File domainInputFile;

    @CommandLine.Option(names = {"-p", "--problem"}, paramLabel = "PROBLEM-FILE", description = "the file that describes the problem")
    File problemInputFile;

    @CommandLine.Option(names = {"-dir", "--directory"}, paramLabel = "EXECUTION-DIRECTORY", description = "the directory that will be used to generate intermediary files and store the results")
    File executionDirectory;

    @CommandLine.Option(names = {"-c", "--clean"}, paramLabel = "CLEANUP", description = "indicates whether intermediary files should be removed")
    boolean cleanDirectory = false;

    @CommandLine.Option(names = {"-u", "--utility"}, paramLabel = "UTILITY-AWARE", description = "indicates whether the utility aware version of the algorithm should be used")
    boolean useUAShop;

    @CommandLine.Option(names = {"-r", "--resource"}, paramLabel = "RESOURCE", description = "the amount that represents the resource")
    double resource = 0.0;

    @CommandLine.Option(names = {"-ra", "--risk-attitude"}, paramLabel = "RISK-ATTITUDE", description = "the type of the risk attitude (0-3)")
    int riskAttitude = 0;

    @Override
    public void run() {
        PlanningAlgorithm algo = null;

        if (useUAShop) {
            algo = UAShop
                    .builder()
                    .resource(resource)
                    .riskAttitude(RiskAttitude.getRiskAttitudeByValue(riskAttitude))
                    .build();
        } else {
            algo = new JShop2();
        }

        AlgorithmExecutionManager executor = AlgorithmExecutionManager.builder()
                .domainDescriptionFile(domainInputFile)
                .problemDescriptionFile(problemInputFile)
                .executionDirectory(executionDirectory)
                .algorithm(algo)
                .removeIntermediaryFiles(cleanDirectory)
                .build();
        try {
            executor.executeAlgorithm();
        } catch (Exception e) {
            throw new CommandLine.ParameterException(spec.commandLine(),
                    "An exception occurred when trying to run the planning algorithm. Reason: " + e.getMessage(), e);
        }
    }
}
