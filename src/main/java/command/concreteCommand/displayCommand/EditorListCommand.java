package command.concreteCommand.displayCommand;

import document.HTMLDocument;
import document.HTMLElement;
import command.Command;

public class EditorListCommand implements Command {
    private Session session;

    public EditorListCommand(Session session) {
        this.session = session;
    }

    public static Command create(Session session) {
        return new EditorListCommand(session);
    }

    @Override
    public void execute() {
        System.out.println(session.getEditorList());
    }
}
