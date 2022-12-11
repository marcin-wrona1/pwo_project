package com.asynchronousboiz.pwo_project;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author Jakub Kozłowski
 * @author Albert Strzyżewski
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

    /**
     * Pobiera liczbę linjii w pliku podanym jako parametr.
     *
     * @param filepath ścieżka do pliku
     * @return liczba linjii w pliku
     * @throws IllegalArgumentException Jeśli podany parametr jest nieprawidłowy
     * @throws IOException Jeśli wystąpił błąd dostępu do podanej ścieżki
     */
    public static long lineCount(String filepath) throws IllegalArgumentException, IOException {
        if (filepath == null) {
            throw new IllegalArgumentException("Nie podano ścieżki");
        }

        Path path = Paths.get(filepath);
        if (!Files.isRegularFile(path)) {
            throw new IllegalArgumentException("Podano nieprawidłowy typ pliku");
        }

        return Files.lines(path).count();
    }

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
            if (line.length() < 1) {
                continue;
            }
            String[] words = line.split(" ");
            ret += words.length;
        }

        return ret;
    }
}
