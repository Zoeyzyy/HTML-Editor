package command.concreteCommand.displayCommand;

import document.HTMLDocument;
import document.HTMLElement;
import command.Command;

public class PrintTreeCommand implements Command {
    private HTMLDocument document;
    private boolean spellCheck;

    public PrintTreeCommand(HTMLDocument document, boolean spellCheck) {
        this.document = document;
        this.spellCheck = spellCheck;
    }

    public static Command create(HTMLDocument document, boolean spellCheck) {
        return new PrintTreeCommand(document, spellCheck);
    }

    @Override
    public void execute() {
        System.out.println(document.getTreeFormat(spellCheck));
    }
}
