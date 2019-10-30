package com.ebaa.alnazer.problem.manage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.ebaa.alnazer.problem.ProblemFileManager;
import com.ebaa.alnazer.problem.movecontainer.generate.RandomProblemGenerator;
import freemarker.template.TemplateException;
import org.junit.jupiter.api.Test;

class ProblemManagerTest {
    @Test
    public void testGenerateFile() throws IOException, TemplateException {
        final int size = 5;
        final int containerCount = 3;
        final int shortcutCount = 12;
        RandomProblemGenerator generator = RandomProblemGenerator
                .builder()
                .size(size)
                .containerCount(containerCount)
                .shortcutCount(shortcutCount)
                .build();
        Path tempDirWithPrefix = Files.createTempDirectory("ua-shop");
        File file = new File(tempDirWithPrefix.toString() + "/problem");
        ProblemFileManager.generateMoveContainerProblemFile(generator.generateProblem(), file);
    }
}