package io.muzoo.ssc;

import io.muzoo.ssc.algos.AlgoFactory;
import io.muzoo.ssc.algos.Algorithms;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * The DuplicateFinder class is responsible for orchestrating the process of finding duplicate files
 * in a specified directory. It uses a specified hashing algorithm to identify duplicates and provides
 * options for counting and printing duplicate files.
 *
 * Responsibilities:
 * - Validate the folder path and ensure it exists.
 * - Create and use a hashing algorithm through the AlgoFactory.
 * - Traverse the directory to calculate file statistics and detect duplicates.
 * - Provide runtime performance statistics, including elapsed time.
 *
 * Features:
 * - Supports multiple hashing algorithms (e.g., SHA-256, MD5, and byte-by-byte comparison).
 * - Allows configurable options to count and print duplicate files.
 * - Uses the `FileVisitorService` class for directory traversal and file processing.
 *
 * Example Usage:
 * DuplicateFinder finder = new DuplicateFinder(
 *     "/path/to/folder",
 *     "sha256",
 *     true,
 *     true
 * );
 * finder.findDuplicates();
 */
public class DuplicateFinder {
    private final String folderPath;
    private final Algorithms hashAlgo;
    private final boolean countDuplicates;
    private final boolean printDuplicates;

    /**
     * Constructs a DuplicateFinder with the specified parameters.
     *
     * @param folderPath      The path to the folder where duplicate detection should be performed.
     * @param algorithm       The name of the hashing algorithm to use (e.g., "sha256", "md5", "bbb").
     * @param countDuplicates A flag indicating whether to count the total number of duplicates.
     * @param printDuplicates A flag indicating whether to print the paths of duplicate files.
     * @throws IllegalArgumentException If the folder path is null, empty, or does not exist.
     */
    public DuplicateFinder(String folderPath,
                           String algorithm,
                           boolean countDuplicates,
                           boolean printDuplicates) {
        if (folderPath == null || folderPath.isEmpty()) {
            throw new IllegalArgumentException("Folder path cannot be null or empty.");
        }

        if (!Files.exists(Paths.get(folderPath))) {
            throw new IllegalArgumentException("Specified folder path does not exist: " + folderPath);
        }

        this.folderPath = folderPath;
        this.hashAlgo = AlgoFactory.createAlgo(algorithm);
        this.countDuplicates = countDuplicates;
        this.printDuplicates = printDuplicates;
    }

    /**
     * Initiates the process of finding duplicate files in the specified folder.
     *
     * Steps:
     * - Validates the folder path and hashing algorithm.
     * - Uses the FileVisitorService to traverse the directory and detect duplicates.
     * - Prints file statistics (e.g., total files, folders, size) and runtime performance.
     *
     * @throws IOException If an I/O error occurs during directory traversal or file processing.
     */
    public void findDuplicates() throws IOException {
        try {
            System.out.println("Starting duplicate file search...");
            long startTime = System.currentTimeMillis();

            FileVisitorService visitor = new FileVisitorService(hashAlgo);
            visitor.visitDirectory(folderPath);

            if (visitor.getStatistics() != null) {
                System.out.println(visitor.getStatistics().getFormattedStatistics());
            } else {
                System.out.println("No statistics available. Directory may be empty or inaccessible.");
            }

            long endTime = System.currentTimeMillis();
            long elapsedTime = endTime - startTime;
            System.out.printf("Duplicate search completed in %d minutes, %d seconds, %d milliseconds.%n",
                    elapsedTime / 60000, (elapsedTime / 1000) % 60, elapsedTime % 1000);

        } catch (IOException e) {
            System.err.println("Error while finding duplicates: " + e.getMessage());
            throw e;
        }
    }
}