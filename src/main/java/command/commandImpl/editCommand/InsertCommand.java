package command.commandImpl.editCommand;

import document.HTMLDocument;
import command.CanUndoCommand;
import command.Command;

public class InsertCommand implements CanUndoCommand {
    private final HTMLDocument document;
    private final String tagName;
    private final String idValue;
    private final String insertLocation; // new element is inserted before the element with this id
    private final String textContent; // optional

    public InsertCommand(HTMLDocument document, String tagName, String idValue, String insertLocation,
                         String textContent) {
        this.document = document;
        this.tagName = tagName;
        this.idValue = idValue;
        this.insertLocation = insertLocation;
        this.textContent = textContent;
    }

    public static Command create(HTMLDocument document, String tagName, String idValue, String insertLocation,
                                 String textContent) {
        return new InsertCommand(document, tagName, idValue, insertLocation, textContent);
    }

    @Override
    public void execute() {
        document.insertElement(tagName, idValue, insertLocation, textContent);
    }

    @Override
    public void undo() {
        document.removeElementById(idValue);
    }
}
