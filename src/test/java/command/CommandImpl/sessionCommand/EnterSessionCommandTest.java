package command.CommandImpl.sessionCommand;

import command.commandImpl.sessionCommand.EnterSessionCommand;
import command.commandImpl.sessionCommand.ExitSessionCommand;
import org.junit.jupiter.api.Test;
import session.Session;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EnterSessionCommandTest {
    @Test
    public void execute() {
        Session session = new Session("example.html");
        ExitSessionCommand exitSessionCommand = new ExitSessionCommand(session);
        exitSessionCommand.execute();
        assertEquals(null, session.getActiveDocument());

        EnterSessionCommand enterSessionCommand = new EnterSessionCommand(session);
        enterSessionCommand.execute();
        // TODO: how to check the session
        assertEquals("example.html", session.getActiveDocument().getFilePath());
    }
}
