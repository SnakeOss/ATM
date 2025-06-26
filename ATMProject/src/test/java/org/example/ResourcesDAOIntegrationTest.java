package org.example.model;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class ResourcesDAOIntegrationTest {

    private static ResourcesDAO dao;

    @BeforeAll
    static void setup() {
        dao = new ResourcesDAO();
    }

    @Test
    void testGetAndUpdateResources() {
        // Retrieve current resources
        Resources res = dao.getResources();
        assertNotNull(res);

        // Update resources
        res.setInk(80);
        res.setPaper(70);
        res.setCash(900);

        dao.updateResources(res);

        // Retrieve again and check updates
        Resources updated = dao.getResources();
        assertEquals(80, updated.getInk());
        assertEquals(70, updated.getPaper());
        assertEquals(900, updated.getCash(), 0.001);
    }

    @Test
    void testRefillResources() {
        boolean result = dao.refillResources();
        assertTrue(result);

        Resources res = dao.getResources();
        assertEquals(100, res.getInk());
        assertEquals(100, res.getPaper());
        assertEquals(1000, res.getCash(), 0.001);
    }
}
