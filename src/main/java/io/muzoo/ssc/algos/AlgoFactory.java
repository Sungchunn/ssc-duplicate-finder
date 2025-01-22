package io.muzoo.ssc.algos;

import java.util.Map;
import java.util.HashMap;

public class AlgoFactory {
    private static final Map<String, Class<? extends Algorithms>> algoMap = new HashMap<>();

    static {
        algoMap.put("sha256", SHA256Algo.class);
        algoMap.put("md5", MD5Algo.class);
        algoMap.put("bbb", BBBAlgo.class);
    }

    public static Algorithms createAlgo(String algoName) {
        try {
            return algoMap.get(algoName.toLowerCase()).getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new IllegalArgumentException("Unsupported algorithm: " + algoName, e);
        }
    }
}