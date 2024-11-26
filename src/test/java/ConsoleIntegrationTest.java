import exception.ElementBadRemoved;
import exception.ElementDuplicateID;
import exception.ElementNotFound;
import org.junit.jupiter.api.*;

import java.io.*;

public class ConsoleIntegrationTest {
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;
    private final PrintStream testOut = new PrintStream(outputStream);
    private final ByteArrayOutputStream errorStream = new ByteArrayOutputStream();
    private final PrintStream testErr = new PrintStream(errorStream);
    private Console console;

    @BeforeEach
    void setUp() {
        System.setErr(testErr);
        outputStream.reset();
        errorStream.reset();
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

    private String getErrorOutput() {
        return errorStream.toString().trim();
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
    @Test
    void testAppendToNonExistentParent() {
        simulateUserInput(
                "init",
                "append div test-div non-existent-parent",
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
    void testDuplicateId() {
        simulateUserInput(
                "init",
                "append div same-id body",
                "append p same-id body",
                "print-indent"
        );

        String expectedHtml =
                "<html>\n" +
                        "  <head>\n" +
                        "    <title>\n" +
                        "    </title>\n" +
                        "  </head>\n" +
                        "  <body>\n" +
                        "    <div id=\"same-id\">\n" +
                        "    </div>\n" +
                        "  </body>\n" +
                        "</html>";

        Assertions.assertEquals(expectedHtml, getOutput());
    }

    @Test
    void testEditNonExistentElementText() {
        simulateUserInput(
                "init",
                "edit-text non-existent-id New text",
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
    void testDeleteNonExistentElement() {
        simulateUserInput(
                "init",
                "delete non-existent-id",
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
    void testCommandBeforeInit() {
        simulateUserInput(
                "append div test-div body",
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
    void testReadNonExistentFile() {
        // 模拟读取不存在的文件
        String input = "load non-existent-file.html\nexit\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        console = new Console(testOut);
        console.run();

        // 验证系统状态应该保持为空或初始状态
        String expectedHtml = "";  // 或者可能是错误消息，取决于您的实现
        Assertions.assertEquals(expectedHtml, getOutput());
    }

    @Test
    void testSaveToInvalidPath() {
        simulateUserInput(
                "init",
                "append div test-div body",
                "save /invalid/path/test.html",  // 使用一个无效的路径
                "print-indent"
        );

        // 验证即使保存失败，HTML结构应该保持不变
        String expectedHtml =
                "<html>\n" +
                        "  <head>\n" +
                        "    <title>\n" +
                        "    </title>\n" +
                        "  </head>\n" +
                        "  <body>\n" +
                        "    <div id=\"test-div\">\n" +
                        "    </div>\n" +
                        "  </body>\n" +
                        "</html>";

        String errorOutput = getErrorOutput();
        Assertions.assertTrue(
                errorOutput.contains("/invalid/path/test.html") &&
                        errorOutput.contains("Cannot save to")
        );
        Assertions.assertEquals(expectedHtml, getOutput());
    }

    @Test
    void testDuplicateIdWithInsertAndAppend() {
        simulateUserInput(
                "init",
                "append div duplicate-id body",
                "append p duplicate-id body",  // 尝试使用相同的ID
                "append div duplicate-id body",  // 再次尝试使用相同的ID
                "print-indent"
        );

        // 验证只有第一个元素被成功创建
        String expectedHtml =
                "<html>\n" +
                        "  <head>\n" +
                        "    <title>\n" +
                        "    </title>\n" +
                        "  </head>\n" +
                        "  <body>\n" +
                        "    <div id=\"duplicate-id\">\n" +
                        "    </div>\n" +
                        "  </body>\n" +
                        "</html>";

        Assertions.assertEquals(expectedHtml, getOutput());
    }

    @Test
    void testInsertAppendCombinations() {
        simulateUserInput(
                "init",
                "append div first-id body",
                "insert div first-id body",  // 尝试insert已存在的ID
                "append div second-id body",
                "append div first-id second-id",  // 尝试在其他位置使用已存在的ID
                "print-indent"
        );

        // 验证只有不重复ID的操作被执行
        String expectedHtml =
                "<html>\n" +
                        "  <head>\n" +
                        "    <title>\n" +
                        "    </title>\n" +
                        "  </head>\n" +
                        "  <body>\n" +
                        "    <div id=\"first-id\">\n" +
                        "    </div>\n" +
                        "    <div id=\"second-id\">\n" +
                        "    </div>\n" +
                        "  </body>\n" +
                        "</html>";

        Assertions.assertEquals(expectedHtml, getOutput());
    }

    @Test
    void testComplexDuplicateIdScenario() {
        simulateUserInput(
                "init",
                "append div container body",
                "append div header container",
                "append div header body",  // 尝试重复使用header ID
                "append p content container",
                "insert div content header",  // 尝试重复使用content ID
                "print-indent"
        );

        // 验证系统正确处理了ID冲突
        String expectedHtml =
                "<html>\n" +
                        "  <head>\n" +
                        "    <title>\n" +
                        "    </title>\n" +
                        "  </head>\n" +
                        "  <body>\n" +
                        "    <div id=\"container\">\n" +
                        "      <div id=\"header\">\n" +
                        "      </div>\n" +
                        "      <p id=\"content\">\n" +
                        "      </p>\n" +
                        "    </div>\n" +
                        "  </body>\n" +
                        "</html>";

        Assertions.assertEquals(expectedHtml, getOutput());
    }

    @Test
    void testElementNotFoundExceptionForDelete() {
        simulateUserInput(
                "init",
                "delete non-existent-element",
                "print-indent"
        );

        Assertions.assertEquals(
                "Element: Id non-existent-element Not Found",
                getErrorOutput()
        );
    }

    @Test
    void testElementNotFoundExceptionForEditText() {
        simulateUserInput(
                "init",
                "edit-text non-existent-element new text",
                "print-indent"
        );

        Assertions.assertEquals(
                "Element: Id non-existent-element Not Found",
                getErrorOutput()
        );
    }




    @Test
    void testElementDuplicateIDExceptionForAppend() {
        simulateUserInput(
                "init",
                "append div test-id body",
                "append p test-id body",
                "print-indent"
        );

        Assertions.assertEquals(
                "Element: Id test-id has existed.",
                getErrorOutput()
        );
    }


    @Test
    void testElementNotFoundExceptionForAppendToNonExistentParent() {
        simulateUserInput(
                "init",
                "append div child non-existent-parent",
                "print-indent"
        );

        Assertions.assertEquals(
                "Element: Id non-existent-parent Not Found",
                getErrorOutput()
        );
    }

    @Test
    void testElementDuplicateIDExceptionForEditId() {
        simulateUserInput(
                "init",
                "append div first-id body",
                "append div second-id body",
                "edit-id second-id first-id",
                "print-indent"
        );

        Assertions.assertEquals(
                "Element: Id first-id has existed.",
                getErrorOutput()
        );
    }

    @Test
    void testMultipleErrorMessages() {
        simulateUserInput(
                "init",
                "append div test-id body",
                "append p test-id body",
                "delete non-existent",
                "print-indent"
        );

        String errorOutput = getErrorOutput();
        String[] errors = errorOutput.split("\n");
        Assertions.assertTrue(
                errors[0].equals("Element: Id test-id has existed.") &&
                        errors[1].equals("Element: Id non-existent Not Found")
        );
    }

    @Test
    void testNestedElementsWithErrors() {
        simulateUserInput(
                "init",
                "append div html body",
                "append div child1 parent",
                "append div child2 non-existent",
                "delete html",
                "print-indent"
        );

        String errorOutput = getErrorOutput();
        String[] errors = errorOutput.split("\n");
        Assertions.assertTrue(
                errors[0].equals("Element: Id non-existent Not Found") &&
                        errors[1].equals("Cannot remove the root element.")
        );
    }

    @Test
    void testCommandSequenceWithErrors() {
        simulateUserInput(
                "init",
                "append div container body",
                "append div header container",
                "append div header body",  // 重复ID
                "edit-text non-existent text",  // 不存在的元素
                "delete container",  // 删除有子元素的父元素
                "print-indent"
        );

        String errorOutput = getErrorOutput();
        String[] errors = errorOutput.split("\n");
        Assertions.assertTrue(
                errors[0].equals("Element: Id header has existed.") &&
                        errors[1].equals("Element: Id non-existent Not Found") &&
                        errors[2].equals("Cannot remove the container element.")
        );
    }

    @Test
    void testEditNonExistentElementId() {
        simulateUserInput(
                "init",
                "edit-id non-existent new-id",
                "print-indent"
        );

        Assertions.assertEquals(
                "Element: Id non-existent Not Found",
                getErrorOutput()
        );
    }

    @Test
    void testAppendWithMultipleErrors() {
        simulateUserInput(
                "init",
                "append div test-id body",
                "append div test-id body",
                "append div new-id non-existent",
                "print-indent"
        );

        String errorOutput = getErrorOutput();
        String[] errors = errorOutput.split("\n");
        Assertions.assertTrue(
                errors[0].equals("Element: Id test-id has existed.") &&
                        errors[1].equals("Element: Id non-existent Not Found")
        );
    }

}