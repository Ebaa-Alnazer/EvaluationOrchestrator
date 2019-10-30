package com.ebaa.alnazer;

import com.ebaa.alnazer.cli.commands.UAShop;
import picocli.CommandLine;

public class Program {
    public static void main(String[] args) {
        CommandLine cl = new CommandLine(new UAShop());
        cl.execute(args);
    }
}
