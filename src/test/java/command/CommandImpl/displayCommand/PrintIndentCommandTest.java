package command.CommandImpl.displayCommand;

import command.commandImpl.displayCommand.PrintIndentCommand;
import document.HTMLDocument;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PrintIndentCommandTest {
    @Test
    public void execute() {
        HTMLDocument doc = new HTMLDocument(null);
        doc.init();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(byteArrayOutputStream);

        PrintIndentCommand printIndentCommand = new PrintIndentCommand(doc, 1);
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
