package command.commandImpl.displayCommand;

import command.Command;
import editor.Editor;

import java.io.PrintStream;

public class PrintTreeCommand implements Command {
    private final Editor editor;
    private final PrintStream printStream;

    public PrintTreeCommand(Editor editor, PrintStream printStream) {
        this.editor = editor;
        this.printStream = printStream;
    }

    public PrintTreeCommand(Editor editor) {
        this.editor = editor;
        this.printStream = System.out;
    }

    public static Command create(Editor editor) {
        return new PrintTreeCommand(editor, System.out);
    }

    public static Command create(Editor editor, PrintStream printStream) {
        return new PrintTreeCommand(editor, printStream);
    }

    @Override
    public void execute() {
        printStream.print(editor.printTree());
    }
}
