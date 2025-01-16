package io.muzoo.ssc;

import io.muzoo.ssc.assignment.tracker.SscAssignment;
import org.apache.commons.cli.*;
import java.io.File;
import java.io.IOException;

public class Main extends SscAssignment {

    public static void main(String[] args) {
        // Default args for testing
        if (args.length == 0) {
            args = new String[]{
                    "-f", "/Users/chromatrical/Downloads/random_files",
                    "-c",
                    "-p"
            };
        }

        Options options = new Options();
        options.addOption("f", "folder", true, "Path to the folder (required)");
        options.addOption("c", "count-duplicates", false, "Count the total number of duplicate files");
        options.addOption("a", "algorithm", true, "Algorithm for finding duplicates (bbb, sha256, md5)");
        options.addOption("p", "print", false, "Print relative paths of all duplicate files");
        options.addOption("h", "help", false, "Display help");

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd;

        try {
            cmd = parser.parse(options, args);

            // Only -f case
            if (args.length == 1 && args[0].equals("-f")) {
                System.err.println("Error: Missing path argument for option: f");
                formatter.printHelp("duplicate-file-finder", options);
                return;
            }

            // Help check
            if (cmd.hasOption("h")) {
                formatter.printHelp("duplicate-file-finder", options);
                return;
            }

            // Required -f check
            if (!cmd.hasOption("f")) {
                System.err.println("Error: Missing required option: f");
                formatter.printHelp("duplicate-file-finder", options);
                return;
            }

            // Extract values from arguments
            String folderPath = cmd.getOptionValue("f");
            boolean countDuplicates = cmd.hasOption("c");
            String algorithm = cmd.getOptionValue("a", "bbb");
            boolean printDuplicates = cmd.hasOption("p");

            // Check for invalid path
            File folder = new File(folderPath);
            if (!folder.exists() || !folder.isDirectory()) {
                System.err.println("Error: Invalid directory path: " + folderPath);
                return;
            }

            // Check for invalid algorithm
            if (cmd.hasOption("a") && !isValidAlgorithm(algorithm)) {
                System.err.println("Input algorithm not supported.");
                System.err.println("usage: duplicate-file-finder");
                System.err.println("-a,--algorithm <arg>   Finding duplicates (bbb, sha256, md5)");
                System.err.println("-c,--count-duplicates  Print total count of duplicate files");
                System.err.println("-f,--folder <arg>      Path to the target folder (required)");
                System.err.println("-p,--print             Print relative paths of duplicates");
                return;
            }

            System.out.println("Starting duplicate file detection...");
            System.out.println("Folder Path: " + folderPath);
            System.out.println("Algorithm: " + algorithm);
            System.out.println("Count Duplicates: " + countDuplicates);
            System.out.println("Print Duplicates: " + printDuplicates);

            // Create and use DuplicateFinder
            DuplicateFinder finder = new DuplicateFinder(folderPath, algorithm, countDuplicates, printDuplicates);
            finder.findDuplicates();

        } catch (ParseException e) {
            System.err.println("Error parsing command-line arguments: " + e.getMessage());
            formatter.printHelp("duplicate-file-finder", options);
        } catch (IOException e) {
            System.err.println("Error processing folder: " + e.getMessage());
        }
    }

    private static boolean isValidAlgorithm(String algorithm) {
        return algorithm != null && (
                algorithm.equals("bbb") ||
                        algorithm.equals("sha256") ||
                        algorithm.equals("md5")
        );
    }
}