package com.elenai.elenaidodge.capability.ledgegrabs;

public interface ILedgeGrabs {

	public void increase(int ledgeGrabs);
	public void decrease(int ledgeGrabs);
	public void set(int ledgeGrabs);

	public int getLedgeGrabs();
}
