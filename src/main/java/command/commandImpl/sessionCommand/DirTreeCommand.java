package command.commandImpl.sessionCommand;

import document.HTMLDocument;
import document.HTMLElement;
import command.Command;

public class DirTreeCommand implements Command {
    private Session session;

    public DirTreeCommand(Session session) {
        this.session = session;
    }

    public static Command create(Session session) {
        return new DirTreeCommand(session);
    }
    
    @Override
    public void execute() {
        // return String
        System.out.println(session.getDirTreeFormat());
    }
}
