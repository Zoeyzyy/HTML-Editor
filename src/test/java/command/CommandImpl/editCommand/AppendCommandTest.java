package command.CommandImpl.editCommand;

import com.sun.jdi.request.DuplicateRequestException;
import command.commandImpl.editCommand.AppendCommand;
import document.HTMLDocument;

import editor.Editor;
import exception.ElementNotFound;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AppendCommandTest {
    @Test
    public void executeAndUndo() {
        Editor editor = new Editor();
        editor.init();

        AppendCommand appendCommand = new AppendCommand(editor, "div", "id1", "body", "Hello World");
        appendCommand.execute();
        assertEquals("Hello World", editor.getDocument().findElementById("id1").getTextContent());

        // deal with same id
        AppendCommand appendCommand2 = new AppendCommand(editor, "div", "id1", "body", "Hello HTML");
        assertThrows(DuplicateRequestException.class, ()->appendCommand2.execute());

        // test undo insert
        appendCommand.undo();
        assertThrows(ElementNotFound.class, () -> editor.getDocument().findElementById("id1"));
    }
}
