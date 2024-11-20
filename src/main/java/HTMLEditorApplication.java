import editor.Editor;

import java.util.Scanner;

public class HTMLEditorApplication {
    private static final String PROMPT = "shell> ";
    private boolean isRunning = true;
    private final Scanner scanner;
    private final Editor editor;

    public HTMLEditorApplication() {
        editor=new Editor();
        scanner = new Scanner(System.in);
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
                processCommand(input);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
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
                editor.executeCommand(command);
        }
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
