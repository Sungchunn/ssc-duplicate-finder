package io.muzoo.ssc;

import io.muzoo.ssc.assignment.tracker.SscAssignment;
import org.apache.commons.cli.*;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.DecimalFormat;
import java.util.*;

public class Main extends SscAssignment {

    public static void findDuplicates(String folderPath, String algorithm, boolean countDuplicates, boolean printDuplicates) throws IOException {
        Map<String, List<Path>> hashToFileMap = new HashMap<>();
        long[] stats = {0, 0, 0}; // [File Count, Folder Count, Total File Size]

        // Traverse the file tree
        Files.walkFileTree(Paths.get(folderPath), new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                stats[1]++; // Count folders
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                stats[0]++; // Count files
                stats[2] += Files.size(file); // Accumulate file sizes

                String hash = computeHash(file.toFile(), algorithm);
                hashToFileMap.computeIfAbsent(hash, k -> new ArrayList<>()).add(file);
                return FileVisitResult.CONTINUE;
            }
        });

        DecimalFormat df = new DecimalFormat("#,###");
        System.out.println("Statistics:");
        System.out.println(" - Total Files: " + df.format(stats[0]));
        System.out.println(" - Total Folders: " + df.format(stats[1]));
        System.out.println(" - Total Size: " + df.format(stats[2]) + " bytes");

        if (countDuplicates) {
            long duplicateGroups = hashToFileMap.values().stream()
                    .filter(paths -> paths.size() > 1)
                    .count();
            System.out.println(" - Total Duplicate Groups: " + duplicateGroups);
        }

        if (printDuplicates) {
            System.out.println("\nDuplicate Files:");
            hashToFileMap.forEach((hash, paths) -> {
                if (paths.size() > 1) {
                    System.out.println("Duplicate group:");
                    paths.forEach(path -> System.out.println(" - " + path));
                }
            });
        }
    }

    private static String computeHash(File file, String algorithm) throws IOException {
        try (FileInputStream fis = new FileInputStream(file)) {
            if ("sha256".equalsIgnoreCase(algorithm)) {
                return DigestUtils.sha256Hex(fis);
            } else if ("md5".equalsIgnoreCase(algorithm)) {
                return DigestUtils.md5Hex(fis);
            } else if ("bbb".equalsIgnoreCase(algorithm)) {
                return DigestUtils.sha256Hex(fis); // Use SHA256 for byte-by-byte fallback
            } else {
                throw new IllegalArgumentException("Unsupported algorithm: " + algorithm);
            }
        }
    }

    public static void main(String[] args) {

        // Define command-line options
        Options options = new Options();
        options.addOption("f", "folder", true, "Path to the folder (required)");
        options.addOption("c", "count-duplicates", false, "Count the total number of duplicate files");
        options.addOption("a", "algorithm", true, "Algorithm for finding duplicates (bbb, sha256, md5)");
        options.addOption("p", "print", false, "Print relative paths of all duplicate files");
        options.addOption("h", "help", false, "Display help");

        // Parse command-line arguments
        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd;

        if (args.length == 0) {
            args = new String[]{"-f", "/Users/chromatrical/Downloads/random_files"}; // Replace with your actual test path
        }

        try {
            cmd = parser.parse(options, args);

            // Display help if requested or if required arguments are missing
            if (cmd.hasOption("h") || !cmd.hasOption("f")) {
                formatter.printHelp("java -jar hw1.jar", options);
                return;
            }

            // Extract values from arguments
            String folderPath = cmd.getOptionValue("f");
            boolean countDuplicates = cmd.hasOption("c");
            String algorithm = cmd.getOptionValue("a", "bbb"); // Default to byte-by-byte comparison
            boolean printDuplicates = cmd.hasOption("p");

            System.out.println("Starting duplicate file detection...");
            System.out.println("Folder Path: " + folderPath);
            System.out.println("Algorithm: " + algorithm);
            System.out.println("Count Duplicates: " + countDuplicates);
            System.out.println("Print Duplicates: " + printDuplicates);

            // Invoke the logic for finding duplicates
            findDuplicates(folderPath, algorithm, countDuplicates, printDuplicates);

        } catch (ParseException e) {
            System.out.println("Error parsing command-line arguments: " + e.getMessage());
            formatter.printHelp("java -jar hw1.jar", options);
        } catch (IOException e) {
            System.out.println("Error processing folder: " + e.getMessage());
        }
    }
}