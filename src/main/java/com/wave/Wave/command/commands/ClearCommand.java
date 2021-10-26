package com.wave.Wave.command.commands;


import com.wave.Wave.command.CommandContext;
import com.wave.Wave.command.ICommand;
import com.wave.Wave.music.GuildMusicManager;
import com.wave.Wave.music.PlayerManager;

public class ClearCommand implements ICommand {

		@Override
		public void handle(CommandContext ctx) {
			PlayerManager manager = PlayerManager.getInstance();
			GuildMusicManager musicManager = manager.getGuildMusicManager(ctx.getGuild());
			
			musicManager.scheduler.getQueue().clear();
			musicManager.player.stopTrack();
			musicManager.player.setPaused(false);
			
			ctx.getChannel().sendMessage("Clearing the queue").queue();
		}

		@Override
		public String getName() {
			// TODO Auto-generated method stub
			return "clear";
		}

		@Override
		public String getHelp() {
			// TODO Auto-generated method stub
			return "Clears the Queue";
		}
		

	}
