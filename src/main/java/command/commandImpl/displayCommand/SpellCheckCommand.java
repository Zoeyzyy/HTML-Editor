package command.commandImpl.displayCommand;

import command.Command;
import editor.Editor;

import java.io.PrintStream;

public class SpellCheckCommand implements Command {
    private final Editor editor;
    private final PrintStream printStream;

    public SpellCheckCommand(Editor editor, PrintStream printStream) {
        this.editor = editor;
        this.printStream = printStream;
    }

    public static Command create(Editor editor, PrintStream printStream) {
        return new SpellCheckCommand(editor, printStream);
    }

    public static Command create(Editor editor) {
        return new SpellCheckCommand(editor, System.out);
    }

    @Override
    public void execute() {
        printStream.println(editor.spellCheck());
    }
}
