package io.muzoo.ssc;

import io.muzoo.ssc.algos.AlgoFactory;
import io.muzoo.ssc.algos.Algorithms;

import java.io.IOException;

public class DuplicateFinder {
    private final String folderPath;
    private final Algorithms hashAlgo;
    private final boolean countDuplicates;
    private final boolean printDuplicates;

    public DuplicateFinder(String folderPath,
                           String algorithm,
                           boolean countDuplicates,
                           boolean printDuplicates) {
        this.folderPath = folderPath;
        this.hashAlgo = AlgoFactory.createAlgo(algorithm);
        this.countDuplicates = countDuplicates;
        this.printDuplicates = printDuplicates;
    }

    public void findDuplicates() throws IOException {
        FileVisitorService visitor = new FileVisitorService(hashAlgo);
        visitor.visitDirectory(folderPath);

        System.out.println(visitor.getStatistics().getFormattedStatistics());

        if (countDuplicates || printDuplicates) {
            DuplicateReport report = new DuplicateReport(visitor.getHashToFileMap());
            System.out.println(report.generateReport(!printDuplicates));
        }
    }
}