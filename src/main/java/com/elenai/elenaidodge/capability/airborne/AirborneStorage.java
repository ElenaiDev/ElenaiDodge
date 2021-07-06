package com.elenai.elenaidodge.capability.airborne;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTPrimitive;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class AirborneStorage implements IStorage<IAirborne> {

	@Override
	public NBTBase writeNBT(Capability<IAirborne> capability, IAirborne instance, EnumFacing side) {
		return new NBTTagInt(instance.isEnabled() ? 1 : 0);
	}

	@Override
	public void readNBT(Capability<IAirborne> capability, IAirborne instance, EnumFacing side, NBTBase nbt) {
		int i = ((NBTPrimitive) nbt).getInt(); 
		boolean b = i > 0;
		instance.set(b);
	}

}
