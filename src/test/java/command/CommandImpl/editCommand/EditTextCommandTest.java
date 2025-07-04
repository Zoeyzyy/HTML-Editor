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
        try {
            editTextCommand1.execute();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        editor.storeCommand(editTextCommand1);
        assertEquals("Hello World", editor.getDocument().findElementById("body").getTextContent());

        EditTextCommand editTextCommand2 = new EditTextCommand(editor, "body", "Hello World Again");
        try {
            editTextCommand2.execute();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        editor.storeCommand(editTextCommand2);
        assertEquals("Hello World Again", editor.getDocument().findElementById("body").getTextContent());

        // test editTextCommand2 undo
        try {
            editTextCommand2.undo();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        assertEquals("Hello World", editor.getDocument().findElementById("body").getTextContent());
    }
}
