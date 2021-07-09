package com.elenai.elenaidodge.capability.ledgegrabs;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class LedgeGrabsProvider implements ICapabilitySerializable<NBTBase> {

	@CapabilityInject(ILedgeGrabs.class)
	public static final Capability<ILedgeGrabs> LEDGEGRABS_CAP = null;
	
	private ILedgeGrabs instance = LEDGEGRABS_CAP.getDefaultInstance();
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == LEDGEGRABS_CAP;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return capability == LEDGEGRABS_CAP ? LEDGEGRABS_CAP.<T> cast (this.instance) : null;
	}

	@Override
	public NBTBase serializeNBT() {
		return LEDGEGRABS_CAP.getStorage().writeNBT(LEDGEGRABS_CAP, this.instance, null);
	}

	@Override
	public void deserializeNBT(NBTBase nbt) {
		LEDGEGRABS_CAP.getStorage().readNBT(LEDGEGRABS_CAP, this.instance, null, nbt);
	}

}
