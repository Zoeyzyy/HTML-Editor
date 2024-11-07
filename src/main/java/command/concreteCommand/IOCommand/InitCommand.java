package command.concreteCommand.IOCommand;

import document.HTMLDocument;
import document.HTMLElement;
import command.Command;

public class InitCommand implements Command {
    private HTMLDocument document;

    public InitCommand(HTMLDocument document) {
        this.document = document;
    }

    @Override
    public void execute() {
        document.init();
    }

    @Override
    public void undo() {
        // init 不能undo 和 redo
    }
}
