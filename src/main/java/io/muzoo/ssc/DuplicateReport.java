package io.muzoo.ssc;

import java.nio.file.Path;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;


/**
 * The DuplicateReport class is responsible for generating reports about duplicate files
 * detected in a directory. It summarizes duplicate statistics and provides detailed
 * information about groups of duplicate files.
 *
 * Responsibilities:
 * - Calculate the number of duplicate groups and total duplicate files.
 * - Generate a human-readable report summarizing duplicate statistics.
 * - Provide detailed information about each group of duplicate files.
 *
 * Features:
 * - Formats numbers with thousands separators for better readability.
 * - Allows generating reports in summary mode (only counts) or detailed mode.
 * - Efficiently processes duplicates using streams and functional programming.
 *
 * Example Usage:
 * DuplicateReport report = new DuplicateReport(duplicatesMap);
 * System.out.println(report.generateReport(false)); // Detailed report
 * System.out.println(report.generateReport(true));  // Summary-only report
 */

public class DuplicateReport {
    private final Map<String, List<Path>> duplicates;

    /**
     * Constructs a DuplicateReport instance with a map of duplicates.
     *
     * @param duplicates A map where the key is the file hash, and the value is a list of file paths
     *                   that share the same hash (i.e., duplicate files).
     */
    public DuplicateReport(Map<String, List<Path>> duplicates) {
        this.duplicates = duplicates;
    }

    /**
     * Generates a report summarizing duplicate statistics and optionally providing
     * detailed information about duplicate file groups.
     *
     * @param countOnly If `true`, only summary statistics are included in the report.
     *                  If `false`, detailed duplicate file group information is also included.
     * @return A string containing the formatted report.
     */
    public String generateReport(boolean countOnly) {
        StringBuilder report = new StringBuilder();
        DecimalFormat df = new DecimalFormat("#,###");

        long duplicateGroups = getDuplicateGroupCount();
        long totalDuplicateFiles = getTotalDuplicateFiles();

        report.append("\nDuplicate Statistics:\n")
                .append(" - Total Duplicate Groups: ").append(df.format(duplicateGroups)).append("\n")
                .append(" - Total Duplicate Files: ").append(df.format(totalDuplicateFiles)).append("\n");

        if (!countOnly) {
            report.append("\nDuplicate File Groups:\n");
            appendDetailedDuplicateInfo(report);
        }
        return report.toString();
    }

    /**
     * Appends detailed information about each duplicate file group to the report.
     * Each group of duplicate files is identified by its hash, and all file paths in
     * the group are listed.
     *
     * @param report A StringBuilder object to which the detailed information will be appended.
     */
    private void appendDetailedDuplicateInfo(StringBuilder report) {
        duplicates.forEach((hash, paths) -> {
            if (paths.size() > 1) {
                report.append("================================================================\n")
                        .append("Duplicate group (size: ").append(paths.size()).append("):\n");
                paths.forEach(path -> report.append(" - ").append(path).append("\n"));
            }
        });
    }

    /**
     * Calculates the total number of duplicate groups.
     * A duplicate group is defined as a set of files that share the same hash
     * and have a size greater than 1.
     *
     * @return The total number of duplicate groups.
     */
    public long getDuplicateGroupCount() {
        return duplicates.values().stream()
                .filter(paths -> paths.size() > 1)
                .count();
    }

    /**
     * Calculates the total number of duplicate files across all groups.
     * Only files in groups with more than one file are counted.
     *
     * @return The total number of duplicate files.
     */
    public long getTotalDuplicateFiles() {
        return duplicates.values().stream()
                .filter(paths -> paths.size() > 1)
                .mapToLong(List::size)
                .sum();
    }
}