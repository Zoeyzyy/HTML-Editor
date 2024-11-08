package command.commandImpl.editCommand;

import document.HTMLDocument;
import document.HTMLElement;
import command.CanUndoCommand;
import command.Command;

public class DeleteCommand implements CanUndoCommand {
    private HTMLDocument document;
    private String element; // ID
    private String tagName;
    private String idValue;
    private String insertLocation;
    private String parentElement;
    private String textContent;

    public DeleteCommand(HTMLDocument document, String element) {
        this.document = document;
        this.element = element;
    }

    public static Command create(HTMLDocument document, String element) {
        return new DeleteCommand(document, element);
    }

    @Override
    public void execute() {
        tagName = document.findElementById(element).getTagName();
        idValue = document.findElementById(element).getId();
        insertLocation = document.findElementById(element).getInsertLocation();
        textContent = document.findElementById(element).getTextContent();
        
        document.removeElementById(element);
    }

    @Override
    public void undo() {
        document.insertElement(tagName, idValue, insertLocation, textContent);
    }
}
