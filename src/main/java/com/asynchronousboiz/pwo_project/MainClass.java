package com.asynchronousboiz.pwo_project;

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
        "l",
        "c",
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
                .desc("Wyświetl zawartość katalogu lub katalogów")
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

    public static void main (String[] argv) {
        final CommandLine cmd = getArgs(argv);
        if (cmd == null) {
            System.err.println("Podano nieprawidłowe parametry");
            System.exit(1);
        }

        // Priorytetyzujemy parametry

        if (cmd.hasOption("h")) {
            print_help();
            System.exit(0);
        }

        // 1. interaktywny interfejs
        if (cmd.hasOption("i")) {
            String[] conflicting_ops = (String[]) Arrays.asList(all_actions)
                .stream()
                // wszystkie pozostałe opcje nie są dozwolone
                .filter(opt -> !opt.equals("i"))
                .toArray();
            check_opt_conflict(cmd, "i", conflicting_ops);

            // TODO: Implement
            System.out.println("Uruchamianie interaktywnego interfejsu...");
            System.exit(0);
        }

        // 2. operacje plikowe (z jednym lub dwoma parametrami bezpośrednio
        //    po parametrze wybierającym operację)
        if (cmd.hasOption("C")) {
            String[] conflicting_ops = (String[]) Arrays.asList(all_actions)
                .stream()
                    // wszystkie pozostałe opcje nie są dozwolone
                .filter(opt -> !opt.equals("C"))
                .toArray();
            check_opt_conflict(cmd, "C", conflicting_ops);

            // TODO: Implement
            System.out.println("Wybrano opcję wyświetlenia zawartości katalogu (katalogów):");
            for (String dir : cmd.getArgs()) {
                System.out.println("* " + dir);
            }

            System.exit(0);
        }

        if (cmd.hasOption("b")) {
            String[] conflicting_ops = (String[]) Arrays.asList(all_actions)
                .stream()
                    // wszystkie pozostałe opcje nie są dozwolone
                .filter(opt -> !opt.equals("b"))
                .toArray();
            check_opt_conflict(cmd, "b", conflicting_ops);

            String[] filenames = cmd.getOptionValues("b");
            // TODO: Implement
            System.out.println("Wybrano opcję kopiowania pliku: " + filenames[0] + " do: " + filenames[1]);

            System.exit(0);
        }

        if (cmd.hasOption("m")) {
            String[] conflicting_ops = (String[]) Arrays.asList(all_actions)
                .stream()
                    // wszystkie pozostałe opcje nie są dozwolone
                .filter(opt -> !opt.equals("m"))
                .toArray();
            check_opt_conflict(cmd, "m", conflicting_ops);

            String[] filenames = cmd.getOptionValues("m");
            // TODO: Implement
            System.out.println("Wybrano opcję przeniesienia pliku: " + filenames[0] + " do: " + filenames[1]);

            System.exit(0);
        }

        // 3. obliczanie statystyk - ilość parametrów dowolna
        if (cmd.hasOption("s")) {
            String[] conflicting_ops = (String[]) Arrays.asList(all_actions)
                .stream()
                    // wszystkie pozostałe opcje nie są dozwolone
                .filter(opt -> !opt.equals("s"))
                .toArray();
            check_opt_conflict(cmd, "s", conflicting_ops);

            String[] filenames = cmd.getArgs();

            // TODO: Implement
            if (filenames.length == 1) {
                System.out.println("Wybrano opcję wyświetlenia rozmiaru pliku: " + filenames[0]);
            } else {
                System.out.println("Wybrano opcję wyświetlenia rozmiarów plików:");
                for (String filename : filenames) {
                    System.out.println("* " + filename);
                }
            }

            System.exit(0);
        }

        if (cmd.hasOption("l")) {
            String[] conflicting_ops = (String[]) Arrays.asList(all_actions)
                .stream()
                    // wszystkie pozostałe opcje nie są dozwolone
                .filter(opt -> !opt.equals("l"))
                .toArray();
            check_opt_conflict(cmd, "l", conflicting_ops);

            String[] filenames = cmd.getArgs();

            // TODO: Implement
            if (filenames.length == 1) {
                System.out.println("Wybrano opcję wyświetlenia ilości linji w pliku: " + filenames[0]);
            } else {
                System.out.println("Wybrano opcję wyświetlenia ilości linji w plikach:");
                for (String filename : filenames) {
                    System.out.println("* " + filename);
                }
            }

            System.exit(0);
        }

        if (cmd.hasOption("w")) {
            String[] conflicting_ops = (String[]) Arrays.asList(all_actions)
                .stream()
                    // wszystkie pozostałe opcje nie są dozwolone
                .filter(opt -> !opt.equals("w"))
                .toArray();
            check_opt_conflict(cmd, "w", conflicting_ops);

            String[] filenames = cmd.getArgs();

            // TODO: Implement
            if (filenames.length == 1) {
                System.out.println("Wybrano opcję wyświetlenia ilości słów w pliku: " + filenames[0]);
            } else {
                System.out.println("Wybrano opcję wyświetlenia ilości słów w plikach:");
                for (String filename : filenames) {
                    System.out.println("* " + filename);
                }
            }

            System.exit(0);
        }

        if (cmd.hasOption("c")) {
            String[] conflicting_ops = (String[]) Arrays.asList(all_actions)
                .stream()
                    // wszystkie pozostałe opcje nie są dozwolone
                .filter(opt -> !opt.equals("c"))
                .toArray();
            check_opt_conflict(cmd, "c", conflicting_ops);

            String[] filenames = cmd.getArgs();

            // TODO: Implement
            if (filenames.length == 1) {
                System.out.println("Wybrano opcję wyświetlenia ilości znaków w pliku: " + filenames[0]);
            } else {
                System.out.println("Wybrano opcję wyświetlenia ilości znaków w plikach:");
                for (String filename : filenames) {
                    System.out.println("* " + filename);
                }
            }

            System.exit(0);
        }

        print_help();

        // brak parametrów - zwracamy błąd
        System.exit(1);
    }
}
