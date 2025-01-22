package io.muzoo.ssc;

import io.muzoo.ssc.algos.Algorithms;
import io.muzoo.ssc.stats.FileStatistics;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileVisitorService {
    private final Algorithms hashStrategy;
    private final FileStatistics statistics;
    private final Map<String, List<Path>> hashToFileMap;

    public FileVisitorService(Algorithms hashStrategy) {
        this.hashStrategy = hashStrategy;
        this.statistics = new FileStatistics();
        this.hashToFileMap = new HashMap<>();
    }

    public void visitDirectory(String folderPath) throws IOException {
        Files.walkFileTree(Paths.get(folderPath), new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                statistics.incrementFolderCount();
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                statistics.incrementFileCount();
                statistics.incrementTotalFileSize(Files.size(file));

                String hash = hashStrategy.computerHash(file.toFile());
                hashToFileMap.computeIfAbsent(hash, k -> new ArrayList<>()).add(file);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    public FileStatistics getStatistics() {
        return statistics;
    }

    public Map<String, List<Path>> getHashToFileMap() {
        return hashToFileMap;
    }
}