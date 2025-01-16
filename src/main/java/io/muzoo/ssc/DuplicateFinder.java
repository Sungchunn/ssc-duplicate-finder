package io.muzoo.ssc;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.DecimalFormat;
import java.util.*;

public class DuplicateFinder {
    private final String folderPath;
    private final String algorithm;
    private final boolean countDuplicates;
    private final boolean printDuplicates;
    private final Map<String, List<Path>> hashToFileMap = new HashMap<>();
    private final long[] stats = {0, 0, 0}; // [File Count, Folder Count, Total File Size]

    public DuplicateFinder(String folderPath, String algorithm, boolean countDuplicates, boolean printDuplicates) {
        this.folderPath = folderPath;
        this.algorithm = algorithm;
        this.countDuplicates = countDuplicates;
        this.printDuplicates = printDuplicates;
    }

    public void findDuplicates() throws IOException {
        Files.walkFileTree(Paths.get(folderPath), new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                stats[1]++;
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                stats[0]++;
                stats[2] += Files.size(file);

                String hash = HashUtils.computeHash(file.toFile(), algorithm);
                hashToFileMap.computeIfAbsent(hash, k -> new ArrayList<>()).add(file);
                return FileVisitResult.CONTINUE;
            }
        });

        // Always print statistics as per requirements
        printStatistics();

        // Print additional information based on flags
        if (countDuplicates || printDuplicates) {
            printDuplicateInformation();
        }

    }

    public long countDuplicates() {
        return hashToFileMap.values().stream()
                .filter(paths -> paths.size() > 1)
                .count();
    }

    public Map<String, List<Path>> getDuplicateGroups() {
        return hashToFileMap;
    }

    private void printStatistics() {
        DecimalFormat df = new DecimalFormat("#,###");
        System.out.println("Statistics:");
        System.out.println(" - Total Files: " + df.format(stats[0]));
        System.out.println(" - Total Folders: " + df.format(stats[1]));
        System.out.println(" - Total Size: " + df.format(stats[2]) + " bytes");
    }

    private void printDuplicateInformation() {
        DecimalFormat df = new DecimalFormat("#,###");
        long duplicateGroups = countDuplicates();
        long totalDuplicateFiles = hashToFileMap.values().stream()
                .filter(paths -> paths.size() > 1)
                .mapToLong(List::size)
                .sum();

        System.out.println("\nDuplicate Statistics:");
        System.out.println(" - Total Duplicate Groups: " + df.format(duplicateGroups));
        System.out.println(" - Total Duplicate Files: " + df.format(totalDuplicateFiles));

        System.out.println("\nDuplicate File Groups:");
        hashToFileMap.forEach((hash, paths) -> {
            if (paths.size() > 1) {
                System.out.println("================================================================");
                System.out.println("Duplicate group (size: " + paths.size() + "):");
                paths.forEach(path -> System.out.println(" - " + path));
            }
        });
        if (duplicateGroups > 0) {
            System.out.println("================================================================");
        }
    }


}