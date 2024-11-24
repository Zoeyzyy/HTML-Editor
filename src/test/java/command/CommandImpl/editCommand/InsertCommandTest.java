package command.CommandImpl.editCommand;

import command.commandImpl.editCommand.AppendCommand;
import command.commandImpl.editCommand.EditTextCommand;
import command.commandImpl.editCommand.InsertCommand;
import document.HTMLDocument;

import editor.Editor;
import exception.ElementNotFound;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InsertCommandTest {

    @Test
    public void executeAndUndo() {
        Editor editor = new Editor();
        editor.init();

        AppendCommand appendCommand = new AppendCommand(editor, "div", "id1", "body", "Hello World");
        InsertCommand insertCommand = new InsertCommand(editor, "div", "id2", "id1", "Hello HTML");
        appendCommand.execute();
        insertCommand.execute();
        editor.storeCommand(insertCommand);
        editor.storeCommand(appendCommand);
        assertEquals("Hello HTML", editor.getDocument().findElementById("id2").getTextContent());
        // test id2 is before id1
        assertEquals("id1", editor.getDocument().findElementById("id2").getNextSibling().getId());

        // test undo insert
        insertCommand.undo();
        assertThrows(ElementNotFound.class, () -> editor.getDocument().findElementById("id2"));
        assertEquals("Hello World", editor.getDocument().findElementById("id1").getTextContent());
    }
}
