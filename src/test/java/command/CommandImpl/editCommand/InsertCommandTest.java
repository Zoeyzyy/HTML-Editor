package command.CommandImpl.editCommand;

import command.commandImpl.editCommand.AppendCommand;
import command.commandImpl.editCommand.InsertCommand;
import document.HTMLDocument;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class InsertCommandTest {

    @Test
    public void executeAndUndo() {
        HTMLDocument doc = new HTMLDocument(null);
        doc.init();
        AppendCommand appendCommand = new AppendCommand(doc, "div", "id1", "body", "hello World");
        InsertCommand insertCommand = new InsertCommand(doc, "div", "id2", "id1", "hello HTML");
        appendCommand.execute();
        insertCommand.execute();
        assertEquals("Hello HTML", doc.findElementById("id2").getTextContent());
        // TODO: test id2 is before id1

        // test undo insert
        insertCommand.undo();
        assertEquals(null, doc.findElementById("id2").getTextContent());
        assertEquals("Hello World", doc.findElementById("id1").getTextContent());
    }
}
