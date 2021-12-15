package com.elenai.elenaidodge.capability;

import com.elenai.elenaidodge.ElenaiDodge;
import com.elenai.elenaidodge.capability.airborne.AirborneProvider;
import com.elenai.elenaidodge.capability.airborne.IAirborne;
import com.elenai.elenaidodge.capability.enabled.EnabledProvider;
import com.elenai.elenaidodge.capability.enabled.IEnabled;
import com.elenai.elenaidodge.capability.invincibility.InvincibilityProvider;
import com.elenai.elenaidodge.capability.joined.IJoined;
import com.elenai.elenaidodge.capability.joined.JoinedProvider;
import com.elenai.elenaidodge.capability.ledgegrabcooldown.LedgeGrabCooldownProvider;
import com.elenai.elenaidodge.capability.ledgegrabs.LedgeGrabsProvider;
import com.elenai.elenaidodge.capability.particles.ParticlesProvider;
import com.elenai.elenaidodge.capability.regen.RegenProvider;
import com.elenai.elenaidodge.capability.walljumpcooldown.WallJumpCooldownProvider;
import com.elenai.elenaidodge.capability.walljumps.WallJumpsProvider;
import com.elenai.elenaidodge.capability.weight.WeightProvider;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CapabilityHandler {

	public static final ResourceLocation JOINED_CAP = new ResourceLocation(ElenaiDodge.MODID, "joined");
	public static final ResourceLocation WEIGHT_CAP = new ResourceLocation(ElenaiDodge.MODID, "weight");
	public static final ResourceLocation INVINCIBILITY_CAP = new ResourceLocation(ElenaiDodge.MODID, "invincibility");
	public static final ResourceLocation PARTICLES_CAP = new ResourceLocation(ElenaiDodge.MODID, "particles");
	public static final ResourceLocation REGEN_CAP = new ResourceLocation(ElenaiDodge.MODID, "regen");
	public static final ResourceLocation ENABLED_CAP = new ResourceLocation(ElenaiDodge.MODID, "enabled");
	public static final ResourceLocation AIRBORNE_CAP = new ResourceLocation(ElenaiDodge.MODID, "airborne");
	public static final ResourceLocation WALLJUMPS_CAP = new ResourceLocation(ElenaiDodge.MODID, "walljumps");
	public static final ResourceLocation LEDGEGRABS_CAP = new ResourceLocation(ElenaiDodge.MODID, "ledgegrabs");
	public static final ResourceLocation LEDGEGRABCOOLDOWN_CAP = new ResourceLocation(ElenaiDodge.MODID, "ledgegrabcooldown");
	public static final ResourceLocation WALLJUMPCOOLDOWN_CAP = new ResourceLocation(ElenaiDodge.MODID, "walljumpcooldown");


	@SubscribeEvent
	public void onEntityConstructing(AttachCapabilitiesEvent<Entity> event) {
		if (event.getObject() instanceof EntityPlayer) {
			if (!event.getObject().hasCapability(JoinedProvider.JOINED_CAP, null)) {
				event.addCapability(JOINED_CAP, new JoinedProvider());
			}
			if (!event.getObject().hasCapability(WeightProvider.WEIGHT_CAP, null)) {
				event.addCapability(WEIGHT_CAP, new WeightProvider());
			}
			if (!event.getObject().hasCapability(InvincibilityProvider.INVINCIBILITY_CAP, null)) {
				event.addCapability(INVINCIBILITY_CAP, new InvincibilityProvider());
			}
			if (!event.getObject().hasCapability(ParticlesProvider.PARTICLES_CAP, null)) {
				event.addCapability(PARTICLES_CAP, new ParticlesProvider());
			}
			if (!event.getObject().hasCapability(RegenProvider.REGEN_CAP, null)) {
				event.addCapability(REGEN_CAP, new RegenProvider());
			}
			if (!event.getObject().hasCapability(EnabledProvider.ENABLED_CAP, null)) {
				event.addCapability(ENABLED_CAP, new EnabledProvider());
			}
			if (!event.getObject().hasCapability(AirborneProvider.AIRBORNE_CAP, null)) {
				event.addCapability(AIRBORNE_CAP, new AirborneProvider());
			}
			if (!event.getObject().hasCapability(WallJumpsProvider.WALLJUMPS_CAP, null)) {
				event.addCapability(WALLJUMPS_CAP, new WallJumpsProvider());
			}
			if (!event.getObject().hasCapability(LedgeGrabsProvider.LEDGEGRABS_CAP, null)) {
				event.addCapability(LEDGEGRABS_CAP, new LedgeGrabsProvider());
			}
			if (!event.getObject().hasCapability(LedgeGrabCooldownProvider.LEDGEGRABCOOLDOWN_CAP, null)) {
				event.addCapability(LEDGEGRABCOOLDOWN_CAP, new LedgeGrabCooldownProvider());
			}
			if (!event.getObject().hasCapability(WallJumpCooldownProvider.WALLJUMPCOOLDOWN_CAP, null)) {
				event.addCapability(WALLJUMPCOOLDOWN_CAP, new WallJumpCooldownProvider());
			}
		}
	}

	@SubscribeEvent
	public void onPlayerCloned(PlayerEvent.Clone event) {
		EntityPlayer player = event.getEntityPlayer();

		IJoined joined = player.getCapability(JoinedProvider.JOINED_CAP, null);
		IJoined oldJoined = event.getOriginal().getCapability(JoinedProvider.JOINED_CAP, null);
		joined.set(oldJoined.hasJoined());
		
		IEnabled enabled = player.getCapability(EnabledProvider.ENABLED_CAP, null);
		IEnabled oldEnabled = event.getOriginal().getCapability(EnabledProvider.ENABLED_CAP, null);
		enabled.set(oldEnabled.isEnabled());

		IAirborne airborne = player.getCapability(AirborneProvider.AIRBORNE_CAP, null);
		IAirborne oldAirborne = event.getOriginal().getCapability(AirborneProvider.AIRBORNE_CAP, null);
		airborne.set(oldAirborne.isEnabled());
		

	}

}
