package com.elenai.elenaidodge.command;

import java.util.StringJoiner;

import com.elenai.elenaidodge.command.main.DisableAirborne;
import com.elenai.elenaidodge.command.main.DisableDodge;
import com.elenai.elenaidodge.command.main.EnableAirborne;
import com.elenai.elenaidodge.command.main.EnableDodge;
import com.elenai.elenaidodge.command.main.ToggleAirborne;
import com.elenai.elenaidodge.command.main.ToggleDodge;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.server.command.CommandTreeBase;

public class MainCommandTree extends CommandTreeBase {

	public MainCommandTree() {
	
		this.addSubcommand(new EnableDodge());
		this.addSubcommand(new DisableDodge());
		this.addSubcommand(new ToggleDodge());
		this.addSubcommand(new EnableAirborne());
		this.addSubcommand(new DisableAirborne());
		this.addSubcommand(new ToggleAirborne());

		
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {

		if (args.length == 0) {
			sender.sendMessage(new TextComponentString(this.getSubCommandDescriptions(sender)));
		}

		else {
			super.execute(server, sender, args);
		}
	}

	private String getSubCommandDescriptions(ICommandSender sender) {
		final StringJoiner joiner = new StringJoiner("\n");

		for (final ICommand command : this.getSubCommands()) {
			joiner.add(command.getUsage(sender));
		}
		return joiner.toString();
	}
	
    @Override
    public int getRequiredPermissionLevel () {
        return 0;
    }

	@Override
	public String getName() {
		return "elenaidodge";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/elenaidodge";
	}
}
