package com.elenai.elenaidodge.capability.walljumpcooldown;

public interface IWallJumpCooldown {

	public void increase(int wallJumps);
	public void decrease(int wallJumps);
	public void set(int wallJumps);

	public int getWallJumps();
}
