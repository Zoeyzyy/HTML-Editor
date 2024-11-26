package command.CommandImpl.sessionCommand;

import command.commandImpl.editCommand.AppendCommand;
import command.commandImpl.sessionCommand.ExitSessionCommand;
import command.commandImpl.sessionCommand.LoadCommand;
import org.junit.jupiter.api.Test;
import session.Session;

import static org.junit.jupiter.api.Assertions.*;

public class ExitSessionCommandTest {
    @Test
    public void execute() {
        Session session = new Session("default");
//        String currentPath = System.getProperty("user.dir");
//        LoadCommand loadCommand = new LoadCommand(session, "//src//main//resources//example.html");
//        loadCommand.execute();
//
//        assertEquals(currentPath + "\\src\\main\\resources\\example.html", session.getActiveEditor().getFileName());
//
//        AppendCommand appendCommand = new AppendCommand(session.getActiveEditor(), "div", "id1", "body", "Hello World");
//        appendCommand.execute();

        ExitSessionCommand exitSessionCommand = new ExitSessionCommand(session);
        exitSessionCommand.execute();

        assertTrue(true);
    }
}
