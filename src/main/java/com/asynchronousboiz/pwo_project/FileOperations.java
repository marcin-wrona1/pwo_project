package com.asynchronousboiz.pwo_project;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 *
 * @author Krzysztof Zarębski
 */
public class FileOperations {
    /**
     * Kopiuje plik z podanej ścieżki źródłowej do podanej ścieżki docelowej.
     *
     * @param from ścieżka źródłowa
     * @param to ścieżka docelowa
     * @throws IllegalArgumentException Podany parametr jest nieprawidłowy
     * @throws IOException Błąd dostępu do podanej ścieżki
     */
    public static void copyFile(String from, String to) throws IllegalArgumentException, IOException {
        if (from == null || to == null) {
            throw new IllegalArgumentException("Nie podano ścieżki źródłowej lub docelowej");
        }

        Path from_path = Paths.get(from);
        if (!Files.isRegularFile(from_path)) {
            throw new IllegalArgumentException("Podano nieprawidłowy typ pliku");
        }

        Path to_path = Paths.get(to);
        if (Files.exists(to_path)) {
            throw new IllegalArgumentException("Plik docelowy już istnieje");
        }

        Files.copy(from_path, to_path, StandardCopyOption.REPLACE_EXISTING);
    }
}
