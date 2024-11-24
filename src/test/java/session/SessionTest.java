package session;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class SessionTest {
    private Session session;

    @BeforeEach
    void setUp() {
        session = new Session("A_session");
    }

    @AfterEach
    void tearDown() {
        session.close();
        session = null;
        Path filePath = Paths.get("./data/session_dump");
        try {
            Files.delete(filePath);
            System.out.println("tearDown: File deleted successfully.");
        } catch (IOException e) {
            System.out.println("tearDown: Failed to delete the file: " + e.getMessage());
        }
    }

    @Test
    void testSession() {
        assertEquals("A_session", session.getId());
    }

    @Test
    void testDumpAndRecover() throws IOException {
        session.dump("./data/" + session.getId());
        session = new Session("A_session");
        assertEquals("A_session", session.getId());

        session.load("./dataexample.html");
        String absolutePath = Paths.get("./dataexample.html").toAbsolutePath().toString();
        assertEquals(absolutePath, session.getActiveEditor().getFileName());

        session.dump("./data/" + session.getId());
        System.out.println(session.getEditorList().size());

        session = new Session("A_session");
        assertEquals("A_session", session.getId());
        System.out.println(session.getEditorList().size());
        assertNotNull(session.getActiveEditor());
        absolutePath = Paths.get("./dataexample.html").toAbsolutePath().toString();
        assertEquals(absolutePath, session.getActiveEditor().getFileName());

    }

    @Test
    void testExitAndEnter() throws IOException {
        String id = session.getId();
        session.load("example.html");
        session.exit();
        assertNotNull(session.getActiveEditor());
        session = new Session(id);
        assertNotNull(session.getActiveEditor());
        String absolutePath = Paths.get("example.html").toAbsolutePath().toString();
        assertEquals(absolutePath, session.getActiveEditor().getFileName());
    }

    @Test
    void testGetEditor() throws IOException {

        assertNull(session.getActiveEditor());
        session.load("example.html");
        session.load("example2.html");
        String absolutePath = Paths.get("example2.html").toAbsolutePath().toString();
        assertEquals(absolutePath, session.getActiveEditor().getFileName());

        absolutePath = Paths.get("example.html").toAbsolutePath().toString();
        assertTrue(session.getEditorList().contains(absolutePath));

        absolutePath = ">" + Paths.get("example2.html").toAbsolutePath().toString();
        System.out.println(session.getEditorList());
        assertTrue(session.getEditorList().contains(absolutePath));
    }

    @Test
    void testCloseFile() throws IOException {
        session.load("example.html");
        session.close();
        assertNull(session.getActiveEditor());

        session.load("example.html");
        session.load("example2.html");
        session.close();

        String absolutePath = Paths.get("example.html").toAbsolutePath().toString();
        assertEquals(absolutePath, session.getActiveEditor().getFileName());

        absolutePath = Paths.get("example2.html").toAbsolutePath().toString();
        assertFalse(session.getEditorList().contains(absolutePath));

        assertEquals(1, session.getEditorList().size());
    }

    @Test
    void testGetDirTreeFormat() throws IOException {
        session.load("./src/main/java/Console");
        assertNotNull(session.getActiveEditor());
        System.out.println("active editor: " + session.getActiveEditor().getFileName());
        System.out.println("result: \n" + session.getDirTreeFormat(0));
    }

    @Test
    void testGetDirIndentFormat() throws IOException {
        session.load("src/main/java/Console");
        assertNotNull(session.getActiveEditor());
        System.out.println("active editor: " + session.getActiveEditor().getFileName());
        System.out.println("result: \n" + session.getDirIndentFormat(0));
    }
}
