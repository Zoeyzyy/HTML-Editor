package command.CommandImpl.historyCommand;

import command.commandImpl.displayCommand.PrintTreeCommand;
import command.commandImpl.editCommand.*;
import command.commandImpl.historyCommand.UndoCommand;
import document.HTMLDocument;
import history.CommandHistory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UndoCommandTest {
    @Test
    void execute() {
        HTMLDocument document = new HTMLDocument(null);
        document.init();
        CommandHistory commandHistory = new CommandHistory();

        // Test all undoable commands
        AppendCommand appendCommand = new AppendCommand(document, "div", "id1", "body", "hello world");
        appendCommand.execute();
        assertEquals("Hello world", document.findElementById("id1").getTextContent());
        commandHistory.push(appendCommand);

        InsertCommand insertCommand = new InsertCommand(document, "div", "id2", "body", "hello HTML");
        insertCommand.execute();
        assertEquals("Hello HTML", document.findElementById("id2").getTextContent());
        commandHistory.push(insertCommand);

        EditIDCommand editIDCommand = new EditIDCommand(document, "id1", "id3");
        editIDCommand.execute();
        assertEquals(null, document.findElementById("id1").getTextContent());
        assertEquals("id3", document.findElementById("id3").getId());
        commandHistory.push(editIDCommand);

        EditTextCommand editTextCommand = new EditTextCommand(document, "id3", "hello HTML");
        editTextCommand.execute();
        assertEquals("hello HTML", document.findElementById("id4").getTextContent());
        commandHistory.push(editTextCommand);

        // Test not undoable commands
        PrintTreeCommand printTreeCommand = new PrintTreeCommand(document);
        printTreeCommand.execute();
        commandHistory.push(printTreeCommand);

        DeleteCommand deleteCommand = new DeleteCommand(document, "id4");
        deleteCommand.execute();
        assertEquals(null, document.findElementById("id4").getTextContent());
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
        assertEquals(null, document.findElementById("id1").getTextContent());
        undoCommand.execute();
        assertEquals(null, document.findElementById("id2").getTextContent());
        undoCommand.execute();
        assertEquals(null, document.findElementById("id3").getTextContent());
    }
}
