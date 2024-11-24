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
    public void execute() {
        try {
            session.exit();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
