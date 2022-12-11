package com.asynchronousboiz.pwo_project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 *
 * @author Piotr Tokarski
 */
public class InteractiveMode {
    /**
     * Wypisuje w konsoli listę wszystkich dozwolonych poleceń trybu interaktywnego.
     */
    private static void printHelp() {
        System.out.println(
            "Dostępne polecenia:\n\n" +
            "h - wyświetla tą wiadomość\n\n" +
            "Statystyki plików:\n" +
            "s - wyświetla rozmiar pliku\n" +
            "l - wyświetla ilość linii w pliku\n" +
            "w - wyświetla ilość słów w pliku\n" +
            "c - wyświetla ilość znaków w pliku\n\n" +
            "Operacje plikowe:\n" +
            "C - wyświetla zawartość katalogu\n" +
            "b - wykonuje kopię pliku\n" +
            "m - przenosi plik"
        );
    }

    /**
     * Wypisuje w konsoli wiadomość o podaniu nieprawidłowego polecenia.
     */
    private static void printInvalidInputMessage() {
        System.out.println("Nieprawidłowe polecenie; wprowadź 'h' aby wyświetlić pomoc");
    }

    /**
     * Uruchamia tryb interaktywny.
     *
     * Tryb interaktywny pozwala na interaktywne wykonywanie poszczególnych funkcji programu.
     */
    public static void interactiveMain() {
        List<Path> content;
        String infile;
        String outfile;

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Wprowadź 'q' aby zakończyć program");
        boolean loop = true;
        while (loop) {
            content = null;
            infile = null;
            outfile = null;

            try {
                System.out.print("> ");
                String input = reader.readLine().trim();
                if (input.length() != 1) {
                    printInvalidInputMessage();
                    continue;
                }
                char option = input.charAt(0);
                switch (option) {
                    case 'h':
                        printHelp();
                        break;
                    case 'q':
                        loop = false;
                        break;

                    case 's':
                        System.out.print("Ścieżka do pliku: ");
                        try {
                            System.out.println("Rozmiar pliku: " + FileStats.fileSize(reader.readLine().trim()));
                        } catch (IllegalArgumentException e) {
                            System.out.println("Podano nieprawidłową ścieżkę");
                        } catch (IOException e) {
                            System.out.println("Błąd wejścia/wyjścia");
                        }
                        break;
                    case 'l':
                        System.out.print("Ścieżka do pliku: ");
                        try {
                            System.out.println("Ilość linii w pliku: " + FileStats.lineCount(reader.readLine().trim()));
                        } catch (IllegalArgumentException e) {
                            System.out.println("Podano nieprawidłową ścieżkę");
                        } catch (IOException e) {
                            System.out.println("Błąd wejścia/wyjścia");
                        }
                        break;
                    case 'w':
                        System.out.print("Ścieżka do pliku: ");
                        try {
                            System.out.println("Ilość słów w pliku: " + FileStats.wordCount(reader.readLine().trim()));
                        } catch (IllegalArgumentException e) {
                            System.out.println("Podano nieprawidłową ścieżkę");
                        } catch (IOException e) {
                            System.out.println("Błąd wejścia/wyjścia");
                        }
                        break;
                    case 'c':
                        System.out.print("Ścieżka do pliku: ");
                        try {
                            System.out.println("Ilość znaków w pliku: " + FileStats.characterCount(reader.readLine().trim()));
                        } catch (IllegalArgumentException e) {
                            System.out.println("Podano nieprawidłową ścieżkę");
                        } catch (IOException e) {
                            System.out.println("Błąd wejścia/wyjścia");
                        }
                        break;

                    case 'C':
                        System.out.print("Ścieżka do katalogu: ");
                        try {
                            content = FileOperations.directoryContent(reader.readLine().trim());
                        } catch (IllegalArgumentException e) {
                            System.out.println("Podano nieprawidłową ścieżkę");
                            continue;
                        } catch (IOException e) {
                            System.out.println("Błąd wejścia/wyjścia");
                            continue;
                        }
                        for (Path file : content) {
                            Path filename = file.getFileName();
                            if (filename == null) {
                                continue;
                            }
                            String name = filename.toString();

                            String header = "[" + (
                                Files.isSymbolicLink(file) ? "l"
                                : Files.isDirectory(file) ? "d"
                                : Files.isRegularFile(file) ? "f"
                                : "?"
                            ) + "]";

                            System.out.println(header + " " + name);
                        }
                        break;
                    case 'b':
                        System.out.print("Ścieżka do pliku źródłowego: ");
                        infile = reader.readLine().trim();
                        System.out.print("Ścieżka do pliku docelowego: ");
                        outfile = reader.readLine().trim();
                        try {
                            FileOperations.copyFile(infile, outfile);
                        } catch (IllegalArgumentException e) {
                            System.out.println("Podano nieprawidłową ścieżkę");
                            continue;
                        } catch (IOException e) {
                            System.out.println("Błąd wejścia/wyjścia");
                            continue;
                        }
                        System.out.println("Plik został skopiowany");
                        break;
                    case 'm':
                        System.out.print("Ścieżka do pliku źródłowego: ");
                        infile = reader.readLine().trim();
                        System.out.print("Ścieżka do pliku docelowego: ");
                        outfile = reader.readLine().trim();
                        try {
                            FileOperations.moveFile(infile, outfile);
                        } catch (IllegalArgumentException e) {
                            System.out.println("Podano nieprawidłową ścieżkę");
                            continue;
                        } catch (IOException e) {
                            System.out.println("Błąd wejścia/wyjścia");
                            continue;
                        }
                        System.out.println("Plik został przeniesiony");
                        break;
                    default:
                        printInvalidInputMessage();
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
    }
}
