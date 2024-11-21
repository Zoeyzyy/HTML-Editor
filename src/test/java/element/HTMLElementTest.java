//package element;
//
//import document.HTMLElement;
//import document.SpellChecker;
//import document.documentImpl.HTMLElementImpl;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
///**
// * 单元测试类，用于测试 HTMLElement 的所有方法
// */
//class HTMLElementTest {
//    private HTMLElement element;
//    private SpellChecker mockSpellChecker;
//
//    @BeforeEach
//    void setUp() {
//        // 使用 HTMLElementImpl 创建 HTMLElement 实例
//        element = HTMLElement.builder()
//                .setTagName("div")
//                .setId("testId")
//                .setTextContent("Sample text")
//                .build();
//
//        mockSpellChecker = mock(SpellChecker.class); // 模拟拼写检查器
//    }
//
//    @Test
//    void testBuilder() {
//        assertEquals("div", element.getTagName());
//        assertEquals("testId", element.getId());
//        assertEquals("Sample text", element.getTextContent());
//    }
//
//    @Test
//    void testAddChildAndGetChildren() {
//        HTMLElement child = HTMLElement.builder()
//                .setTagName("span")
//                .setId("child1")
//                .setTextContent("Child content")
//                .build();
//
//        element.addChild(child);
//
//        List<HTMLElement> children = element.getChildren();
//        assertNotNull(children);
//        assertEquals(1, children.size());
//        assertEquals("span", children.get(0).getTagName());
//    }
//
//    @Test
//    void testRemoveChild() {
//        HTMLElement child = HTMLElement.builder()
//                .setTagName("span")
//                .setId("child1")
//                .setTextContent("Child content")
//                .build();
//
//        element.addChild(child);
//        assertEquals(1, element.getChildren().size());
//
//        element.removeChild(child);
//        assertTrue(element.getChildren().isEmpty());
//    }
//
//    @Test
//    void testRemoveChildById() {
//        HTMLElement child = HTMLElement.builder()
//                .setTagName("span")
//                .setId("child1")
//                .setTextContent("Child content")
//                .build();
//
//        element.addChild(child);
//        assertEquals(1, element.getChildren().size());
//
//        element.removeChild("child1");
//        assertTrue(element.getChildren().isEmpty());
//    }
//
//    @Test
//    void testDisplay() {
//        // 直接调用 display 方法，如果需要打印到控制台，可以观察输出
//        element.display();
//    }
//
//    @Test
//    void testCheckSpelling() throws IOException {
//        // 配置 mock 行为
//        List<String> mockResults = new ArrayList<>();
//        mockResults.add("error1");
//        when(mockSpellChecker.checkSpelling(anyString())).thenReturn(mockResults);
//
//        List<String> spellCheckResults = element.checkSpelling(mockSpellChecker);
//        assertNotNull(spellCheckResults);
//        assertEquals(1, spellCheckResults.size());
//        assertEquals("error1", spellCheckResults.get(0));
//    }
//
//    @Test
//    void testUpdateSpellCheckResults() throws IOException {
//        // 配置 mock 行为
//        List<String> mockResults = new ArrayList<>();
//        mockResults.add("error1");
//        when(mockSpellChecker.checkSpelling(anyString())).thenReturn(mockResults);
//
//        element.updateSpellCheckResults(mockSpellChecker);
//        List<String> spellCheckResults = element.getSpellCheckResults();
//
//        assertNotNull(spellCheckResults);
//        assertEquals(1, spellCheckResults.size());
//        assertEquals("error1", spellCheckResults.get(0));
//    }
//}
