package command.commandImpl.editCommand;

import document.HTMLDocument;
import command.CanUndoCommand;
import command.Command;
import exception.ElementBadRemoved;
import exception.ElementNotFound;

public class DeleteCommand implements CanUndoCommand {
    private final HTMLDocument document;
    private final String element; // ID
    private String tagName;
    private String idValue;
    private String insertLocation;
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

        try {
            document.removeElementById(element);
        } catch (ElementBadRemoved e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void undo() {
        try {
            document.insertElement(tagName, idValue, insertLocation, textContent);
        }catch (ElementNotFound e) {
            System.out.println(e.getMessage());
        }
    }
}
