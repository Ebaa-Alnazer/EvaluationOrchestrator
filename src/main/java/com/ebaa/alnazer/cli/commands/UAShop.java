package com.ebaa.alnazer.cli.commands;

import picocli.CommandLine;

@CommandLine.Command(subcommands = {Generate.class, Execute.class})
public class UAShop implements Runnable{
    @CommandLine.Spec
    private CommandLine.Model.CommandSpec spec;

    @Override
    public void run() {
        throw new CommandLine.ParameterException(spec.commandLine(), "Missing required subcommand");
    }
}
