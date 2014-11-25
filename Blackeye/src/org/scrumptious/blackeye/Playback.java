package org.scrumptious.blackeye;
import java.io.File;
import java.io.IOException;
import android.media.*;
import android.os.Environment;
import android.provider.MediaStore.Audio.Media;
import android.util.Log;


public class Playback {
	
	private MediaPlayer mediaPlayer;
	
	public Playback() {
		mediaPlayer = new MediaPlayer();
	}
	
	
	public void playToResume(int position) {
		mediaPlayer.seekTo(position);
	    mediaPlayer.start();
	}
	
	public void stop() {
		mediaPlayer.pause();
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
		System.out.println(mediaPlayer.getCurrentPosition());
	}
	
	public void resume() {
		mediaPlayer.start();
		System.out.println(mediaPlayer.getCurrentPosition());
	}
	
	public double getPercentPlayed() {
		double position = mediaPlayer.getCurrentPosition();
		double totalDuration = mediaPlayer.getDuration();
		System.out.println(position/totalDuration);
		return position/totalDuration;
	}
	
	public int getPosition() {
		return mediaPlayer.getCurrentPosition();
	}
	
	public MediaPlayer getPlayer() {
		return this.mediaPlayer;
	}
	 
	public void skipForward(int amnt) {
		if(mediaPlayer.getCurrentPosition() + amnt < mediaPlayer.getDuration())
			mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() + amnt);
	}
	
	public void skipBack(int amnt) {
		if(mediaPlayer.getCurrentPosition() - amnt > 0)
			mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() - amnt);
	}
	
	public void setSrc(String src) throws IllegalArgumentException, SecurityException, IllegalStateException, IOException {
		File root = Environment.getExternalStorageDirectory();
		File newFile = new File(root, src+".mp3");
		mediaPlayer = new MediaPlayer();
		mediaPlayer.reset();
		mediaPlayer.setDataSource(newFile.getAbsolutePath());
		mediaPlayer.prepare();
		mediaPlayer.start();
		mediaPlayer.pause();
		Log.d("Media",""+mediaPlayer.getDuration());
	}
}	
