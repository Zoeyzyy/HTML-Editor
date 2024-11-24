package command.commandImpl.IOCommand;

import command.Command;
import editor.Editor;

public class InitCommand implements Command {
    private final Editor editor;

    public InitCommand(Editor editor) {
        this.editor = editor;
    }

    public static Command create(Editor editor) {
        return new InitCommand(editor);
    }

    @Override
    public void execute() {
        try {
            editor.init();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
