package command.CommandImpl.sessionCommand;

import command.commandImpl.sessionCommand.ExitSessionCommand;
import org.junit.jupiter.api.Test;
import session.Session;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExitSessionCommandTest {
    @Test
    public void execute() {
        Session session = new Session("example.html");
        assertEquals("example.html", session.getActiveDocument().getFilePath());
        ExitSessionCommand exitSessionCommand = new ExitSessionCommand(session);
        exitSessionCommand.execute();
        // TODO: how to check exited session
        assertEquals(null, session.getActiveDocument());
    }
}
