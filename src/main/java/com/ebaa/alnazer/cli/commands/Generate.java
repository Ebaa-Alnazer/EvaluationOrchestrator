package com.ebaa.alnazer.cli.commands;

import java.io.File;

import picocli.CommandLine;

@CommandLine.Command(name = "generate-problem", subcommands = {GenerateMoveContainerProblem.class}, description = "generates an input file that describes a random problem")
public class Generate implements Runnable {
    @CommandLine.Option(order = 3, names = {"-f", "--file"}, paramLabel = "OUTPUT", description = "the generated problem file")
    File problemFile;

    @CommandLine.Spec
    private CommandLine.Model.CommandSpec spec;

    @Override
    public void run() {
        throw new CommandLine.ParameterException(spec.commandLine(), "Missing required subcommand");
    }
}
