package command.commandImpl.displayCommand;

import command.Command;
import editor.Editor;

import java.io.PrintStream;

public class PrintIndentCommand implements Command {
    private final Editor editor;
    private final int indent;
    private final PrintStream printStream;

    public PrintIndentCommand(Editor editor, int indent, PrintStream printStream) {
        this.editor = editor;
        this.indent = indent;
        this.printStream = printStream;
    }

    public PrintIndentCommand(Editor editor, int indent) {
        this(editor, indent, System.out);
    }

    public PrintIndentCommand(Editor editor) {
        this(editor, 2, System.out);
    }

    public static Command create(Editor editor, int indent, PrintStream printStream) {
        return new PrintIndentCommand(editor, indent, printStream);
    }

    public static Command create(Editor editor, PrintStream output) {
        return new PrintIndentCommand(editor, 2, output);
    }

    public static Command create(Editor editor, int indent) {
        return new PrintIndentCommand(editor, indent);
    }

    public static Command create(Editor editor) {
        return new PrintIndentCommand(editor);
    }

    @Override
    public void execute() throws Exception {
        printStream.print(editor.printIndent(indent));
    }
}
