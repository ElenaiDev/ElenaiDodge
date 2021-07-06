package com.elenai.elenaidodge.capability.airborne;

public class Airborne implements IAirborne {

	private boolean enabled = false;

	@Override
	public void enable() {
		this.enabled = true;
	}
	
	@Override
	public void disable() {
		this.enabled = false;
	}

	@Override
	public boolean isEnabled() {
		return this.enabled;
	}

	@Override
	public void set(boolean bool) {
		this.enabled = bool;
	}



}
