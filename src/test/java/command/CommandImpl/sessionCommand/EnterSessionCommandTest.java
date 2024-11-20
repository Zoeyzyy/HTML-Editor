package command.CommandImpl.sessionCommand;

import command.commandImpl.sessionCommand.EnterSessionCommand;
import command.commandImpl.sessionCommand.ExitSessionCommand;
import org.junit.jupiter.api.Test;
import session.Session;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class EnterSessionCommandTest {
    @Test
    public void execute() {
        Session session = new Session("example.html");
        ExitSessionCommand exitSessionCommand = new ExitSessionCommand(session);
        exitSessionCommand.execute();
        assertNull(session.getActiveEditor());

        EnterSessionCommand enterSessionCommand = new EnterSessionCommand(session);
        enterSessionCommand.execute();
        // TODO: how to check the session
        assertEquals("example.html", session.getActiveEditor().getFileName());
    }
}
