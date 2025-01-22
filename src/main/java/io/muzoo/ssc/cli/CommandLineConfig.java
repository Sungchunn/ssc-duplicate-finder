package io.muzoo.ssc.cli;

/**
 * The CommandLineConfig class encapsulates the configuration options
 * parsed from the command-line arguments. It acts as a data transfer object
 * (DTO) that holds user-specified options such as the folder path, hashing algorithm,
 * and flags for counting and printing duplicates.
 *
 * Responsibilities:
 * - Stores the folder path where the program should look for files.
 * - Specifies the hashing algorithm to use for duplicate detection.
 * - Flags for enabling counting and printing duplicate files.
 *
 * Example Usage:
 * CommandLineConfig config = new CommandLineConfig("/path/to/folder", "sha256", true, true);
 * String folderPath = config.getFolderPath();
 * boolean shouldCountDuplicates = config.isCountDuplicates();
 *
 */

public class CommandLineConfig {
    private final String folderPath;
    private final String algorithm;
    private final boolean countDuplicates;
    private final boolean printDuplicates;

    /**
     * Constructors
     *
     * @param folderPath The path to the folder where files will be processed.
     * @param algorithm The hashing algorithm to use (e.g., "md5", "sha256", "bbb").
     * @param countDuplicates A flag indicating whether to count duplicate files.
     * @param printDuplicates A flag indicating whether to print duplicate file paths.
     */
    public CommandLineConfig(String folderPath, String algorithm, boolean countDuplicates, boolean printDuplicates) {
        this.folderPath = folderPath;
        this.algorithm = algorithm;
        this.countDuplicates = countDuplicates;
        this.printDuplicates = printDuplicates;
    }

    /**
     * Returns the folder path specified in the command-line arguments.
     * @return The folder path as a string.
     */
    public String getFolderPath() { return folderPath; }

    /**
     * Returns the hashing algorithm specified in the command-line arguments.
     * @return The name of the hashing algorithm (e.g., "md5", "sha256").
     */
    public String getAlgorithm() { return algorithm; }

    /**
     * Indicates whether the program should count duplicate files.
     * @return `true` if duplicate counting is enabled, otherwise `false`.
     */
    public boolean isCountDuplicates() { return countDuplicates; }

    /**
     * Indicates whether the program should print duplicate file paths.
     * @return `true` if printing duplicates is enabled, otherwise `false`.
     */
    public boolean isPrintDuplicates() { return printDuplicates; }

}