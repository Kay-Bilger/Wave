package com.wave.Wave.mainPackage;

import com.wave.Wave.config.Config;
import me.duncte123.botcommons.BotCommons;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageHandler extends ListenerAdapter {

	private final CommandManager manager = new CommandManager();
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		User user = event.getAuthor();
		String prefix = Config.getInstance().getString("prefix");
		String raw = event.getMessage().getContentRaw();
		System.out.println(raw);
		
		if(event.isWebhookMessage()) {
			return;
		}
	
		if(raw.equalsIgnoreCase(prefix + "kill") && user.getId().equals(Config.getInstance().getString("owner_id"))) {
			event.getChannel().sendMessage("Shutting down").queue();
			event.getJDA().shutdown();
			BotCommons.shutdown(event.getJDA());
			
			return;
		}
		
		if(raw.startsWith(prefix)) {
			manager.handle(event);
		}
	}
	
	public static void makeMessage(GuildMessageReceivedEvent event, String message) {
		event.getChannel().sendMessage(message).queue();
	}

	public static void makeMessage(GuildMessageReceivedEvent event, MessageEmbed message) {
		event.getChannel().sendMessage(message).queue();
	}
	
}
