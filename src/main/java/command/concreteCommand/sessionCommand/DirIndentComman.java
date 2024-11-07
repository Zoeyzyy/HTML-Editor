package command.concreteCommand.sessionCommand;

import document.HTMLDocument;
import document.HTMLElement;
import command.Command;

public class DirIndentComman implements Command {
    private Session session;
    private int indent;


    public DirIdentCommand(Session session, int indent) {
        this.session = session;
        this.indent = indent;
    }

    public static Command create(Session session, int indent) {
        return new DirIndentComman(session, indent);
    }

    @Override
    public void execute() {
        System.out.println(session.getDirIndentFormat(indent));
    }
}
