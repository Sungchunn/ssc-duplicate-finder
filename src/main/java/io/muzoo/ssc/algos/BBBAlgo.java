package io.muzoo.ssc.algos;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

public class BBBAlgo implements Algorithms {
    @Override
    public String computerHash(File file) throws IOException {
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] content = fis.readAllBytes();
            return String.valueOf(Arrays.hashCode(content));
        }
    }
}
