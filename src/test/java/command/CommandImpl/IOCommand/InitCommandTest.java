package command.CommandImpl.IOCommand;

import command.commandImpl.IOCommand.InitCommand;
import command.commandImpl.displayCommand.PrintIndentCommand;
import document.HTMLDocument;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InitCommandTest {
    @Test
    public void execute() {
        HTMLDocument document = new HTMLDocument(null);

        InitCommand initCommand = new InitCommand(document);
        initCommand.execute();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(byteArrayOutputStream);

        PrintIndentCommand printIndentCommand = new PrintIndentCommand(document, 1);
        printIndentCommand.execute();

        String output = byteArrayOutputStream.toString();
        assertEquals("<html>\n" +
                " <head>\n" +
                "  <title></title>\n" +
                " </head>\n" +
                " <body>\n" +
                " </body>\n" +
                "</html>", output);
        printStream.close();
    }
}
