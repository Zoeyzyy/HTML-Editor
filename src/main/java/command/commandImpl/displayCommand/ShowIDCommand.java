package command.commandImpl.displayCommand;

import command.Command;
import editor.Editor;

public class ShowIDCommand implements Command {
    private final Editor editor;
    private final boolean showID;


    public ShowIDCommand(Editor editor, boolean showID) {
        this.editor = editor;
        this.showID = showID;
    }

    public static Command create(Editor editor, boolean showID) {
        return new ShowIDCommand(editor, showID);
    }

    @Override
    public void execute() throws Exception {
        editor.showId(showID);
    }
}
