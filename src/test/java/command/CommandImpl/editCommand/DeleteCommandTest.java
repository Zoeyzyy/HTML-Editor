package command.CommandImpl.editCommand;

import command.commandImpl.editCommand.AppendCommand;
import command.commandImpl.editCommand.DeleteCommand;
import document.HTMLDocument;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeleteCommandTest {
    @Test
    public void executeAndUndo() {
        HTMLDocument doc = new HTMLDocument(null);
        AppendCommand appendCommand = new AppendCommand(doc, "div", "id1", "body", "hello world");
        appendCommand.execute();

        DeleteCommand deleteCommand = new DeleteCommand(doc, "id1" );
        deleteCommand.execute();
        assertEquals(null, doc.findElementById("id1").getTextContent());

        // test undo
        deleteCommand.undo();
        assertEquals("id1", doc.findElementById("id1").getId());
        assertEquals("hello world", doc.findElementById("id1").getTextContent());
//        assertEquals("body", doc.findElementById("id1").getParentNode());
        assertEquals("div", doc.findElementById("id1").getTagName());
    }
}
