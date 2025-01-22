package io.muzoo.ssc;

import io.muzoo.ssc.cli.AlgorithmValidator;
import io.muzoo.ssc.cli.CommandLineConfig;
import io.muzoo.ssc.cli.CommandLineHandler;
import io.muzoo.ssc.cli.FilePathValidator;

/**
 * The Main class serves as the entry point for the Duplicate File Finder application.
 * It processes command-line arguments, validates user input, and orchestrates the duplicate
 * file detection workflow using the `DuplicateFinder` class.
 */
public class Main {
    public static void main(String[] args) {
        // Hardcoded values for testing purposes
        String hardcodedFolderPath = "/Users/chromatrical/Downloads/test_files"; // Replace with your test folder path
        String hardcodedAlgorithm = "sha256"; // "sha256" , "md5", "bbb"
        boolean hardcodedCountDuplicates = true;
        boolean hardcodedPrintDuplicates = true;

        if (args.length == 0) {
            System.out.println("No arguments provided. Running with hardcoded values:");
            System.out.println("Folder Path: " + hardcodedFolderPath);
            System.out.println("Algorithm: " + hardcodedAlgorithm);
            System.out.println("Count Duplicates: " + hardcodedCountDuplicates);
            System.out.println("Print Duplicates: " + hardcodedPrintDuplicates);

            // Directly create a DuplicateFinder instance with hardcoded values
            try {
                DuplicateFinder finder = new DuplicateFinder(
                        hardcodedFolderPath,
                        hardcodedAlgorithm,
                        hardcodedCountDuplicates,
                        hardcodedPrintDuplicates
                );
                finder.findDuplicates();
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
        } else {
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
}