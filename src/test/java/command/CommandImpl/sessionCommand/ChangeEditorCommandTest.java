package command.CommandImpl.sessionCommand;

import command.commandImpl.sessionCommand.ChangeEditorCommand;
import org.junit.jupiter.api.Test;
import session.Session;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChangeEditorCommandTest {
    @Test
    public void execute() {
        // TODO: give a filepath with a document
        String currentPath = System.getProperty("user.dir");
        String filePath1 = currentPath + "\\src\\main\\resources\\Test.html";
        String filePath2 = currentPath + "\\src\\main\\resources\\template.html";
        Session session = new Session("");
        session.load(filePath1);
        assertEquals(filePath1, session.getActiveEditor().getFileName());
        ChangeEditorCommand changeEditorCommand = new ChangeEditorCommand(session, filePath2);
        changeEditorCommand.execute();

        // TODO: check the active document
        assertEquals(filePath2, session.getActiveEditor().getFileName());
    }
}
