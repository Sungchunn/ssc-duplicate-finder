package io.muzoo.ssc;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

public class HashUtils {

    private static final String ALGORITHM_SHA256 = "sha256";
    private static final String ALGORITHM_MD5 = "md5";
    private static final String ALGORITHM_BBB = "bbb";

    public static String computeHash(File file, String algorithm) throws IOException {
        switch (algorithm.toLowerCase()) {
            case ALGORITHM_SHA256:
                return computeSHA256Hash(file);
            case ALGORITHM_MD5:
                return computeMD5Hash(file);
            case ALGORITHM_BBB:
                return computeBBBHash(file);
            default:
                throw new IllegalArgumentException("Unsupported algorithm: " + algorithm);
        }
    }

    private static String computeSHA256Hash(File file) throws IOException {
        try (FileInputStream fis = new FileInputStream(file)) {
            return DigestUtils.sha256Hex(fis);
        }
    }

    private static String computeMD5Hash(File file) throws IOException {
        try (FileInputStream fis = new FileInputStream(file)) {
            return DigestUtils.md5Hex(fis);
        }
    }

    private static String computeBBBHash(File file) throws IOException {
        // byte-by-byte comparison base on entire file content
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] content = fis.readAllBytes();
            return String.valueOf(Arrays.hashCode(content));
        }
    }
}