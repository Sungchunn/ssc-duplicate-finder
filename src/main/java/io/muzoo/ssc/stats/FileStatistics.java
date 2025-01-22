package io.muzoo.ssc.stats;

import java.text.DecimalFormat;

/**
 * The FileStatistics class is responsible for collecting and managing statistics
 * about files and folders during a directory traversal process.
 *
 * Features:
 * - Tracks the total number of files and folders.
 * - Calculates the combined size of all files.
 * - Provides formatted statistics for display.
 * - Offers a reset method to clear statistics, allowing reuse.
 *
 * Example Usage:
 * FileStatistics stats = new FileStatistics();
 * stats.incrementFileCount();
 * stats.incrementFolderCount();
 * stats.incrementTotalFileSize(1024);
 * System.out.println(stats.getFormattedStatistics());
 *
 * Output:
 * Statistics:
 *  - Total Files: 1
 *  - Total Folders: 1
 *  - Total Size: 1,024 bytes
 *
 * Author: Your Name
 * Date: Jan 22, 2025
 */

public class FileStatistics {

    private long fileCount;
    private long folderCount;
    private long totalFileSize;

    // Increment methods
    /**
     * Increments the total file count by 1.
     * Adds the specified size (in bytes) to the total file size.
     * @param size The size of the file to be added to the total, in bytes.
     */
    public void incrementFileCount() { fileCount++; }
    public long getFileCount() { return fileCount; }

    /**
     * Increments the total folder count by 1.
     * Adds the specified size (in bytes) to the total folder size.
     * @param size The size of the file to be added to the total, in bytes.
     */
    public void incrementFolderCount() { folderCount++; }
    public long getFolderCount() { return folderCount; }

    /**
     * Returns the combined size of all files tracked, in bytes.
     * @return The total file size, in bytes.
     */
    public void incrementTotalFileSize(long size) { totalFileSize += size; }
    public long getTotalFileSize() { return totalFileSize; }

    /**
     * Returns a formatted string representation of the current statistics,
     * including the total file count, folder count, and total file size.
     *
     * Example Output:
     * Statistics:
     *  - Total Files: 1,000
     *  - Total Folders: 200
     *  - Total Size: 1,024,000 bytes
     *
     * @return A string containing the formatted statistics.
     */
    public String getFormattedStatistics() {
        DecimalFormat df = new DecimalFormat("#,###");
        return String.format("""
            Statistics:
             - Total Files: %s
             - Total Folders: %s
             - Total Size: %s bytes
            """,
                df.format(fileCount),
                df.format(folderCount),
                df.format(totalFileSize));
    }

    /**
     * Optional:
     * Resets all tracked statistics (file count, folder count, and total file size)
     * to zero, allowing the object to be reused for a new directory traversal.
     */
    public void reset() {
        fileCount = 0;
        folderCount = 0;
        totalFileSize = 0;
    }
}