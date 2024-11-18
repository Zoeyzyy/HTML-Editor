package command.CommandImpl.editCommand;

import command.commandImpl.editCommand.EditTextCommand;
import document.HTMLDocument;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EditTextCommandTest {
    @Test
    public void executeAndUndo() {
        HTMLDocument doc = new HTMLDocument(null);
        doc.init();
        EditTextCommand editTextCommand1 = new EditTextCommand(doc, "body", "hello world");
        editTextCommand1.execute();
        assertEquals("Hello World", doc.findElementById("body").getTextContent());
        EditTextCommand editTextCommand2 = new EditTextCommand(doc, "body", "hello world again");
        editTextCommand2.execute();
        assertEquals("Hello World Again", doc.findElementById("body").getTextContent());

        // test editTextCommand2 undo
        editTextCommand2.undo();
        assertEquals("Hello World", doc.findElementById("body").getTextContent());
    }
}
