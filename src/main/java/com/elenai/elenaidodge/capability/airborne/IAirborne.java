package com.elenai.elenaidodge.capability.airborne;

public interface IAirborne {

	public void enable();
	public void disable();

	public void set(boolean bool);

	public boolean isEnabled();
}
