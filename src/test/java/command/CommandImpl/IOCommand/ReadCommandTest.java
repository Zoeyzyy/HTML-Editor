package command.CommandImpl.IOCommand;

import command.commandImpl.IOCommand.ReadCommand;
import command.commandImpl.displayCommand.PrintIndentCommand;
import document.HTMLDocument;
import editor.Editor;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReadCommandTest {
    @Test
    public void execute() {
        Editor editor = new Editor();

        ReadCommand readCommand = new ReadCommand(editor, "\\src\\main\\resources\\template.html");
        readCommand.execute();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(byteArrayOutputStream);

        PrintIndentCommand printIndentCommand = new PrintIndentCommand(editor, 2);
        printIndentCommand.execute();

        String output = byteArrayOutputStream.toString();
        assertEquals("<html>\n" +
                "  <head>\n" +
                "    <title>\n" +
                "    </title>\n" +
                "  </head>\n" +
                "<body>\n" +
                "</body>\n" +
                "</html>", output);
        printStream.close();
    }
}
