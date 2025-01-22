package io.muzoo.ssc.algos;

import io.muzoo.ssc.algos.Algorithms;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * The SHA256Algo class implements the Algorithms interface to compute
 * the SHA-256 hash of a given file. It uses Apache Commons Codec to
 * perform the hashing operation.
 *
 * Responsibilities:
 * - Read the contents of a file.
 * - Generate a SHA-256 hash from the file's contents.
 *
 * Features:
 * - Ensures efficient hashing using a FileInputStream.
 * - Automatically closes the file input stream using try-with-resources.
 *
 * Example Usage:
 * SHA256Algo algo = new SHA256Algo();
 * String hash = algo.computerHash(new File("/path/to/file"));
 * System.out.println("SHA-256 Hash: " + hash);
 */
public class SHA256Algo implements Algorithms {

    /**
     * Computes the SHA-256 hash of the specified file.
     *
     * @param file The file for which the hash is to be computed.
     * @return A string representing the computed SHA-256 hash.
     * @throws IOException If an I/O error occurs while reading the file.
     */
    @Override
    public String computerHash(File file) throws IOException {
        try (FileInputStream fis = new FileInputStream(String.valueOf(file))) {
            return DigestUtils.sha256Hex(fis);
        }
    }
}
