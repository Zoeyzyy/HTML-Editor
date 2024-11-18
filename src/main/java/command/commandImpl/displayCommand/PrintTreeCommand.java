package command.commandImpl.displayCommand;

import document.HTMLDocument;
import command.Command;

import java.io.PrintStream;

public class PrintTreeCommand implements Command {
    private final HTMLDocument document;
    private final PrintStream printStream;

    public PrintTreeCommand(HTMLDocument document, PrintStream printStream) {
        this.document = document;
        this.printStream = printStream;
    }

    public PrintTreeCommand(HTMLDocument document) {
        this.document = document;
        this.printStream = System.out;
    }

    public static Command create(HTMLDocument document) {
        return new PrintTreeCommand(document, System.out);
    }

    public static Command create(HTMLDocument document, PrintStream printStream) {
        return new PrintTreeCommand(document, printStream);
    }

    @Override
    public void execute() {
        printStream.println(document.getTreeFormat(true));
    }
}
