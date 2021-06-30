package com.elenai.elenaidodge.capability.weight;

public class Weight implements IWeight {

	private double weight = 0;

	@Override
	public void set(double weight) {
		this.weight = weight;
	}

	@Override
	public double getWeight() {
		return this.weight;
	}

}
