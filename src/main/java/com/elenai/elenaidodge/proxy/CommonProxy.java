package com.elenai.elenaidodge.proxy;

import com.elenai.elenaidodge.ElenaiDodge;
import com.elenai.elenaidodge.capability.CapabilityHandler;
import com.elenai.elenaidodge.capability.airborne.Airborne;
import com.elenai.elenaidodge.capability.airborne.AirborneStorage;
import com.elenai.elenaidodge.capability.airborne.IAirborne;
import com.elenai.elenaidodge.capability.enabled.Enabled;
import com.elenai.elenaidodge.capability.enabled.EnabledStorage;
import com.elenai.elenaidodge.capability.enabled.IEnabled;
import com.elenai.elenaidodge.capability.invincibility.IInvincibility;
import com.elenai.elenaidodge.capability.invincibility.Invincibility;
import com.elenai.elenaidodge.capability.invincibility.InvincibilityStorage;
import com.elenai.elenaidodge.capability.joined.IJoined;
import com.elenai.elenaidodge.capability.joined.Joined;
import com.elenai.elenaidodge.capability.joined.JoinedStorage;
import com.elenai.elenaidodge.capability.ledgegrabcooldown.ILedgeGrabCooldown;
import com.elenai.elenaidodge.capability.ledgegrabcooldown.LedgeGrabCooldown;
import com.elenai.elenaidodge.capability.ledgegrabcooldown.LedgeGrabCooldownStorage;
import com.elenai.elenaidodge.capability.ledgegrabs.ILedgeGrabs;
import com.elenai.elenaidodge.capability.ledgegrabs.LedgeGrabs;
import com.elenai.elenaidodge.capability.ledgegrabs.LedgeGrabsStorage;
import com.elenai.elenaidodge.capability.particles.IParticles;
import com.elenai.elenaidodge.capability.particles.Particles;
import com.elenai.elenaidodge.capability.particles.ParticlesStorage;
import com.elenai.elenaidodge.capability.regen.IRegen;
import com.elenai.elenaidodge.capability.regen.Regen;
import com.elenai.elenaidodge.capability.regen.RegenStorage;
import com.elenai.elenaidodge.capability.walljumpcooldown.IWallJumpCooldown;
import com.elenai.elenaidodge.capability.walljumpcooldown.WallJumpCooldown;
import com.elenai.elenaidodge.capability.walljumpcooldown.WallJumpCooldownStorage;
import com.elenai.elenaidodge.capability.walljumps.IWallJumps;
import com.elenai.elenaidodge.capability.walljumps.WallJumps;
import com.elenai.elenaidodge.capability.walljumps.WallJumpsStorage;
import com.elenai.elenaidodge.capability.weight.IWeight;
import com.elenai.elenaidodge.capability.weight.Weight;
import com.elenai.elenaidodge.capability.weight.WeightStorage;
import com.elenai.elenaidodge.event.ConfigEventListener;
import com.elenai.elenaidodge.event.InvincibilityEventListener;
import com.elenai.elenaidodge.event.PotionTickEventListener;
import com.elenai.elenaidodge.event.RenderEventListener;
import com.elenai.elenaidodge.event.ServerDodgeEventListener;
import com.elenai.elenaidodge.event.ServerLedgeGrabEventListener;
import com.elenai.elenaidodge.event.ServerWallJumpEventListener;
import com.elenai.elenaidodge.event.TickEventListener;
import com.elenai.elenaidodge.init.ItemInit;
import com.elenai.elenaidodge.init.PotionInit;
import com.elenai.elenaidodge.network.PacketHandler;
import com.elenai.elenaidodge.util.PatronRewardHandler;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod.EventBusSubscriber(modid = ElenaiDodge.MODID)
public class CommonProxy {

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		PacketHandler.registerMessages(ElenaiDodge.MODID);
		PatronRewardHandler.init();
		CapabilityManager.INSTANCE.register(IJoined.class, new JoinedStorage(), Joined::new);
		CapabilityManager.INSTANCE.register(IWeight.class, new WeightStorage(), Weight::new);
		CapabilityManager.INSTANCE.register(IInvincibility.class, new InvincibilityStorage(), Invincibility::new);
		CapabilityManager.INSTANCE.register(IParticles.class, new ParticlesStorage(), Particles::new);
		CapabilityManager.INSTANCE.register(IRegen.class, new RegenStorage(), Regen::new);
		CapabilityManager.INSTANCE.register(IEnabled.class, new EnabledStorage(), Enabled::new);
		CapabilityManager.INSTANCE.register(IAirborne.class, new AirborneStorage(), Airborne::new);
		CapabilityManager.INSTANCE.register(IWallJumps.class, new WallJumpsStorage(), WallJumps::new);
		CapabilityManager.INSTANCE.register(ILedgeGrabs.class, new LedgeGrabsStorage(), LedgeGrabs::new);
		CapabilityManager.INSTANCE.register(ILedgeGrabCooldown.class, new LedgeGrabCooldownStorage(), LedgeGrabCooldown::new);
		CapabilityManager.INSTANCE.register(IWallJumpCooldown.class, new WallJumpCooldownStorage(), WallJumpCooldown::new);

		MinecraftForge.EVENT_BUS.register(new RenderEventListener());
		ItemInit.init();
		PotionInit.registerPotions();

	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(new TickEventListener());
		MinecraftForge.EVENT_BUS.register(new ConfigEventListener());
		MinecraftForge.EVENT_BUS.register(new ServerDodgeEventListener());
		MinecraftForge.EVENT_BUS.register(new CapabilityHandler());
		MinecraftForge.EVENT_BUS.register(new PotionTickEventListener());
		MinecraftForge.EVENT_BUS.register(new ItemInit());
		MinecraftForge.EVENT_BUS.register(new InvincibilityEventListener());
		
		MinecraftForge.EVENT_BUS.register(new ServerWallJumpEventListener());
		MinecraftForge.EVENT_BUS.register(new ServerLedgeGrabEventListener());
		


	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {

	}

}
