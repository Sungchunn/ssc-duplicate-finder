package io.muzoo.ssc.stats;

import java.text.DecimalFormat;

public class FileStatistics {
    private long fileCount;
    private long folderCount;
    private long totalFileSize;

    // Methods
    public void incrementFileCount() { fileCount++; }
    public void incrementFolderCount() { folderCount++; }
    public void incrementTotalFileSize(long size) { totalFileSize += size; }

    public String getFormattedStatistics() {
        DecimalFormat df = new DecimalFormat("#,###");
        return String.format("""
            Statistics:
             - Total Files: %s
             - Total Folders: %s
             - Total Size: %s bytes""",
                df.format(fileCount),
                df.format(folderCount),
                df.format(totalFileSize));
    }
}
