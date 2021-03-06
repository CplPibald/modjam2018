package com.rwtema.monkmod.command;

import com.rwtema.monkmod.MonkManager;
import com.rwtema.monkmod.MonkMod;
import com.rwtema.monkmod.data.MonkData;
import net.minecraft.advancements.Advancement;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.server.command.CommandTreeBase;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class CommandMonkLevelManip extends CommandTreeBase {

	{

		addSubcommand(new CommandBase() {
			@Nonnull
			@Override
			public String getName() {
				return "setlevel";
			}

			@Nonnull
			@Override
			public String getUsage(@Nonnull ICommandSender sender) {
				return "setlevel";
			}

			@Override
			public int getRequiredPermissionLevel() {
				return 2;
			}

			@Override
			public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args) throws CommandException {

				EntityPlayerMP entityplayer = args.length > 1 ? getPlayer(server, sender, args[1]) : getCommandSenderAsPlayer(sender);

				MonkData monkData = MonkManager.get(entityplayer);

				int amount = parseInt(args[0]);

				if (amount < monkData.getLevel()) {
					Advancement advancement = FMLCommonHandler.instance().getMinecraftServerInstance().getAdvancementManager().getAdvancement(
							new ResourceLocation("monk:level_" + (amount + 1))
					);
					if (advancement != null) {

						revoke(entityplayer, advancement);

					}
				}

				monkData.setLevel(amount);
				MonkMod.TRIGGER.trigger(entityplayer, amount);
			}

			private void revoke(EntityPlayerMP entityplayer, Advancement advancement) {
				for (Advancement child : advancement.getChildren()) {
					revoke(entityplayer, child);
				}
				for (String s : advancement.getCriteria().keySet()) {
					entityplayer.getAdvancements().revokeCriterion(advancement, s);
				}
			}

			@Nonnull
			public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
				return args.length == 2 ? getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames()) : Collections.emptyList();
			}
		});
	}

	@Nonnull
	@Override
	public String getName() {
		return "monk";
	}

	@Nonnull
	@Override
	public String getUsage(@Nonnull ICommandSender sender) {
		return "monk";
	}

}
