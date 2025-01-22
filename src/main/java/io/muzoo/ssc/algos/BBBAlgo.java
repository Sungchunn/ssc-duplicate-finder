package io.muzoo.ssc.algos;

import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;

/**
 * The BBBAlgo class implements the Algorithms interface to perform a
 * byte-by-byte comparison of a file's contents. This algorithm determines
 * the consistency and basic characteristics of the file, such as its size.
 *
 * Responsibilities:
 * - Verify that the file exists and is not null.
 * - Compute a unique hash-like string based on the file's length and consistency.
 *
 * Features:
 * - Uses Apache Commons IO to perform a content consistency check.
 * - Throws meaningful exceptions for null or non-existent files.
 *
 * Example Usage:
 * BBBAlgo algo = new BBBAlgo();
 * String hash = algo.computerHash(new File("/path/to/file"));
 * System.out.println("BBB Hash: " + hash);
 */
public class BBBAlgo implements Algorithms {

    /**
     * Computes a hash-like string for the specified file. The hash is based on:
     * - The file's length.
     * - A consistency check using Apache Commons IO.
     *
     * @param file The file for which the hash is to be computed.
     * @return A string representing the file's length and consistency status.
     * @throws IOException If the file is null, does not exist, or cannot be read.
     */
    @Override
    public String computerHash(File file) throws IOException {
        if (file == null || !file.exists()) {
            throw new IOException("File does not exist or is null.");
        }

        long fileLength = file.length();
        boolean isConsistent = FileUtils.contentEquals(file, file);

        return fileLength + "-" + isConsistent;
    }
}