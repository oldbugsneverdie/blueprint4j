package com.blueprint4j.core.util;

import java.io.File;
import java.io.IOException;

/**
 * Created by jan on 29-6-14.
 */
public class FileUtils {

    public static String fileSeparator = System.getProperty("file.separator");

    public static File createSubDirectory(File baseDirectory, String subDirectoryName) {

        validateIsDirectory(baseDirectory);

        if (subDirectoryName == null) {
            throw new RuntimeException("Subdirectory name can not be null");
        }
        if (subDirectoryName.isEmpty()) {
            throw new RuntimeException("Subdirectory name can not be empty");
        }
        try {
            return createSub(baseDirectory, subDirectoryName);
        } catch (IOException e) {
            throw new RuntimeException("Could not create sub directory with name: " + subDirectoryName
                    + ", in directory: " + baseDirectory.getAbsolutePath(), e);
        }
    }

    public static void validateIsDirectory(File baseDirectory) {
        if (baseDirectory == null) {
            throw new RuntimeException("Base directory can not be null");
        }
        if (!baseDirectory.isDirectory()) {
            throw new RuntimeException("Base directory is not a directory: " + baseDirectory);
        }
    }

    private static File createSub(File outputDirectory, String subDirectoryName) throws IOException {
        String currentPath = outputDirectory.getCanonicalPath();
        String subDirectoryPath = currentPath + fileSeparator + subDirectoryName;
        File subDirectory = new File(subDirectoryPath);
        if (!subDirectory.exists()) {
            if (!subDirectory.mkdir()) {
                throw new IOException("Could not create directory '" + subDirectoryPath);
            }
        }
        return subDirectory;
    }

}