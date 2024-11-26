package command;

import session.Session;

public class CommandInvoker {
    private Session session;
    public CommandInvoker(Session session) {
        this.session = session;
    }

    public void executeAndStore(Command command) throws Exception {
        command.execute();
        session.getActiveEditor().storeCommand(command);
    }

    public void execute(Command command) throws Exception {
        command.execute();
    }
}
