package command.commandImpl.sessionCommand;

import document.HTMLDocument;
import document.HTMLElement;
import command.Command;

public class ChangeEditorCommand implements Command {
    private String filePath;

    public ChangeEditorCommand(String filePath) {
        this.filePath = filePath;
    }

    public static Command create(String filePath) {
        return new ChangeEditorCommand(filePath);
    }

    @Override
    public void execute() {
        session.activateEditor(filePath);
    }
}
