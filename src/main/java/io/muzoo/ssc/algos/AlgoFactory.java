package io.muzoo.ssc.algos;

import java.util.Map;
import java.util.HashMap;


/**
 * The AlgoFactory class is responsible for creating instances of hashing algorithms
 * based on a user-specified algorithm name. It implements the factory design pattern
 * to provide flexibility and extensibility for adding new algorithms.
 *
 * Responsibilities:
 * - Maintain a mapping between algorithm names and their corresponding classes.
 * - Instantiate the appropriate algorithm class based on the user input.
 * - Handle unsupported algorithm names by throwing meaningful exceptions.
 *
 * Features:
 * - Supports multiple algorithms (e.g., "sha256", "md5", "bbb").
 * - Easily extensible by adding new algorithm mappings to the `algoMap`.
 *
 * Example Usage:
 * Algorithms algo = AlgoFactory.createAlgo("sha256");
 * String hash = algo.computerHash(new File("/path/to/file"));
 *
 */

public class AlgoFactory {
    // A mapping of algorithm names to their corresponding classes
    private static final Map<String, Class<? extends Algorithms>> algoMap = new HashMap<>();

    // Static initializer block to populate the map with supported algorithms
    static {
        algoMap.put("sha256", SHA256Algo.class);
        algoMap.put("md5", MD5Algo.class);
        algoMap.put("bbb", BBBAlgo.class);
    }


    /**
     * Creates an instance of the specified hashing algorithm.
     *
     * @param algoName The name of the hashing algorithm (e.g., "sha256", "md5", "bbb").
     * @return An instance of the corresponding `Algorithms` implementation.
     * @throws IllegalArgumentException If the specified algorithm is not supported.
     */
    public static Algorithms createAlgo(String algoName) {
        try {
            return algoMap.get(algoName.toLowerCase()).getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new IllegalArgumentException("Unsupported algorithm: " + algoName, e);
        }
    }
}