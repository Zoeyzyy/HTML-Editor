package command.commandImpl.IOCommand;

import editor.Editor;

import java.io.File;
import java.io.IOException;

import command.Command;

public class ReadCommand implements Command {
    private final Editor editor;
    private final String filePath;

    public ReadCommand(Editor editor, String filePath) {
        this.editor = editor;
        this.filePath = filePath;
    }

    public static Command create(Editor editor, String filePath) {
        return new ReadCommand(editor, filePath);
    }

    @Override
    public void execute() {
        // Read the HTML file
        try { // let editor to check filepath
            editor.load(filePath);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
