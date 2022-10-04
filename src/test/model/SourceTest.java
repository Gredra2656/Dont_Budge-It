package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SourceTest {
    Source testSource;

    @BeforeEach
    public void setup() {
        testSource = new Source("Grocery Clerk Income", 500);
    }

    @Test
    public void testSourceConstructor() {
        assertEquals("Grocery Clerk Income", testSource.getName());
        assertEquals(500, testSource.getValue());
    }
}
