package command.CommandImpl.sessionCommand;

import command.commandImpl.displayCommand.ShowIDCommand;
import command.commandImpl.sessionCommand.EnterSessionCommand;
import command.commandImpl.sessionCommand.ExitSessionCommand;
import command.commandImpl.sessionCommand.LoadCommand;
import org.junit.jupiter.api.Test;
import session.Session;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class EnterSessionCommandTest {
    @Test
    public void execute() {
        Session session = new Session("default");
        String currentPath = System.getProperty("user.dir");
        String fileName = "//src//main//resources//template.html";
        LoadCommand loadCommand = new LoadCommand(session, fileName);
        loadCommand.execute();

        ShowIDCommand showIDCommand = new ShowIDCommand(session.getActiveEditor(), true);
        showIDCommand.execute();

        ExitSessionCommand exitSessionCommand = new ExitSessionCommand(session);
        exitSessionCommand.execute();

        EnterSessionCommand enterSessionCommand = new EnterSessionCommand(session);
        enterSessionCommand.execute();
        // check: editlist, active editor, file showid, no redo/undo history
        assertEquals(">" + currentPath + "src\\main\\resources\\template.html", session.getEditorList().get(0));
        assertEquals(1, session.getEditorList().size());
        assertEquals(true, session.getActiveEditor().getDocument().getShowID());
//        assertEquals(0, session.getActiveEditor().getRedohistoy().size());
//        assertEquals(0, session.getActiveEditor().getUndohistory().size());
    }
}
