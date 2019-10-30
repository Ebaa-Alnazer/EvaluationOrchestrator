package com.ebaa.alnazer.problem;

import java.io.File;
import java.io.IOException;

import com.ebaa.alnazer.problem.movecontainer.generate.DiagonalProblemGenerator;
import com.ebaa.alnazer.problem.movecontainer.generate.RandomProblemGenerator;
import com.ebaa.alnazer.problem.movecontainer.model.MoveContainerProblem;
import freemarker.template.TemplateException;

public class ProblemFileManager {
    enum ProblemEnum {
        MOVE_CONTAINER("move-container-problem.ftlh");

        private String templateFile;

        public String getTemplateFile() {
            return templateFile;
        }

        private ProblemEnum(String name) {
            this.templateFile = name;
        }
    }

    private static void generateProblemFile(Problem problem, ProblemEnum problemEnum, File outputFilePath) throws IOException, TemplateException {
        ProblemFileGenerator.generateFile(problemEnum.getTemplateFile(), problem, outputFilePath.getPath());
    }

    public static void generateMoveContainerProblemFile(MoveContainerProblem problem, File outputFilePath) throws IOException, TemplateException {
        generateProblemFile(problem, ProblemEnum.MOVE_CONTAINER, outputFilePath);
    }

    public static void generateRandomMoveContainerProblemFile(int size, int containerCount, int shortcutCount, File outputFilePath) throws IOException, TemplateException {
        RandomProblemGenerator generator = RandomProblemGenerator
                .builder()
                .size(size)
                .containerCount(containerCount)
                .shortcutCount(shortcutCount)
                .build();
        MoveContainerProblem problem = generator.generateProblem();
        generateMoveContainerProblemFile(problem, outputFilePath);
    }

    public static void generateDiagonalMoveContainerProblemFile(int size, boolean hasShortcut, File outputFilePath) throws IOException, TemplateException {
        DiagonalProblemGenerator generator = DiagonalProblemGenerator.builder().size(size).hasShortcut(hasShortcut).build();
        MoveContainerProblem problem = generator.generateProblem();
        generateMoveContainerProblemFile(problem, outputFilePath);
    }
}
