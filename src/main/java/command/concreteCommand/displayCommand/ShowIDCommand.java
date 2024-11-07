package command.concreteCommand.displayCommand;

import document.HTMLDocument;
import document.HTMLElement;
import command.Command;

public class ShowIDCommand implements Command {
    private HTMLDocument document;
    private boolean showID;

    public ShowIDCommand(HTMLDocument document, boolean showID) {
        this.document = document;
        this.showID = showID;
    }

    public static Command create(HTMLDocument document, boolean showID) {
        return new ShowIDCommand(document, showID);
    }

    @Override
    public void execute() {
        document.setShowID(showID);
    }
}
