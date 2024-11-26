package command.CommandImpl.editCommand;

import command.commandImpl.editCommand.AppendCommand;
import editor.Editor;
import exception.ElementDuplicateID;
import exception.ElementNotFound;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;

import static org.junit.jupiter.api.Assertions.*;

public class AppendCommandTest {
    @Test
    public void executeAndUndo() {
        Editor editor = new Editor();
        editor.init();

        AppendCommand appendCommand = new AppendCommand(editor, "div", "id1", "body", "Hello World");
        try {
            appendCommand.execute();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        editor.storeCommand(appendCommand);

        assertEquals("Hello World", editor.getDocument().findElementById("id1").getTextContent());
        assertTrue(editor.isModified());
        // deal with same id
        AppendCommand appendCommand2 = new AppendCommand(editor, "div", "id1", "body", "Hello HTML");
        assertThrows(ElementDuplicateID.class, () -> appendCommand2.execute());

        // test undo insert
        try {
            appendCommand.undo();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        assertThrows(ElementNotFound.class, () -> editor.getDocument().findElementById("id1"));
    }
}
