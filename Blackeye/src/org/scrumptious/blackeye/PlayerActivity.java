package org.scrumptious.blackeye;

import java.io.IOException;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;

public class PlayerActivity extends Activity {
	
	String title;
	Playback player = new Playback();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_player);
		title = getIntent().getExtras().getString("castTitle");
		//final Cast cast = getIntent().getExtras().getParcelable("cast");
		this.setTitle(title);
		final Cast cast = CastParser.podcasts.get(title);
		final Button playButton = (Button)this.findViewById(R.id.button1);
		final Button beginningButton = (Button)this.findViewById(R.id.Button2);
		final Button listenedToButton = (Button)this.findViewById(R.id.Button3);
		//final Playback player = new Playback();
		listenedToButton.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View arg0, MotionEvent arg1) {
				cast.setListenedTo(true);
				System.out.println(cast.isListenedTo());
				return true;
			}
		});
		
		beginningButton.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View arg0, MotionEvent arg1) {
				if(arg1.getAction() != MotionEvent.ACTION_DOWN) return false;
				player.pause();
				cast.setProgress(0);
				try {
					player.playAudio(title, cast.getProgress());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				} 
				return true;
				
			}
		});
		
		playButton.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				// TODO Auto-generated method stub
				if(arg1.getAction() != MotionEvent.ACTION_DOWN) return false;
				if(!player.isPlaying()) {
					try {
						if(playButton.getText().equals("Play")) {
							player.playAudio(title, cast.getProgress());
							//player.playAudio(title);
							cast.setPercentPlayed(player.getPercentPlayed());
							cast.setProgress(player.getPosition());
							CastParser.podcasts.put(cast.getTitle(), cast);
							
						} else {
							player.resume();
							cast.setPercentPlayed(player.getPercentPlayed());
							cast.setProgress(player.getPosition());
							CastParser.podcasts.put(cast.getTitle(), cast);
						}
						playButton.setText("Pause");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					playButton.setText("Resume");
					player.pause();
					cast.setPercentPlayed(player.getPercentPlayed());
					cast.setProgress(player.getPosition());
					System.out.println(cast.getPercentPlayed());
					CastParser.podcasts.put(cast.getTitle(), cast);
				}
				return true;
			}});
		
	}
	
	@Override
	public void onPause() {
		player.stop();
		Cast cast = CastParser.podcasts.get(title);
		cast.setPercentPlayed(player.getPercentPlayed());
		cast.setProgress(player.getPosition());
		System.out.println(cast.getPercentPlayed());
		CastParser.podcasts.put(cast.getTitle(), cast);
		
		super.onPause();
		
	}
}
