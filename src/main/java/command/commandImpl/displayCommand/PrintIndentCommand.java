package command.commandImpl.displayCommand;

import document.HTMLDocument;
import command.Command;

import java.io.PrintStream;

public class PrintIndentCommand implements Command {
    private final HTMLDocument document;
    private final int indent;
    private final PrintStream printStream;

    public PrintIndentCommand(HTMLDocument document, int indent, PrintStream printStream) {
        this.document = document;
        this.indent = indent;
        this.printStream = printStream;
    }

    public PrintIndentCommand(HTMLDocument document, int indent) {
        this(document, indent, System.out);
    }

    public PrintIndentCommand(HTMLDocument document) {
        this(document, 2, System.out);
    }

    public static Command create(HTMLDocument document, int indent, PrintStream printStream) {
        return new PrintIndentCommand(document, indent, printStream);
    }

    public static Command create(HTMLDocument document, PrintStream output) {
        return new PrintIndentCommand(document, 2, output);
    }

    public static Command create(HTMLDocument document, int indent) {
        return new PrintIndentCommand(document, indent);
    }

    public static Command create(HTMLDocument document) {
        return new PrintIndentCommand(document);
    }

    @Override
    public void execute() {
        printStream.println(document.getIndentFormat(indent));
    }
}
