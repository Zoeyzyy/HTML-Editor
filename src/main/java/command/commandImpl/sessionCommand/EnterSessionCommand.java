package command.commandImpl.sessionCommand;

import command.Command;

public class EnterSessionCommand implements Command{
    public EnterSessionCommand() {
    }

    public static Command create() {
        return new EnterSessionCommand();
    }

    @Override
    public void execute() {
        session.enter();
    }
}
