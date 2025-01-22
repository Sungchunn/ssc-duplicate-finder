package io.muzoo.ssc;

import io.muzoo.ssc.cli.AlgorithmValidator;
import io.muzoo.ssc.cli.CommandLineConfig;
import io.muzoo.ssc.cli.CommandLineHandler;
import io.muzoo.ssc.cli.FilePathValidator;

public class Main {
    public static void main(String[] args) {
//        CommandLineHandler cmdHandler = new CommandLineHandler();
        CommandLineHandler cmdHandler = new CommandLineHandler(new FilePathValidator(), new AlgorithmValidator());
        try {
            CommandLineConfig config = cmdHandler.parse(args);
            if (config != null) {
                DuplicateFinder finder = new DuplicateFinder(
                        config.getFolderPath(),
                        config.getAlgorithm(),
                        config.isCountDuplicates(),
                        config.isPrintDuplicates()
                );
                finder.findDuplicates();
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}