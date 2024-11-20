package command.CommandImpl.displayCommand;

import command.commandImpl.displayCommand.PrintTreeCommand;
import command.commandImpl.displayCommand.ShowIDCommand;
import document.HTMLDocument;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class ShowIDCommandTest {
    @Test
    public void execute() {
        HTMLDocument doc = new HTMLDocument(null);
        doc.init();

        ShowIDCommand showIDCommand = new ShowIDCommand(doc, true);
        showIDCommand.execute();
        assertTrue(doc.getShowID());

        // test printTree
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(byteArrayOutputStream);
        PrintTreeCommand printTreeCommand = new PrintTreeCommand(doc, printStream);
        printTreeCommand.execute();
        String output = byteArrayOutputStream.toString();
        assertEquals("<html>\n" +
                "  <head>\n" +
                "    <title>" +
                "    </title>#title\n" +
                "  </head>#head\n" +
                "  <body></body>#body\n" +
                "</html>#html", output);
        printStream.close();

        showIDCommand = new ShowIDCommand(doc, false);
        showIDCommand.execute();
        assertFalse(doc.getShowID());

        // test printTree
        byteArrayOutputStream = new ByteArrayOutputStream();
        printStream = new PrintStream(byteArrayOutputStream);
        printTreeCommand = new PrintTreeCommand(doc, printStream);
        printTreeCommand.execute();
        output = byteArrayOutputStream.toString();
        assertEquals("<html>\n" +
                "  <head>\n" +
                "    <title>\n" +
                "    </title>\n" +
                "  </head>\n" +
                "  <body></body>\n" +
                "</html>", output);
        printStream.close();
    }
}
