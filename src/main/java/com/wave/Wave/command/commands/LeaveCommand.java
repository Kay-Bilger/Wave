package com.wave.Wave.command.commands;


import com.wave.Wave.command.CommandContext;
import com.wave.Wave.command.ICommand;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;

public class LeaveCommand implements ICommand {

	@Override
	public void handle(CommandContext ctx) {
		AudioManager audioManager = ctx.getGuild().getAudioManager();
		
		if(!audioManager.isConnected()) {
			ctx.getChannel().sendMessage("Not Connected to a voicechannel").queue();
			return;
		}
		
		VoiceChannel voiceChannel = audioManager.getConnectedChannel();
		
		if(!voiceChannel.getMembers().contains(ctx.getMember())) {
			ctx.getChannel().sendMessage("You have to be in my voicechannel to use me").queue();
			return;
		}
		
		audioManager.closeAudioConnection();
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "leave";
	}

	@Override
	public String getHelp() {
		// TODO Auto-generated method stub
		return "Bot leaves your voicechannel";
	}

}
