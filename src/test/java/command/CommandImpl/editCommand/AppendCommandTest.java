package command.CommandImpl.editCommand;

import command.commandImpl.editCommand.AppendCommand;
import document.HTMLDocument;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import editor.Editor;
import org.junit.jupiter.api.Test;

public class AppendCommandTest {
    @Test
    public void executeAndUndo() {
        Editor editor = new Editor();
        editor.init();

        AppendCommand appendCommand = new AppendCommand(editor, "div", "id1", "body", "Hello World");
        appendCommand.execute();
        assertEquals("Hello World", editor.getDocument().findElementById("id1").getTextContent());
        // TODO: test id1's parent is body

        // test undo insert
        appendCommand.undo();
        assertNull(editor.getDocument().findElementById("id1"));
    }
}
