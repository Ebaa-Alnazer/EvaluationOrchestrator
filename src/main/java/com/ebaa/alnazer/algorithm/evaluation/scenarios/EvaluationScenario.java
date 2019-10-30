package com.ebaa.alnazer.algorithm.evaluation.scenarios;

import java.io.File;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public abstract class EvaluationScenario implements Runnable {
    protected File outputDirectory;

    public void verifyInputs() throws IllegalArgumentException {
        if (outputDirectory == null || outputDirectory.isFile()) {
            throw new IllegalArgumentException("Output directory does not exist or refers to an existing file!");
        }
    }

    public abstract File retrieveResults();

    public abstract int getIndexOfDurationColumnInOutput();

    protected File getMoveContainerDomainFile() {
        return new File(
                Objects.requireNonNull(getClass().getClassLoader().getResource("domains/movecontainer")).getFile()
        );
    }
}
