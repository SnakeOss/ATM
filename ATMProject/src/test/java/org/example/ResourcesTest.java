package org.example.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ResourcesTest {

    @Test
    void testIsLowOnResources() {
        Resources r = new Resources(10, 50, 1000);
        assertTrue(r.isLowOnResources(), "Ink is low, should return true");

        r = new Resources(30, 15, 1000);
        assertTrue(r.isLowOnResources(), "Paper is low, should return true");

        r = new Resources(30, 50, 10);
        assertTrue(r.isLowOnResources(), "Cash is low, should return true");

        r = new Resources(30, 50, 100);
        assertFalse(r.isLowOnResources(), "All resources sufficient, should return false");
    }

    @Test
    void testIsOutOfResources() {
        Resources r = new Resources(0, 50, 1000);
        assertTrue(r.isOutOfResources(), "Ink is zero, should return true");

        r = new Resources(30, 0, 1000);
        assertTrue(r.isOutOfResources(), "Paper is zero, should return true");

        r = new Resources(30, 50, 0);
        assertTrue(r.isOutOfResources(), "Cash is zero, should return true");

        r = new Resources(10, 10, 10);
        assertFalse(r.isOutOfResources(), "No zero resource, should return false");
    }
}
