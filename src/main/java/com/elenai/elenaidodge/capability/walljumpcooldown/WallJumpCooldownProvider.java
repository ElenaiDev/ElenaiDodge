package com.elenai.elenaidodge.capability.walljumpcooldown;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class WallJumpCooldownProvider implements ICapabilitySerializable<NBTBase> {

	@CapabilityInject(IWallJumpCooldown.class)
	public static final Capability<IWallJumpCooldown> WALLJUMPCOOLDOWN_CAP = null;
	
	private IWallJumpCooldown instance = WALLJUMPCOOLDOWN_CAP.getDefaultInstance();
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == WALLJUMPCOOLDOWN_CAP;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return capability == WALLJUMPCOOLDOWN_CAP ? WALLJUMPCOOLDOWN_CAP.<T> cast (this.instance) : null;
	}

	@Override
	public NBTBase serializeNBT() {
		return WALLJUMPCOOLDOWN_CAP.getStorage().writeNBT(WALLJUMPCOOLDOWN_CAP, this.instance, null);
	}

	@Override
	public void deserializeNBT(NBTBase nbt) {
		WALLJUMPCOOLDOWN_CAP.getStorage().readNBT(WALLJUMPCOOLDOWN_CAP, this.instance, null, nbt);
	}

}
