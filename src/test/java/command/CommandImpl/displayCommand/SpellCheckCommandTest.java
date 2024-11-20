package command.CommandImpl.displayCommand;

import command.commandImpl.displayCommand.SpellCheckCommand;
import document.HTMLDocument;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SpellCheckCommandTest {
    @Test
    public void execute() {
        HTMLDocument doc = new HTMLDocument(null);
        doc.init();

        // printStream to capture output
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);
        PrintStream printStream = new PrintStream(byteArrayOutputStream);

        SpellCheckCommand spellCheckCommand = new SpellCheckCommand(doc, printStream);
        spellCheckCommand.execute();

        String output = byteArrayOutputStream.toString();
        assertEquals("<html>\n" +
                "  <head>\n" +
                "    <title></title>\n" +
                "  </head>\n" +
                "  <body>\n" +
                "  </body>\n" +
                "</html>", output);
        printStream.close();
    }
}
