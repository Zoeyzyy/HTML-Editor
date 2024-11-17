package command.commandImpl.historyCommand;

import command.Command;
import command.CommandHistory;


public class RedoCommand implements Command {
    private final CommandHistory commandHistory;

    public RedoCommand(CommandHistory commandHistory) {
        this.commandHistory = commandHistory;
    }

    public Command create(CommandHistory commandHistory) {
        return new RedoCommand(commandHistory);
    }

    @Override
    public void execute() {
        commandHistory.redoLastCommand();
    }
}
