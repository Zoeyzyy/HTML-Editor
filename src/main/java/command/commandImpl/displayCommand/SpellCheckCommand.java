package command.commandImpl.displayCommand;

import document.HTMLDocument;
import document.HTMLElement;
import command.Command;

public class SpellCheckCommand implements Command {
    private HTMLDocument document;

    public SpellCheckCommand(HTMLDocument document) {
        this.document = document;
    }

    public static Command create(HTMLDocument document) {
        return new SpellCheckCommand(document);
    }

    @Override
    public void execute() {
        System.out.println(document.getSpellCheck());
    }
}
