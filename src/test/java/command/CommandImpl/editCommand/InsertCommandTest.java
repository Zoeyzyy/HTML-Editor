package command.CommandImpl.editCommand;

import command.commandImpl.editCommand.AppendCommand;
import command.commandImpl.editCommand.EditTextCommand;
import command.commandImpl.editCommand.InsertCommand;
import document.HTMLDocument;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import editor.Editor;
import org.junit.jupiter.api.Test;

public class InsertCommandTest {

    @Test
    public void executeAndUndo() {
        Editor editor = new Editor();
        editor.init();

        AppendCommand appendCommand = new AppendCommand(editor, "div", "id1", "body", "Hello World");
        InsertCommand insertCommand = new InsertCommand(editor, "div", "id2", "id1", "Hello HTML");
        appendCommand.execute();
        insertCommand.execute();
        assertEquals("Hello HTML", editor.getDocument().findElementById("id2").getTextContent());
        // TODO: test id2 is before id1

        // test undo insert
        insertCommand.undo();
        assertNull(editor.getDocument().findElementById("id2"));
        assertEquals("Hello World", editor.getDocument().findElementById("id1").getTextContent());
    }
}
