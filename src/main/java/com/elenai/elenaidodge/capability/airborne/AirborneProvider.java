package com.elenai.elenaidodge.capability.airborne;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class AirborneProvider implements ICapabilitySerializable<NBTBase> {

	@CapabilityInject(IAirborne.class)
	public static final Capability<IAirborne> AIRBORNE_CAP = null;
	
	private IAirborne instance = AIRBORNE_CAP.getDefaultInstance();
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == AIRBORNE_CAP;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return capability == AIRBORNE_CAP ? AIRBORNE_CAP.<T> cast (this.instance) : null;
	}

	@Override
	public NBTBase serializeNBT() {
		return AIRBORNE_CAP.getStorage().writeNBT(AIRBORNE_CAP, this.instance, null);
	}

	@Override
	public void deserializeNBT(NBTBase nbt) {
		AIRBORNE_CAP.getStorage().readNBT(AIRBORNE_CAP, this.instance, null, nbt);
	}

}
