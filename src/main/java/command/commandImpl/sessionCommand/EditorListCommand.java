package command.commandImpl.sessionCommand;

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
        // return [String1, string2]
        for (HTMLElement element : session.getEditorList()) {
            System.out.println(element);
        }
    }
}
