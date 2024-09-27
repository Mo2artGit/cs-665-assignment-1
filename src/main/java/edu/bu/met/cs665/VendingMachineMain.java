/**
 * Name: Raymond Chen
 * Course: CS-665 Software Designs & Patterns
 * Date: 09/26/2024
 * File Name: VendingMachineMain.java
 * Description: This class is responsible for providing a command-line interface
 * for interacting with the vending machine.
 */

package edu.bu.met.cs665;

import edu.bu.met.cs665.vendingmachine.IllegalCondimentException;
import edu.bu.met.cs665.vendingmachine.VendingMachineImpl;
import java.util.Scanner;

/**
 * The VendingMachineMain class serves as the entry point for the Vending Machine application.
 * It provides a command-line interface for users to interact with the vending machine,
 * allowing them to select beverages, add condiments, and complete transactions.
 */
public class VendingMachineMain {

  private static VendingMachineImpl vendingMachine;

  /**
   * The main method that runs the Vending Machine application.
   * It initializes the vending machine, manages user interactions, and processes orders.
   * The method performs the following steps in a loop until the program is terminated:
   * 1. Prompts the user to select a beverage
   * 2. Allows the user to add condiments (optional)
   * 3. Calculates and displays the total price
   * 4. Processes payment or cancellation
   * 5. Brews the beverage
   *
   * @param args not used in this application
   */
  public static void main(String[] args) {

    vendingMachine = new VendingMachineImpl();
    Scanner scan = new Scanner(System.in);

    while (true) {
      // Step 1: Select a beverage
      System.out.println("\nWelcome to The Vending Machine!");
      String selectedBeverage = null;
      while (selectedBeverage == null) {
        System.out.println("Please select a beverage from the menu:");
        vendingMachine.displayMenu();
        System.out.print("Enter the name of your choice: ");
        selectedBeverage = scan.nextLine().toLowerCase();

        if (!vendingMachine.checkBeverage(selectedBeverage)) {
          System.out.println("Invalid input. Please try again.");
          selectedBeverage = null;
        }
      }

      // Step 2: Select condiments (optional)
      String selectedCondiment = null;
      while (true) {
        if (vendingMachine.getTotalCondimentUnits() > 0) {
          System.out.println("\nEnter a condiment or type 'done':");
        } else {
          System.out.println("\nEnter a condiment or type 'done' or 'skip':");
        }
        vendingMachine.displayCondiments();
        System.out.print("Input: ");
        selectedCondiment = scan.nextLine().trim().toLowerCase();;

        if (vendingMachine.getTotalCondimentUnits() > 0
                && selectedCondiment.equalsIgnoreCase("done")) {
          break;
        }
        if (vendingMachine.getTotalCondimentUnits() == 0
                && selectedCondiment.equalsIgnoreCase("skip")) {
          break;
        }
        if (selectedCondiment.equalsIgnoreCase("done")) {
          break;
        }

        System.out.println("Enter the units of condiment (0-3): ");
        int units = Integer.parseInt(scan.nextLine());

        try {
          vendingMachine.addCondiment(selectedCondiment, units);
        } catch (IllegalCondimentException e) {
          System.out.println(e.getMessage());
        }
      }

      // Step 3: Calculate and display the total price
      double totalPrice = vendingMachine.calculatePrice();
      System.out.println("\nYour total is: $" + totalPrice);
      String payMenu =
              "Please select an option from the list below (select number):\n"
                      + " 1. Pay\n"
                      + " 2. Cancel";

      System.out.println(payMenu);
      int response = Integer.parseInt(scan.nextLine());

      // Step 4: Processes payment or cancellation
      switch (response) {
        case 1:
          vendingMachine.pay();
          break;
        case 2:
          vendingMachine.reset();
          break;
        default:
          System.out.println("Selection: '"
                  + response
                  + "' is invalid. Please try again");
      }

      //Step 5: Brew the beverage
      if (response == 1) {
        vendingMachine.brew(selectedBeverage);
      }
      System.out.println("\nThank you for using the Vending Machine!");
    }

  }
}
