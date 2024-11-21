package session;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

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
    }

    @Test
    void testSession() {
        assertEquals("A_session", session.getId());
    }

    @Test
    void testDumpAndRecover() throws IOException {
        session.dump("./data" + session.getId());
        session = new Session("A_session");
        assertEquals("A_session", session.getId());

        session.load("./dataexample.html");
        assertEquals("/dataexample.html", session.getActiveEditor().getFileName());

        session.dump("./data" + session.getId());
        session = new Session("A_session");
        assertEquals("A_session", session.getId());
        assertEquals("./dataexample.html", session.getActiveEditor().getFileName());

    }

    @Test
    void testExitAndEnter(){
        String id = session.getId();
        session.exit();
        assertNull(session.getActiveEditor());
        session = new Session(id);
        assertNotNull(session.getActiveEditor());
    }
}
