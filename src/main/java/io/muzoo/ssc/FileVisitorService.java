package io.muzoo.ssc;

import io.muzoo.ssc.algos.Algorithms;
import io.muzoo.ssc.stats.FileStatistics;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The FileVisitorService class traverses a directory structure using the FileVisitor API.
 * It computes file hashes to identify duplicates and collects file and folder statistics.
 *
 * Responsibilities:
 * - Counts the total number of files and folders in the directory.
 * - Calculates the total size of all files.
 * - Computes a hash for each file to detect duplicates.
 * - Stores duplicate file paths in a map for later retrieval.
 *
 * Dependencies:
 * - Algorithms: Defines the hashing strategy (e.g., bbb, md5, sha256).
 * - FileStatistics: Collects statistics about files and folders.
 *
 * Example Usage:
 * FileVisitorService visitor = new FileVisitorService(new BBBAlgo());
 * visitor.visitDirectory("/path/to/folder");
 */

public class FileVisitorService {
    private final Algorithms hashStrategy;
    private final FileStatistics statistics;
    private final Map<String, List<Path>> hashToFileMap;

    /**
     * Constructor for FileVisitorService.
     * Initializes the hashing strategy, statistics tracker, and duplicate file map.
     *
     * @param hashStrategy The hashing algorithm to use for computing file hashes (e.g., md5, sha256).
     */
    public FileVisitorService(Algorithms hashStrategy) {
        this.hashStrategy = hashStrategy;
        this.statistics = new FileStatistics();
        this.hashToFileMap = new HashMap<>();
    }

    /**
     * Traverses the specified directory recursively to collect file statistics
     * and identify duplicate files based on their computed hashes.
     *
     * @param folderPath The path to the directory to traverse.
     * @throws IOException If an I/O error occurs while accessing files or directories.
     */
    public void visitDirectory(String folderPath) throws IOException {
        Files.walkFileTree(Paths.get(folderPath), new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                // Increment the folder count for each directory visited
                statistics.incrementFolderCount();
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                // Increment the file count and total file size
                statistics.incrementFileCount();
                statistics.incrementTotalFileSize(Files.size(file));

                try {
                    // Compute the hash of the file and group duplicates
                    String hash = hashStrategy.computerHash(file.toFile());
                    hashToFileMap.computeIfAbsent(hash, k -> new ArrayList<>()).add(file);
                } catch (Exception e) {
                    System.err.println("Error processing file: " + file + " - " + e.getMessage());
                }

                return FileVisitResult.CONTINUE;
            }
        });
    }

    /**
     * Returns the collected statistics, including total file count,
     * folder count, and total file size.
     *
     * @return An instance of FileStatistics containing the aggregated statistics.
     */
    public FileStatistics getStatistics() {
        return statistics;
    }

    /**
     * Returns a map of file hashes to lists of file paths,
     * which can be used to identify duplicate files.
     *
     * @return A map where the key is the hash of a file,
     *         and the value is a list of paths to files with the same hash.
     */
    public Map<String, List<Path>> getHashToFileMap() {
        return hashToFileMap;
    }
}