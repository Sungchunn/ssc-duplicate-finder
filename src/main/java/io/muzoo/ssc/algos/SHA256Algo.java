package io.muzoo.ssc.algos;

import io.muzoo.ssc.algos.Algorithms;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class SHA256Algo implements Algorithms {
    @Override
    public String computerHash(File file) throws IOException {
        try (FileInputStream fis = new FileInputStream(String.valueOf(file))) {
            return DigestUtils.sha256Hex(fis);
        }
    }
}
