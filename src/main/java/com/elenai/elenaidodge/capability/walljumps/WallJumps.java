package com.elenai.elenaidodge.capability.walljumps;

public class WallJumps implements IWallJumps {

	private int wallJumps = 0;

	@Override
	public void increase(int wallJumps) {
		this.wallJumps += wallJumps;
	}

	@Override
	public void decrease(int wallJumps) {
		this.wallJumps -= wallJumps;
	}

	@Override
	public void set(int wallJumps) {
		this.wallJumps = wallJumps;
	}

	@Override
	public int getWallJumps() {
		return this.wallJumps;
	}

}
