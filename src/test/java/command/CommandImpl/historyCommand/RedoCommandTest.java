package command.CommandImpl.historyCommand;

import command.commandImpl.editCommand.*;
import command.commandImpl.historyCommand.RedoCommand;
import command.commandImpl.historyCommand.UndoCommand;
import document.HTMLDocument;
import history.CommandHistory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RedoCommandTest {
    @Test
    void execute() {
        HTMLDocument document = new HTMLDocument(null);
        document.init();
        CommandHistory commandHistory = new CommandHistory();

        // Test all undoable commands
        InsertCommand insertCommand = new InsertCommand(document, "div", "id1", "body", "hello HTML");
        insertCommand.execute();
        assertEquals("Hello HTML", document.findElementById("id1").getTextContent());
        commandHistory.push(insertCommand);

        UndoCommand undoCommand = new UndoCommand(commandHistory);
        undoCommand.execute();
        assertEquals(null, document.findElementById("id1").getTextContent());

        // Test redo
        RedoCommand redoCommand = new RedoCommand(commandHistory);
        redoCommand.execute();
        assertEquals("Hello HTML", document.findElementById("id1").getTextContent());
    }
}
