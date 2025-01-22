package io.muzoo.ssc.algos;

import org.apache.commons.codec.digest.DigestUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * The MD5Algo class implements the Algorithms interface to compute
 * the MD5 hash of a given file. It uses Apache Commons Codec to perform
 * the hashing operation.
 *
 * Responsibilities:
 * - Read the contents of a file.
 * - Generate an MD5 hash from the file's contents.
 *
 * Features:
 * - Ensures efficient hashing using a FileInputStream.
 * - Automatically closes the file input stream using try-with-resources.
 *
 * Example Usage:
 * MD5Algo algo = new MD5Algo();
 * String hash = algo.computerHash(new File("/path/to/file"));
 * System.out.println("MD5 Hash: " + hash);
 */

public class MD5Algo implements Algorithms {

    /**
     * Computes the MD5 hash of the specified file.
     *
     * @param file The file for which the hash is to be computed.
     * @return A string representing the computed MD5 hash.
     * @throws IOException If an I/O error occurs while reading the file.
     */
    @Override
    public String computerHash(File file) throws IOException {
        try (FileInputStream fis = new FileInputStream(file)) {
            return DigestUtils.md5Hex(fis);
        }
    }
}
