package command.CommandImpl.editCommand;

import command.commandImpl.editCommand.EditIDCommand;
import command.commandImpl.editCommand.InsertCommand;
import document.HTMLDocument;
import editor.Editor;
import exception.ElementNotFound;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EditIDCommandTest {
    @Test
    public void executeAndUndo() {
        Editor editor = new Editor();
        editor.init();

        InsertCommand insertCommand = new InsertCommand(editor, "div", "id1", "body", "Hello World");
        insertCommand.execute();
        editor.storeCommand(insertCommand);
        assertEquals("id1", editor.getDocument().findElementById("id1").getId());

        EditIDCommand editIDCommand = new EditIDCommand(editor, "id1", "id2");
        editIDCommand.execute();
        editor.storeCommand(editIDCommand);
        assertThrows(ElementNotFound.class, () -> editor.getDocument().findElementById("id1"));
        assertEquals("id2", editor.getDocument().findElementById("id2").getId());

        // test undo
        editIDCommand.undo();
        assertEquals("id1", editor.getDocument().findElementById("id1").getId());
        assertThrows(ElementNotFound.class, () -> editor.getDocument().findElementById("id2"));
    }
}
