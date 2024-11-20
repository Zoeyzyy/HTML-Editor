package command.CommandImpl.sessionCommand;

import command.commandImpl.sessionCommand.CloseCommand;
import org.junit.jupiter.api.Test;
import session.Session;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CloseCommandTest {
    @Test
    public void execute() {
        // TODO: close的创建 和 执行结果
        Session session = new Session("");
        CloseCommand closeCommand = new CloseCommand(session);
        assertEquals("example.html", session.getActiveDocument().getFilePath());
        closeCommand.execute();
        assertEquals(null, session.getActiveDocument());
    }

}
