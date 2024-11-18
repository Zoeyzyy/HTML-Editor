package command.CommandImpl.editCommand;

import command.commandImpl.editCommand.EditIDCommand;
import command.commandImpl.editCommand.InsertCommand;
import document.HTMLDocument;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EditIDCommandTest {
    @Test
    public void executeAndUndo() {
        HTMLDocument doc = new HTMLDocument(null);
        doc.init();
        InsertCommand insertCommand = new InsertCommand(doc, "div", "id1", "body", "hello world");
        insertCommand.execute();
        assertEquals("id1", doc.findElementById("id1").getId());

        EditIDCommand editIDCommand = new EditIDCommand(doc, "id1", "id2");
        editIDCommand.execute();
        assertEquals(null, doc.findElementById("id1").getId());
        assertEquals("id2", doc.findElementById("id2").getId());

        // test undo
        editIDCommand.undo();
        assertEquals("id1", doc.findElementById("id1").getId());
        assertEquals(null, doc.findElementById("id2").getId());
    }
}
