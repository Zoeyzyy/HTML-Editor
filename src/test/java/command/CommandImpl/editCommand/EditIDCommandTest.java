package command.CommandImpl.editCommand;

import command.commandImpl.editCommand.EditIDCommand;
import command.commandImpl.editCommand.InsertCommand;
import document.HTMLDocument;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class EditIDCommandTest {
    @Test
    public void executeAndUndo() {
        HTMLDocument doc = new HTMLDocument(null);
        doc.init();
        InsertCommand insertCommand = new InsertCommand(doc, "div", "id1", "body", "Hello World");
        insertCommand.execute();
        assertEquals("id1", doc.findElementById("id1").getId());

        EditIDCommand editIDCommand = new EditIDCommand(doc, "id1", "id2");
        editIDCommand.execute();
        assertNull(doc.findElementById("id1"));
        assertEquals("id2", doc.findElementById("id2").getId());

        // test undo
        editIDCommand.undo();
        assertEquals("id1", doc.findElementById("id1").getId());
        assertNull(doc.findElementById("id2"));
    }
}
