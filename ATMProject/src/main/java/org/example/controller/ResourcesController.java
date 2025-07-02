package org.example.controller;

import org.example.model.Resources;
import org.example.model.ResourcesDAO;

public class ResourcesController {
    private final ResourcesDAO resourcesDAO;

    public ResourcesController(ResourcesDAO resourcesDAO) {
        this.resourcesDAO = resourcesDAO;
    }

    public Resources getResources() {
        return resourcesDAO.getResources();
    }

    public void updateResourcesAfterTransaction(double amount, boolean isDeposit) {
        Resources currentResources = getResources();

        if (currentResources.isOutOfResources()) {
            System.out.println("❌ ATM out of service – technician required.");
            return;
        }

        currentResources.setInk(Math.max(0, currentResources.getInk() - 1));
        currentResources.setPaper(Math.max(0, currentResources.getPaper() - 1));

        if (isDeposit) {
            currentResources.setCash(currentResources.getCash() + amount);
        } else {
            currentResources.setCash(currentResources.getCash() - amount);
        }

        resourcesDAO.updateResources(currentResources);

        if (currentResources.isLowOnResources()) {
            System.out.println("⚠️  Transaction complete – supplies are LOW.");
        } else {
            System.out.println("✅ Transaction complete.");
        }
    }

    // Refill ink to max (e.g., 100 units)
    public void refillInk() {
        Resources currentResources = getResources();
        currentResources.setInk(100); // max ink
        resourcesDAO.updateResources(currentResources);
        System.out.println("Ink refilled to full.");
    }

    // Refill paper to max (e.g., 100 sheets)
    public void refillPaper() {
        Resources currentResources = getResources();
        currentResources.setPaper(100); // max paper
        resourcesDAO.updateResources(currentResources);
        System.out.println("Paper refilled to full.");
    }

    // Refill cash to max (e.g., $10,000)
    public void refillCash() {
        Resources currentResources = getResources();
        currentResources.setCash(1000); // max cash amount
        resourcesDAO.updateResources(currentResources);
        System.out.println("Cash refilled to full.");
    }

    // Refill all resources
    public void refillAll() {
        refillInk();
        refillPaper();
        refillCash();
    }

    // Display current resources (optional helper)
    public void displayResources() {
        Resources currentResources = getResources();
        System.out.println("Current ATM resources:");
        System.out.println("Ink: " + currentResources.getInk());
        System.out.println("Paper: " + currentResources.getPaper());
        System.out.println("Cash: $" + currentResources.getCash());
    }
}
