package com.elenai.elenaidodge.capability.walljumps;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTPrimitive;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class WallJumpsStorage implements IStorage<IWallJumps> {

	@Override
	public NBTBase writeNBT(Capability<IWallJumps> capability, IWallJumps instance, EnumFacing side) {
		return new NBTTagInt(instance.getWallJumps());
	}

	@Override
	public void readNBT(Capability<IWallJumps> capability, IWallJumps instance, EnumFacing side, NBTBase nbt) {
		instance.set(((NBTPrimitive) nbt).getInt());
	}

}
