package com.elenai.elenaidodge.capability.ledgegrabcooldown;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTPrimitive;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class LedgeGrabCooldownStorage implements IStorage<ILedgeGrabCooldown> {

	@Override
	public NBTBase writeNBT(Capability<ILedgeGrabCooldown> capability, ILedgeGrabCooldown instance, EnumFacing side) {
		return new NBTTagInt(instance.getLedgeGrabs());
	}

	@Override
	public void readNBT(Capability<ILedgeGrabCooldown> capability, ILedgeGrabCooldown instance, EnumFacing side, NBTBase nbt) {
		instance.set(((NBTPrimitive) nbt).getInt());
	}

}
