package io.muzoo.ssc.cli;

import java.util.Set;

/**
 * The AlgorithmValidator class implements the IAlgorithmValidator interface
 * to validate the hashing algorithms specified by the user. It ensures that
 * only supported algorithms are accepted and provides a usage guide to inform
 * users about valid options.
 *
 * Responsibilities:
 * - Verify if a specified algorithm is among the supported algorithms.
 * - Provide a list of supported algorithms when requested.
 *
 * Features:
 * - Maintains a static set of supported algorithms (e.g., "sha256", "md5", "bbb").
 * - Offers a simple method to check algorithm validity.
 * - Prints usage instructions to guide users.
 *
 * Example Usage:
 * AlgorithmValidator validator = new AlgorithmValidator();
 * if (validator.isValid("sha256")) {
 *     System.out.println("Valid algorithm.");
 * } else {
 *     System.err.println("Invalid algorithm.");
 *     validator.printUsage();
 * }
 *
 */
public class AlgorithmValidator implements IAlgorithmValidator {
    private static final Set<String> SUPPORTED_ALGORITHMS = Set.of("sha256", "md5", "bbb");

    /**
     * Checks whether the specified algorithm is valid.
     *
     * An algorithm is considered valid if it exists in the predefined set
     * of supported algorithms.
     *
     * @param algorithm The name of the algorithm to validate (e.g., "sha256", "md5", "bbb").
     * @return `true` if the algorithm is valid, otherwise `false`.
     */
    @Override
    public boolean isValid(String algorithm) {
        return SUPPORTED_ALGORITHMS.contains(algorithm);
    }

    /**
     * Prints a usage message displaying the supported hashing algorithms.
     *
     * Example Output:
     * Supported algorithms: sha256, md5, bbb
     */
    @Override
    public void printUsage() {
        System.out.println("Supported algorithms: " + String.join(", ", SUPPORTED_ALGORITHMS));
    }
}
