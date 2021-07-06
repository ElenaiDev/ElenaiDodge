package com.elenai.elenaidodge.capability.enabled;

public class Enabled implements IEnabled {

	private boolean enabled = true;

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
