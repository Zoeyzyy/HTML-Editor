package command.CommandImpl.sessionCommand;

import command.commandImpl.sessionCommand.LoadCommand;
import org.junit.jupiter.api.Test;
import session.Session;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoadCommandTest {
    @Test
    public void execute() {
        Session session = new Session("example.html");
        assertEquals("example.html", session.getActiveDocument().getFilePath());
        LoadCommand command = new LoadCommand(session, "example2.html");
        command.execute();
        assertEquals("example2.html", session.getActiveDocument().getFilePath());
    }
}
