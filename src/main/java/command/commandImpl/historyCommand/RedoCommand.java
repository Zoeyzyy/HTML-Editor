package command.commandImpl.historyCommand;

import command.Command;
import command.CommandHistory;


public class RedoCommand implements Command {
    private CommandHistory commandHistory;

    public RedoCommand(CommandHistory commandHistory) {
        this.commandHistory = commandHistory;
    }

    @Override
    public void execute() {
        commandHistory.redoLastCommand();
    }
}
