package command.CommandImpl.sessionCommand;

import command.commandImpl.sessionCommand.ChangeEditorCommand;
import org.junit.jupiter.api.Test;
import session.Session;

public class ChangeEditorCommandTest {
    @Test
    public void execute() {
        // TODO: give a filepath with a document
        String filePath1 = "";
        String filePath2 = "";
        Session session = new Session(filePath1);
        ChangeEditorCommand changeEditorCommand = new ChangeEditorCommand(session, filePath2);
        changeEditorCommand.execute();

        // TODO: check the active document

    }
}
