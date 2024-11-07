package command.commandImpl.editCommand;

import document.HTMLDocument;
import document.HTMLElement;
import command.CanUndoCommand;
import command.Command;

public class InsertCommand implements CanUndoCommand {
    private HTMLDocument document;
    private String tagName;
    private String idValue;
    private String insertLocation; // new element is inserted before the element with this id
    private String textContent; // optional

    public InsertCommand(HTMLDocument document, String tagName, String idValue, String parentElement,
            String textContent) {
        this.document = document;
        this.tagName = tagName;
        this.idValue = idValue;
        this.insertLocation = parentElement;
        this.textContent = textContent;
    }

    public static Command create(HTMLDocument document, String tagName, String idValue, String parentElement,
            String textContent) {
        return new InsertCommand(document, tagName, idValue, parentElement, textContent);
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
