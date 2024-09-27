/**
 * Name: Raymond Chen
 * Course: CS-665 Software Designs & Patterns
 * Date: 09/26/2024
 * File Name: VendingMachineImpTest.java
 * Description: This class is responsible for the Vending Machine Testing.
 */

package edu.bu.met.cs665;

import edu.bu.met.cs665.vendingmachine.IllegalCondimentException;
import edu.bu.met.cs665.vendingmachine.VendingMachineImpl;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class VendingMachineImplTest {

    VendingMachineImpl vendingMachine;

    @Before
    public void setUp() {
        vendingMachine = new VendingMachineImpl();
    }

    @Test
    public void testCheckBeverage() {
        // Test valid beverages
        assertTrue(vendingMachine.checkBeverage("Espresso"));
        assertTrue(vendingMachine.checkBeverage("black tea"));
        // Test invalid beverage
        assertFalse(vendingMachine.checkBeverage("orange juice"));
    }

    @Test
    public void testCalculatePrice() throws IllegalCondimentException {
        // Test base price
        vendingMachine.checkBeverage("espresso");
        assertEquals(2.00, vendingMachine.calculatePrice(), 0.001);

        // Test price with condiments
        vendingMachine.addCondiment("sugar", 2);
        assertEquals(3.00, vendingMachine.calculatePrice(), 0.001);

    }


    @Test(expected = IllegalCondimentException.class)
    public void testAddCondimentExceedingIndividualLimit() throws IllegalCondimentException {
        // Test invalid units of condiment
        vendingMachine.checkBeverage("Latte macchiato");
        vendingMachine.addCondiment("sugar", 4); // This should throw an exception
    }

    @Test
    public void testGetTotalCondimentUnits() throws IllegalCondimentException {
        vendingMachine.checkBeverage("green tea");
        vendingMachine.addCondiment("sugar", 2);
        vendingMachine.addCondiment("milk", 3);
        assertEquals(5, vendingMachine.getTotalCondimentUnits());
    }

    @Test
    public void testMaximumCondiments() throws IllegalCondimentException {
        vendingMachine.checkBeverage("americano");
        vendingMachine.addCondiment("Sugar", 3);
        vendingMachine.addCondiment("milk", 3);
        assertEquals(6, vendingMachine.getTotalCondimentUnits());
    }

    @Test
    public void testReset() throws IllegalCondimentException {
        vendingMachine.checkBeverage("Americano");
        vendingMachine.addCondiment("sugar", 1);
        vendingMachine.reset();
        assertEquals(0, vendingMachine.getTotalCondimentUnits());
        assertEquals(0, vendingMachine.calculatePrice(), 0.001);
    }

    @Test(expected = IllegalStateException.class)
    public void testBrewWithoutPayment() {
        vendingMachine.checkBeverage("espresso");
        vendingMachine.brew("espresso");
    }

    @Test
    public void testBrewAfterPayment() {
        vendingMachine.checkBeverage("espresso");
        vendingMachine.pay();
        vendingMachine.brew("espresso");
    }

    @Test
    public void testOverwriteCondiment() throws IllegalCondimentException {
        vendingMachine.checkBeverage("latte macchiato");
        vendingMachine.addCondiment("Sugar", 3);
        vendingMachine.addCondiment("milk", 2);
        assertEquals(5, vendingMachine.getTotalCondimentUnits());
        vendingMachine.addCondiment("sugar", 1); // Overwrite Sugar from 3 to 1
        assertEquals(1, vendingMachine.getCondimentUnits("sugar"));
        assertEquals(3, vendingMachine.getTotalCondimentUnits());
        vendingMachine.addCondiment("Milk", 3); // Overwrite Milk from 2 to 3
        assertEquals(4, vendingMachine.getTotalCondimentUnits());
    }

    @Test(expected = IllegalCondimentException.class)
    public void testMissingCondimentPrice() throws IllegalCondimentException {
        vendingMachine.checkBeverage("latte macchiato");
        vendingMachine.addCondiment("cinnamon", 1);
        vendingMachine.calculatePrice();
    }

    @Test(expected = IllegalStateException.class)
    public void testAddCondimentWithoutSelectingBeverage() throws IllegalCondimentException {
        vendingMachine.addCondiment("sugar", 1);
    }


}
