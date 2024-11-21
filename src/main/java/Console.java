import session.Session;

import java.util.Scanner;

public class Console {
    private static final String PROMPT = "shell> ";
    private boolean isRunning = true;
    private final Scanner scanner;
    private final Session session;
    // TODO : add command parser
//    private final CommandParser commandParser;

    public Console() {
        this.session = new Session("default");
        this.scanner = new Scanner(System.in);
//        this.commandParser = new CommandParser();
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
//                Command command = commandParser.processCommand(input);
//                command.execute();
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
