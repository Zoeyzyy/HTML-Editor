package command.CommandImpl.editCommand;

import command.commandImpl.editCommand.AppendCommand;
import command.commandImpl.editCommand.InsertCommand;
import document.HTMLDocument;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class InsertCommandTest {

    @Test
    public void executeAndUndo() {
        HTMLDocument doc = new HTMLDocument(null);
        doc.init();
        AppendCommand appendCommand = new AppendCommand(doc, "div", "id1", "body", "Hello World");
        InsertCommand insertCommand = new InsertCommand(doc, "div", "id2", "id1", "Hello HTML");
        appendCommand.execute();
        insertCommand.execute();
        assertEquals("Hello HTML", doc.findElementById("id2").getTextContent());
        // TODO: test id2 is before id1

        // test undo insert
        insertCommand.undo();
        assertNull(doc.findElementById("id2"));
        assertEquals("Hello World", doc.findElementById("id1").getTextContent());
    }
}
