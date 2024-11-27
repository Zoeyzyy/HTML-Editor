package command.CommandImpl.IOCommand;

import command.commandImpl.IOCommand.LoadCommand;
import org.junit.jupiter.api.Test;
import session.Session;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class LoadCommandTest {
    @Test
    public void execute() {
        Session session = new Session("default_1");
//        assertNull(session.getActiveEditor());
        String currentPath = System.getProperty("user.dir");
        LoadCommand command = new LoadCommand(session, "src/main/resources/example.html");
        try {
            command.execute();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        assertEquals(currentPath + "\\src\\main\\resources\\example.html", session.getActiveEditor().getFileName());
    }
}
