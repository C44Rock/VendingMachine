package com.techelevator;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;



public class VendingMachineTest {
	
	private VendingMachine vm;
	private Item item;
	
	@Before
	public void setup() {
		vm = new VendingMachine();
	}
	
	@Test
	public void expecting_getting_item() {
		Item item = vm.getItem("A1");
		Assert.assertEquals("Expecting to get Item from Inventory", "A1",  item.getItemLocation());
	}
	
	@Test
	public void should_decrease_quantity() {
		Item item = vm.getItem("A1");
		
		Assert.assertEquals("There should be 5 items", 5, item.getQuantity());
		
		vm.vend("A1");
		
		item = vm.getItem("A1");
		
		Assert.assertEquals("There should only be 4 items", 4, item.getQuantity());
		
		List<String> ordered = vm.getProductsOrdered();
		
		Assert.assertEquals("There should be one product ordered", 1, ordered.size());
		Assert.assertEquals("This should be the thing we ordered", "A1", ordered.get(0));
	}
	
	@Test
	public void verify_consume_clears_list() {
		vm.vend("A1");
		vm.consumeItemsOrdered();
		List<String> ordered = vm.getProductsOrdered();
		Assert.assertEquals("There should be no products in the list", 0, ordered.size());
	}
	
}
