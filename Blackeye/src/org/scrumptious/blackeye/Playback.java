package org.scrumptious.blackeye;
import java.io.IOException;

import android.media.*;

public class Playback {
	
	private MediaPlayer mediaPlayer;
	
	public void playAudio(String url) throws IllegalArgumentException, SecurityException, IllegalStateException, IOException {
		mediaPlayer = new MediaPlayer();
	    mediaPlayer.setDataSource(url);
	    mediaPlayer.prepare();
	    mediaPlayer.start();
	}
	
}