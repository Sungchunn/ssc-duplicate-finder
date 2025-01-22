package io.muzoo.ssc.cli;

public class CommandLineConfig {
    private final String folderPath;
    private final String algorithm;
    private final boolean countDuplicates;
    private final boolean printDuplicates;

    // Constructor
    public CommandLineConfig(String folderPath, String algorithm, boolean countDuplicates, boolean printDuplicates) {
        this.folderPath = folderPath;
        this.algorithm = algorithm;
        this.countDuplicates = countDuplicates;
        this.printDuplicates = printDuplicates;
    }

    // Getters
    public String getFolderPath() {
        return folderPath;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public boolean isCountDuplicates() {
        return countDuplicates;
    }

    public boolean isPrintDuplicates() {
        return printDuplicates;
    }

}