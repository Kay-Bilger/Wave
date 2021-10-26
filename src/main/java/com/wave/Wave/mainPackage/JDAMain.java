package com.wave.Wave.mainPackage;

import com.wave.Wave.config.Config;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;

public class JDAMain {

	public static JDA jda;
	
	public JDAMain() throws LoginException{
		jda = JDABuilder.createDefault(Config.getInstance().getString("token"))
				.addEventListeners(new MessageHandler())
				.setActivity(Activity.watching(Config.getInstance().getString("prefix") + "help"))
				.setStatus(OnlineStatus.ONLINE)
				.build();
	}
	
	public static JDA getJDA() {
		return jda;
	}
}
