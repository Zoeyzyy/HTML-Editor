package command.CommandImpl.editCommand;

import command.commandImpl.editCommand.AppendCommand;
import command.commandImpl.editCommand.DeleteCommand;
import document.HTMLDocument;
import editor.Editor;
import exception.ElementNotFound;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DeleteCommandTest {
    @Test
    public void executeAndUndo() {
        Editor editor = new Editor();
        editor.init();

        AppendCommand appendCommand = new AppendCommand(editor, "div", "id1", "body", "Hello World");
        try {
            appendCommand.execute();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        editor.storeCommand(appendCommand);

        DeleteCommand deleteCommand = new DeleteCommand(editor, "id1");
        try {
            deleteCommand.execute();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        editor.storeCommand(deleteCommand);
        assertThrows(ElementNotFound.class, () -> editor.getDocument().findElementById("id1"));

        // test undo
        try {
            deleteCommand.undo();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        assertNotNull(editor.getDocument().findElementById("id1"));
        assertEquals("id1", editor.getDocument().findElementById("id1").getId());
        assertEquals("Hello World", editor.getDocument().findElementById("id1").getTextContent());
//        assertEquals("body", doc.findElementById("id1").getParentNode());
        assertEquals("div", editor.getDocument().findElementById("id1").getTagName());
    }
}
