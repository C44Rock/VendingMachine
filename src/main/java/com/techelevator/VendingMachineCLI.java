package com.techelevator;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Scanner;

import com.techelevator.view.Menu;

public class VendingMachineCLI {

	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE };

	private static final String[] PURCHASE_MENU_OPTIONS = { "Feed Money", "Select Product", "Finish Transaction" };

	private Menu menu;
	private VendingMachine vendingMachine;
	private Money money;

	public VendingMachineCLI(Menu menu) {
		this.menu = menu;
		this.vendingMachine = new VendingMachine();
		this.money = new Money();
	}

	public String[] getPurchaseMenuOptions() {
		return PURCHASE_MENU_OPTIONS;
	}

	public void run() {
		while (true) {
			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);

			if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
				// display vending machine items
				
				System.out.println(String.format("%-16s%-21s%-15s%-15s", "Slot", "Food", "Cost", "Quantity"));
				System.out.println("--------------------------------------------------------------");
				
				for (String location : vendingMachine.getAllLocations()) {

					Item nextItem = vendingMachine.getItem(location);
					System.out.print(String.format("%-15s", nextItem.getItemLocation()));
					System.out.print(" ");
					System.out.print(String.format("%-20s", nextItem.getItemName()));
					System.out.print(" ");
					System.out.print(String.format("%-15.2f", nextItem.getItemPrice()));
					System.out.print(" ");
					System.out.print(String.format("%-15d", nextItem.getQuantity()));
					System.out.println();
				}

			} else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
				// do purchase
				while (true) {
					DecimalFormat formatter = new DecimalFormat("#0.00");
					
					System.out.println();
					System.out.println("Current balance is: $" + formatter.format(money.getBalance()));
					
					String nextChoice = (String) menu.getChoiceFromOptions(PURCHASE_MENU_OPTIONS);

					if (nextChoice.equals(PURCHASE_MENU_OPTIONS[0])) {
						this.feedMoney();
					}
					if (nextChoice.equals(PURCHASE_MENU_OPTIONS[1])) {
						this.selectProduct();
					}
					if (nextChoice.equals(PURCHASE_MENU_OPTIONS[2])) {
						this.finishTransaction();
						break;
					}
					
				}
			}
		}
	}

	public void feedMoney() {
		System.out.println();
		System.out.println("Please enter the amount of money in whole dollar amounts");
		String transactionType = "FEED MONEY";
		BigDecimal beginningBalance = money.getBalance();
		
		Scanner moneyScanner = menu.getScanner();
		BigDecimal moneyEntered = new BigDecimal(moneyScanner.nextLine());
		money.addBalance(moneyEntered);
		BigDecimal endBalance = money.getBalance();
		
		vendingMachine.log(transactionType, beginningBalance, endBalance);
	}

	public void selectProduct() {
		System.out.println();
		System.out.println("What product would you like to purchase?");

		Scanner productScanner = menu.getScanner();
		String productLocation = productScanner.nextLine().toUpperCase();
		Item thisItem = vendingMachine.getItem(productLocation);

		if (!vendingMachine.getAllLocations().contains(productLocation)) {
			System.out.println("Sorry, that product doesn't exist!");
		} else if (thisItem.getQuantity() == 0) {
			System.out.println("Sorry, that item is sold out!");
		} else if (thisItem.getItemPrice().compareTo(money.getBalance()) > 0) {
			System.out.println("Are you trying to rob me?!");
		} else {
			BigDecimal beginningBalance = money.getBalance();
			System.out.println("Here you go!");
			money.subtractBalance(thisItem.getItemPrice());
			vendingMachine.vend(productLocation);
			BigDecimal endBalance = money.getBalance();
			
			String transactionType = thisItem.getItemName() + " " + thisItem.getItemLocation();
			vendingMachine.log(transactionType, beginningBalance, endBalance);
		}
	}

	public void finishTransaction() {
		String transactionType = "GIVE CHANGE";
		BigDecimal beginningBalance = money.getBalance();
		
		int numQuarters = money.giveBackQuarters(money.getBalance());
		money.subtractBalance(new BigDecimal(numQuarters).multiply(Money.QUARTER));
		int numDimes = money.giveBackDimes(money.getBalance());
		money.subtractBalance(new BigDecimal(numDimes).multiply(Money.DIME));
		int numNickels = money.giveBackNickels(money.getBalance());
		money.wipeBalance();
		
		vendingMachine.log(transactionType, beginningBalance, BigDecimal.ZERO);

		System.out.println("your change is " + numQuarters + " quarters, " + numDimes + " dimes, and " + numNickels + " nickels.");
		System.out.println();
		vendingMachine.consumeItemsOrdered();
		
		vendingMachine.generateSalesReport();
	}

	public static void main(String[] args) {
		Menu menu = new Menu(System.in, System.out);
		VendingMachineCLI cli = new VendingMachineCLI(menu);
		cli.run();
	}
}
