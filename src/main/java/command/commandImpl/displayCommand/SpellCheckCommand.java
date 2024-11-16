package command.commandImpl.displayCommand;

import document.HTMLDocument;
import command.Command;

import java.io.PrintStream;

public class SpellCheckCommand implements Command {
    private final HTMLDocument document;
    private final PrintStream printStream;

    public SpellCheckCommand(HTMLDocument document, PrintStream printStream) {
        this.document = document;
        this.printStream = printStream;
    }

    public static Command create(HTMLDocument document, PrintStream printStream) {
        return new SpellCheckCommand(document, printStream);
    }

    public static Command create(HTMLDocument document) {
        return new SpellCheckCommand(document, System.out);
    }

    @Override
    public void execute() {
        printStream.println(document.getSpellCheck());
    }
}
