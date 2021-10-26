package com.wave.Wave;

import com.wave.Wave.config.Config;
import com.wave.Wave.mainPackage.JDAMain;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.IOException;

@SpringBootApplication
public class WaveApplication {

	public static void main(String[] args) throws LoginException, IOException {
		SpringApplication.run(WaveApplication.class, args);
		new Config(new File("BotConfig.json"));
		new JDAMain();
	}

}
