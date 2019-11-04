package com.ebaa.alnazer.algorithm.evaluation;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.ebaa.alnazer.algorithm.algorithms.JShop2;
import com.ebaa.alnazer.algorithm.algorithms.PlanningAlgorithm;
import com.ebaa.alnazer.algorithm.algorithms.RiskAttitude;
import com.ebaa.alnazer.algorithm.algorithms.UAShop;
import com.ebaa.alnazer.algorithm.evaluation.scenarios.*;

import com.ebaa.alnazer.algorithm.evaluation.scenarios.decorator.RepetitionsDecorator;
import com.ebaa.alnazer.problem.movecontainer.model.Position;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;

class AlgorithmEvaluationManagerTest {
    final int START_SIZE = 4;
    final int END_SIZE = 26;
    final int STEP = 2;
    final int REPS = 10;

    @Test
    public void runEvaluation() throws IOException {
        final Path RESULT_PATH = Files.createTempDirectory("evaluation-results-");
        AlgorithmEvaluationManager manager = new AlgorithmEvaluationManager();
        manager.setFolderPath(RESULT_PATH.toFile());

        // diagonal scenarios
        //-------------------
        final double RESOURCE = 350.0;
        final double SMALLRESOURCE = 5.0;
        manager.addScenario(createUADiagonalScenario(RESOURCE, RiskAttitude.RISK_SEEKING, false));
        manager.addScenario(createUADiagonalScenario(RESOURCE, RiskAttitude.RISK_AVERSE, false));
        manager.addScenario(createUADiagonalScenario(RESOURCE, RiskAttitude.RISK_AWARE, false));
        manager.addScenario(createUADiagonalScenario(RESOURCE, RiskAttitude.RISK_NEUTRAL, false));
        manager.addScenario(createJShopDiagonalScenario(false));


        manager.addScenario(createUAOrderingMethodsScenario(RESOURCE, RiskAttitude.RISK_SEEKING, true));
        manager.addScenario(createUAOrderingMethodsScenario(RESOURCE, RiskAttitude.RISK_AVERSE, true));
        manager.addScenario(createUAOrderingMethodsScenario(RESOURCE, RiskAttitude.RISK_NEUTRAL, true));
        manager.addScenario(createUAOrderingMethodsScenario(RESOURCE, RiskAttitude.RISK_AWARE, true));
        manager.addScenario(createJSHOPOrderingMethodsScenario( true));


        manager.addScenario(createUAResourseScenario(20,2.0,50, Position.builder().x(0).y(0).build(), Position.builder().x(0).y(0).build(),Position.builder().x(9).y(9).build(), RiskAttitude.RISK_SEEKING));
        manager.addScenario(createUAResourseScenario(20,2.0,50, Position.builder().x(0).y(0).build(), Position.builder().x(0).y(0).build(),Position.builder().x(9).y(9).build(), RiskAttitude.RISK_AVERSE));
        manager.addScenario(createUAResourseScenario(20,2.0,50, Position.builder().x(0).y(0).build(), Position.builder().x(0).y(0).build(),Position.builder().x(9).y(9).build(), RiskAttitude.RISK_NEUTRAL));
        manager.addScenario(createUAResourseScenario(20,2.0,50, Position.builder().x(0).y(0).build(), Position.builder().x(0).y(0).build(),Position.builder().x(9).y(9).build(), RiskAttitude.RISK_AWARE));

        manager.addScenario(createUASmallResourceScenario(SMALLRESOURCE, RiskAttitude.RISK_SEEKING, false));
        manager.addScenario(createUASmallResourceScenario(SMALLRESOURCE, RiskAttitude.RISK_AWARE, false));
        manager.addScenario(createUASmallResourceScenario(SMALLRESOURCE, RiskAttitude.RISK_NEUTRAL, false));
        manager.addScenario(createUASmallResourceScenario(SMALLRESOURCE, RiskAttitude.RISK_AVERSE, false));
        manager.addScenario(createJSHOPSmallResourceScenario( false));
        manager.run();
    }

    @Test
    public void runDiagonalScenario() throws IOException {
        final Path RESULT_PATH = Files.createTempDirectory("diagonal-results-");
        AlgorithmEvaluationManager manager = new AlgorithmEvaluationManager();
        manager.setFolderPath(RESULT_PATH.toFile());
        final double RESOURCE = 350.0;
        manager.addScenario(createUADiagonalScenario(RESOURCE, RiskAttitude.RISK_SEEKING, false));
        manager.addScenario(createJShopDiagonalScenario(false));
        manager.run();
    }

    @Test
    public void runDifferentRiskAttitudesScenario() throws IOException {
        final Path RESULT_PATH = Files.createTempDirectory("differentRiskAttitudes-results-");
        AlgorithmEvaluationManager manager = new AlgorithmEvaluationManager();
        manager.setFolderPath(RESULT_PATH.toFile());

        final double RESOURCE = 350.0;
        manager.addScenario(createUADiagonalScenario(RESOURCE, RiskAttitude.RISK_SEEKING, false));
        manager.addScenario(createUADiagonalScenario(RESOURCE, RiskAttitude.RISK_AVERSE, false));
        manager.addScenario(createUADiagonalScenario(RESOURCE, RiskAttitude.RISK_AWARE, false));
        manager.addScenario(createUADiagonalScenario(RESOURCE, RiskAttitude.RISK_NEUTRAL, false));
        manager.run();
    }

    @Test
    public void runOrderingMethodsScenario() throws IOException {
        final Path RESULT_PATH = Files.createTempDirectory("orderingMethods-results-");
        AlgorithmEvaluationManager manager = new AlgorithmEvaluationManager();
        manager.setFolderPath(RESULT_PATH.toFile());
        final double RESOURCE = 350.0;
        manager.addScenario(createUAOrderingMethodsScenario(RESOURCE, RiskAttitude.RISK_SEEKING, true));
        manager.addScenario(createUAOrderingMethodsScenario(RESOURCE, RiskAttitude.RISK_AVERSE, true));
        manager.addScenario(createUAOrderingMethodsScenario(RESOURCE, RiskAttitude.RISK_NEUTRAL, true));
        manager.addScenario(createUAOrderingMethodsScenario(RESOURCE, RiskAttitude.RISK_AWARE, true));
        manager.addScenario(createJSHOPOrderingMethodsScenario( true));
        manager.run();
    }

    @Test
    public void runResourceBasedScenario() throws IOException {
        final Path RESULT_PATH = Files.createTempDirectory("resourceBased-results-");
        AlgorithmEvaluationManager manager = new AlgorithmEvaluationManager();
        manager.setFolderPath(RESULT_PATH.toFile());
        manager.addScenario(createUAResourseScenario(20,2.0,50, Position.builder().x(0).y(0).build(), Position.builder().x(0).y(0).build(),Position.builder().x(9).y(9).build(), RiskAttitude.RISK_SEEKING));
        manager.addScenario(createUAResourseScenario(20,2.0,50, Position.builder().x(0).y(0).build(), Position.builder().x(0).y(0).build(),Position.builder().x(9).y(9).build(), RiskAttitude.RISK_AVERSE));
        manager.addScenario(createUAResourseScenario(20,2.0,50, Position.builder().x(0).y(0).build(), Position.builder().x(0).y(0).build(),Position.builder().x(9).y(9).build(), RiskAttitude.RISK_NEUTRAL));
        manager.addScenario(createUAResourseScenario(20,2.0,50, Position.builder().x(0).y(0).build(), Position.builder().x(0).y(0).build(),Position.builder().x(9).y(9).build(), RiskAttitude.RISK_AWARE));
        manager.run();
    }
    @Test
    public void runSmallResourceScenario() throws IOException {
        final Path RESULT_PATH = Files.createTempDirectory("smallResource-results-");
        AlgorithmEvaluationManager manager = new AlgorithmEvaluationManager();
        manager.setFolderPath(RESULT_PATH.toFile());
        final double RESOURCE = 5.0;
        manager.addScenario(createUASmallResourceScenario(RESOURCE, RiskAttitude.RISK_SEEKING, false));
        manager.addScenario(createUASmallResourceScenario(RESOURCE, RiskAttitude.RISK_AWARE, false));
        manager.addScenario(createUASmallResourceScenario(RESOURCE, RiskAttitude.RISK_NEUTRAL, false));
        manager.addScenario(createUASmallResourceScenario(RESOURCE, RiskAttitude.RISK_AVERSE, false));
        manager.addScenario(createJSHOPSmallResourceScenario(false));
        manager.run();
    }

    private EvaluationScenario createUAResourseScenario(int gridSize, double resourceS, int stepsNo, Position truckPos, Position containerPos, Position targetPos , RiskAttitude riskAtt) throws IOException{
        Path currentPath= Files.createTempDirectory("resource-uashop-");
        ResourceBasedScenario scenario = ResourceBasedScenario
                .builder()
                .size(gridSize)
                .truckPosition(truckPos)
                .containerPosition(containerPos)
                .targetPosition(targetPos)
                .resourceStep(resourceS)
                .steps(stepsNo)
                .riskAttitude(riskAtt)
                .outputDirectory(new File(currentPath.toString()))
                .build();
         return repeat(scenario);
    }
    private EvaluationScenario createJShopDiagonalScenario(boolean hasShortcut) throws IOException {
        PlanningAlgorithm algorithm = new JShop2();
        Path currentPath = Files.createTempDirectory("diagonal-jshop2-");
        DiagonalProblemEvaluationScenario scenario = DiagonalProblemEvaluationScenario
                .builder()
                .algorithm(algorithm)
                .startSize(START_SIZE)
                .endSize(END_SIZE)
                .step(STEP)
                .outputDirectory(new File(currentPath.toString()))
                .hasShortcuts(hasShortcut)
                .build();

        return repeat(scenario);
    }



    private EvaluationScenario createUADiagonalScenario(double resource, RiskAttitude riskAttitude, boolean hasShortcut) throws IOException {
        PlanningAlgorithm algorithm = UAShop.builder().resource(resource).riskAttitude(riskAttitude).build();
        Path currentPath = Files.createTempDirectory("diagonal-uashop-");
        DiagonalScenario scenario = DiagonalScenario
                .builder()
                .algorithm(algorithm)
                .startSize(START_SIZE)
                .endSize(END_SIZE)
                .step(STEP)
                .hasShortcuts(hasShortcut)

                .outputDirectory(new File(currentPath.toString()))
                .build();
        return repeat(scenario);
    }

    private EvaluationScenario createUAOrderingMethodsScenario(double resource, RiskAttitude riskAttitude, boolean hasShortcut) throws IOException {
        PlanningAlgorithm algorithm = UAShop.builder().resource(resource).riskAttitude(riskAttitude).build();
        Path currentPath = Files.createTempDirectory("orderingMethods-uashop-");
        OrderingMethodsScenario scenario = OrderingMethodsScenario
                .builder()
                .algorithm(algorithm)
                .startSize(START_SIZE)
                .endSize(END_SIZE)
                .step(STEP)
                .hasShortcuts(hasShortcut)
                .outputDirectory(new File(currentPath.toString()))
                .build();
        return repeat(scenario);
    }

    private EvaluationScenario createJSHOPOrderingMethodsScenario( boolean hasShortcut) throws IOException {
        PlanningAlgorithm algorithm = new JShop2();
        Path currentPath = Files.createTempDirectory("orderingMethods-jshop2-");
        OrderingMethodsScenario scenario = OrderingMethodsScenario
                .builder()
                .algorithm(algorithm)
                .startSize(START_SIZE)
                .endSize(END_SIZE)
                .step(STEP)
                .hasShortcuts(hasShortcut)
                .outputDirectory(new File(currentPath.toString()))
                .build();
        return repeat(scenario);
    }

    private EvaluationScenario createUASmallResourceScenario(double resource, RiskAttitude riskAttitude, boolean hasShortcut) throws IOException {
        PlanningAlgorithm algorithm = UAShop.builder().resource(resource).riskAttitude(riskAttitude).build();
        Path currentPath = Files.createTempDirectory("smallResource-uashop-");
        SmallResourceScenario scenario = SmallResourceScenario
                .builder()
                .algorithm(algorithm)
                .startSize(START_SIZE)
                .endSize(END_SIZE)
                .step(STEP)
                .hasShortcuts(hasShortcut)
                .outputDirectory(new File(currentPath.toString()))
                .build();
        return repeat(scenario);
    }

    private EvaluationScenario createJSHOPSmallResourceScenario( boolean hasShortcut) throws IOException {
        PlanningAlgorithm algorithm = new JShop2();
        Path currentPath = Files.createTempDirectory("smallResource-jshop2-");
        SmallResourceScenario scenario = SmallResourceScenario
                .builder()
                .algorithm(algorithm)
                .startSize(START_SIZE)
                .endSize(END_SIZE)
                .step(STEP)
                .hasShortcuts(hasShortcut)
                .outputDirectory(new File(currentPath.toString()))
                .build();
        return repeat(scenario);
    }



    private EvaluationScenario repeat(EvaluationScenario original) {
        return new RepetitionsDecorator(original, REPS);
    }
}