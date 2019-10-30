package com.ebaa.alnazer.cli.commands;

import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import static org.junit.jupiter.api.Assertions.*;

class UAShopTest {

    @Test
    public void testGenerateCL() {
        CommandLine cl = new CommandLine(new UAShop());
        cl.execute("generate-problem", "-s", "3", "-c", "2", "-sh", "5", "-f", "C:\\temp");
    }

}