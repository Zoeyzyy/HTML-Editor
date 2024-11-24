package command.CommandImpl.sessionCommand;

import command.commandImpl.sessionCommand.LoadCommand;
import org.junit.jupiter.api.Test;
import session.Session;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class LoadCommandTest {
    @Test
    public void execute() {
        Session session = new Session("");
        assertNull(session.getActiveEditor());
        String currentPath = System.getProperty("user.dir");
        LoadCommand command = new LoadCommand(session, "src/main/resources/template.html");
        command.execute();
        assertEquals(currentPath + "\\src\\main\\resources\\template.html", session.getActiveEditor().getFileName());
    }
}
