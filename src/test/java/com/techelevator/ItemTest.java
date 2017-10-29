package com.techelevator;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import org.junit.Assert;

public class ItemTest {
	Item item;
	
	@Before
	public void setup() {
		item = new Item("A1|Potato Crisps|3.05");
	}
	
	@Test
	public void itemInitializesCorrectly() {
		Assert.assertEquals("should return A1", "A1", item.getItemLocation());
		Assert.assertEquals("should return Potato Crisps", "Potato Crisps", item.getItemName());
		Assert.assertEquals("should return 3.05", new BigDecimal("3.05"), item.getItemPrice());
		Assert.assertEquals("should return 5", 5, item.getQuantity());

	}
	
	@Test
	public void quantitySetsCorrectly() {
		item.setQuantity(3);
		Assert.assertEquals("should be 3", 3, item.getQuantity());
	}
}
