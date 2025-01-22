package io.muzoo.ssc.cli;

import org.apache.commons.cli.*;

public class CommandLineHandler implements ICommandLineParser {
    private final Options options;
    private final HelpFormatter formatter;
    private final IPathValidator pathValidator;
    private final IAlgorithmValidator algorithmValidator;

    public CommandLineHandler(FilePathValidator filePathValidator, AlgorithmValidator algorithmValidator) {
        this.options = createOptions();
        this.formatter = new HelpFormatter();
        this.pathValidator = new FilePathValidator();
        this.algorithmValidator = new AlgorithmValidator();
    }

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

    private CommandLine parseCommandLine(String[] args) throws ParseException {
        return new DefaultParser().parse(options, args);
    }

    private Options createOptions() {
        Options options = new Options();
        options.addOption("f", "folder", true, "Path to the folder (required)");
        options.addOption("c", "count-duplicates", false, "Count the total number of duplicate files");
        options.addOption("a", "algorithm", true, "Algorithm for finding duplicates (bbb, sha256, md5)");
        options.addOption("p", "print", false, "Print relative paths of all duplicate files");
        options.addOption("h", "help", false, "Display help");
        return options;
    }

    private String[] getDefaultOrProvidedArgs(String[] args) {
        if (args.length == 0) {
            return new String[]{"-f", "/Users/chromatrical/Downloads/test_files", "-c", "-p"};
        }
        return args;
    }

    private boolean shouldShowHelp(CommandLine cmd) {
        return cmd.hasOption("h") || !cmd.hasOption("f");
    }

    private void printHelp() {
        formatter.printHelp("duplicate-file-finder", options);
    }

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