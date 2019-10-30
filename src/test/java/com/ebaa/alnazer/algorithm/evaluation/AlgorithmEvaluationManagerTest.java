package com.ebaa.alnazer.algorithm.evaluation;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.ebaa.alnazer.algorithm.algorithms.JShop2;
import com.ebaa.alnazer.algorithm.algorithms.PlanningAlgorithm;
import com.ebaa.alnazer.algorithm.algorithms.RiskAttitude;
import com.ebaa.alnazer.algorithm.algorithms.UAShop;
import com.ebaa.alnazer.algorithm.evaluation.scenarios.DiagonalProblemEvaluationScenario;
import com.ebaa.alnazer.algorithm.evaluation.scenarios.EvaluationScenario;
import com.ebaa.alnazer.algorithm.evaluation.scenarios.ResourceBasedScenario;
import com.ebaa.alnazer.algorithm.evaluation.scenarios.decorator.RepetitionsDecorator;
import com.ebaa.alnazer.problem.movecontainer.model.Position;
import lombok.ToString;
import org.junit.jupiter.api.Test;

class AlgorithmEvaluationManagerTest {
    final int START_SIZE = 4;
    final int END_SIZE = 26;
    final int STEP = 1;
    final int REPS = 10;

    @Test
    public void runEvaluation() throws IOException {
        final Path RESULT_PATH = Files.createTempDirectory("evaluation-results-");
        AlgorithmEvaluationManager manager = new AlgorithmEvaluationManager();
        manager.setFolderPath(RESULT_PATH.toFile());

        // diagonal scenarios
        //-------------------
        final double RESOURCE = 350.0;
        manager.addScenario(createUADiagonalScenario(RESOURCE, RiskAttitude.RISK_AVERSE, false));
        manager.addScenario(createUADiagonalScenario(RESOURCE, RiskAttitude.RISK_SEEKING, false));
        manager.addScenario(createUADiagonalScenario(RESOURCE, RiskAttitude.RISK_NEUTRAL, false));
        manager.addScenario(createUADiagonalScenario(RESOURCE, RiskAttitude.RISK_AWARE, false));
        manager.addScenario(createUADiagonalScenario(RESOURCE, RiskAttitude.RISK_AWARE, true));
        manager.addScenario(createJShopDiagonalScenario(true));
        manager.addScenario(createUAResourseSecenario(20,2.0,10, Position.builder().x(0).y(0).build(), Position.builder().x(0).y(0).build(),Position.builder().x(9).y(9).build(), RiskAttitude.RISK_AVERSE));

        manager.run();
    }

    @Test
    public void test() throws IOException {
        final Path RESULT_PATH = Files.createTempDirectory("evaluation-results-");
        AlgorithmEvaluationManager manager = new AlgorithmEvaluationManager();
        manager.setFolderPath(RESULT_PATH.toFile());

        // diagonal scenarios
        //-------------------
        final double RESOURCE = 350.0;
/*        manager.addScenario(createUADiagonalScenario(RESOURCE, RiskAttitude.RISK_SEEKING, false));
        manager.addScenario(createJShopDiagonalScenario(true));
        manager.addScenario(createUADiagonalScenario(RESOURCE, RiskAttitude.RISK_AVERSE, false));
        manager.addScenario(createUADiagonalScenario(RESOURCE, RiskAttitude.RISK_NEUTRAL, false));
        manager.addScenario(createUADiagonalScenario(RESOURCE, RiskAttitude.RISK_AWARE, false));*/


       /* manager.addScenario(createUAResourseSecenario(20,2.0,50, Position.builder().x(0).y(0).build(), Position.builder().x(0).y(0).build(),Position.builder().x(9).y(9).build(), RiskAttitude.RISK_AVERSE));
        manager.addScenario(createUAResourseSecenario(20,2.0,50, Position.builder().x(0).y(0).build(), Position.builder().x(0).y(0).build(),Position.builder().x(9).y(9).build(), RiskAttitude.RISK_SEEKING));
        manager.addScenario(createUAResourseSecenario(20,2.0,50, Position.builder().x(0).y(0).build(), Position.builder().x(0).y(0).build(),Position.builder().x(9).y(9).build(), RiskAttitude.RISK_AWARE));
        manager.addScenario(createUAResourseSecenario(20,2.0,50, Position.builder().x(0).y(0).build(), Position.builder().x(0).y(0).build(),Position.builder().x(9).y(9).build(), RiskAttitude.RISK_NEUTRAL));*/

        manager.addScenario(createUAResourseSecenario(20,2.0,50, Position.builder().x(1).y(1).build(), Position.builder().x(18).y(18).build(),Position.builder().x(9).y(9).build(), RiskAttitude.RISK_AVERSE));
        manager.addScenario(createUAResourseSecenario(20,2.0,50, Position.builder().x(1).y(1).build(), Position.builder().x(18).y(18).build(),Position.builder().x(9).y(9).build(), RiskAttitude.RISK_SEEKING));
      //  manager.addScenario(createUAResourseSecenario(20,2.0,50, Position.builder().x(1).y(1).build(), Position.builder().x(18).y(18).build(),Position.builder().x(9).y(9).build(), RiskAttitude.RISK_AWARE));
        //manager.addScenario(createUAResourseSecenario(20,2.0,50, Position.builder().x(1).y(1).build(), Position.builder().x(18).y(18).build(),Position.builder().x(9).y(9).build(), RiskAttitude.RISK_NEUTRAL));
        manager.run();
    }

    @Test
    public void testShortcut() throws  IOException{
        final Path RESULT_PATH = Files.createTempDirectory("evaluation-results-testShortcut");
        AlgorithmEvaluationManager manager = new AlgorithmEvaluationManager();
        manager.setFolderPath(RESULT_PATH.toFile());
        final double RESOURCE = 350.0;
        manager.addScenario(createUADiagonalScenario(RESOURCE, RiskAttitude.RISK_SEEKING, true));
        manager.addScenario(createUADiagonalScenario(RESOURCE, RiskAttitude.RISK_AVERSE, true));
        manager.addScenario(createUADiagonalScenario(RESOURCE, RiskAttitude.RISK_NEUTRAL, true));
        manager.addScenario(createUADiagonalScenario(RESOURCE, RiskAttitude.RISK_AWARE, true));
        manager.addScenario(createJShopDiagonalScenario(true));
        manager.run();
    }

    @Test
    public void testSmallResource() throws  IOException{
        final Path RESULT_PATH = Files.createTempDirectory("evaluation-results-smallResource");
        AlgorithmEvaluationManager manager = new AlgorithmEvaluationManager();
        manager.setFolderPath(RESULT_PATH.toFile());
        final double RESOURCE = 5.0;
        manager.addScenario(createUADiagonalScenario(RESOURCE, RiskAttitude.RISK_SEEKING, false));
        manager.addScenario(createUADiagonalScenario(RESOURCE, RiskAttitude.RISK_AVERSE, false));
        manager.addScenario(createUADiagonalScenario(RESOURCE, RiskAttitude.RISK_NEUTRAL, false));
        manager.addScenario(createUADiagonalScenario(RESOURCE, RiskAttitude.RISK_AWARE, false));
        manager.addScenario(createJShopDiagonalScenario(false));
        manager.run();
    }


    private EvaluationScenario createUAResourseSecenario(int gridSize, double resourceS, int stepsNo, Position truckPos, Position containerPos, Position targetPos , RiskAttitude riskAtt) throws IOException{
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
        DiagonalProblemEvaluationScenario scenario = DiagonalProblemEvaluationScenario
                .builder()
                .algorithm(algorithm)
                .startSize(START_SIZE)
                .endSize(END_SIZE)
                .step(STEP)
                .hasShortcuts(hasShortcut)
                .outputDirectory(new File(currentPath.toString()))
                .build();

        // return repeat(scenario);

        return repeat(scenario);
    }



    private EvaluationScenario repeat(EvaluationScenario original) {
        return new RepetitionsDecorator(original, REPS);
    }
}