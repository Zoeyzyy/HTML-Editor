import command.Command;
import command.CommandInvoker;
import command.CommandParser;
import command.commandImpl.sessionCommand.ExitSessionCommand;
import session.Session;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class Console {
    private final InputStream inputStream;
    private final PrintStream printStream;

    private static final String PROMPT = "shell> ";
    private boolean isRunning = true;
    private final Scanner scanner;
    private final Session session;
    // TODO : add command parser
    private final CommandParser commandParser;
    private final CommandInvoker commandInvoker;

    public Console(PrintStream printStream, InputStream inputStream) {
        this.session = new Session("default");
        this.scanner = new Scanner(inputStream);
        this.printStream = printStream;
        this.inputStream = inputStream;
        this.commandParser = new CommandParser(session, inputStream, printStream);
        this.commandInvoker = new CommandInvoker(session);
    }

    public Console() {
        this(System.out, System.in);
    }

    public Console(PrintStream printStream) {
        this(printStream, System.in);
    }

    public Console(InputStream inputStream) {
        this(System.out, inputStream);
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
                if (command instanceof ExitSessionCommand) {
                    isRunning = false;
                    commandInvoker.execute(command);
                } else {
                    commandInvoker.executeAndStore(command);
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }

        scanner.close();
        System.out.println("Goodbye!");
    }

    public static void main(String[] args) {
        Console shell = new Console();
        shell.run();
    }
}
