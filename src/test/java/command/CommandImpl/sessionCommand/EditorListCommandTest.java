package command.CommandImpl.sessionCommand;

import command.commandImpl.editCommand.AppendCommand;
import command.commandImpl.sessionCommand.EditorListCommand;
import org.junit.jupiter.api.Test;
import session.Session;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EditorListCommandTest {
    @Test
    public void execute() {
        Session session = new Session("default");
        String currentPath = System.getProperty("user.dir");
        String fileName = "\\src\\main\\resources\\Test.html";
        String filePath = currentPath + fileName;
        try {
            session.load(filePath);
        } catch (Exception e) {
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(byteArrayOutputStream);

        EditorListCommand editorListCommand = new EditorListCommand(session, printStream);
        try {
            editorListCommand.execute();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        String output = byteArrayOutputStream.toString();
        assertEquals(">" + filePath + "\r\n", output);
        printStream.close();

        // TODO: 新增编辑器，再次检测
        String fileName2 = "\\src\\main\\resources\\example.html";
        String filePath2 = currentPath + fileName2;
        try {
            session.load(filePath2);
        } catch (Exception e) {
        }
        AppendCommand appendCommand = new AppendCommand(session.getActiveEditor(), "div", "id1", "body", "Hello World");
        try {
            appendCommand.execute();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        session.getActiveEditor().storeCommand(appendCommand);

        ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
        PrintStream printStream2 = new PrintStream(byteArrayOutputStream2);

        EditorListCommand editorListCommand2 = new EditorListCommand(session, printStream2);
        try {
            editorListCommand2.execute();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        String output2 = byteArrayOutputStream2.toString();
        assertEquals(filePath + "\r\n" + ">" + filePath2 + "*\r\n", output2);
        printStream.close();
    }
}
