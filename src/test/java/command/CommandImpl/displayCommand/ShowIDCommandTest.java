package command.CommandImpl.displayCommand;

import command.commandImpl.displayCommand.PrintTreeCommand;
import command.commandImpl.displayCommand.ShowIDCommand;
import document.HTMLDocument;
import editor.Editor;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class ShowIDCommandTest {
    @Test
    public void execute() {
        Editor editor = new Editor();
        editor.init();

        ShowIDCommand showIDCommand = new ShowIDCommand(editor, true);
        showIDCommand.execute();
        assertTrue(editor.getDocument().getShowID());

        // test printTree
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(byteArrayOutputStream);
        PrintTreeCommand printTreeCommand = new PrintTreeCommand(editor, printStream);
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

        showIDCommand = new ShowIDCommand(editor, false);
        showIDCommand.execute();
        assertFalse(editor.getDocument().getShowID());

        // test printTree
        byteArrayOutputStream = new ByteArrayOutputStream();
        printStream = new PrintStream(byteArrayOutputStream);
        printTreeCommand = new PrintTreeCommand(editor, printStream);
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
