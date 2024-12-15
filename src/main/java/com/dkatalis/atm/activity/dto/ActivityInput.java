package com.dkatalis.atm.activity.dto;

public class ActivityInput {

	private String name;
	
	private double amount;
	
	private String recipientName;
	
	public ActivityInput(Builder builder) {
		this.amount = builder.amount;
		this.name = builder.name;
		this.recipientName = builder.recipientName;
	}
	
	public String getName() {
		return name;
	}

	public double getAmount() {
		return amount;
	}

	public String getRecipientName() {
		return recipientName;
	}

	
	public static Builder Builder() {
		return new Builder();
	}
	

    public static class Builder {

	    private String name;
	    private double amount;
	    private String recipientName;

	    public Builder withName(String name) {
	        this.name = name;
	        return this;
	    }

	    public Builder withAmount(double amount) {
	        this.amount = amount;
	        return this;
	    }

	    public Builder withRecipientName(String recipientName) {
	        this.recipientName = recipientName;
	        return this;
	    }

	    public ActivityInput build() {
	        return new ActivityInput(this);
	    }
	}

	
}
