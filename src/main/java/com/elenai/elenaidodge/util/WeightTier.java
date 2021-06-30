package com.elenai.elenaidodge.util;

public class WeightTier {


    private double entryWeight;
    public double getEntryWeight() {
		return entryWeight;
	}

	public void setEntryWeight(double entryWeight) {
		this.entryWeight = entryWeight;
	}

	public double getForce() {
		return force;
	}

	public void setForce(double force) {
		this.force = force;
	}

	public int getCooldown() {
		return cooldown;
	}

	public void setCooldown(int cooldown) {
		this.cooldown = cooldown;
	}

	private double force;
    private int cooldown;
  
    public WeightTier(double entryWeight, int cooldown, double force) {
  
        this.entryWeight = entryWeight;
        this.force = force;
        this.cooldown = cooldown;
    }
    
	
}
