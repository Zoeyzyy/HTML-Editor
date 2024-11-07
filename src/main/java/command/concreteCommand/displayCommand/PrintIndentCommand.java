package command.concreteCommand.displayCommand;

import document.HTMLDocument;
import document.HTMLElement;
import command.Command;

public class PrintIndentCommand implements Command {
    private HTMLDocument document;
    private int indent;

    public PrintIndentCommand(HTMLDocument document, int indent) {
        this.document = document;
        this.indent = indent;
    }

    public static Command create(HTMLDocument document, int indent) {
        return new PrintIndentCommand(document, indent);
    }

    @Override
    public void execute() {
        System.out.println(document.getIndentFormat(indent));
    }
}
