package command.commandImpl.editCommand;

import document.HTMLDocument;
import command.CanUndoCommand;
import command.Command;

public class EditIDCommand implements CanUndoCommand {
    private final HTMLDocument document;
    private final String oldID;
    private final String newID;

    public EditIDCommand(HTMLDocument document, String oldID, String newID) {
        this.document = document;
        this.oldID = oldID;
        this.newID = newID;
    }

    public static Command create(HTMLDocument document, String oldID, String newID) {
        return new EditIDCommand(document, oldID, newID);
    }

    @Override
    public void execute() {
        document.editID(oldID, newID);
    }

    @Override
    public void undo() {
        document.editID(newID, oldID); 
    }
}
