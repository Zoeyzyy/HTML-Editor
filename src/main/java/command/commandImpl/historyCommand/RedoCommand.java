package command.commandImpl.historyCommand;

import command.Command;
import editor.Editor;


public class RedoCommand implements Command {
    private final Editor editor;

    public RedoCommand(Editor editor) {
        this.editor = editor;
    }

    public static Command create(Editor editor) {
        return new RedoCommand(editor);
    }

    @Override
    public void execute() {
        editor.redo();
    }
}
