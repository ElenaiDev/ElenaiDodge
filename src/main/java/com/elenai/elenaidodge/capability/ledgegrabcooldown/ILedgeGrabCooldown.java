package com.elenai.elenaidodge.capability.ledgegrabcooldown;

public interface ILedgeGrabCooldown {

	public void increase(int ledgeGrabs);
	public void decrease(int ledgeGrabs);
	public void set(int ledgeGrabs);

	public int getLedgeGrabs();
}
