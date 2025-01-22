package io.muzoo.ssc.cli;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * The FilePathValidator class implements the IPathValidator interface
 * to validate directory paths provided by the user. It ensures that the
 * specified path exists, is accessible, and points to a valid directory.
 *
 * Responsibilities:
 * - Verify that the given path is not null.
 * - Check if the specified path exists in the file system.
 * - Ensure that the path points to a directory and not a file.
 *
 * Features:
 * - Leverages Java's NIO API (java.nio.file) for file system validation.
 * - Returns a simple boolean result for easy integration with other components.
 *
 * Example Usage:
 * FilePathValidator validator = new FilePathValidator();
 * boolean isValid = validator.isValid("/path/to/folder");
 * if (isValid) {
 *     System.out.println("Valid folder path.");
 * } else {
 *     System.err.println("Invalid folder path.");
 * }
 *
 */

public class FilePathValidator implements IPathValidator {

    /**
     * Validates whether the given path is a valid directory.
     *
     * Criteria for a valid directory:
     * - The path is not null.
     * - The path exists in the file system.
     * - The path refers to a directory, not a file.
     *
     * @param path The directory path to validate.
     * @return `true` if the path is valid, exists, and is a directory; otherwise, `false`.
     */
    @Override
    public boolean isValid(String path) {
        return path != null && Files.exists(Path.of(path)) && Files.isDirectory(Path.of(path));
    }
}
