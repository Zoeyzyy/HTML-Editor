package command.commandImpl.sessionCommand;

import command.Command;
import command.commandImpl.IOCommand.SaveCommand;
import session.Session;

import java.io.PrintStream;

public class CloseCommand implements Command {
    private final Session session;
    private final PrintStream printStream;

    public CloseCommand(Session session) {
        this.session = session;
        this.printStream = System.out;
    }

    public CloseCommand(Session session, PrintStream printStream) {
        this.session = session;
        this.printStream = printStream;
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
            String input = new java.util.Scanner(System.in).nextLine();
            if (input.equals("y")) {
                printStream.println("Please input file path:");
                String filePath = new java.util.Scanner(System.in).nextLine();
                Command saveCommand = SaveCommand.create(session.getActiveEditor().getDocument(), filePath);
                saveCommand.execute();
                break;
            }else{ // confirm again
                printStream.println("File is modified, comfirm not to save?(y/n)");
                String inputNext = new java.util.Scanner(System.in).nextLine();
                if (inputNext.equals("y")) {
                    break;
                }
            }
        }
        session.close();
    }
}
