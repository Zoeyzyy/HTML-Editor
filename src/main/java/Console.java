import command.Command;
import command.CommandInvoker;
import command.CommandParser;
import session.Session;

import java.util.Scanner;

public class Console {
    private static final String PROMPT = "shell> ";
    private boolean isRunning = true;
    private final Scanner scanner;
    private final Session session;
    // TODO : add command parser
    private final CommandParser commandParser;
    private final CommandInvoker commandInvoker;

    public Console() {
        this.session = new Session("");
        session.enter("default");
        this.scanner = new Scanner(System.in);
        this.commandParser = new CommandParser(session);
        this.commandInvoker = new CommandInvoker(session);
    }

    public void run() {
        System.out.println("Welcome to HTML Editor! (Type 'exit' to quit)");

        while (isRunning) {
            try {
                System.out.print(PROMPT);
                String input = scanner.nextLine().trim();

                if (input.isEmpty()) {
                    continue;
                }
                // TODO : add command parser
                Command command = commandParser.processCommand(input);
                commandInvoker.executeAndStore(command);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }

        scanner.close();
        System.out.println("Goodbye!");
    }


    private void clearScreen() {
        // This is a simple way to clear screen, might not work in all terminals
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void main(String[] args) {
        Console shell = new Console();
        shell.run();
    }
}
