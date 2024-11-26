package command.commandImpl.sessionCommand;

import command.Command;
import session.Session;

import java.io.PrintStream;

public class DirIndentCommand implements Command {
    private final Session session;
    private final int indent;
    private final PrintStream printStream;


    public DirIndentCommand(Session session, int indent) {
        this.session = session;
        this.indent = indent;
        this.printStream = System.out;
    }

    public DirIndentCommand(Session session) {
        this.session = session;
        this.indent = 2;
        this.printStream = System.out;
    }

    public DirIndentCommand(Session session, int indent, PrintStream printStream) {
        this.session = session;
        this.indent = indent;
        this.printStream = printStream;
    }

    public DirIndentCommand(Session session, PrintStream printStream) {
        this.session = session;
        this.indent = 2;
        this.printStream = printStream;
    }

    public static Command create(Session session, int indent) {
        return new DirIndentCommand(session, indent);
    }

    public static Command create(Session session, int indent, PrintStream printStream) {
        return new DirIndentCommand(session, indent, printStream);
    }

    public static Command create(Session session) {
        return new DirIndentCommand(session);
    }

    public static Command create(Session session, PrintStream printStream) {
        return new DirIndentCommand(session, printStream);
    }

    @Override
    public void execute() throws Exception {
        // return String
        printStream.print(session.getDirIndentFormat(indent));
    }
}
