package io.muzoo.ssc.cli;

/**
 * The IAlgorithmValidator interface defines a contract for validating algorithms
 * used for file hashing or duplicate detection. It ensures that only supported
 * algorithms are accepted and provides guidance on how to use the application.
 *
 * Use Cases:
 * - Validate user-specified algorithms like "md5", "sha256", or "bbb".
 * - Print usage instructions if an invalid algorithm is provided.
 *
 * Example Usage:
 * IAlgorithmValidator validator = new AlgorithmValidator();
 * if (!validator.isValid("sha256")) {
 *     validator.printUsage();
 * }
 *
 */
public interface IAlgorithmValidator {
    /**
     * Validates if the specified algorithm is supported.
     *
     * @param algorithm The name of the algorithm to validate (e.g., "md5", "sha256").
     * @return `true` if the algorithm is valid, otherwise `false`.
     */
    boolean isValid(String algorithm);

    /**
     * Prints usage instructions to guide the user on valid algorithms and usage options.
     * Typically called when an invalid algorithm is provided.
     */
    void printUsage();
}
