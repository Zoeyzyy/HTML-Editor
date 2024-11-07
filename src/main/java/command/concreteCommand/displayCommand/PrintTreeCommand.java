package command.concreteCommand.displayCommand;

import document.HTMLDocument;
import document.HTMLElement;
import command.Command;

public class PrintTreeCommand implements Command {
    private HTMLDocument document;

    public PrintTreeCommand(HTMLDocument document) {
        this.document = document;
    }

    @Override
    public void execute() {
        document.printTree();
    }

    @Override
    public void undo() {
    }
}
