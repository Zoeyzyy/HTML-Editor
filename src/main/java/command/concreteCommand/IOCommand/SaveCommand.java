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

/*************  ✨ Codeium Command ⭐  *************/
/**
 * Undo operation for SaveCommand.
 * This command cannot be undone or redone, hence this method provides no functionality.
 */
/******  c9659552-71c8-4471-a626-a648bb96d0de  *******/
    @Override
    public void undo() {
        // save 不能undo 和 redo
    }
}
