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
        try {
            appendCommand.execute();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        // printStream to capture output
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);
        PrintStream printStream = new PrintStream(byteArrayOutputStream);

        SpellCheckCommand spellCheckCommand = new SpellCheckCommand(editor, printStream);
        try {
            spellCheckCommand.execute();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        String output = byteArrayOutputStream.toString();
        assertEquals("Id: id1[Hello World]\n", output);
        printStream.close();
    }
}
