package command.CommandImpl.editCommand;

import command.commandImpl.editCommand.AppendCommand;
import document.HTMLDocument;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class AppendCommandTest {
    @Test
    public void executeAndUndo() {
        HTMLDocument doc = new HTMLDocument(null);
        doc.init();
        AppendCommand appendCommand = new AppendCommand(doc, "div", "id1", "body", "hello World");
        appendCommand.execute();
        assertEquals("Hello world", doc.findElementById("id1").getTextContent());
        // TODO: test id1's parent is body

        // test undo insert
        appendCommand.undo();
        assertEquals(null, doc.findElementById("id1").getTextContent());
    }
}
