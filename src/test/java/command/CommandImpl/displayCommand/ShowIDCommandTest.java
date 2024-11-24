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
        appendCommand.execute();

        ShowIDCommand showIDCommand = new ShowIDCommand(editor, true);
        showIDCommand.execute();
        assertTrue(editor.getDocument().getShowID());

        // test printTree
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(byteArrayOutputStream);
        PrintTreeCommand printTreeCommand = new PrintTreeCommand(editor, printStream);
        printTreeCommand.execute();
        String output = byteArrayOutputStream.toString();
        assertEquals("html\n" +
                "├── head\n" +
                "│   └── title\n" +
                "└── body\n" +
                "    └── div#id1\n" +
                "        └── Hello World\n",output);
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
        assertEquals("html\n" +
                "├── head\n" +
                "│   └── title\n" +
                "└── body\n" +
                "    └── div\n" +
                "        └── Hello World\n",output);
        printStream.close();
    }
}
