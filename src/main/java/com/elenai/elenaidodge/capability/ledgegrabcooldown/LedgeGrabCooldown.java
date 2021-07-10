package com.elenai.elenaidodge.capability.ledgegrabcooldown;

public class LedgeGrabCooldown implements ILedgeGrabCooldown {

	private int ledgeGrabs = 0;

	@Override
	public void increase(int ledgeGrabs) {
		this.ledgeGrabs += ledgeGrabs;
	}

	@Override
	public void decrease(int ledgeGrabs) {
		this.ledgeGrabs -= ledgeGrabs;
	}

	@Override
	public void set(int ledgeGrabs) {
		this.ledgeGrabs = ledgeGrabs;
	}

	@Override
	public int getLedgeGrabs() {
		return this.ledgeGrabs;
	}

}
