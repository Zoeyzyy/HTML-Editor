package command.commandImpl.IOCommand;

import command.Command;
import session.Session;

public class LoadCommand implements Command {
    private final Session session;
    private final String filePath;

    public LoadCommand(Session session, String filePath) {
        this.session = session;
        this.filePath = filePath;
    }

    public static Command create(Session session, String filePath) {
        return new LoadCommand(session, filePath);
    }

    @Override
    public void execute() throws Exception {
        // let session to check filepath
        session.load(filePath);
    }
}
