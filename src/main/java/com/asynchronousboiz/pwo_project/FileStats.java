package com.asynchronousboiz.pwo_project;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author Jakub Kozłowski
 */
public class FileStats {
    /**
     * Pobiera rozmiar pliku podanego jako parametr.
     *
     * @param filepath ścieżka do pliku
     * @return rozmiar pliku
     * @throws IllegalArgumentException Jeśli podany parametr jest nieprawidłowy
     * @throws IOException Jeśli wystąpił błąd dostępu do podanej ścieżki
     */
    public static long fileSize(String filepath) throws IllegalArgumentException, IOException {
        if (filepath == null) {
            throw new IllegalArgumentException("Nie podano ścieżki");
        }

        Path path = Paths.get(filepath);
        if (!Files.isRegularFile(path)) {
            throw new IllegalArgumentException("Podano nieprawidłowy typ pliku");
        }

        return Files.size(path);
    }
}
