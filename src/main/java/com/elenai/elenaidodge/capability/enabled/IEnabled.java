package com.elenai.elenaidodge.capability.enabled;

public interface IEnabled {

	public void enable();
	public void disable();

	public void set(boolean bool);

	public boolean isEnabled();
}
