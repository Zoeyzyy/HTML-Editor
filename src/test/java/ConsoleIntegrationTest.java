import org.junit.jupiter.api.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class ConsoleIntegrationTest {
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;
    private Console console;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outputStream));
        System.setErr(new PrintStream(outputStream));
        outputStream.reset();
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    private void simulateUserInput(String... inputs) {
        String input = "load a.t\n"+String.join("\n", inputs) + "\nexit\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        console = new Console();
        console.run();
    }

    private String getOutput() {
        return outputStream.toString();
    }

    private String extractHtmlOutput(String fullOutput) {
        int startIndex = fullOutput.lastIndexOf("print-indent\n") + "print-indent\n".length();
        int endIndex = fullOutput.indexOf("shell>", startIndex);
        if (endIndex == -1) {
            endIndex = fullOutput.length();
        }
        return fullOutput.substring(startIndex, endIndex).trim();
    }

    @Test
    void testInitCommand() {
        simulateUserInput(
                "init",
                "print-indent"
        );

        String expectedHtml =
                "<html>\n" +
                        "  <head>\n" +
                        "        <title>\n" +
                        "        </title>\n" +
                        "    </head>\n" +
                        "<body>\n" +
                        "</body>\n" +
                        "</html>";

        String actualHtml = extractHtmlOutput(getOutput());
        Assertions.assertEquals(expectedHtml, actualHtml);
    }

    @Test
    void testInsertCommand() {
        simulateUserInput(
                "init",
                "insert div main-content body",
                "insert p paragraph-1 main-content Welcome to my website",
                "print-indent"
        );

        String expectedHtml =
                "<html>\n" +
                        "  <head>\n" +
                        "        <title>\n" +
                        "        </title>\n" +
                        "    </head>\n" +
                        "<body>\n" +
                        "    <div id=\"main-content\">\n" +
                        "        <p id=\"paragraph-1\">Welcome to my website</p>\n" +
                        "    </div>\n" +
                        "</body>\n" +
                        "</html>";

        String actualHtml = extractHtmlOutput(getOutput());
        Assertions.assertEquals(expectedHtml, actualHtml);
    }

    @Test
    void testAppendCommand() {
        simulateUserInput(
                "init",
                "append div content-wrapper body",
                "append p welcome-text content-wrapper Hello World!",
                "print-indent"
        );

        String expectedHtml =
                "<html>\n" +
                        "  <head>\n" +
                        "        <title>\n" +
                        "        </title>\n" +
                        "    </head>\n" +
                        "<body>\n" +
                        "    <div id=\"content-wrapper\">\n" +
                        "        <p id=\"welcome-text\">Hello World!</p>\n" +
                        "    </div>\n" +
                        "</body>\n" +
                        "</html>";

        String actualHtml = extractHtmlOutput(getOutput());
        Assertions.assertEquals(expectedHtml, actualHtml);
    }

    @Test
    void testEditIdCommand() {
        simulateUserInput(
                "init",
                "insert div old-id body",
                "edit-id old-id new-id",
                "print-indent"
        );

        String expectedHtml =
                "<html>\n" +
                        "  <head>\n" +
                        "        <title>\n" +
                        "        </title>\n" +
                        "    </head>\n" +
                        "<body>\n" +
                        "    <div id=\"new-id\">\n" +
                        "    </div>\n" +
                        "</body>\n" +
                        "</html>";

        String actualHtml = extractHtmlOutput(getOutput());
        Assertions.assertEquals(expectedHtml, actualHtml);
    }

    @Test
    void testEditTextCommand() {
        simulateUserInput(
                "init",
                "insert p test-p body Initial text",
                "edit-text test-p Updated content",
                "print-indent"
        );

        String expectedHtml =
                "<html>\n" +
                        "  <head>\n" +
                        "        <title>\n" +
                        "        </title>\n" +
                        "    </head>\n" +
                        "<body>\n" +
                        "    <p id=\"test-p\">Updated content</p>\n" +
                        "</body>\n" +
                        "</html>";

        String actualHtml = extractHtmlOutput(getOutput());
        Assertions.assertEquals(expectedHtml, actualHtml);
    }

    @Test
    void testDeleteCommand() {
        simulateUserInput(
                "init",
                "insert div to-delete body",
                "insert p test-p to-delete Test content",
                "delete to-delete",
                "print-indent"
        );

        String expectedHtml =
                "<html>\n" +
                        "  <head>\n" +
                        "        <title>\n" +
                        "        </title>\n" +
                        "    </head>\n" +
                        "<body>\n" +
                        "</body>\n" +
                        "</html>";

        String actualHtml = extractHtmlOutput(getOutput());
        Assertions.assertEquals(expectedHtml, actualHtml);
    }

    @Test
    void testUndoRedoCommands() {
        simulateUserInput(
                "init",
                "insert div test-div body",
                "print-indent",
                "undo",
                "print-indent",
                "redo",
                "print-indent"
        );

        String output = getOutput();
        String[] outputs = output.split("shell>");

        String withDivHtml =
                "<html>\n" +
                        "  <head>\n" +
                        "        <title>\n" +
                        "        </title>\n" +
                        "    </head>\n" +
                        "<body>\n" +
                        "    <div id=\"test-div\">\n" +
                        "    </div>\n" +
                        "</body>\n" +
                        "</html>";

        String withoutDivHtml =
                "<html>\n" +
                        "  <head>\n" +
                        "        <title>\n" +
                        "        </title>\n" +
                        "    </head>\n" +
                        "<body>\n" +
                        "</body>\n" +
                        "</html>";

        // 验证三个状态的输出
        Assertions.assertTrue(outputs[1].trim().endsWith(withDivHtml));  // 初始插入后
        Assertions.assertTrue(outputs[2].trim().endsWith(withoutDivHtml));  // undo后
        Assertions.assertTrue(outputs[3].trim().endsWith(withDivHtml));  // redo后
    }

    @Test
    void testComplexStructure() {
        simulateUserInput(
                "init",
                "append div container body",
                "append div header container",
                "append h1 title header Welcome to My Website",
                "append div content container",
                "append p paragraph-1 content First paragraph",
                "append ul list content",
                "append li item1 list Item 1",
                "append li item2 list Item 2",
                "print-indent"
        );

        String expectedHtml =
                "<html>\n" +
                        "  <head>\n" +
                        "        <title>\n" +
                        "        </title>\n" +
                        "    </head>\n" +
                        "<body>\n" +
                        "    <div id=\"container\">\n" +
                        "        <div id=\"header\">\n" +
                        "            <h1 id=\"title\">Welcome to My Website</h1>\n" +
                        "        </div>\n" +
                        "        <div id=\"content\">\n" +
                        "            <p id=\"paragraph-1\">First paragraph</p>\n" +
                        "            <ul id=\"list\">\n" +
                        "                <li id=\"item1\">Item 1</li>\n" +
                        "                <li id=\"item2\">Item 2</li>\n" +
                        "            </ul>\n" +
                        "        </div>\n" +
                        "    </div>\n" +
                        "</body>\n" +
                        "</html>";

        String actualHtml = extractHtmlOutput(getOutput());
        Assertions.assertEquals(expectedHtml, actualHtml);
    }

    @Test
    void testNestedStructureModification() {
        simulateUserInput(
                "init",
                "append div footer body",
                "append p copyright footer Copyright © 2024",
                "append p last-updated footer Last updated: 2024-01-01",
                "edit-text copyright Copyright © 2024 MyWebpage.com",
                "print-indent"
        );

        String expectedHtml =
                "<html>\n" +
                        "  <head>\n" +
                        "        <title>\n" +
                        "        </title>\n" +
                        "    </head>\n" +
                        "<body>\n" +
                        "    <div id=\"footer\">\n" +
                        "        <p id=\"copyright\">Copyright © 2024 MyWebpage.com</p>\n" +
                        "        <p id=\"last-updated\">Last updated: 2024-01-01</p>\n" +
                        "    </div>\n" +
                        "</body>\n" +
                        "</html>";

        String actualHtml = extractHtmlOutput(getOutput());
        Assertions.assertEquals(expectedHtml, actualHtml);
    }

    @Test
    void testInvalidCommands() {
        simulateUserInput(
                "invalid-command",
                "insert div",  // Missing parameters
                "edit-id",     // Missing parameters
                "delete",      // Missing parameters
                "print-indent"
        );

        String output = getOutput();
        Assertions.assertTrue(
                output.contains("Invalid command") ||
                        output.contains("Missing required parameters")
        );
    }
}