package com.asynchronousboiz.pwo_project;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author Marcin Wrona
 */
public class FileOperations {
    /**
     * Pobiera listę plików w podanym katalogu.
     *
     * @param filepath ścieżka do katalogu, którego zawartość sprawdzamy
     * @return lista plików i katalogów zawartych w podanym katalogu
     * @throws IllegalArgumentException kiedy podany parametr jest niepoprawny
     * @throws IOException kiedy wystąpi błąd dostępu do katalogu lub któregokolwiek z elementów jego zawartości
     */
    public static List<Path> directoryContent(String filepath) throws IllegalArgumentException, IOException {
        if (filepath == null) {
            throw new IllegalArgumentException("Nie podano ścieżki");
        }

        Path path = Paths.get(filepath);
        if (!Files.isDirectory(path)) {
            throw new IllegalArgumentException("Podano nieprawidłowy typ pliku");
        }

        Stream<Path> paths = Files.walk(path, 1);
        return paths.collect(Collectors.toList());
    }
}
