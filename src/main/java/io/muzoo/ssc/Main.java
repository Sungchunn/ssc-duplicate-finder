package io.muzoo.ssc;

import io.muzoo.ssc.cli.AlgorithmValidator;
import io.muzoo.ssc.cli.CommandLineConfig;
import io.muzoo.ssc.cli.CommandLineHandler;
import io.muzoo.ssc.cli.FilePathValidator;

/**
 * The Main class serves as the entry point for the Duplicate File Finder application.
 * It processes command-line arguments, validates user input, and orchestrates the duplicate
 * file detection workflow using the `DuplicateFinder` class.
 *
 * Responsibilities:
 * - Parse and validate command-line arguments using `CommandLineHandler`.
 * - Initialize dependencies such as the `FilePathValidator` and `AlgorithmValidator`.
 * - Execute the duplicate file search process and handle any errors.
 *
 * Features:
 * - Supports user-specified folder paths, hashing algorithms, and optional flags:
 *   - `-f` or `--folder`: Specifies the folder to process (required).
 *   - `-a` or `--algorithm`: Specifies the hashing algorithm (default: "bbb").
 *   - `-c` or `--count-duplicates`: Enables counting of duplicate files.
 *   - `-p` or `--print`: Enables printing of duplicate file paths.
 * - Displays usage instructions when no arguments are provided or input is invalid.
 *
 * Example Usage:
 * java -jar hw1.jar -f /path/to/folder -a sha256 -c -p
 */
public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("No arguments provided. Usage: java -jar hw1.jar -f <folderPath> -a <algorithm> [-c] [-p]");
            return;
        }

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