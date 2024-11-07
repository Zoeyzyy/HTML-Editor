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

    @Override
    public void execute() {
        document.read(filePath);
    }

    @Override
    public void undo() {
        // read 不能undo 和 redo
    }
}
