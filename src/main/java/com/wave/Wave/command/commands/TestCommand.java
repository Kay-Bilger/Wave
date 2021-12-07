package com.wave.Wave.command.commands;

import com.wave.Wave.command.CommandContext;
import com.wave.Wave.command.ICommand;
import com.wave.Wave.mainPackage.Track;
import com.wave.Wave.mainPackage.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class TestCommand implements ICommand {

    @Autowired
    private TrackRepository repository;

    @Override
    public void handle(CommandContext ctx) {
        repository.save( new Track(ctx.getArgs().toString()));
        Track track = repository.findTrackByName("test");
        ctx.getChannel().sendMessage(track.toString()).queue();
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
