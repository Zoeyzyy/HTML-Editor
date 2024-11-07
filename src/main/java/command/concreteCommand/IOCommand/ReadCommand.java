package command.concreteCommand.IOCommand;

import document.HTMLDocument;
import document.HTMLElement;
import command.Command;

public class ReadCommand implements Command {
    private HTMLDocument document;
    private String filePath;

    public ReadCommand(HTMLDocument document, String filePath) {
        this.document = document;
        this.filePath = filePath;
    }

    public static Command create(HTMLDocument document, String filePath) {
        return new ReadCommand(document, filePath);
    }

    @Override
    public void execute() {
        document.read(filePath);
    }
}
