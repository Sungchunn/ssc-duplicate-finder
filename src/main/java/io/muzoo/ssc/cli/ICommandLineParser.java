package io.muzoo.ssc.cli;

import org.apache.commons.cli.ParseException;

/**
 * The ICommandLineParser interface defines a contract for parsing command-line arguments.
 * It is responsible for processing input arguments, validating them, and returning a
 * configuration object (`CommandLineConfig`) with the parsed options.
 *
 * Use Cases:
 * - Validate required arguments such as file paths and algorithms.
 * - Extract optional arguments like flags for counting and printing duplicates.
 * - Handle parsing errors by throwing a `ParseException`.
 *
 * Example Usage:
 * ICommandLineParser parser = new CommandLineHandler(...);
 * CommandLineConfig config = parser.parse(args);
 *
 * Dependencies:
 * - Apache Commons CLI for command-line argument parsing.
 *
 */
public interface ICommandLineParser {

    /**
     * Parses the command-line arguments and validates them.
     *
     * @param args The array of command-line arguments passed to the application.
     * @return A `CommandLineConfig` object containing the parsed configuration options.
     * @throws ParseException If the arguments are invalid or parsing fails.
     */
    CommandLineConfig parse(String[] args) throws ParseException;
}