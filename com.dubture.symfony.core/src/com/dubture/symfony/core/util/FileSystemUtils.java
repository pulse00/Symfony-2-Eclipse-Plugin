package com.dubture.symfony.core.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public final class FileSystemUtils {

    private FileSystemUtils() {
        // Cannot instantiate
    }

    public static void copyDirectory(File sourceDirectory, File destinationDirectory) throws IOException {
        FileUtils.copyDirectory(sourceDirectory, destinationDirectory);
    }

}
