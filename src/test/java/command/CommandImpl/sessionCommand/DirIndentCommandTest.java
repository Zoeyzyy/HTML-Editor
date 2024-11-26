package command.CommandImpl.sessionCommand;

import command.commandImpl.sessionCommand.DirIndentCommand;
import org.junit.jupiter.api.Test;
import session.Session;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DirIndentCommandTest {
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

        DirIndentCommand dirIndentCommand = new DirIndentCommand(session, 4, printStream);
        try {
            dirIndentCommand.execute();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        String output = byteArrayOutputStream.toString();
        // TODO: 文件夹内容
        assertEquals("resources\n" +
                "    example.html\n" +
                "    template.html\n" +
                "    Test.html*\n", output);
        printStream.close();
    }
}
