package com.wave.Wave.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import com.wave.Wave.mainPackage.JDAMain;
import net.dv8tion.jda.api.entities.Activity;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * This class schedules tracks for the audio player. It contains the queue of tracks.
 */
public class TrackScheduler extends AudioEventAdapter {
  private final AudioPlayer player;
  private final BlockingQueue<AudioTrack> queue;
  private AudioTrack playingTrack;

  /**
   * @param player The audio player this scheduler uses
   */
  public TrackScheduler(AudioPlayer player) {
    this.player = player;
    this.queue = new LinkedBlockingQueue<>();
  }

  /**
   * Add the next track to queue or play right away if nothing is in the queue.
   *
   * @param track The track to play or add to queue.
   */
  public void queue(AudioTrack track) {
    // Calling startTrack with the noInterrupt set to true will start the track only if nothing is currently playing. If
    // something is playing, it returns false and does nothing. In that case the player was already playing so this
    // track goes to the queue instead.
    if (!player.startTrack(track, true)) {
      queue.offer(track);
    }
  }

  public BlockingQueue<AudioTrack> getQueue(){
	  return queue;
  }
  
  /**
   * Start the next track, stopping the current one if it is playing.
   */
  public void nextTrack() {
    // Start the next track, regardless of if something is already playing or not. In case queue was empty, we are
    // giving null to startTrack, which is a valid argument and will simply stop the player.
    player.startTrack(queue.poll(), false);
  }

  @Override
  public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
    // Only start the next track if the end reason is suitable for it (FINISHED or LOAD_FAILED)
	  
    if (endReason.mayStartNext) {
      nextTrack();
      return;
    }
    
    if(endReason.equals(AudioTrackEndReason.STOPPED))
    JDAMain.getJDA().getPresence().setActivity(Activity.playing("Nothing in Queue"));
    
    if(endReason.equals(AudioTrackEndReason.LOAD_FAILED)) {
    JDAMain.getJDA().getPresence().setActivity(Activity.playing("Error while loading Track"));
    }
    
  }
  
  @Override
  public void onTrackStart(AudioPlayer player, AudioTrack track) {
	  this.playingTrack = track;
	  JDAMain.getJDA().getPresence().setActivity(Activity.playing(playingTrack.getInfo().title));
    // A track started playing
  }
  
  @Override
  public void onPlayerPause(AudioPlayer player) {
	  JDAMain.getJDA().getPresence().setActivity(Activity.playing("paused"));
    // A track started playing
  }
  
  @Override
  public void onPlayerResume(AudioPlayer player) {
	  if(!JDAMain.getJDA().getPresence().getActivity().getName().equals("Nothing in Queue")) {
		  JDAMain.getJDA().getPresence().setActivity(Activity.playing(playingTrack.getInfo().title));
	  }
    // A track started playing
  }
  
}