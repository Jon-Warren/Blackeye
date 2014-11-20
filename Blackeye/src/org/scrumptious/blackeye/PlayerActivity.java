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
import android.widget.TextView;

public class PlayerActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_player);
		final String title = getIntent().getExtras().getString("castTitle");
		this.setTitle(title);
		final Button playButton = (Button)this.findViewById(R.id.button1);
		final Playback player = new Playback();
		
		//zach's podcasts description stuff
		TextView Author = (TextView)findViewById(R.id.text_author);
		Author.setText(CastParser.podcasts.get(title).getAuthor());
		
		TextView Title = (TextView)findViewById(R.id.text_title);
		Title.setText(CastParser.podcasts.get(title).getTitle());
		
		TextView Duration = (TextView)findViewById(R.id.text_duration);
		Duration.setText(CastParser.podcasts.get(title).getDuration());
		
		TextView PubDate = (TextView)findViewById(R.id.text_pubdate);
		PubDate.setText(CastParser.podcasts.get(title).getPubDate());
		
		TextView Description = (TextView)findViewById(R.id.text_description);
		Description.setText(CastParser.podcasts.get(title).getDescription());
		
		playButton.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				// TODO Auto-generated method stub
				if(arg1.getAction() != MotionEvent.ACTION_DOWN) return false;
				if(!player.isPlaying()) {
					try {
						if(playButton.getText().equals("Play")) {
							player.playAudio(title);
						} else {
							player.resume();
						}
						playButton.setText("Pause");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					playButton.setText("Resume");
					player.pause();
				}
				return true;
			}});
		
	}
	
}
