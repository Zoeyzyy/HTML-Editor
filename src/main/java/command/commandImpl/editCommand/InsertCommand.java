package command.commandImpl.editCommand;

import command.CanUndoCommand;
import command.Command;
import editor.Editor;
import exception.ElementBadRemoved;
import exception.ElementNotFound;

public class InsertCommand implements CanUndoCommand {
    private final Editor editor;
    private final String tagName;
    private final String idValue;
    private final String insertLocation; // new element is inserted before the element with this id
    private final String textContent; // optional

    public InsertCommand(Editor editor, String tagName, String idValue, String insertLocation,
                         String textContent) {
        this.editor = editor;
        this.tagName = tagName;
        this.idValue = idValue;
        this.insertLocation = insertLocation;
        this.textContent = textContent;
    }

    public static Command create(Editor editor, String tagName, String idValue, String insertLocation,
                                 String textContent) {
        return new InsertCommand(editor, tagName, idValue, insertLocation, textContent);
    }

    @Override
    public void execute() throws Exception {
        editor.insert(tagName, idValue, insertLocation, textContent);
    }

    @Override
    public void undo() throws Exception {
        editor.delete(idValue);
    }
}
