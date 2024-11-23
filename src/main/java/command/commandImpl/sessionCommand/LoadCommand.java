package command.commandImpl.sessionCommand;

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
    public void execute() {
        try {
            String currentPath = System.getProperty("user.dir");
            session.load(currentPath + filePath);
        }catch (Exception e){

        }
    }
}
