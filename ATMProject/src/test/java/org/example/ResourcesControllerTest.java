package org.example;

import org.example.controller.ResourcesController;  // <- Correct import here
import org.example.model.Resources;
import org.example.model.ResourcesDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ResourcesControllerTest {

    private ResourcesDAO mockDao;
    private ResourcesController controller;

    @BeforeEach
    void setup() {
        mockDao = mock(ResourcesDAO.class);
        controller = new ResourcesController(mockDao);
    }

    @Test
    void testGetResources() {
        Resources mockResources = new Resources(50, 50, 500);
        when(mockDao.getResources()).thenReturn(mockResources);

        Resources result = controller.getResources();

        assertEquals(50, result.getInk());
        assertEquals(50, result.getPaper());
        assertEquals(500, result.getCash());
        verify(mockDao).getResources();
    }

    @Test
    void testUpdateResourcesAfterTransaction_Deposit() {
        Resources currentResources = new Resources(100, 100, 1000);
        when(mockDao.getResources()).thenReturn(currentResources);

        controller.updateResourcesAfterTransaction(200, true);

        // Cash should increase by 200, ink and paper decreased by 1
        assertEquals(99, currentResources.getInk());
        assertEquals(99, currentResources.getPaper());
        assertEquals(1200, currentResources.getCash(), 0.001);

        verify(mockDao).updateResources(currentResources);
    }

    @Test
    void testUpdateResourcesAfterTransaction_Withdraw() {
        Resources currentResources = new Resources(100, 100, 1000);
        when(mockDao.getResources()).thenReturn(currentResources);

        controller.updateResourcesAfterTransaction(300, false);

        // Cash should decrease by 300, ink and paper decreased by 1
        assertEquals(99, currentResources.getInk());
        assertEquals(99, currentResources.getPaper());
        assertEquals(700, currentResources.getCash(), 0.001);

        verify(mockDao).updateResources(currentResources);
    }

    @Test
    void testRefillInk() {
        Resources res = new Resources(10, 10, 1000);
        when(mockDao.getResources()).thenReturn(res);

        controller.refillInk();

        assertEquals(100, res.getInk());
        verify(mockDao).updateResources(res);
    }

    @Test
    void testRefillPaper() {
        Resources res = new Resources(10, 10, 1000);
        when(mockDao.getResources()).thenReturn(res);

        controller.refillPaper();

        assertEquals(100, res.getPaper());
        verify(mockDao).updateResources(res);
    }

    @Test
    void testRefillCash() {
        Resources res = new Resources(10, 10, 500);
        when(mockDao.getResources()).thenReturn(res);

        controller.refillCash();

        assertEquals(1000, res.getCash());
        verify(mockDao).updateResources(res);
    }

    @Test
    void testRefillAll() {
        Resources res = new Resources(10, 10, 500);
        when(mockDao.getResources()).thenReturn(res);

        controller.refillAll();

        assertEquals(100, res.getInk());
        assertEquals(100, res.getPaper());
        assertEquals(1000, res.getCash());
        verify(mockDao, times(3)).updateResources(res);
    }
}
