/**
 * Name: Raymond Chen
 * Course: CS-665 Software Designs & Patterns
 * Date: 09/26/2024
 * File Name: VendingMachineImpl.java
 * Description: This class is responsible for the methods and attributes of Vending Machine.
 */

package edu.bu.met.cs665.vendingmachine;

import java.util.*;

/**
 * This class implements the VendingMachine interface and represents a fully automated
 * beverage vending machine capable of dispensing coffee and tea beverages with
 * optional condiments.
 */
public class VendingMachineImpl implements VendingMachine {

    /**
     * Indicates whether payment has been processed.
     */
    private boolean paid;
    /**
     * Stores the currently selected beverage.
     */
    private String selectedBeverage;
    /**
     * Stores the condiments and their quantities added to the current beverage.
     */
    private Map<String, Integer> condiments;
    /**
     * Stores the prices of available beverages.
     */
    private Map<String, Double> beveragePrices;
    /**
     * Stores the prices of available condiments.
     */
    private Map<String, Double> condimentPrices;

    /**
     * Constructs a VendingMachineImpl instance and initializes its state.
     */
    public VendingMachineImpl() {
        paid = false;
        selectedBeverage = null;
        condiments = new HashMap<>();
        condimentPrices = new HashMap<>();
        beveragePrices = new HashMap<>();
        initializeBerveragePrices();
        initializeCondimentPrices();
    }

    /**
     * Initializes the prices for available beverages.
     */
    private void initializeBerveragePrices() {
        beveragePrices.put("espresso", 2.00);
        beveragePrices.put("americano", 2.00);
        beveragePrices.put("latte macchiato", 3.00);
        beveragePrices.put("black tea", 1.50);
        beveragePrices.put("green tea", 1.50);
        beveragePrices.put("yellow tea", 1.50);
    }

    /**
     * Initializes the prices for available condiments.
     */
    private void initializeCondimentPrices() {
        condimentPrices.put("sugar", 0.50);
        condimentPrices.put("milk", 0.50);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double calculatePrice() {
        if (selectedBeverage == null) {
            return 0; // No beverage selected yet
        }
        double price = beveragePrices.get(selectedBeverage);

        for (Map.Entry<String, Integer> entry : condiments.entrySet()) {
            String condiment = entry.getKey();
            int units = entry.getValue();

            if (condimentPrices.containsKey(condiment)) {
                price += units * condimentPrices.get(condiment);
            } else {
                throw new IllegalStateException("Price not found for condiment");
            }
        }

        return price;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void displayMenu() {
        for (Map.Entry<String, Double> entry : beveragePrices.entrySet()) {
            System.out.println(entry.getKey() + " - $" + entry.getValue());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void displayCondiments() {
        for (Map.Entry<String, Double> entry : condimentPrices.entrySet()) {
            System.out.println(entry.getKey() + " - $" + entry.getValue() + " per unit");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean checkBeverage(String beverage) {
        beverage = beverage.toLowerCase();
        if (beveragePrices.containsKey(beverage)) {
            selectedBeverage = beverage;
            return true;
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addCondiment(String condiment, int units) throws IllegalCondimentException {
        condiment = condiment.toLowerCase();
        if (selectedBeverage == null) {
            throw new IllegalStateException("Please select a beverage first");
        }
        if (!condimentPrices.containsKey(condiment)) {
            throw new IllegalCondimentException(condiment + " is not available.");
        }
        if (units < 0 || units > 3) {
            throw new IllegalCondimentException("Invalid units of " + condiment);
        }
        int totalUnits = getTotalCondimentUnits();
        if (totalUnits + units > 6) {
            throw new IllegalCondimentException("Cannot exceed 6 total condiment units.");
        }
        condiments.put(condiment, units);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getTotalCondimentUnits() {
        return condiments.values().stream().mapToInt(Integer::intValue).sum();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getCondimentUnits(String condiment) {
        return condiments.getOrDefault(condiment, 0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reset() {
        paid = false;
        selectedBeverage = null;
        condiments.clear();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void pay() {
        paid = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void brew(String beverage) {
        if (!paid) {
            throw new IllegalStateException("Payment not processed.");
        }
        if (selectedBeverage == null) {
            throw new IllegalStateException("Please select a beverage.");
        }
        System.out.println("\nAdding " + beverage + "...");
        if (!condiments.isEmpty()) {
            for (Map.Entry<String, Integer> entry : condiments.entrySet()) {
                System.out.println("Adding " + entry.getKey() + "...");
            }
        }
        System.out.println("Brewing completed!");
        reset();
    }

}
