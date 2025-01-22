package io.muzoo.ssc;

import java.nio.file.Path;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

public class DuplicateReport {
    private final Map<String, List<Path>> duplicates;

    public DuplicateReport(Map<String, List<Path>> duplicates) {
        this.duplicates = duplicates;
    }

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

    private void appendDetailedDuplicateInfo(StringBuilder report) {
        duplicates.forEach((hash, paths) -> {
            if (paths.size() > 1) {
                report.append("================================================================\n")
                        .append("Duplicate group (size: ").append(paths.size()).append("):\n");
                paths.forEach(path -> report.append(" - ").append(path).append("\n"));
            }
        });
    }

    private long getDuplicateGroupCount() {
        return duplicates.values().stream()
                .filter(paths -> paths.size() > 1)
                .count();
    }

    private long getTotalDuplicateFiles() {
        return duplicates.values().stream()
                .filter(paths -> paths.size() > 1)
                .mapToLong(List::size)
                .sum();
    }
}