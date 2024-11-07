package command.concreteCommand.editCommand;

import document.HTMLDocument;
import document.HTMLElement;
import command.Command;

public class EditIDCommand implements Command {
    private HTMLDocument document;
    private String oldID;
    private String newID;

    public EditIDCommand(HTMLDocument document, String oldID, String newID) {
        this.document = document;
        this.oldID = oldID;
        this.newID = newID;
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
