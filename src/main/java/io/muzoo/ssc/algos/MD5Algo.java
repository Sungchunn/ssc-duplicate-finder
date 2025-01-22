package io.muzoo.ssc.algos;

import org.apache.commons.codec.digest.DigestUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MD5Algo implements Algorithms {

    @Override
    public String computerHash(File file) throws IOException {
        try (FileInputStream fis = new FileInputStream(file)) {
            return DigestUtils.md5Hex(fis);
        }
    }
}
