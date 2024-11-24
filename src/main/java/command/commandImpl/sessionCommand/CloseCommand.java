package command.commandImpl.sessionCommand;

import command.Command;
import command.commandImpl.IOCommand.SaveCommand;
import session.Session;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class CloseCommand implements Command {
    private final Session session;
    private final PrintStream printStream;
    private final InputStream inputStream;
    private final Scanner scanner;

    public CloseCommand(Session session) {
        this.session = session;
        this.printStream = System.out;
        this.inputStream = System.in;
        this.scanner = new Scanner(inputStream);
    }

    public CloseCommand(Session session, PrintStream printStream) {
        this.session = session;
        this.printStream = printStream;
        this.inputStream = System.in;
        this.scanner = new Scanner(inputStream);
    }

    public static Command create(Session session) {
        return new CloseCommand(session, System.out);
    }

    public static Command create(Session session, PrintStream printStream) {
        return new CloseCommand(session, printStream);
    }

    @Override
    public void execute() {
        // return boolean： check active file is modified
        boolean modified = session.confirm();
        while (modified) {
            printStream.println("File is modified, do you want to save?(y/n)");
            String input = scanner.nextLine();
            if (input.equals("y")) {
                String filePath = session.getActiveEditor().getFileName();
                Command saveCommand = SaveCommand.create(session, filePath);
                saveCommand.execute();
                break;
            }else{ // confirm again
                printStream.println("File is modified, comfirm not to save?(y/n)");
                String inputNext = scanner.nextLine();
                if (inputNext.equals("y")) {
                    break;
                }
            }
        }
        session.close();
    }
}
