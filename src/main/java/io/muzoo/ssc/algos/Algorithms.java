package io.muzoo.ssc.algos;

import java.io.File;
import java.io.IOException;


/**
 * The Algorithms interface defines the contract for implementing
 * different file hashing algorithms. Implementations of this interface
 * are responsible for generating unique hash values for files, which can
 * be used to detect duplicates.
 *
 * Responsibilities:
 * - Compute a hash for a given file using a specific algorithm.
 * - Handle any I/O exceptions that may occur during file processing.
 *
 * Use Cases:
 * - Hash files to identify duplicates in a directory.
 * - Integrate with a factory pattern to support multiple hashing algorithms.
 *
 * Example Usage:
 * Algorithms algo = new SHA256Algo();
 * String hash = algo.computerHash(new File("/path/to/file"));
 */
public interface Algorithms {
    /**
     * Computes a hash value for the specified file.
     *
     * @param file The file for which the hash is to be computed.
     * @return A string representing the computed hash value.
     * @throws IOException If an I/O error occurs while reading the file.
     */
    String computerHash(File file) throws IOException;
}


