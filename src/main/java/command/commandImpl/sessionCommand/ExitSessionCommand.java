package command.commandImpl.sessionCommand;

import command.Command;

public class ExitSessionCommand implements Command {
    public ExitSessionCommand() {
    }

    public static Command create() {
        return new ExitSessionCommand();
    }

    @Override
    public void execute() {
        session.exit();
    }
}
