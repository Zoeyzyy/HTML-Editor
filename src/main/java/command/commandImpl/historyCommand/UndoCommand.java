package command.commandImpl.historyCommand;

import command.Command;
import editor.Editor;


public class UndoCommand implements Command {
    private final Editor editor;

    public UndoCommand(Editor editor) {
        this.editor = editor;
    }

    public static Command create(Editor editor) {
        return new UndoCommand(editor);
    }

    @Override
    public void execute() throws Exception {
        editor.undo();
    }
}
