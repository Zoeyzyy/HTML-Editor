package command.concreteCommand.sessionCommand;

import command.Command;

public class ExitSessionCommand implements Command {
    public ExitSessionCommand(String sessionID) {
    }

    public static Command create(String sessionID) {
        return new ExitSessionCommand(sessionID);
    }

    @Override
    public void execute() {
        session.exit();
    }
}
