package com.wave.Wave.command.commands;

import com.wave.Wave.command.CommandContext;
import com.wave.Wave.command.ICommand;
import com.wave.Wave.mainPackage.Track;
import com.wave.Wave.mainPackage.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;


public class TestCommand implements ICommand {

    @Autowired
    private TrackRepository trackRepository;

    @Override
    public void handle(CommandContext ctx) {
        trackRepository.save( new Track(ctx.getArgs().toString()));
        Track track = trackRepository.findTrackByName("test");
        //ctx.getChannel().sendMessage(track.toString()).queue();
        ctx.getChannel().sendMessage("test-success").queue();
    }

    @Override
    public String getName() {
        return "test";
    }

    @Override
    public String getHelp() {
        return "just for testing";
    }
}
