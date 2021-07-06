package com.elenai.elenaidodge.capability.walljumps;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class WallJumpsProvider implements ICapabilitySerializable<NBTBase> {

	@CapabilityInject(IWallJumps.class)
	public static final Capability<IWallJumps> WALLJUMPS_CAP = null;
	
	private IWallJumps instance = WALLJUMPS_CAP.getDefaultInstance();
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == WALLJUMPS_CAP;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return capability == WALLJUMPS_CAP ? WALLJUMPS_CAP.<T> cast (this.instance) : null;
	}

	@Override
	public NBTBase serializeNBT() {
		return WALLJUMPS_CAP.getStorage().writeNBT(WALLJUMPS_CAP, this.instance, null);
	}

	@Override
	public void deserializeNBT(NBTBase nbt) {
		WALLJUMPS_CAP.getStorage().readNBT(WALLJUMPS_CAP, this.instance, null, nbt);
	}

}
