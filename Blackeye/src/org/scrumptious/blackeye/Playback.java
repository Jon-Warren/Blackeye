package org.scrumptious.blackeye;
import java.io.File;
import java.io.IOException;
import android.media.*;
import android.os.Environment;
import android.provider.MediaStore.Audio.Media;


public class Playback {
	
	private MediaPlayer mediaPlayer;
	
	public Playback() {
		mediaPlayer = new MediaPlayer();
	}
	
	public void playAudio(String url) throws IllegalArgumentException, SecurityException, IllegalStateException, IOException {
		File root = Environment.getExternalStorageDirectory();
		File newFile = new File(root, url+".mp3");
		mediaPlayer.reset();
		mediaPlayer.setDataSource(newFile.getCanonicalPath());
		mediaPlayer.prepare();
	    mediaPlayer.start();
	}
	
	public void stop() {
		mediaPlayer.stop();
	}
	
	public boolean isPlaying() {
		if(mediaPlayer != null)
			return mediaPlayer.isPlaying();
		else
			return false;
	}

	public void pause() {
		// TODO Auto-generated method stub
		mediaPlayer.pause();
	}
	
	public void resume() {
		mediaPlayer.start();
	}
	
}	
