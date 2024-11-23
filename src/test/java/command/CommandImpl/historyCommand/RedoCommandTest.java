package command.CommandImpl.historyCommand;

import command.commandImpl.editCommand.*;
import command.commandImpl.historyCommand.RedoCommand;
import command.commandImpl.historyCommand.UndoCommand;
import document.HTMLDocument;
import editor.Editor;
import exception.ElementNotFound;
import history.CommandHistory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RedoCommandTest {
    @Test
    void execute() {
        Editor editor = new Editor();
        editor.init();

        // Test all undoable commands
        AppendCommand appendCommand = new AppendCommand(editor, "div", "id1", "body", "Hello HTML");
        appendCommand.execute();
        editor.storeCommand(appendCommand);
        assertEquals("Hello HTML", editor.getDocument().findElementById("id1").getTextContent());

        UndoCommand undoCommand = new UndoCommand(editor);
        undoCommand.execute();
        assertThrows(ElementNotFound.class, () -> editor.getDocument().findElementById("id1"));

        // Test redo
        RedoCommand redoCommand = new RedoCommand(editor);
        redoCommand.execute();
        assertEquals("Hello HTML", editor.getDocument().findElementById("id1").getTextContent());
    }
}
