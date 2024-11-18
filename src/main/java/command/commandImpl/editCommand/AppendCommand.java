package command.commandImpl.editCommand;

import document.HTMLDocument;
import command.CanUndoCommand;
import command.Command;
import exception.ElementNotFound;

public class AppendCommand implements CanUndoCommand {
    private final HTMLDocument document;
    private final String tagName;
    private final String idValue;
    private final String parentElement; // parent element id
    private final String textContent; // optional

    public AppendCommand(HTMLDocument document, String tagName, String idValue, String parentElement, String textContent) {
        this.document = document;
        this.tagName = tagName;
        this.idValue = idValue;
        this.parentElement = parentElement;
        this.textContent = textContent;
    }

    public static Command create(HTMLDocument document, String tagName, String idValue, String parentElement, String textContent) {
        return new AppendCommand(document, tagName, idValue, parentElement, textContent);
    }

    @Override
    public void execute() {
        try {
            document.appendElement(tagName, idValue, textContent, parentElement);
        }catch (ElementNotFound e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void undo() {
        document.removeElementById(idValue);
    }
}
