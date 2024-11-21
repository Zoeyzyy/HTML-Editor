package command.CommandImpl.historyCommand;

import command.commandImpl.editCommand.*;
import command.commandImpl.historyCommand.RedoCommand;
import command.commandImpl.historyCommand.UndoCommand;
import document.HTMLDocument;
import editor.Editor;
import history.CommandHistory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class RedoCommandTest {
    @Test
    void execute() {
        Editor editor = new Editor();
        editor.init();

        // Test all undoable commands
        InsertCommand insertCommand = new InsertCommand(editor, "div", "id1", "body", "Hello HTML");
        insertCommand.execute();
        assertEquals("Hello HTML", editor.getDocument().findElementById("id1").getTextContent());

        UndoCommand undoCommand = new UndoCommand(editor);
        undoCommand.execute();
        assertNull(editor.getDocument().findElementById("id1"));

        // Test redo
        RedoCommand redoCommand = new RedoCommand(editor);
        redoCommand.execute();
        assertEquals("Hello HTML", editor.getDocument().findElementById("id1").getTextContent());
    }
}
