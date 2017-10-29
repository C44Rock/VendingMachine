package com.techelevator;

import java.math.BigDecimal;

public class Money {
	public static final BigDecimal QUARTER = new BigDecimal("0.25");
	public static final BigDecimal DIME = new BigDecimal("0.10");
	public static final BigDecimal NICKEL = new BigDecimal("0.05");
	
	private BigDecimal balance;
	
	public Money() {
		balance = new BigDecimal(0);
	}
	
	public BigDecimal getBalance() {
		return balance;
	}
	
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	
	public BigDecimal addBalance(BigDecimal money) {
		return balance = balance.add(money);
	}
	
	public BigDecimal subtractBalance(BigDecimal money) {
		return balance = balance.subtract(money);
	}
	
	public void wipeBalance() {
		balance = new BigDecimal(0);
	}
	
	public int giveBackQuarters(BigDecimal balance) {
		int quarters = 0;
		
		while (balance.compareTo(QUARTER) >= 0) {
			quarters++;
			balance = balance.subtract(QUARTER);
		}

		return quarters;
	}
	
	public int giveBackDimes(BigDecimal balance) {
		int dimes = 0;
		
		while (balance.compareTo(DIME) >= 0) {
			dimes++;
			balance = balance.subtract(DIME);
		}

		return dimes;
	}
	
	public int giveBackNickels(BigDecimal balance) {
		int nickels = 0;
		
		while (balance.compareTo(NICKEL) >= 0) {
			nickels++;
			balance = balance.subtract(NICKEL);
		}

		return nickels;
	}

}
