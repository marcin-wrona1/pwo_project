package com.asynchronousboiz.pwo_project;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author Albert Strzyżewski
 */
public class FileStats {
    /**
     * Pobiera ilość znaków w podanym pliku.
     *
     * @param filepath ścieżka do pliku
     * @return ilość znaków w podanym pliku
     * @throws IllegalArgumentException Jeśli podano nieprawidłowy parametr
     * @throws IOException Jeśli wystąpi błąd przy dostępie do pliku
     */
    public static long characterCount(String filepath) throws IllegalArgumentException, IOException {
        if (filepath == null) {
            throw new IllegalArgumentException("Nie podano ścieżki");
        }

        Path path = Paths.get(filepath);
        if (!Files.isRegularFile(path)) {
            throw new IllegalArgumentException("Podano nieprawidłowy typ pliku");
        }

        long ret = 0;

        BufferedReader br = Files.newBufferedReader(path);
        String line;
        while ((line = br.readLine()) != null) {
            ret += line.length();
        }

        return ret;
    }

    /**
     * Pobiera ilość słów w podanym pliku.
     *
     * @param filepath ścieżka do pliku
     * @return ilość słów w podanym pliku
     * @throws IllegalArgumentException Jeśli podano nieprawidłowy parametr
     * @throws IOException Jeśli wystąpi błąd przy dostępie do pliku
     */
    public static long wordCount(String filepath) throws IllegalArgumentException, IOException {
        if (filepath == null) {
            throw new IllegalArgumentException("Nie podano ścieżki");
        }

        Path path = Paths.get(filepath);
        if (!Files.isRegularFile(path)) {
            throw new IllegalArgumentException("Podano nieprawidłowy typ pliku");
        }

        long ret = 0;

        BufferedReader br = Files.newBufferedReader(path);
        String line;
        while ((line = br.readLine()) != null) {
            String[] words = line.split(" ");
            ret += words.length;
        }

        return ret;
    }
}
