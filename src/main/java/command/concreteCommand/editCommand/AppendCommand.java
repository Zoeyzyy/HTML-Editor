package command.concreteCommand.editCommand;

import document.HTMLDocument;
import document.HTMLElement;
import command.Command;

public class AppendCommand implements Command {
    private HTMLDocument document;
    private String tagName;
    private String idValue;
    private String parentElement; // parent element id
    private String textContent; // optional

    public InsertCommand(HTMLDocument document, String tagName, String idValue, String parentElement, String textContent) {
        this.document = document;
        this.tagName = tagName;
        this.idValue = idValue;
        this.parentElement = parentElement;
        this.textContent = textContent;
    }

    @Override
    public void execute() {
        document.appendElement(tagName, idValue, parentElement, textContent);
        
    }

    @Override
    public void undo() {
        document.removeElement(idValue); 
    }
}
