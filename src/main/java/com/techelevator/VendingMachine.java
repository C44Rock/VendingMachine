package com.techelevator;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class VendingMachine {

	private Map<String, Item> inventory = new HashMap<String, Item>();
	private List<String> productsOrdered = new ArrayList<String>();

	public VendingMachine() {
		String fileString = "vendingmachine.csv";
		File inventoryFile = new File(fileString);

		try (Scanner fileInput = new Scanner(inventoryFile)) {
			while (fileInput.hasNextLine()) {
				String line = fileInput.nextLine();
				Item myItem = new Item(line);
				inventory.put(myItem.getItemLocation(), myItem);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Item getItem(String location) {
		return inventory.get(location);
	}

	public List<String> getAllLocations() {
		List<String> locations = new ArrayList<String>();

		locations.addAll(inventory.keySet());
		Collections.sort(locations);

		return locations;
	}
	
	public void vend(String location) {
		productsOrdered.add(location);
		Item item = inventory.get(location);
		item.setQuantity(item.getQuantity() - 1);
	}

	public List<String> getProductsOrdered() {
		return this.productsOrdered;
	}

	public void consumeItemsOrdered() {
		for (int i = 0; i < productsOrdered.size(); i++) {
			
			if (productsOrdered.get(i).substring(0, 1).equals("A")) {
				System.out.println("Crunch Crunch, Yum!");
			} else if (productsOrdered.get(i).substring(0, 1).equals("B")) {
				System.out.println("Munch Munch, Yum!");
			} else if (productsOrdered.get(i).substring(0, 1).equals("C")) {
				System.out.println("Glug Glug, Yum!");
			} else {
				System.out.println("Chew Chew, Yum!");
			}
		}
		
		productsOrdered.clear();
	}
	
	public void log(String transactionType, BigDecimal beginningBalance, BigDecimal endBalance) {
		DecimalFormat formatter = new DecimalFormat("#0.00");

		try (FileWriter writer = new FileWriter("log.txt", true)) {
			final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		    LocalDateTime now = LocalDateTime.now();
	        writer.write(dtf.format(now) + " " + transactionType + " $" + formatter.format(beginningBalance) + " $" + formatter.format(endBalance) + "\n");
	        writer.write("");
			
		} catch (Exception e){
			e.printStackTrace();
		} 
	}
	
	public void generateSalesReport() {
		BigDecimal totalSales = new BigDecimal(0);

		try (FileWriter writer = new FileWriter("SalesReport.txt", true)) {
			for (String location : this.getAllLocations()) {
				Item item = inventory.get(location);
				
				BigDecimal itemDiff = new BigDecimal(5 - item.getQuantity());
				BigDecimal itemSales = itemDiff.multiply(item.getItemPrice());
				
				writer.write(item.getItemName() + "|$" + itemSales + "\n");
				writer.write("");
				totalSales = totalSales.add(itemSales);
			}
			writer.write("");
			writer.write("**TOTAL SALES** $" + totalSales);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
