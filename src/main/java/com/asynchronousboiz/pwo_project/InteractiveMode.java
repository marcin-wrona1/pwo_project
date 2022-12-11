package com.asynchronousboiz.pwo_project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Wprowadź 'q' aby zakończyć program, 'h' aby wyświetlić pomoc");
        boolean loop = true;
        while (loop) {
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
                    case 'l':
                    case 'w':
                    case 'c':
                    case 'C':
                    case 'b':
                    case 'm':
                        System.out.println("Polecenie nie zaimplementowane");
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
