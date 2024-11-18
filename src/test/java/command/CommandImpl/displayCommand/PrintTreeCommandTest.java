package command.CommandImpl.displayCommand;

import command.commandImpl.displayCommand.PrintTreeCommand;
import document.HTMLDocument;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PrintTreeCommandTest {
    @Test
    public void execute() {
        HTMLDocument doc = new HTMLDocument(null);
        doc.init();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(byteArrayOutputStream);

        PrintTreeCommand printTreeCommand = new PrintTreeCommand(doc, printStream);
        printTreeCommand.execute();

        String output = byteArrayOutputStream.toString();
        assertEquals("html\n" +
                "├── head\n" +
                "│ └── title\n" +
                "└── body\n", output);
        printStream.close();
    }
}
