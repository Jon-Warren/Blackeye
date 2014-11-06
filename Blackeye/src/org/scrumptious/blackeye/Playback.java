package org.scrumptious.blackeye;
import java.io.IOException;

import android.media.*;

public class Playback {
	
	private MediaPlayer mediaPlayer;
	
	public Playback() {
		mediaPlayer = new MediaPlayer();
	}
	
	public void playAudio(String url) throws IllegalArgumentException, SecurityException, IllegalStateException, IOException {
		mediaPlayer.reset();
		mediaPlayer.setDataSource(url);
		mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		mediaPlayer.prepare();
	    mediaPlayer.start();
	}
	
	public void stop() {
		mediaPlayer.stop();
	}
	
}
