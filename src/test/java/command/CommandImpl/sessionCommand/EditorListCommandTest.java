package command.CommandImpl.sessionCommand;

import command.commandImpl.sessionCommand.EditorListCommand;
import org.junit.jupiter.api.Test;
import session.Session;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EditorListCommandTest {
    @Test
    public void execute() {
        Session session = new Session("./src/main/java/resource/Test.html");

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(byteArrayOutputStream);

        EditorListCommand editorListCommand = new EditorListCommand(session, printStream);
        editorListCommand.execute();

        String output = byteArrayOutputStream.toString();
        assertEquals(">./src/main/java/resource/Test.html", output);
        printStream.close();

        // TODO: 新增编辑器，再次检测
    }
}
