package command.commandImpl.historyCommand;

import command.Command;
import history.CommandHistory;


public class UndoCommand implements Command {
    private final CommandHistory commandHistory;

    public UndoCommand(CommandHistory commandHistory) {  
        this.commandHistory = commandHistory;
    }

    public static Command create(CommandHistory commandHistory) {
        return new UndoCommand(commandHistory);
    }

    @Override
    public void execute() {
        commandHistory.undo();
    }
}
