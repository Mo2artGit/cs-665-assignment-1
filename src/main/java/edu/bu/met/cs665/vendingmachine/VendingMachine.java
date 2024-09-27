/**
 * Name: Raymond Chen
 * Course: CS-665 Software Designs & Patterns
 * Date: 09/26/2024
 * File Name: VendingMachine.java
 * Description: This interface is responsible for the Vending Machine.
 */

package edu.bu.met.cs665.vendingmachine;

/**
 * This is the VendingMachine interface.
 * This interface is responsible for the Vending Machine.
 */
public interface VendingMachine {

    /**
     * Calculate the total price of the selected beverage and condiments.
     *
     * @return The total price.
     */
    double calculatePrice();

    /**
     * Display the menu of available beverages.
     */
    void displayMenu();

    /**
     * Display the available condiments.
     */
    void displayCondiments();

    /**
     * Check if a beverage is available and select.
     *
     * @param beverage The name of the beverage to check and select.
     * @return true if the beverage is available, false otherwise.
     */
    boolean checkBeverage(String beverage);

    /**
     * Add a condiment to the selected beverage.
     *
     * @param condiment The name of the condiment to add.
     * @param units     The number of units of the condiment to add.
     * @throws IllegalCondimentException If the condiment is invalid or exceeds the limit.
     */
    void addCondiment(String condiment, int units) throws IllegalCondimentException;

    /**
     * Get the total number of condiment units added.
     *
     * @return The total number of condiment units.
     */
    int getTotalCondimentUnits();

    /**
     * Get the number of units for a specific condiment.
     *
     * @param condiment The name of the condiment.
     * @return The number of units for the specified condiment.
     */
    int getCondimentUnits(String condiment);

    /**
     * Reset the vending machine state.
     */
    void reset();

    /**
     * Process the payment for the selected items.
     */
    void pay();

    /**
     * Brew the selected beverage with added condiments.
     *
     * @param beverage The name of the beverage to brew.
     */
    void brew(String beverage);


}
