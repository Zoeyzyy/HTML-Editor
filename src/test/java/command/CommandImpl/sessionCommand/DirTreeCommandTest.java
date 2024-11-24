package command.CommandImpl.sessionCommand;

import command.commandImpl.sessionCommand.DirTreeCommand;
import org.junit.jupiter.api.Test;
import session.Session;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DirTreeCommandTest {
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

        DirTreeCommand dirTreeCommand = new DirTreeCommand(session, printStream);
        dirTreeCommand.execute();

        String output = byteArrayOutputStream.toString();
        // TODO: 树形结构的目录
        assertEquals("├── resources/\n" +
                "│   ├── template.html\n" +
                "│   └── Test.html*", output);
        printStream.close();
    }
}
