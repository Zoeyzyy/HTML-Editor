package command.concreteCommand.IOCommand;

import document.HTMLDocument;
import document.HTMLElement;
import command.Command;

public class SaveCommand implements Command {
    private HTMLDocument document;
    private String filePath;

    public SaveCommand(HTMLDocument document, String filePath) {
        this.document = document;
        this.filePath = filePath;
    }

    public static Command create(HTMLDocument document, String filePath) {
        return new SaveCommand(document, filePath);
    }

    @Override
    public void execute() {
        document.save(filePath);
    }
}
