package com.elenai.elenaidodge.integration;

import static codersafterdark.reskillable.lib.LibMisc.MOD_ID;

import com.elenai.elenaidodge.ElenaiDodge;
import com.elenai.elenaidodge.ModConfig;
import com.elenai.elenaidodge.util.ClientStorage;

import codersafterdark.reskillable.api.unlockable.Trait;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class ReskillableTraitDodge extends Trait {
	
    public ReskillableTraitDodge() {
    	super(new ResourceLocation(ElenaiDodge.MODID, "dodge"), 2, 0, new ResourceLocation(MOD_ID, "agility"), 0, "");
    	 if (FMLCommonHandler.instance().getSide().isClient()) {
             MinecraftForge.EVENT_BUS.register(this);
         }
    }

    @Override
    public void onUnlock(EntityPlayer player) {
    	if(ModConfig.client.hud.tutorial) {
    	ClientStorage.shownTutorial = false;
		ClientStorage.tutorialDodges = 0;
    	}
    }
    
}
