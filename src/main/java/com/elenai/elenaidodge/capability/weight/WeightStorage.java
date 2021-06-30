package com.elenai.elenaidodge.capability.weight;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTPrimitive;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class WeightStorage implements IStorage<IWeight> {

	@Override
	public NBTBase writeNBT(Capability<IWeight> capability, IWeight instance, EnumFacing side) {
		return new NBTTagDouble(instance.getWeight());
	}

	@Override
	public void readNBT(Capability<IWeight> capability, IWeight instance, EnumFacing side, NBTBase nbt) {
		instance.set(((NBTPrimitive) nbt).getDouble());
	}

}
