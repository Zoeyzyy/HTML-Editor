package command.CommandImpl.sessionCommand;

import command.commandImpl.editCommand.InsertCommand;
import command.commandImpl.sessionCommand.CloseCommand;
import org.junit.jupiter.api.Test;
import session.Session;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CloseCommandTest {
    @Test
    public void execute() {
        // TODO: close的创建 和 执行结果
        Session session = new Session("");
        String currentPath = System.getProperty("user.dir");
        String filePath = currentPath + "\\src\\main\\resources\\template.html";
        session.load(filePath);
        assertEquals(filePath, session.getActiveEditor().getFileName());
        CloseCommand closeCommand = new CloseCommand(session);
        closeCommand.execute();
        assertNull(session.getActiveEditor());
    }

    @Test
    public void executeModifiedFile() {
        Session session = new Session("");
        String currentPath = System.getProperty("user.dir");
        String filePath = currentPath + "\\src\\main\\resources\\template.html";
        session.load(filePath);
        assertEquals(filePath, session.getActiveEditor().getFileName());
        InsertCommand insertCommand = new InsertCommand(session.getActiveEditor().getDocument(), "div", "id1", "body", "Hello HTML");
        insertCommand.execute();
        CloseCommand closeCommand = new CloseCommand(session);
        closeCommand.execute();
        assertNull(session.getActiveEditor());
    }

}
