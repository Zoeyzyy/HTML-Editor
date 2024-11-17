package test.java.command.CommandImpl;

import command.CanUndoCommand;
import command.Command;
import command.commandImpl.editCommand.InsertCommand;
import document.HTMLDocument;
import org.junit.jupiter.api.Test;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import org.junit.jupiter.api.Test;

public class InsertCommandTest {

    @Test
    public void executeAndUndo() {
        HTMLDocument doc = new HTMLDocument(null);
        InsertCommand insertCommand = new InsertCommand(doc, "div", "id1", "body", "hello world");
        insertCommand.execute();
        assertEquals("Hello World", doc.findElementById("id1").getTextContent());

        insertCommand.undo();
        assertEquals(null, doc.findElementById("id1").getTextContent());
    }
}
