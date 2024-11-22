package command.CommandImpl.historyCommand;

import command.commandImpl.displayCommand.PrintTreeCommand;
import command.commandImpl.editCommand.*;
import command.commandImpl.historyCommand.UndoCommand;
import editor.Editor;
import exception.ElementNotFound;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
        assertThrows(ElementNotFound.class, () -> editor.getDocument().findElementById("id1"));
        assertEquals("id3", editor.getDocument().findElementById("id3").getId());

        EditTextCommand editTextCommand = new EditTextCommand(editor, "id3", "Hello HTML");
        editTextCommand.execute();
        editor.storeCommand(editTextCommand);
        assertEquals("Hello HTML", editor.getDocument().findElementById("id3").getTextContent());

        // Test not undoable commands
        PrintTreeCommand printTreeCommand = new PrintTreeCommand(editor);
        printTreeCommand.execute();
        editor.storeCommand(printTreeCommand);

        DeleteCommand deleteCommand = new DeleteCommand(editor, "id3");
        deleteCommand.execute();
        editor.storeCommand(deleteCommand);
        assertThrows(ElementNotFound.class, () -> editor.getDocument().findElementById("id3"));

        UndoCommand undoCommand = new UndoCommand(editor);
        undoCommand.execute();//undo delete
        assertEquals("Hello HTML", editor.getDocument().findElementById("id3").getTextContent());
        undoCommand.execute();// drop PrintTreeCommand,undo editText
        assertEquals("Hello World", editor.getDocument().findElementById("id3").getTextContent());
        undoCommand.execute();//undo editID
        assertEquals("Hello World", editor.getDocument().findElementById("id1").getTextContent());
        assertThrows(ElementNotFound.class, () -> editor.getDocument().findElementById("id3"));
        undoCommand.execute();// undo insert
        assertThrows(ElementNotFound.class, () -> editor.getDocument().findElementById("id2"));
        undoCommand.execute();// undo append
        assertThrows(ElementNotFound.class, () -> editor.getDocument().findElementById("id1"));
    }
}
