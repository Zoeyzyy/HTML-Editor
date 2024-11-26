package command.commandImpl.editCommand;

import editor.Editor;
import command.CanUndoCommand;
import command.Command;

public class EditIDCommand implements CanUndoCommand {
    private final Editor editor;
    private final String oldID;
    private final String newID;

    public EditIDCommand(Editor editor, String oldID, String newID) {
        this.editor = editor;
        this.oldID = oldID;
        this.newID = newID;
    }

    public static Command create(Editor editor, String oldID, String newID) {
        return new EditIDCommand(editor, oldID, newID);
    }

    @Override
    public void execute() throws Exception {
        editor.editId(oldID, newID);
    }

    @Override
    public void undo() throws Exception {
        editor.editId(newID, oldID);
    }
}
