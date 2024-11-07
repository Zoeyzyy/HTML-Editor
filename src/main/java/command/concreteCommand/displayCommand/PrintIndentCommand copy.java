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

    @Override
    public void execute() {
        document.printIndent(indent);
    }

    @Override
    public void undo() {
    }
}
