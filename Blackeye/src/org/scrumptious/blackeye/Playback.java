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
		//System.out.println(newFile.getCanonicalPath());
		mediaPlayer.reset();
		mediaPlayer.setDataSource(newFile.getCanonicalPath());
		mediaPlayer.prepare();
	    mediaPlayer.start();
	    System.out.println(mediaPlayer.getCurrentPosition());
	}
	
	public void playAudio(String url, int position) throws IllegalArgumentException, SecurityException, IllegalStateException, IOException {
		File root = Environment.getExternalStorageDirectory();
		File newFile = new File(root, url+".mp3");
		//System.out.println(newFile.getCanonicalPath());
		mediaPlayer.reset();
		mediaPlayer.setDataSource(newFile.getCanonicalPath());
		mediaPlayer.prepare();
		mediaPlayer.seekTo(position);
	    mediaPlayer.start();
	    System.out.println(mediaPlayer.getCurrentPosition());
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
	
}	
