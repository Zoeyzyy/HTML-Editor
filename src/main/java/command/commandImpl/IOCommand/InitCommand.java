package command.commandImpl.IOCommand;

import command.Command;
import editor.Editor;
import session.Session;

public class InitCommand implements Command {
    private final Session session;

    public InitCommand(Session session) {
        this.session = session;
    }

    public static Command create(Session session) {
        return new InitCommand(session);
    }

    @Override
    public void execute() throws Exception {
        session.load("");
    }
}
