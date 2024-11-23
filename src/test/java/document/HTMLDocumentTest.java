package document;

import exception.ElementBadRemoved;
import exception.ElementNotFound;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

class HTMLDocumentTest {
    private HTMLDocument document;
    private HTMLElement rootElement;
    private File testFile;

    @BeforeEach
    void setUp() throws IOException {
        // 创建一个基础的HTML文档用于测试
        document = new HTMLDocument();

        // 创建测试用的HTML文件
        testFile = File.createTempFile("test", ".html");
        FileWriter writer = null;
        try {
            writer = new FileWriter(testFile);
            writer.write("<html id=\"root\">\n" +
                    "    <head id=\"head\">\n" +
                    "        <title id=\"title\">Test Page</title>\n" +
                    "    </head>\n" +
                    "    <body id=\"body\">\n" +
                    "        <div id=\"content\">Hello World</div>\n" +
                    "    </body>\n" +
                    "</html>");
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    @AfterEach
    void tearDown() {
        if (testFile != null && testFile.exists()) {
            testFile.delete();
        }
    }

    @Test
    void testReadHTML() {
        // 测试读取HTML文件
        document.read(testFile);
        HTMLElement root = document.findElementById("root");
        assertNotNull(root);
        assertEquals("html", root.getTagName());
    }

    @Test
    void testFindElementById() {
        document.read(testFile);

        // 测试查找存在的元素
        HTMLElement content = document.findElementById("content");
        assertNotNull(content);
        assertEquals("div", content.getTagName());
        assertEquals("Hello World", content.getTextContent());

        // 测试查找不存在的元素
        try{
            document.findElementById("nonexistent");
        }catch (ElementNotFound e) {
            assertTrue(true);
        }
    }

    @Test
    void testAppendElement() {
        document.read(testFile);

        try {
            // 测试添加新元素
            document.appendElement("p", "new-paragraph", "New Content", "body");

            HTMLElement newElement = document.findElementById("new-paragraph");
            assertNotNull(newElement);
            assertEquals("p", newElement.getTagName());
            assertEquals("New Content", newElement.getTextContent());
        } catch (ElementNotFound e) {
            fail("Should not throw ElementNotFound exception");
        }
    }

    @Test
    void testAppendElementToNonExistentParent() {
        document.read(testFile);

        try {
            document.appendElement("p", "new-paragraph", "New Content", "non-existent-parent");
            fail("Should throw ElementNotFound exception");
        } catch (ElementNotFound e) {
            // 预期的异常
            assertTrue(true);
        }
    }

    @Test
    void testRemoveElementById() {
        document.read(testFile);

        try {
            // 测试移除元素
            document.removeElementById("content");
            document.findElementById("content");
        } catch (ElementBadRemoved e) {
            fail("Should not throw ElementBadRemoved exception");
        } catch (ElementNotFound e) {
            assertTrue(true);
        }
    }

    @Test
    void testRemoveRootElement() {
        document.read(testFile);

        try {
            document.removeElementById("root");
            fail("Should throw ElementBadRemoved exception");
        } catch (ElementBadRemoved e) {
            // 预期的异常
            assertTrue(true);
        }
    }

    @Test
    void testEditID() {
        document.read(testFile);

        // 测试编辑元素ID
        document.editID("body", "new-body");

        try{
            document.findElementById("body");
        }catch (ElementNotFound e){
            assertNotNull(document.findElementById("new-body"));
        }
    }

    @Test
    void testGetTreeFormat() {
        document.read(testFile);

        String treeFormat = document.getTreeFormat(false);
        assertNotNull(treeFormat);
        assertTrue(treeFormat.contains("html"));
        assertTrue(treeFormat.contains("body"));
        assertTrue(treeFormat.contains("div"));
        document.setShowID(true);
        treeFormat = document.getTreeFormat(false);
        assertTrue(treeFormat.contains("div#content"));
    }


}