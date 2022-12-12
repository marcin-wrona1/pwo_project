package com.asynchronousboiz.pwo_project;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author Krzysztof Zarębski
 * @author Marcin Wrona
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

    /**
     * Przenosi plik z podanej ścieżki źródłowej do podanej ścieżki docelowej.
     *
     * @param from ścieżka źródłowa
     * @param to ścieżka docelowa
     * @throws IllegalArgumentException Podany parametr jest nieprawidłowy
     * @throws IOException Błąd dostępu do podanej ścieżki
     */
    public static void moveFile(String from, String to) throws IllegalArgumentException, IOException {
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

        Files.move(from_path, to_path, StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
    }

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

        Path path = Paths.get(filepath).toRealPath();
        if (!Files.isDirectory(path)) {
            throw new IllegalArgumentException("Podano nieprawidłowy typ pliku");
        }

        String parentPath = path.toAbsolutePath().normalize().toString();
        Stream<Path> paths = Files.walk(path, 1)
                .filter(it -> !parentPath.equals(it.toAbsolutePath().normalize().toString()));
        return paths.collect(Collectors.toList());
    }
}
