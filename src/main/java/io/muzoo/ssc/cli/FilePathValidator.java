package io.muzoo.ssc.cli;
import java.nio.file.Files;
import java.nio.file.Path;

public class FilePathValidator implements IPathValidator {
    @Override
    public boolean isValid(String path) {
        return path != null && Files.exists(Path.of(path)) && Files.isDirectory(Path.of(path));
    }
}
