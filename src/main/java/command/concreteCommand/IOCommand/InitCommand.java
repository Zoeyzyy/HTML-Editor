package command.concreteCommand.IOCommand;

import document.HTMLDocument;
import document.HTMLElement;
import command.Command;

public class InitCommand implements Command {
    private HTMLDocument document;

    public InitCommand(HTMLDocument document) {
        this.document = document;
    }

    public static Command create(HTMLDocument document) {
        return new InitCommand(document);
    }

    @Override
    public void execute() {
        document.init();
    }
}
