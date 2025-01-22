package io.muzoo.ssc.cli;

public interface IAlgorithmValidator {
    boolean isValid(String algorithm);
    void printUsage();
}
