package command.commandImpl.sessionCommand;

import command.Command;
import session.Session;

public class ExitSessionCommand implements Command {
    private final Session session;

    public ExitSessionCommand(Session session) {
        this.session = session;
    }

    public static Command create(Session session) {
        return new ExitSessionCommand(session);
    }

    @Override
    public void execute() throws Exception {
        session.exit();
    }
}
