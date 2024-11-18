package command.commandImpl.sessionCommand;

import command.Command;
import session.Session;

public class EnterSessionCommand implements Command{
    private final Session session;
    public EnterSessionCommand(Session session) {
        this.session = session;
    }

    public static Command create(Session session) {
        return new EnterSessionCommand(session);
    }

    @Override
    public void execute() {
        session.enter("");
    }
}
