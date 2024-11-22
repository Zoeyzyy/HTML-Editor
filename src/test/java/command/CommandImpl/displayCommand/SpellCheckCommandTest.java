package command.CommandImpl.displayCommand;

import command.commandImpl.displayCommand.SpellCheckCommand;
import command.commandImpl.editCommand.AppendCommand;
import document.HTMLDocument;
import editor.Editor;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SpellCheckCommandTest {
    @Test
    public void execute() {
        Editor editor = new Editor();
        editor.init();

        AppendCommand appendCommand = new AppendCommand(editor, "div", "id1", "body", "Hello Wrold");
        appendCommand.execute();

        // printStream to capture output
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);
        PrintStream printStream = new PrintStream(byteArrayOutputStream);

        SpellCheckCommand spellCheckCommand = new SpellCheckCommand(editor, printStream);
        spellCheckCommand.execute();

        String output = byteArrayOutputStream.toString();
        assertEquals("Hello Wrold", output);
        printStream.close();
    }
}
