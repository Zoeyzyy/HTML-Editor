package command.CommandImpl.editCommand;

import command.commandImpl.editCommand.EditTextCommand;
import document.HTMLDocument;
import editor.Editor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EditTextCommandTest {
    @Test
    public void executeAndUndo() {
        Editor editor = new Editor();
        editor.init();

        EditTextCommand editTextCommand1 = new EditTextCommand(editor, "body", "Hello World");
        editTextCommand1.execute();
        assertEquals("Hello World", editor.getDocument().findElementById("body").getTextContent());
        EditTextCommand editTextCommand2 = new EditTextCommand(editor, "body", "Hello World Again");
        editTextCommand2.execute();
        assertEquals("Hello World Again", editor.getDocument().findElementById("body").getTextContent());

        // test editTextCommand2 undo
        editTextCommand2.undo();
        assertEquals("Hello World", editor.getDocument().findElementById("body").getTextContent());
    }
}
