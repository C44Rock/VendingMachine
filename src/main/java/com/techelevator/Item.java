package com.techelevator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Item {
	
	private String itemLocation;
	private String itemName;
	private BigDecimal itemPrice;
	private int itemQuantity;

	public Item(String lineItem) {
		String[] parts = lineItem.split("\\|");
		itemLocation = parts[0];
		itemName = parts[1];
		itemPrice = new BigDecimal(parts[2]);
		itemQuantity = 5;
	}

	public String getItemLocation() {
		return itemLocation;
	}

	public String getItemName() {
		return itemName;
	}

	public BigDecimal getItemPrice() {
		return itemPrice;
	}

	
	public int getQuantity() {
		return itemQuantity;
	}
	
	public void setQuantity(int itemQuantity) {
		this.itemQuantity = itemQuantity;
	}
}
