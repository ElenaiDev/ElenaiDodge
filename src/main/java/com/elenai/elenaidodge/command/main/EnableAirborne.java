package com.elenai.elenaidodge.command.main;

import java.util.ArrayList;
import java.util.List;

import com.elenai.elenaidodge.capability.airborne.AirborneProvider;
import com.elenai.elenaidodge.capability.airborne.IAirborne;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class EnableAirborne implements ICommand {

	private final List<String> aliases;

	public EnableAirborne() {
		aliases = new ArrayList<String>();
		aliases.add("enable_airborne");

	}

	@Override
	public int compareTo(ICommand o) {
		return 0;
	}

	@Override
	public String getName() {
		return "enable_airborne";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/elenaidodge enable_airborne <player>";
	}

	@Override
	public List<String> getAliases() {
		return this.aliases;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {

		if (args.length == 0) {
			sender.sendMessage(new TextComponentString(TextFormatting.RED + "/elenaidodge enable_airborne <player>"));
		} else
			try {
				EntityPlayer target = CommandBase.getPlayer(server, sender, args[0]);
				World world = sender.getEntityWorld();

				if (!world.isRemote) {
					IAirborne a = target.getCapability(AirborneProvider.AIRBORNE_CAP, null);
					a.enable();
					sender.sendMessage(new TextComponentString(
							TextFormatting.GREEN + "Enabled dodging whilst airborne for " + target.getName() + "!"));
				}

			} catch (NumberFormatException e) {
				sender.sendMessage(new TextComponentString(TextFormatting.RED + "No player found!"));
			}

	}

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
		return true;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args,
			BlockPos targetPos) {
		if (args.length == 1)
			return CommandBase.getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames());
		else
			return new ArrayList();
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index) {
		return false;
	}

}
