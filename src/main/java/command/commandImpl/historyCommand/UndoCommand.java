package command.commandImpl.historyCommand;

import document.HTMLDocument;
import document.HTMLElement;
import command.Command;
import command.CommandHistory


public class UndoCommand implements Command {
    private CommandHistory commandHistory;

    public UndoCommand(CommandHistory commandHistory) {  
        this.commandHistory = commandHistory;
    }

    @Override
    public void execute() {
        commandHistory.undoLastCommand();
    }
}
