package io.muzoo.ssc;

import io.muzoo.ssc.assignment.tracker.SscAssignment;
import org.apache.commons.cli.*;
import java.io.IOException;

public class Main extends SscAssignment {

    public static void main(String[] args) {
        // Remove the default args as -f is required
        if (args.length == 0) {
            args = new String[]{
                    "-f", "/Users/chromatrical/Downloads/test_files",
                    "-c",
                    "-p"
            };
        }
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

            // Create and use DuplicateFinder
            DuplicateFinder finder = new DuplicateFinder(folderPath, algorithm, countDuplicates, printDuplicates);
            finder.findDuplicates();

        } catch (ParseException e) {
            System.out.println("Error parsing command-line arguments: " + e.getMessage());
            formatter.printHelp("java -jar hw1.jar", options);
        } catch (IOException e) {
            System.out.println("Error processing folder: " + e.getMessage());
        }
    }
}