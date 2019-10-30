package com.ebaa.alnazer.algorithm.evaluation.scenarios.decorator;

import java.io.File;

import ch.qos.logback.core.pattern.IdentityCompositeConverter;
import com.ebaa.alnazer.algorithm.evaluation.scenarios.EvaluationScenario;

public abstract class ScenarioDecorator extends EvaluationScenario {
    protected EvaluationScenario decoratedScenario;


    public ScenarioDecorator(EvaluationScenario scenario) {
        super(scenario.getOutputDirectory());
        this.decoratedScenario = scenario;
    }

    @Override
    public int getIndexOfDurationColumnInOutput() {
        return decoratedScenario.getIndexOfDurationColumnInOutput();
    }

    @Override
    public File retrieveResults() {
        return this.decoratedScenario.retrieveResults();
    }
}
