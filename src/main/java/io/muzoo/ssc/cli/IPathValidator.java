package io.muzoo.ssc.cli;

/**
 * The IPathValidator interface defines a contract for validating directory paths.
 * It ensures that paths provided by the user (e.g., via command-line arguments)
 * are valid and accessible, and can be used by the application for file processing.
 *
 * Responsibilities:
 * - Validate the syntax and structure of a given path.
 * - Check whether the specified path exists and is accessible.
 * - Provide a clean and simple method for validating paths.
 *
 * Use Cases:
 * - Verify that the folder path specified by the user exists and is readable.
 * - Prevent runtime errors caused by invalid or inaccessible paths.
 *
 * Example Usage:
 * IPathValidator pathValidator = new FilePathValidator();
 * boolean isValidPath = pathValidator.isValid("/path/to/folder");
 */

public interface IPathValidator {

    /**
     * Validates whether the given path is valid and accessible.
     *
     * @param path The directory path to validate.
     * @return `true` if the path is valid and accessible, otherwise `false`.
     */
    boolean isValid(String path);
}
