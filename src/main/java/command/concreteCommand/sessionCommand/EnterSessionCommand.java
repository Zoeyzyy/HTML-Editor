package command.concreteCommand.sessionCommand;

import command.Command;

public class EnterSessionCommand implements Command{
    private String sessionID;

    public EnterSessionCommand(String sessionID) {
        this.sessionID = sessionID;
    }

    public static Command create(String sessionID) {
        return new EnterSessionCommand(sessionID);
    }

    @Override
    public void execute() {
        session.enter(sessionID);
    }
}
