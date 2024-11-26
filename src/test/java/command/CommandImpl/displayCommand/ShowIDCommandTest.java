package command.CommandImpl.displayCommand;

import command.commandImpl.displayCommand.PrintTreeCommand;
import command.commandImpl.displayCommand.ShowIDCommand;
import command.commandImpl.editCommand.AppendCommand;
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

        AppendCommand appendCommand = new AppendCommand(editor, "div", "id1", "body", "Hello World");
        try {
            appendCommand.execute();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        ShowIDCommand showIDCommand = new ShowIDCommand(editor, true);
        try {
            showIDCommand.execute();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        assertTrue(editor.getDocument().getShowID());

        // test printTree
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(byteArrayOutputStream);
        PrintTreeCommand printTreeCommand = new PrintTreeCommand(editor, printStream);
        try {
            printTreeCommand.execute();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        String output = byteArrayOutputStream.toString();
        assertEquals("html\n" +
                "├── head\n" +
                "│   └── title\n" +
                "└── body\n" +
                "    └── div#id1\n" +
                "        └── Hello World\n",output);
        printStream.close();

        showIDCommand = new ShowIDCommand(editor, false);
        try {
            showIDCommand.execute();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        assertFalse(editor.getDocument().getShowID());

        // test printTree
        byteArrayOutputStream = new ByteArrayOutputStream();
        printStream = new PrintStream(byteArrayOutputStream);
        printTreeCommand = new PrintTreeCommand(editor, printStream);
        try {
            printTreeCommand.execute();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        output = byteArrayOutputStream.toString();
        assertEquals("html\n" +
                "├── head\n" +
                "│   └── title\n" +
                "└── body\n" +
                "    └── div\n" +
                "        └── Hello World\n",output);
        printStream.close();
    }
}
