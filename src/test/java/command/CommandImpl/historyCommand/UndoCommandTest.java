package command.CommandImpl.historyCommand;

import command.commandImpl.displayCommand.PrintTreeCommand;
import command.commandImpl.editCommand.*;
import command.commandImpl.historyCommand.UndoCommand;
import editor.Editor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class UndoCommandTest {
    @Test
    void execute() {
        Editor editor = new Editor();
        editor.init();

        // Test all undoable commands
        AppendCommand appendCommand = new AppendCommand(editor, "div", "id1", "body", "Hello World");
        appendCommand.execute();
        editor.storeCommand(appendCommand);
        assertEquals("Hello World", editor.getDocument().findElementById("id1").getTextContent());

        InsertCommand insertCommand = new InsertCommand(editor, "div", "id2", "body", "Hello HTML");
        insertCommand.execute();
        editor.storeCommand(insertCommand);
        assertEquals("Hello HTML", editor.getDocument().findElementById("id2").getTextContent());

        EditIDCommand editIDCommand = new EditIDCommand(editor, "id1", "id3");
        editIDCommand.execute();
        editor.storeCommand(editIDCommand);
        assertNull(editor.getDocument().findElementById("id1"));
        assertEquals("id3", editor.getDocument().findElementById("id3").getId());

        EditTextCommand editTextCommand = new EditTextCommand(editor, "id3", "Hello HTML");
        editTextCommand.execute();
        editor.storeCommand(editTextCommand);
        assertEquals("Hello HTML", editor.getDocument().findElementById("id4").getTextContent());

        // Test not undoable commands
        PrintTreeCommand printTreeCommand = new PrintTreeCommand(editor);
        printTreeCommand.execute();
        editor.storeCommand(printTreeCommand);

        DeleteCommand deleteCommand = new DeleteCommand(editor, "id4");
        deleteCommand.execute();
        editor.storeCommand(deleteCommand);
        assertNull(editor.getDocument().findElementById("id4"));

        UndoCommand undoCommand = new UndoCommand(editor);
        undoCommand.execute();
        assertEquals("Hello HTML", editor.getDocument().findElementById("id4").getTextContent());
        undoCommand.execute();
        assertEquals("Hello HTML", editor.getDocument().findElementById("id3").getTextContent());
        undoCommand.execute();
        assertEquals("Hello HTML", editor.getDocument().findElementById("id2").getTextContent());
        undoCommand.execute();
        assertEquals("Hello world", editor.getDocument().findElementById("id1").getTextContent());
        undoCommand.execute();
        assertNull(editor.getDocument().findElementById("id1"));
        undoCommand.execute();
        assertNull(editor.getDocument().findElementById("id2"));
        undoCommand.execute();
        assertNull(editor.getDocument().findElementById("id3"));
    }
}
