package com.elenai.elenaidodge.integration;

import static codersafterdark.reskillable.lib.LibMisc.MOD_ID;

import com.elenai.elenaidodge.ElenaiDodge;

import codersafterdark.reskillable.api.unlockable.Trait;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class ReskillableTraitLedgeGrab extends Trait {
	
    public ReskillableTraitLedgeGrab() {
    	super(new ResourceLocation(ElenaiDodge.MODID, "ledgegrab"), 0, 2, new ResourceLocation(MOD_ID, "agility"), 0, "");
    	 if (FMLCommonHandler.instance().getSide().isClient()) {
             MinecraftForge.EVENT_BUS.register(this);
         }
    }
}
