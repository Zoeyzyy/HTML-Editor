package command.CommandImpl.historyCommand;

import command.commandImpl.displayCommand.PrintTreeCommand;
import command.commandImpl.editCommand.*;
import command.commandImpl.historyCommand.UndoCommand;
import document.HTMLDocument;
import history.CommandHistory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class UndoCommandTest {
    @Test
    void execute() {
        HTMLDocument document = new HTMLDocument(null);
        document.init();
        CommandHistory commandHistory = new CommandHistory();

        // Test all undoable commands
        AppendCommand appendCommand = new AppendCommand(document, "div", "id1", "body", "Hello World");
        appendCommand.execute();
        assertEquals("Hello World", document.findElementById("id1").getTextContent());
        commandHistory.push(appendCommand);

        InsertCommand insertCommand = new InsertCommand(document, "div", "id2", "body", "Hello HTML");
        insertCommand.execute();
        assertEquals("Hello HTML", document.findElementById("id2").getTextContent());
        commandHistory.push(insertCommand);

        EditIDCommand editIDCommand = new EditIDCommand(document, "id1", "id3");
        editIDCommand.execute();
        assertNull(document.findElementById("id1"));
        assertEquals("id3", document.findElementById("id3").getId());
        commandHistory.push(editIDCommand);

        EditTextCommand editTextCommand = new EditTextCommand(document, "id3", "Hello HTML");
        editTextCommand.execute();
        assertEquals("Hello HTML", document.findElementById("id4").getTextContent());
        commandHistory.push(editTextCommand);

        // Test not undoable commands
        PrintTreeCommand printTreeCommand = new PrintTreeCommand(document);
        printTreeCommand.execute();
        commandHistory.push(printTreeCommand);

        DeleteCommand deleteCommand = new DeleteCommand(document, "id4");
        deleteCommand.execute();
        assertNull(document.findElementById("id4"));
        commandHistory.push(deleteCommand);

        UndoCommand undoCommand = new UndoCommand(commandHistory);
        undoCommand.execute();
        assertEquals("Hello HTML", document.findElementById("id4").getTextContent());
        undoCommand.execute();
        assertEquals("Hello HTML", document.findElementById("id3").getTextContent());
        undoCommand.execute();
        assertEquals("Hello HTML", document.findElementById("id2").getTextContent());
        undoCommand.execute();
        assertEquals("Hello world", document.findElementById("id1").getTextContent());
        undoCommand.execute();
        assertNull(document.findElementById("id1"));
        undoCommand.execute();
        assertNull(document.findElementById("id2"));
        undoCommand.execute();
        assertNull(document.findElementById("id3"));
    }
}
