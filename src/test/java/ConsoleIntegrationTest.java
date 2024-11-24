import org.junit.jupiter.api.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

public class ConsoleIntegrationTest {
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;
    private Console console;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outputStream));
        System.setErr(new PrintStream(outputStream));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    private void simulateUserInput(String... inputs) {
        String input = String.join("\n", inputs) + "\nexit\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        console = new Console();
        console.run();
    }

    private String getOutput() {
        return outputStream.toString();
    }

    @Test
    void testInitCommand() {
        simulateUserInput("init");
        String output = getOutput();
        Assertions.assertTrue(output.contains("HTML template initialized"));
    }

    @Test
    void testInsertCommand() {
        simulateUserInput(
                "init",
                "insert div main-content body",
                "insert p paragraph-1 main-content Welcome to my website"
        );
        String output = getOutput();
        Assertions.assertTrue(output.contains("Element inserted successfully"));
    }

    @Test
    void testAppendCommand() {
        simulateUserInput(
                "init",
                "append div content-wrapper body",
                "append p welcome-text content-wrapper Hello World!"
        );
        String output = getOutput();
        Assertions.assertTrue(output.contains("Element appended successfully"));
    }

    @Test
    void testEditIdCommand() {
        simulateUserInput(
                "init",
                "insert div old-id body",
                "edit-id old-id new-id"
        );
        String output = getOutput();
        Assertions.assertTrue(output.contains("ID updated successfully"));
    }

    @Test
    void testEditTextCommand() {
        simulateUserInput(
                "init",
                "insert p test-p body Initial text",
                "edit-text test-p Updated content"
        );
        String output = getOutput();
        Assertions.assertTrue(output.contains("Text content updated successfully"));
    }

    @Test
    void testDeleteCommand() {
        simulateUserInput(
                "init",
                "insert div to-delete body",
                "delete to-delete"
        );
        String output = getOutput();
        Assertions.assertTrue(output.contains("Element deleted successfully"));
    }

    @Test
    void testUndoRedoCommands() {
        simulateUserInput(
                "init",
                "insert div test-div body",
                "undo",
                "redo"
        );
        String output = getOutput();
        Assertions.assertTrue(output.contains("Operation undone successfully"));
        Assertions.assertTrue(output.contains("Operation redone successfully"));
    }

    @Test
    void testComplexWorkflow() {
        simulateUserInput(
                "init",
                "insert div container body",
                "append div header container",
                "append h1 title header Welcome to My Website",
                "append div content container",
                "append p paragraph-1 content First paragraph",
                "edit-text paragraph-1 Updated first paragraph",
                "edit-id content main-content",
                "undo",
                "redo",
                "delete paragraph-1"
        );

        String output = getOutput();
        List<String> expectedMessages = Arrays.asList(
                "HTML template initialized",
                "Element inserted successfully",
                "Element appended successfully",
                "Element appended successfully",
                "Element appended successfully",
                "Element appended successfully",
                "Text content updated successfully",
                "ID updated successfully",
                "Operation undone successfully",
                "Operation redone successfully",
                "Element deleted successfully"
        );

        for (String message : expectedMessages) {
            Assertions.assertTrue(output.contains(message),
                    "Output should contain: " + message);
        }
    }

    @Test
    void testInvalidCommands() {
        simulateUserInput(
                "invalid-command",
                "insert div",  // Missing parameters
                "edit-id",     // Missing parameters
                "delete"       // Missing parameters
        );

        String output = getOutput();
        Assertions.assertTrue(output.contains("Invalid") ||
                output.contains("Unknown command"));
    }

    @Test
    void testEmptyInput() {
        simulateUserInput(
                "",
                "   ",
                "\t"
        );

        String output = getOutput();
        Assertions.assertTrue(output.contains("shell>"));
        Assertions.assertFalse(output.contains("Invalid command"));
    }
}