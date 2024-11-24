package command.commandImpl.sessionCommand;

import document.HTMLDocument;
import document.HTMLElement;
import command.Command;
import session.Session;

import java.io.PrintStream;

public class DirTreeCommand implements Command {
    private final Session session;
    private final PrintStream printStream;

    public DirTreeCommand(Session session) {
        this.session = session;
        this.printStream = System.out;
    }

    public DirTreeCommand(Session session, PrintStream printStream) {
        this.session = session;
        this.printStream = printStream;
    }

    public static Command create(Session session) {
        return new DirTreeCommand(session);
    }

    public static Command create(Session session, PrintStream printStream) {
        return new DirTreeCommand(session, printStream);
    }

    @Override
    public void execute() {
        // return String
        try {
            printStream.print(session.getDirTreeFormat(0));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
