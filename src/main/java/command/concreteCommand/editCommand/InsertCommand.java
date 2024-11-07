package command.concreteCommand.editCommand;

import document.HTMLDocument;
import document.HTMLElement;
import command.Command;

public class InsertCommand implements Command {
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
        this.parentElement = parentElement;
        this.textContent = textContent;
    }

    @Override
    public void execute() {
        document.insertElement(tagName, idValue, parentElement, textContent);
    }

    @Override
    public void undo() {
        document.removeElement(idValue);
    }
}
