package com.elenai.elenaidodge.capability.walljumps;

public interface IWallJumps {

	public void increase(int wallJumps);
	public void decrease(int wallJumps);
	public void set(int wallJumps);

	public int getWallJumps();
}
