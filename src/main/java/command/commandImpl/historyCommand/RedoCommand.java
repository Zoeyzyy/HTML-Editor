package command.commandImpl.historyCommand;

import command.Command;
import history.CommandHistory;


public class RedoCommand implements Command {
    private final CommandHistory commandHistory;

    public RedoCommand(CommandHistory commandHistory) {
        this.commandHistory = commandHistory;
    }

    public static Command create(CommandHistory commandHistory) {
        return new RedoCommand(commandHistory);
    }

    @Override
    public void execute() {
        commandHistory.redo();
    }
}
