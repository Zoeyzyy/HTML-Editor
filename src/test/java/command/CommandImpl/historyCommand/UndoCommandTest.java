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
        try {
            appendCommand.execute();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        editor.storeCommand(appendCommand);
        assertEquals("Hello World", editor.getDocument().findElementById("id1").getTextContent());

        InsertCommand insertCommand = new InsertCommand(editor, "div", "id2", "body", "Hello HTML");
        try {
            insertCommand.execute();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        editor.storeCommand(insertCommand);
        assertEquals("Hello HTML", editor.getDocument().findElementById("id2").getTextContent());

        EditIDCommand editIDCommand = new EditIDCommand(editor, "id1", "id3");
        try {
            editIDCommand.execute();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        editor.storeCommand(editIDCommand);
        assertThrows(ElementNotFound.class, () -> editor.getDocument().findElementById("id1"));
        assertEquals("id3", editor.getDocument().findElementById("id3").getId());

        EditTextCommand editTextCommand = new EditTextCommand(editor, "id3", "Hello HTML");
        try {
            editTextCommand.execute();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        editor.storeCommand(editTextCommand);
        assertEquals("Hello HTML", editor.getDocument().findElementById("id3").getTextContent());

        // Test not undoable commands
        PrintTreeCommand printTreeCommand = new PrintTreeCommand(editor);
        try {
            printTreeCommand.execute();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        editor.storeCommand(printTreeCommand);

        DeleteCommand deleteCommand = new DeleteCommand(editor, "id3");
        try {
            deleteCommand.execute();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        editor.storeCommand(deleteCommand);
        assertThrows(ElementNotFound.class, () -> editor.getDocument().findElementById("id3"));

        UndoCommand undoCommand = new UndoCommand(editor);
        try {
            undoCommand.execute();//undo delete
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        assertEquals("Hello HTML", editor.getDocument().findElementById("id3").getTextContent());
        try {
            undoCommand.execute();// drop PrintTreeCommand,undo editText
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        assertEquals("Hello World", editor.getDocument().findElementById("id3").getTextContent());
        try {
            undoCommand.execute();//undo editID
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        assertEquals("Hello World", editor.getDocument().findElementById("id1").getTextContent());
        assertThrows(ElementNotFound.class, () -> editor.getDocument().findElementById("id3"));
        try {
            undoCommand.execute();// undo insert
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        assertThrows(ElementNotFound.class, () -> editor.getDocument().findElementById("id2"));
        try {
            undoCommand.execute();// undo append
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        assertThrows(ElementNotFound.class, () -> editor.getDocument().findElementById("id1"));
    }
}
