package command.commandImpl.editCommand;

import command.CanUndoCommand;
import command.Command;
import editor.Editor;
import exception.ElementNotFound;

public class AppendCommand implements CanUndoCommand {
    private final Editor editor;
    private final String tagName;
    private final String idValue;
    private final String parentElement; // parent element id
    private final String textContent; // optional

    public AppendCommand(Editor editor, String tagName, String idValue, String parentElement, String textContent) {
        this.editor = editor;
        this.tagName = tagName;
        this.idValue = idValue;
        this.parentElement = parentElement;
        this.textContent = textContent;
    }

    public static Command create(Editor editor, String tagName, String idValue, String parentElement, String textContent) {
        return new AppendCommand(editor, tagName, idValue, parentElement, textContent);
    }

    @Override
    public void execute() {
        try {
            editor.append(tagName, idValue, parentElement, textContent);
        }catch (Error e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void undo() {
        try {
            editor.delete(idValue);
        }catch (Error e) {
            System.err.println(e.getMessage());
        }
    }
}
