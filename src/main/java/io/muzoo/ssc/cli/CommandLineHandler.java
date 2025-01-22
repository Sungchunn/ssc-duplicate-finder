package io.muzoo.ssc.cli;

import org.apache.commons.cli.*;

/**
 * The CommandLineHandler class is responsible for parsing and validating command-line arguments.
 * It uses Apache Commons CLI to define the accepted arguments, validate their correctness,
 * and create a `CommandLineConfig` object based on the user's input.
 *
 * Responsibilities:
 * - Define command-line options and their descriptions.
 * - Parse the provided arguments and handle invalid or missing options.
 * - Validate the folder path and hashing algorithm.
 * - Display help messages for incorrect usage.
 *
 * Features:
 * - Supports required and optional arguments, including:
 *   - `-f` or `--folder`: Specifies the folder path for processing (required).
 *   - `-a` or `--algorithm`: Specifies the hashing algorithm (default: "bbb").
 *   - `-c` or `--count-duplicates`: Counts the total number of duplicate files.
 *   - `-p` or `--print`: Prints paths of duplicate files.
 *   - `-h` or `--help`: Displays the help message.
 *
 * Example Usage:
 * CommandLineHandler handler = new CommandLineHandler(new FilePathValidator(), new AlgorithmValidator());
 * CommandLineConfig config = handler.parse(args);
 *
 */

public class CommandLineHandler implements ICommandLineParser {
    private final Options options;
    private final HelpFormatter formatter;
    private final IPathValidator pathValidator;
    private final IAlgorithmValidator algorithmValidator;

    /**
     * Constructs a CommandLineHandler with specified validators.
     *
     * @param filePathValidator The validator for the folder path.
     * @param algorithmValidator The validator for the hashing algorithm.
     */
    public CommandLineHandler(FilePathValidator filePathValidator, AlgorithmValidator algorithmValidator) {
        this.options = createOptions();
        this.formatter = new HelpFormatter();
        this.pathValidator = new FilePathValidator();
        this.algorithmValidator = new AlgorithmValidator();
    }

    /**
     * Parses and validates the command-line arguments. Displays help or error messages
     * if the arguments are invalid or incomplete.
     *
     * @param args The array of command-line arguments passed to the application.
     * @return A `CommandLineConfig` object containing parsed options or `null` if help is requested or validation fails.
     * @throws ParseException If the arguments are invalid or cannot be parsed.
     */
    @Override
    public CommandLineConfig parse(String[] args) throws ParseException {
        String[] processedArgs = getDefaultOrProvidedArgs(args);
        CommandLine cmd = parseCommandLine(processedArgs);

        if (shouldShowHelp(cmd)) {
            printHelp();
            return null;
        }

        return createConfig(cmd);
    }

    /**
     * Parses the command-line arguments using Apache Commons CLI's DefaultParser.
     * @param args The array of arguments to parse.
     * @return A `CommandLine` object containing the parsed options.
     * @throws ParseException If parsing fails due to invalid arguments.
     */
    private CommandLine parseCommandLine(String[] args) throws ParseException {
        return new DefaultParser().parse(options, args);
    }

    /**
     * Creates and configures the available command-line options.
     * @return An `Options` object containing all defined command-line options.
     */
    private Options createOptions() {
        Options options = new Options();
        options.addOption("f", "folder", true, "Path to the folder (required)");
        options.addOption("c", "count-duplicates", false, "Count the total number of duplicate files");
        options.addOption("a", "algorithm", true, "Algorithm for finding duplicates (bbb, sha256, md5)");
        options.addOption("p", "print", false, "Print relative paths of all duplicate files");
        options.addOption("h", "help", false, "Display help");
        return options;
    }

    /**
     * Returns default arguments if none are provided. This ensures the program can
     * run with a fallback folder path and default options for testing purposes.
     *
     * @param args The user-provided command-line arguments.
     * @return The user-provided arguments, or default arguments if none are provided.
     */
    private String[] getDefaultOrProvidedArgs(String[] args) {
        if (args.length == 0) {
            return new String[]{"-f", "/Users/chromatrical/Downloads/test_files", "-c", "-p"};
        }
        return args;
    }

    /**
     * Checks whether the help message should be displayed based on the arguments.
     * Help is shown if the `-h` option is provided or if the required `-f` option is missing.
     *
     * @param cmd The parsed `CommandLine` object.
     * @return `true` if help should be displayed, otherwise `false`.
     */
    private boolean shouldShowHelp(CommandLine cmd) {
        return cmd.hasOption("h") || !cmd.hasOption("f");
    }

    /**
     * Displays the help message, including usage instructions and available options.
     */
    private void printHelp() {
        formatter.printHelp("duplicate-file-finder", options);
    }

    /**
     * Creates a `CommandLineConfig` object based on the parsed and validated command-line arguments.
     * Validates the folder path and hashing algorithm before creating the configuration.
     *
     * @param cmd The parsed `CommandLine` object containing the user's arguments.
     * @return A `CommandLineConfig` object, or `null` if validation fails.
     */
    private CommandLineConfig createConfig(CommandLine cmd) {
        String folderPath = cmd.getOptionValue("f");
        String algorithm = cmd.getOptionValue("a", "bbb");

        if (!pathValidator.isValid(folderPath)) {
            System.err.println("Error: Invalid directory path: " + folderPath);
            return null;
        }

        if (cmd.hasOption("a") && !algorithmValidator.isValid(algorithm)) {
            System.err.println("Error: Invalid algorithm: " + algorithm);
            algorithmValidator.printUsage();
            return null;
        }

        return new CommandLineConfig(
                folderPath,
                algorithm,
                cmd.hasOption("c"),
                cmd.hasOption("p")
        );
    }
}