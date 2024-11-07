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

    @Override
    public void execute() {
        document.save(filePath);
    }

    @Override
    public void undo() {
        // save 不能undo 和 redo
    }
}
