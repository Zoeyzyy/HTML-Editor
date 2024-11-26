package command.CommandImpl.displayCommand;

import command.commandImpl.displayCommand.PrintIndentCommand;
import document.HTMLDocument;
import editor.Editor;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PrintIndentCommandTest {
    @Test
    public void execute() {
        Editor editor = new Editor();
        editor.init();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(byteArrayOutputStream);

        PrintIndentCommand printIndentCommand = new PrintIndentCommand(editor, 1, printStream);
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
