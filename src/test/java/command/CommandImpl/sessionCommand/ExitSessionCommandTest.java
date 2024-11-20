package command.CommandImpl.sessionCommand;

import command.commandImpl.sessionCommand.ExitSessionCommand;
import org.junit.jupiter.api.Test;
import session.Session;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ExitSessionCommandTest {
    @Test
    public void execute() {
        Session session = new Session("example.html");
        assertEquals("example.html", session.getActiveEditor().getFileName());
        ExitSessionCommand exitSessionCommand = new ExitSessionCommand(session);
        exitSessionCommand.execute();
        // TODO: how to check exited session
        assertNull(session.getActiveEditor());
    }
}
