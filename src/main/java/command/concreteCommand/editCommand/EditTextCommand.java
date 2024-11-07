package command.concreteCommand.editCommand;

import document.HTMLDocument;
import document.HTMLElement;
import command.CanUndoCommand;
import command.Command;

public class EditTextCommand implements CanUndoCommand {
    private HTMLDocument document;
    private String element; // ID
    private String newTextContent;
    private String oldTextContent;

    public EditTextCommand(HTMLDocument document, String element, String newTextContent) {
        this.document = document;
        this.element = element;
        this.newTextContent = newTextContent;
    }

    public static Command create(HTMLDocument document, String element, String newTextContent) {
        return new EditTextCommand(document, element, newTextContent);  
    }

    @Override
    public void execute() {
        oldTextContent = document.getTextContent(element);
        document.editTextContent(element, newTextContent);
    }

    @Override
    public void undo() {
        document.editTextContent(element, oldTextContent); 
    }
}
