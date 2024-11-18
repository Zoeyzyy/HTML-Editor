import java.util.Scanner;

public class HTMLEditorApplication {
    private static final String PROMPT = "shell> ";
    private boolean isRunning = true;
    private final Scanner scanner;

    public HTMLEditorApplication() {
        scanner = new Scanner(System.in);
    }

    public void run() {
        System.out.println("Welcome to HTML Editor! (Type 'exit' to quit)");

        while (isRunning) {
            System.out.print(PROMPT);
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                continue;
            }

            processCommand(input);
        }

        scanner.close();
        System.out.println("Goodbye!");
    }

    private void processCommand(String command) {
        switch (command.toLowerCase()) {
            case "exit":
                isRunning = false;
                break;
            case "clear":
                clearScreen();
                break;
            default:

        }
    }

    private void showHelp() {
        System.out.println("Available commands:");
        System.out.println("  help  - Show this help message");
        System.out.println("  date  - Show current date and time");
        System.out.println("  clear - Clear the screen");
        System.out.println("  exit  - Exit the shell");
    }

    private void clearScreen() {
        // This is a simple way to clear screen, might not work in all terminals
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void main(String[] args) {
        HTMLEditorApplication shell = new HTMLEditorApplication();
        shell.run();
    }
}
