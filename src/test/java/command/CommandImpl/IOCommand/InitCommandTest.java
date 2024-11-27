package command.CommandImpl.IOCommand;

import command.commandImpl.IOCommand.InitCommand;
import command.commandImpl.displayCommand.PrintIndentCommand;
import document.HTMLDocument;
import editor.Editor;
import org.junit.jupiter.api.Test;
import session.Session;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InitCommandTest {
    @Test
    public void execute() {
        Session session = new Session("default");

        InitCommand initCommand = new InitCommand(session);
        try {
            initCommand.execute();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(byteArrayOutputStream);

        PrintIndentCommand printIndentCommand = new PrintIndentCommand(session.getActiveEditor(), 1, printStream);
        try {
            printIndentCommand.execute();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        String output = byteArrayOutputStream.toString();
        assertEquals("<html>\n" +
                " <head>\n" +
                "  <title>\n" +
                "  </title>\n" +
                " </head>\n" +
                " <body>\n" +
                " </body>\n" +
                "</html>", output);
        printStream.close();
    }
}
