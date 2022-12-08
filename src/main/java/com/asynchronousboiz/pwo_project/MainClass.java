package com.asynchronousboiz.pwo_project;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;

/**
 *
 * @author Marcin Wrona
 */
public class MainClass {
    private static final String[] all_actions = {
        "h",
        "s",
        "l",
        "w",
        "c",
        "C",
        "b",
        "m",
        "i"
    };

    private static Options getOptions() {
        Options opts = new Options();

        // wyświetlanie pomocy
        opts.addOption(
            Option.builder("h")
                .longOpt("help")
                .required(false)
                .desc("Wyświetl tą wiadomość")
                .build()
        );

        // statystyki plików
        // ilość parametrów dowolna, obliczymy dla każdego z osobna
        opts.addOption(
            Option.builder("s")
                .longOpt("size")
                .required(false)
                .desc("Wyświetl rozmiar pliku lub plików")
                .hasArg()
                .build()
        );
        opts.addOption(
            Option.builder("l")
                .longOpt("line-count")
                .required(false)
                .desc("Wyświetl ilość linii w pliku lub plikach")
                .build()
        );
        opts.addOption(
            Option.builder("w")
                .longOpt("word-count")
                .required(false)
                .desc("Wyświetl ilość słów w pliku lub plikach")
                .build()
        );
        opts.addOption(
            Option.builder("c")
                .longOpt("character-count")
                .required(false)
                .desc("Wyświetl ilość znaków w pliku lub plikach")
                .build()
        );

        // operacje plikowe
        opts.addOption(
            Option.builder("C")
                .longOpt("content")
                .required(false)
                .desc("Wyświetl zawartość katalogu")
                .build()
        );
        opts.addOption(
            Option.builder("b")
                .longOpt("backup")
                .required(false)
                .desc("Wykonaj kopię pliku")
                .hasArgs()
                .numberOfArgs(2)
                .build()
        );
        opts.addOption(
            Option.builder("m")
                .longOpt("move")
                .required(false)
                .desc("Przenieś lub zmień nazwę pliku")
                .hasArgs()
                .numberOfArgs(2)
                .build()
        );

        // interaktywny interfejs
        opts.addOption(
            Option.builder("i")
                .longOpt("interactive")
                .required(false)
                .desc("Uruchom interaktywny interfejs")
                .build()
        );

        return opts;
    }

    private static CommandLine getArgs(String[] args) {
        final Options opts = getOptions();

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = null;
        try {
            cmd = parser.parse(opts, args);
            if (cmd == null) {
                throw new Exception();
            }
        } catch (Exception e) {
            // ignoruj
        }

        return cmd;
    }

    private static void check_opt_conflict(CommandLine cmd, String opt, String[] conflicting_ops) {
        for (String conflicting_opt : conflicting_ops) {
            if (cmd.hasOption(conflicting_opt)) {
                System.err.println("Podano niezgodne parametry: '-" + opt + "' oraz '-" + opt + "'");
                System.exit(1);
            }
        }
    }

    private static void print_help() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("pwo_project", getOptions());
    }

    public static void main (String[] argv) throws IOException {
        final CommandLine cmd = getArgs(argv);
        if (cmd == null) {
            System.err.println("Podano nieprawidłowe parametry");
            System.exit(1);
        }
        String[] args = cmd.getArgs();

        // Priorytetyzujemy parametry

        if (cmd.hasOption("h")) {
            print_help();
            System.exit(0);
        }

        // 1. interaktywny interfejs
        if (cmd.hasOption("i")) {
            String[] conflicting_ops = Arrays.asList(all_actions)
                .stream()
                // wszystkie pozostałe opcje nie są dozwolone
                .filter(opt -> !opt.equals("i"))
                .toArray(String[]::new);
            check_opt_conflict(cmd, "i", conflicting_ops);

            System.out.println("Uruchamianie interaktywnego interfejsu...");
            InteractiveMode.interactiveMain();
            System.exit(0);
        }

        // 2. operacje plikowe (z jednym lub dwoma parametrami bezpośrednio
        //    po parametrze wybierającym operację)
        if (cmd.hasOption("C")) {
            String[] conflicting_ops = Arrays.asList(all_actions)
                .stream()
                    // wszystkie pozostałe opcje nie są dozwolone
                .filter(opt -> !opt.equals("C"))
                .toArray(String[]::new);
            check_opt_conflict(cmd, "C", conflicting_ops);

            if (args.length < 1) {
                System.err.println("Nie podano katalogu do wyświetlenia");
                System.exit(1);
            }

            List<Path> content = null;
            try {
                content = FileOperations.directoryContent(args[0]);
            } catch (IllegalArgumentException ex) {
                System.err.println("Podano nieprawidłowy parametr");
                System.exit(1);
            } catch (IOException ex) {
                System.err.println("Błąd wejścia/wyjścia");
                System.exit(1);
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
            
            System.exit(0);
        }

        if (cmd.hasOption("b")) {
            String[] conflicting_ops = Arrays.asList(all_actions)
                .stream()
                    // wszystkie pozostałe opcje nie są dozwolone
                .filter(opt -> !opt.equals("b"))
                .toArray(String[]::new);
            check_opt_conflict(cmd, "b", conflicting_ops);

            String[] filenames = cmd.getOptionValues("b");
            try {
                FileOperations.copyFile(filenames[0], filenames[1]);
            } catch (IllegalArgumentException ex) {
                System.err.println("Podano nieprawidłowy parametr");
                System.exit(1);
            } catch (IOException ex) {
                System.err.println("Błąd wejścia/wyjścia");
                System.exit(1);
            }
            System.out.println("Plik został skopiowany");
            System.exit(0);
        }

        if (cmd.hasOption("m")) {
            String[] conflicting_ops = Arrays.asList(all_actions)
                .stream()
                    // wszystkie pozostałe opcje nie są dozwolone
                .filter(opt -> !opt.equals("m"))
                .toArray(String[]::new);
            check_opt_conflict(cmd, "m", conflicting_ops);

            String[] filenames = cmd.getOptionValues("m");
            try {
                FileOperations.moveFile(filenames[0], filenames[1]);
            } catch (IllegalArgumentException ex) {
                System.err.println("Podano nieprawidłowy parametr");
                System.exit(1);
            } catch (IOException ex) {
                System.err.println("Błąd wejścia/wyjścia");
                System.exit(1);
            }
            System.out.println("Plik został przeniesiony");
            System.exit(0);
        }

        // 3. obliczanie statystyk - ilość parametrów dowolna
        if (cmd.hasOption("s")) {
            String[] conflicting_ops = Arrays.asList(all_actions)
                .stream()
                    // wszystkie pozostałe opcje nie są dozwolone
                .filter(opt -> !opt.equals("s"))
                .toArray(String[]::new);
            check_opt_conflict(cmd, "s", conflicting_ops);

            if (args.length < 1) {
                System.err.println("Nie podano katalogu do wyświetlenia");
                System.exit(1);
            }

            try {
                System.out.println(FileStats.fileSize(args[0]));
            } catch (IllegalArgumentException ex) {
                System.err.println("Podano nieprawidłowy parametr");
                System.exit(1);
            } catch (IOException ex) {
                System.err.println("Błąd wejścia/wyjścia");
                System.exit(1);
            }
            System.exit(0);
        }

        if (cmd.hasOption("l")) {
            String[] conflicting_ops = Arrays.asList(all_actions)
                .stream()
                    // wszystkie pozostałe opcje nie są dozwolone
                .filter(opt -> !opt.equals("l"))
                .toArray(String[]::new);
            check_opt_conflict(cmd, "l", conflicting_ops);

            if (args.length < 1) {
                System.err.println("Nie podano katalogu do wyświetlenia");
                System.exit(1);
            }

            try {
                System.out.println(FileStats.lineCount(args[0]));
            } catch (IllegalArgumentException ex) {
                System.err.println("Podano nieprawidłowy parametr");
                System.exit(1);
            } catch (IOException ex) {
                System.err.println("Błąd wejścia/wyjścia");
                System.exit(1);
            }

            System.exit(0);
        }

        if (cmd.hasOption("w")) {
            String[] conflicting_ops = Arrays.asList(all_actions)
                .stream()
                    // wszystkie pozostałe opcje nie są dozwolone
                .filter(opt -> !opt.equals("w"))
                .toArray(String[]::new);
            check_opt_conflict(cmd, "w", conflicting_ops);

            if (args.length < 1) {
                System.err.println("Nie podano katalogu do wyświetlenia");
                System.exit(1);
            }

            try {
                System.out.println(FileStats.wordCount(args[0]));
            } catch (IllegalArgumentException ex) {
                System.err.println("Podano nieprawidłowy parametr");
                System.exit(1);
            } catch (IOException ex) {
                System.err.println("Błąd wejścia/wyjścia");
                System.exit(1);
            }

            System.exit(0);
        }

        if (cmd.hasOption("c")) {
            String[] conflicting_ops = Arrays.asList(all_actions)
                .stream()
                    // wszystkie pozostałe opcje nie są dozwolone
                .filter(opt -> !opt.equals("c"))
                .toArray(String[]::new);
            check_opt_conflict(cmd, "c", conflicting_ops);

            if (args.length < 1) {
                System.err.println("Nie podano katalogu do wyświetlenia");
                System.exit(1);
            }

            try {
                System.out.println(FileStats.characterCount(args[0]));
            } catch (IllegalArgumentException ex) {
                System.err.println("Podano nieprawidłowy parametr");
                System.exit(1);
            } catch (IOException ex) {
                System.err.println("Błąd wejścia/wyjścia");
                System.exit(1);
            }

            System.exit(0);
        }

        print_help();

        // brak parametrów - zwracamy błąd
        System.exit(1);
    }
}
