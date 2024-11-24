import org.junit.jupiter.api.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class ConsoleIntegrationTest {
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;
    private final PrintStream testOut = new PrintStream(outputStream);
    private Console console;

    @BeforeEach
    void setUp() {
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
        console = new Console(testOut);
        console.run();
    }

    private String getOutput() {
        return outputStream.toString();
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
                        "    <title>\n" +
                        "    </title>\n" +
                        "  </head>\n" +
                        "  <body>\n" +
                        "  </body>\n" +
                        "</html>";


        Assertions.assertEquals(expectedHtml, getOutput());
    }

    @Test
    void testInsertCommand() {
        simulateUserInput(
                "init",
                "append div main-content body",
                "append p paragraph-1 main-content Welcome to my website",
                "print-indent"
        );

        String expectedHtml =
                "<html>\n" +
                        "  <head>\n" +
                        "    <title>\n" +
                        "    </title>\n" +
                        "  </head>\n" +
                        "  <body>\n" +
                        "    <div id=\"main-content\">\n" +
                        "      <p id=\"paragraph-1\">\n" +
                        "        Welcome to my website\n" +
                        "      </p>\n" +
                        "    </div>\n" +
                        "  </body>\n" +
                        "</html>";

        Assertions.assertEquals(expectedHtml, getOutput());
    }

    @Test
    void testAppendCommand() {
        simulateUserInput(
                "init",
                "append div content-wrapper body",
                "append p welcome-text content-wrapper Hello World!",
                "print-indent"
        );

        String expectedHtml ="<html>\n" +
                "  <head>\n" +
                "    <title>\n" +
                "    </title>\n" +
                "  </head>\n" +
                "  <body>\n" +
                "    <div id=\"content-wrapper\">\n" +
                "      <p id=\"welcome-text\">\n" +
                "        Hello World!\n" +
                "      </p>\n" +
                "    </div>\n" +
                "  </body>\n" +
                "</html>";
        Assertions.assertEquals(expectedHtml, getOutput());
    }

    @Test
    void testEditIdCommand() {
        simulateUserInput(
                "init",
                "append div old-id body",
                "edit-id old-id new-id",
                "print-indent"
        );

        String expectedHtml =
                "<html>\n" +
                        "  <head>\n" +
                        "    <title>\n" +
                        "    </title>\n" +
                        "  </head>\n" +
                        "  <body>\n" +
                        "    <div id=\"new-id\">\n" +
                        "    </div>\n" +
                        "  </body>\n" +
                        "</html>";


        Assertions.assertEquals(expectedHtml, getOutput());
    }

    @Test
    void testEditTextCommand() {
        simulateUserInput(
                "init",
                "append p test-p body Initial text",
                "edit-text test-p Updated content",
                "print-indent"
        );

        String expectedHtml =
                "<html>\n" +
                        "  <head>\n" +
                        "    <title>\n" +
                        "    </title>\n" +
                        "  </head>\n" +
                        "  <body>\n" +
                        "    <p id=\"test-p\">\n" +
                        "      Updated content\n" +
                        "    </p>\n" +
                        "  </body>\n" +
                        "</html>";

        Assertions.assertEquals(expectedHtml, getOutput());
    }

    @Test
    void testDeleteCommand() {
        simulateUserInput(
                "init",
                "insert div to-delete body",
                "append p test-p to-delete Test content",
                "delete to-delete",
                "print-indent"
        );

        String expectedHtml ="<html>\n" +
                "  <head>\n" +
                "    <title>\n" +
                "    </title>\n" +
                "  </head>\n" +
                "  <body>\n" +
                "  </body>\n" +
                "</html>";

        Assertions.assertEquals(expectedHtml, getOutput());
    }

    @Test
    void testUndoRedoCommands() {
        simulateUserInput(
                "init",
                "append div test-div body",
                "print-indent",
                "undo",
                "print-indent",
                "redo",
                "print-indent"
        );

        String[] outputs = getOutput().split("<html>");

        String withDivHtml =
                "\n" +
                        "  <head>\n" +
                        "    <title>\n" +
                        "    </title>\n" +
                        "  </head>\n" +
                        "  <body>\n" +
                        "    <div id=\"test-div\">\n" +
                        "    </div>\n" +
                        "  </body>\n" +
                        "</html>";


        String withoutDivHtml =
                "\n" +
                        "  <head>\n" +
                        "    <title>\n" +
                        "    </title>\n" +
                        "  </head>\n" +
                        "  <body>\n" +
                        "  </body>\n" +
                        "</html>";
        // 验证三个状态的输出
        Assertions.assertEquals(withDivHtml,outputs[1]);
        Assertions.assertEquals(withoutDivHtml,outputs[2]);
        Assertions.assertEquals(withDivHtml,outputs[3] );
    }

    @Test
    void testComplexStructure() {
        simulateUserInput(
                "init",
                "append div container body",
                "append div header container",
                "append h1 title_1 header Welcome to My Website",
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
                        "    <title>\n" +
                        "    </title>\n" +
                        "  </head>\n" +
                        "  <body>\n" +
                        "    <div id=\"container\">\n" +
                        "      <div id=\"header\">\n" +
                        "        <h1 id=\"title_1\">\n" +
                        "          Welcome to My Website\n" +
                        "        </h1>\n" +
                        "      </div>\n" +
                        "      <div id=\"content\">\n" +
                        "        <p id=\"paragraph-1\">\n" +
                        "          First paragraph\n" +
                        "        </p>\n" +
                        "        <ul id=\"list\">\n" +
                        "          <li id=\"item1\">\n" +
                        "            Item 1\n" +
                        "          </li>\n" +
                        "          <li id=\"item2\">\n" +
                        "            Item 2\n" +
                        "          </li>\n" +
                        "        </ul>\n" +
                        "      </div>\n" +
                        "    </div>\n" +
                        "  </body>\n" +
                        "</html>";


        Assertions.assertEquals(expectedHtml, getOutput());
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

        String expectedHtml ="<html>\n" +
                "  <head>\n" +
                "    <title>\n" +
                "    </title>\n" +
                "  </head>\n" +
                "  <body>\n" +
                "    <div id=\"footer\">\n" +
                "      <p id=\"copyright\">\n" +
                "        Copyright © 2024 MyWebpage.com\n" +
                "      </p>\n" +
                "      <p id=\"last-updated\">\n" +
                "        Last updated: 2024-01-01\n" +
                "      </p>\n" +
                "    </div>\n" +
                "  </body>\n" +
                "</html>";

        Assertions.assertEquals(expectedHtml, getOutput());
    }
}