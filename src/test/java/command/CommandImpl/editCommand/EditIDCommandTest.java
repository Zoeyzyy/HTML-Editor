package command.CommandImpl.editCommand;

import command.commandImpl.editCommand.EditIDCommand;
import command.commandImpl.editCommand.InsertCommand;
import document.HTMLDocument;
import editor.Editor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class EditIDCommandTest {
    @Test
    public void executeAndUndo() {
        Editor editor = new Editor();
        editor.init();

        InsertCommand insertCommand = new InsertCommand(editor, "div", "id1", "body", "Hello World");
        insertCommand.execute();
        assertEquals("id1", editor.getDocument().findElementById("id1").getId());

        EditIDCommand editIDCommand = new EditIDCommand(editor, "id1", "id2");
        editIDCommand.execute();
        assertNull(editor.getDocument().findElementById("id1"));
        assertEquals("id2", editor.getDocument().findElementById("id2").getId());

        // test undo
        editIDCommand.undo();
        assertEquals("id1", editor.getDocument().findElementById("id1").getId());
        assertNull(editor.getDocument().findElementById("id2"));
    }
}
