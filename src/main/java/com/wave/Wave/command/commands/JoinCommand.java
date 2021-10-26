package com.wave.Wave.command.commands;


import com.wave.Wave.command.CommandContext;
import com.wave.Wave.command.ICommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;

public class JoinCommand implements ICommand {

	@Override
	public void handle(CommandContext ctx) {
		TextChannel channel = ctx.getChannel();
		AudioManager audioManager = ctx.getGuild().getAudioManager();
		
		if(audioManager.isConnected()) {
			channel.sendMessage("Allready connected to a channel").queue();
			return;
		}
		
		GuildVoiceState memberVoiceState = ctx.getMember().getVoiceState();
		
		if(!memberVoiceState.inVoiceChannel()) {
			channel.sendMessage("Please connect to a voicechannel first").queue();
			return;
		}
		
		VoiceChannel voiceChannel = memberVoiceState.getChannel();
		Member selfMember = ctx.getGuild().getSelfMember();
		
		if(!selfMember.hasPermission(voiceChannel, Permission.VOICE_CONNECT)) {
			channel.sendMessageFormat("Missing permission to join your voicechannel", voiceChannel).queue();
			return;
		}
		
		audioManager.openAudioConnection(voiceChannel);
	}

	@Override
	public String getName() {
		return "join";
	}

	@Override
	public String getHelp() {
		return "Bot joins your voicechannel";
	}

}
