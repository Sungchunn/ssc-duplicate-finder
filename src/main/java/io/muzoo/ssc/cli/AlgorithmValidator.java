package io.muzoo.ssc.cli;

import java.util.Set;

public class AlgorithmValidator implements IAlgorithmValidator {
    private static final Set<String> SUPPORTED_ALGORITHMS = Set.of("sha256", "md5", "bbb");

    @Override
    public boolean isValid(String algorithm) {
        return SUPPORTED_ALGORITHMS.contains(algorithm);
    }

    @Override
    public void printUsage() {
        System.out.println("Supported algorithms: " + String.join(", ", SUPPORTED_ALGORITHMS));
    }
}
