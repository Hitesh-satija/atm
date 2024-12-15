package com.dkatalis.atm.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * 
 */
public class Account {

	private String name;
	private double balance;
	private Map<String, Double> debtPayable;
	private Map<String, Double> debtReceivable;

	private Account(Builder builder) {
		this.balance = builder.balance;
		this.name = builder.name;
		this.debtPayable = new HashMap<String, Double>();
		this.debtReceivable = new HashMap<String, Double>();
	}
	
	public void setDebtPayable(Map<String, Double> debtPayable) {
		this.debtPayable = debtPayable;
	}

	public void setDebtReceivable(Map<String, Double> debtReceivable) {
		this.debtReceivable = debtReceivable;
	}
	
	public Map<String, Double> getDebtPayable() {
		return debtPayable;
	}

	public Map<String, Double> getDebtReceivable() {
		return debtReceivable;
	}

	public boolean isAnyDebtPayable() {
		return this.getDebtPayable().size() > 0;
	}
	
	public double getTotalDebt() {
		return this.getDebtPayable().values()
				.stream().mapToDouble(Double::doubleValue).sum();
	}
	
	public String getName() {
		return name;
	}
	
	public double getBalance() {
		return balance;
	}
	
	
	public double increaseBalance(double amount) {
		balance = balance + amount;
		return balance;
	}
	
	public double decreaseBalance(double amount) {
		balance = balance - amount;
		return balance;
	}
	 
	
	public static Builder builder() {
        return new Builder();
    }
	
	public static class Builder {
		
		private String name;
        private double balance = 0;
		
		public Builder withName(final String name) {
			this.name = name;
			return this;
		}
		
		public Builder withBalance(double balance) {
			this.balance = balance;
			return this;
		}
		
		public Account build() {
            if (name == null || name.isEmpty()) {
                throw new IllegalStateException("Name cannot be null or empty");
            }
            return new Account(this);
        }
	}

	public void payDebt(double amount) {
		if(amount<=0) {
			return;
		}
		
		Set<Entry<String, Double>> entrySet = debtPayable.entrySet();
		for(Entry<String,Double> entry : entrySet) {
			double debtAmount = entry.getValue();
			if(entry.getValue() >= amount) {
				debtPayable.remove(entry.getKey());
				payDebt(amount - debtAmount);
			}else {
				debtPayable.put(entry.getKey(), debtAmount - amount);
			}
		}
	}
	
	public void addPayableDebt(final String name, double amount) {
		this.debtPayable.put(name, amount);
	}
	
	public void addReceivableDebt(final String name, double amount) {
		this.debtReceivable.put(name, amount);
	}
	
}
