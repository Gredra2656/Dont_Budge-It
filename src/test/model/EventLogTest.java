package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EventLogTest {
    EventLog el;
    Event testEvent;

    @BeforeEach
    public void setup() {
        el = EventLog.getInstance();
        testEvent = new Event("Test Event");
    }

    @Test
    public void testGetInstance() {
        assertTrue(EventLog.getInstance().equals(el));
    }

    @Test
    public void testLogEvent() {
        el.logEvent(testEvent);
        assertTrue(el.iterator().hasNext());
        assertEquals(el.iterator().next(), testEvent);
    }

    @Test
    public void testClear() {
        el.logEvent(testEvent);
        for (Event event : el) {
            assertEquals(testEvent, event);
        }
        el.clear();
        assertNotEquals(testEvent, el.iterator().next());
    }
}
