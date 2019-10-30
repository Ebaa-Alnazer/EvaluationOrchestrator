package com.ebaa.alnazer.algorithm.evaluation;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.ebaa.alnazer.algorithm.evaluation.scenarios.EvaluationScenario;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AlgorithmEvaluationManager implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(AlgorithmEvaluationManager.class);
    private List<EvaluationScenario> scenarios = new ArrayList<>();
    private File folderPath;

    public void setFolderPath(File folderPath) {
        this.folderPath = folderPath;
    }

    public void addScenario(EvaluationScenario scenario) {
        this.scenarios.add(scenario);
    }

    @Override
    public void run() {
        if (folderPath.mkdirs()) {
            log.info("Folder " + folderPath + " is successfully created!");
        }

        File currentOriginalFile;
        File currentTargetFile;
        try {
            for (EvaluationScenario scenario : scenarios) {
                scenario.verifyInputs();
                scenario.run();
                currentOriginalFile = scenario.retrieveResults();
                currentTargetFile = new File(folderPath, scenario.toString() + ".csv");
                FileUtils.moveFile(currentOriginalFile, currentTargetFile);
                File parentFolder = currentOriginalFile.getParentFile();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
