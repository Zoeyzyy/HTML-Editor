package command.CommandImpl.displayCommand;

import command.commandImpl.displayCommand.PrintTreeCommand;
import command.commandImpl.editCommand.AppendCommand;
import document.HTMLDocument;
import editor.Editor;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PrintTreeCommandTest {
    @Test
    public void execute() {
        Editor editor = new Editor();
        editor.init();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(byteArrayOutputStream);

        PrintTreeCommand printTreeCommand = new PrintTreeCommand(editor, printStream);
        printTreeCommand.execute();

        String output = byteArrayOutputStream.toString();
        assertEquals("html\n" +
                "├── head\n" +
                "│ └── title\n" +
                "└── body\n", output);
        printStream.close();
    }

    @Test
    public void executeTreeSpellCheck() {
        Editor editor = new Editor();
        editor.init();

        AppendCommand appendCommand = new AppendCommand(editor, "div", "id1", "body", "Hello Wrold");
        appendCommand.execute();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(byteArrayOutputStream);

        PrintTreeCommand printTreeCommand = new PrintTreeCommand(editor, printStream);
        printTreeCommand.execute();

        String output = byteArrayOutputStream.toString();
        assertEquals("html\n" +
                "├── head\n" +
                "│ └── title\n" +
                "└── body\n"+
                "  └── [X]div\n"+
                "      └── Hello Wrold\n", output);
        printStream.close();
    }
}
