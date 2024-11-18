package command.CommandImpl.sessionCommand;

import command.commandImpl.sessionCommand.DirIndentCommand;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DirIndentCommandTest {
    @Test
    public void execute() {
        Session session = new Session("example.html");

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(byteArrayOutputStream);

        DirIndentCommand dirIndentCommand = new DirIndentCommand(session, 4, printStream);
        dirIndentCommand.execute();

        String output = byteArrayOutputStream.toString();
        // TODO: 文件夹内容
        assertEquals("./example.html", output);
        printStream.close();
    }
}
