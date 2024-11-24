package command.commandImpl.sessionCommand;

import command.Command;
import session.Session;

import java.io.PrintStream;

public class EditorListCommand implements Command {
    private final Session session;
    private final PrintStream printStream;

    public EditorListCommand(Session session) {
        this.session = session;
        this.printStream = System.out;
    }

    public EditorListCommand(Session session, PrintStream printStream) {
        this.session = session;
        this.printStream = printStream;
    }

    public static Command create(Session session) {
        return new EditorListCommand(session);
    }

    public static Command create(Session session, PrintStream printStream) {
        return new EditorListCommand(session, printStream);
    }

    @Override
    public void execute() {
        // return [String1, string2, ...]
        try {
            for (String editorName : session.getEditorList()) {
                printStream.println(editorName);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
