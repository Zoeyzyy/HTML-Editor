package command.commandImpl.displayCommand;

import document.HTMLDocument;
import command.Command;

public class ShowIDCommand implements Command {
    private final HTMLDocument document;
    private final boolean showID;


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
