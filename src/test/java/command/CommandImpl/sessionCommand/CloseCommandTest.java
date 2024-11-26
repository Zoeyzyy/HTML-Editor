package command.CommandImpl.sessionCommand;

import command.commandImpl.editCommand.InsertCommand;
import command.commandImpl.sessionCommand.CloseCommand;
import org.junit.jupiter.api.Test;
import session.Session;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CloseCommandTest {
    @Test
    public void execute() {
        // TODO: close的创建 和 执行结果
        Session session = new Session("");
        String currentPath = System.getProperty("user.dir");
        String filePath = currentPath + "\\src\\main\\resources\\example.html";
        try {
            session.load(filePath);
        } catch (Exception e){

        }
        assertEquals(filePath, session.getActiveEditor().getFileName());
        CloseCommand closeCommand = new CloseCommand(session);
        closeCommand.execute();
        assertNull(session.getActiveEditor());
    }

    @Test
    public void executeModifiedFile() {
        Session session = new Session("");
        String currentPath = System.getProperty("user.dir");
        String filePath = currentPath + "\\src\\main\\resources\\example.html";
        try{
            session.load(filePath);
        } catch (Exception e){

        }
        assertEquals(filePath, session.getActiveEditor().getFileName());
        InsertCommand insertCommand = new InsertCommand(session.getActiveEditor(), "div", "id1", "body", "Hello HTML");
        insertCommand.execute();
        session.getActiveEditor().storeCommand(insertCommand);

        String simulatedInput = "y";
        InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);

        CloseCommand closeCommand = new CloseCommand(session);
        closeCommand.execute();
        assertNull(session.getActiveEditor());
    }

}
