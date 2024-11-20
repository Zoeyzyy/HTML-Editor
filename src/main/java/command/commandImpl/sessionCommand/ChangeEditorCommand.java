package command.commandImpl.sessionCommand;

import document.HTMLDocument;
import document.HTMLElement;
import command.Command;
import session.Session;

public class ChangeEditorCommand implements Command {
    private final Session session;
    private final String filePath;

    public ChangeEditorCommand(Session session,String filePath) {
        this.session = session;
        this.filePath = filePath;
    }

    public static Command create(Session session,String filePath) {
        return new ChangeEditorCommand(session, filePath);
    }

    @Override
    public void execute() {
        session.activateEditor(filePath);
    }
}
