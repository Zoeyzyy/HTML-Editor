package element;

import document.HTMLElement;
import document.SpellChecker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HTMLElementTest {

    private HTMLElement rootElement;
    private SpellChecker spellChecker;

    @BeforeEach
    void setUp() {
        spellChecker = new SpellChecker();

        rootElement = HTMLElement.builder()
                .setTagName("div")
                .setId("root")
                .setTextContent("Thiss is a sampple textt with speling erorrs.")
                .addChild(
                        HTMLElement.builder()
                                .setTagName("p")
                                .setTextContent("Child elemnt with incorect words.")
                                .build()
                )
                .build();
    }

    @Test
    void testCheckSpelling() throws IOException {
        // Check spelling for the root element
        List<String> errors = rootElement.checkSpelling(spellChecker);

        // Assertions
        assertEquals(6, errors.size()); // Expect 6 spelling errors in total
        assertTrue(errors.get(0).contains("Thiss")); // Check first error
        assertTrue(errors.get(1).contains("sampple")); // Check second error
        assertTrue(errors.get(4).contains("incorect")); // Check child element error
    }

    @Test
    void testAddChild() {
        HTMLElement newChild = HTMLElement.builder()
                .setTagName("span")
                .setTextContent("Another text.")
                .build();

        rootElement.addChild(newChild);

        // Assertions
        assertEquals(2, rootElement.getChildren().size()); // Expect 2 children
        assertEquals("span", rootElement.getChildren().get(1).getTagName());
    }

    @Test
    void testRemoveChild() {
        HTMLElement child = rootElement.getChildren().get(0);
        rootElement.removeChild(child);

        // Assertions
        assertEquals(0, rootElement.getChildren().size()); // Expect no children
    }
}
