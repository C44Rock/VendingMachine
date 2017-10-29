package com.techelevator;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import org.junit.Assert;

public class MoneyTest {
	Money money;
	
	@Before
	public void setup() {
		money = new Money();
	}
	
	@Test
	public void moneyInitializesCorrectly() {
		Assert.assertEquals("should be 0", BigDecimal.ZERO, money.getBalance());
	}
	
	@Test
	public void balanceAddsCorrectly() {
		money.addBalance(new BigDecimal("2"));
		Assert.assertEquals("should be 2", new BigDecimal("2"), money.getBalance());
	}
	
	@Test
	public void balanceSubtractsCorrectly() {
		money.subtractBalance(new BigDecimal("1"));
		Assert.assertEquals("should be -1", new BigDecimal("-1"), money.getBalance());
	}
	
	@Test
	public void wipeBalanceWorksCorrectly() {
		money.wipeBalance();
		Assert.assertEquals("should be 0", BigDecimal.ZERO, money.getBalance());
	}

	@Test
	public void quartersWorksCorrectly() {
		Assert.assertEquals("should be 8", 8, money.giveBackQuarters(new BigDecimal("2")));
	}
	
	@Test
	public void dimesWorksCorrectly() {
		Assert.assertEquals("should be 20", 20, money.giveBackDimes(new BigDecimal("2")));
	}
	
	@Test
	public void nickelsWorksCorrectly() {
		Assert.assertEquals("should be 40", 40, money.giveBackNickels(new BigDecimal("2")));
	}
}
