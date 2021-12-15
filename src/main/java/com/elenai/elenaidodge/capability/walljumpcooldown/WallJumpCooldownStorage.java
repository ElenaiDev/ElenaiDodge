package com.elenai.elenaidodge.capability.walljumpcooldown;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTPrimitive;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class WallJumpCooldownStorage implements IStorage<IWallJumpCooldown> {

	@Override
	public NBTBase writeNBT(Capability<IWallJumpCooldown> capability, IWallJumpCooldown instance, EnumFacing side) {
		return new NBTTagInt(instance.getWallJumps());
	}

	@Override
	public void readNBT(Capability<IWallJumpCooldown> capability, IWallJumpCooldown instance, EnumFacing side, NBTBase nbt) {
		instance.set(((NBTPrimitive) nbt).getInt());
	}

}
