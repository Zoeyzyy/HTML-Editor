package command.concreteCommand.displayCommand;

import document.HTMLDocument;
import document.HTMLElement;
import command.Command;

public class SpellCheckCommand implements Command {
    private HTMLDocument document;

    public SpellCheckCommand(HTMLDocument document) {
        this.document = document;
    }

    @Override
    public void execute() {
        document.spellCheck();
    }

    @Override
    public void undo() {
    }
}
