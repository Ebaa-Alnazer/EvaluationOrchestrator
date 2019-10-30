package com.ebaa.alnazer.cli.commands;

import java.io.IOException;

import com.ebaa.alnazer.problem.ProblemFileManager;
import freemarker.template.TemplateException;
import picocli.CommandLine;

@CommandLine.Command(name = "move-container", description = "specifies that the problem should correspond to the move-container domain and sets the inputs required for the generation process")
public class GenerateMoveContainerProblem implements Runnable {
    @CommandLine.Spec
    CommandLine.Model.CommandSpec spec;

    @CommandLine.ParentCommand
    private Generate generateCommand;

    @CommandLine.Option(names = {"-s", "--size"}, paramLabel = "SIZE", description = "the size of the problem (one side of the square grid)")
    int size;

    @CommandLine.Option(names = {"-c", "--c"}, paramLabel = "CONTAINERS", description = "the number of containers to generate")
    int containers;

    @CommandLine.Option(names = {"-sh", "--shortcuts"}, paramLabel = "SHORTCUTS", description = "the number of shortcuts to generate")
    int shortcuts;

    @Override
    public void run() {
        try {
            System.out.println("Generating a random move-container problem file...");
            ProblemFileManager.generateRandomMoveContainerProblemFile(size, containers, shortcuts, generateCommand.problemFile);
            System.out.println("Successfully generated a random move-container problem file at: " + generateCommand.problemFile);
        } catch (IOException | TemplateException e) {
            throw new CommandLine.ParameterException(spec.commandLine(),
                    "An exception occurred when trying to generate the problem file. Reason: " + e.getMessage(), e);
        }
    }
}
