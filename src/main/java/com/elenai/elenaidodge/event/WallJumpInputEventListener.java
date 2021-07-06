package com.elenai.elenaidodge.event;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.input.Keyboard;

import com.elenai.elenaidodge.api.WallJumpEvent;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;

public class WallJumpInputEventListener {

	/*
	 * The Input Event Listener 1) This class fires a dodge event when the dodge key
	 * is pressed with a movement key if double tap mode is disabled. 2) This class
	 * fires a dodge event when a movement key is double tapped if double tap mode
	 * is enabled.
	 */

	public static boolean needsInit = true;
	public static HashMap<KeyBinding, Long> keyTimesLastPressed = new HashMap<>();
	public static HashMap<KeyBinding, Boolean> keyLastState = new HashMap<>();
	public static HashMap<KeyBinding, String> lookupKeyToDirection = new HashMap<>();

	@SubscribeEvent
	public void onKeyInput(TickEvent.ClientTickEvent event) {

		if (event.phase == Phase.START && Minecraft.getMinecraft().currentScreen == null) {

			if (needsInit) {
				needsInit = false;
				tickInit();
			}
			tickWallJump();
		}
	}

	public static void tickInit() {
		lookupKeyToDirection.put(Minecraft.getMinecraft().gameSettings.keyBindJump, "jump");
		keyLastState.put(Minecraft.getMinecraft().gameSettings.keyBindJump, false);
	}

	public static void tickWallJump() {
		lookupKeyToDirection.forEach((k, d) -> processKey(k, d));
	}

	public static void processKey(KeyBinding key, String direction) {
		long curTime = System.currentTimeMillis();
		long lastTime = getLastKeyTime(key);

		if (key.getKeyCode() > 0) {
			if (Keyboard.isKeyDown(key.getKeyCode()) && !keyLastState.get(key)) {
				if (lastTime == -1L) {
					setLastKeyTime(key, curTime);
				} else {
					if (lastTime + 70 > curTime) {
						setLastKeyTime(key, -1L);
					} else {
						if (!Minecraft.getMinecraft().player.onGround) {
							WallJumpEvent ev = new WallJumpEvent.RequestWallJumpEvent();
							MinecraftForge.EVENT_BUS.post(ev);
						}
						setLastKeyTime(key, curTime);
					}
				}
			}

			if (!Keyboard.isKeyDown(key.getKeyCode()) && keyLastState.get(key)) {
				for (Map.Entry<KeyBinding, Long> entry : keyTimesLastPressed.entrySet()) {
					if (entry.getKey() != key) {
						entry.setValue(-1L);
					}
				}
			}
			keyLastState.put(key, Keyboard.isKeyDown(key.getKeyCode()));
		}
	}

	public static long getLastKeyTime(KeyBinding keybind) {
		if (!keyTimesLastPressed.containsKey(keybind)) {
			keyTimesLastPressed.put(keybind, -1L);
		}
		return keyTimesLastPressed.get(keybind);
	}

	public static void setLastKeyTime(KeyBinding keybind, long time) {
		keyTimesLastPressed.put(keybind, time);
	}
}
