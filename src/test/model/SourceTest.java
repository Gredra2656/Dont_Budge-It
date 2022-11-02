package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests the model.Source class for functionality
 */
public class SourceTest {
    Source testSource;

    @BeforeEach
    public void setup() {
        testSource = new Source("Grocery Clerk Income", BigDecimal.valueOf(500));
    }

    @Test
    public void testSourceConstructor() {
        assertEquals("Grocery Clerk Income", testSource.getName());
        assertEquals(BigDecimal.valueOf(500), testSource.getValue());
    }
}
