package io.muzoo.ssc.stats;

import java.text.DecimalFormat;

/**
 * The FileStatistics class is responsible for collecting and managing statistics
 * about files and folders during a directory traversal process.
 *
 * Features:
 * - Tracks the total number of files and folders.
 * - Tracks the number of duplicate files and folders.
 * - Calculates the combined size of all files.
 * - Provides formatted statistics for display.
 * - Offers a reset method to clear statistics, allowing reuse.
 *
 * Example Usage:
 * FileStatistics stats = new FileStatistics();
 * stats.incrementFileCount();
 * stats.incrementFolderCount();
 * stats.incrementTotalFileSize(1024);
 * stats.incrementDuplicateFileCount();
 * stats.incrementDuplicateFolderCount();
 * System.out.println(stats.getFormattedStatistics());
 *
 * Output:
 * Statistics:
 *  - Total Files: 1
 *  - Total Folders: 1
 *  - Total Size: 1,024 bytes
 *  - Duplicate Files: 1
 *  - Duplicate Folders: 1
 */
public class FileStatistics {

    private long fileCount;
    private long folderCount;
    private long totalFileSize;
    private long duplicateFileCount;
    private long duplicateFolderCount;

    // Increment methods
    /**
     * Increments the total file count by 1.
     */
    public void incrementFileCount() {
        fileCount++;
    }

    public long getFileCount() {
        return fileCount;
    }

    /**
     * Increments the total folder count by 1.
     */
    public void incrementFolderCount() {
        folderCount++;
    }

    public long getFolderCount() {
        return folderCount;
    }

    /**
     * Adds the specified size (in bytes) to the total file size.
     * @param size The size of the file to be added to the total, in bytes.
     */
    public void incrementTotalFileSize(long size) {
        totalFileSize += size;
    }

    public long getTotalFileSize() {
        return totalFileSize;
    }

    /**
     * Increments the duplicate file count by 1.
     */
    public void incrementDuplicateFileCount() {
        duplicateFileCount++;
    }

    public long getDuplicateFileCount() {
        return duplicateFileCount;
    }

    /**
     * Increments the duplicate folder count by 1.
     */
    public void incrementDuplicateFolderCount() {
        duplicateFolderCount++;
    }

    public long getDuplicateFolderCount() {
        return duplicateFolderCount;
    }

    /**
     * Returns a formatted string representation of the current statistics,
     * including the total file count, folder count, total file size, and duplicate counts.
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
             - Duplicate Files: %s
             - Duplicate Folders: %s
            """,
                df.format(fileCount),
                df.format(folderCount),
                df.format(totalFileSize),
                df.format(duplicateFileCount),
                df.format(duplicateFolderCount));
    }

    /**
     * Resets all tracked statistics (file count, folder count, total file size, and duplicate counts)
     * to zero, allowing the object to be reused for a new directory traversal.
     */
    public void reset() {
        fileCount = 0;
        folderCount = 0;
        totalFileSize = 0;
        duplicateFileCount = 0;
        duplicateFolderCount = 0;
    }
}