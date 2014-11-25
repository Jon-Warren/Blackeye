package org.scrumptious.blackeye;

import java.io.IOException;

import org.scrumptious.blackeye.utils.Globals;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class PlayerActivity extends Activity {
	
	String title;
	Playback player = new Playback();
	Cast cast;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_player);
		final String title = getIntent().getExtras().getString("castTitle");
		try {
		this.setTitle(title.split("/")[1]);
		} catch(Exception e) {
			this.setTitle(title);
		}
		try {
			if(CastParser.podcasts.get(title) == null) {
				System.out.println("No podcast named "+title);
			}
			System.out.println(title);
			player.setSrc(title);
		} catch (IllegalArgumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalStateException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		final SeekBar mSeekBar = (SeekBar)findViewById(R.id.seekBar1);
		mSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onStopTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
				player.getPlayer().seekTo(arg0.getProgress()*1000);
			}});
		final Handler mHandler = new Handler();
		Runnable mRunnable = new Runnable() {

		    @Override
		    public void run() {
		        if(player.getPlayer() != null){
		            int mCurrentPosition = player.getPlayer().getCurrentPosition();
		            mSeekBar.setProgress(mCurrentPosition/1000);
		            TextView text = (TextView)findViewById(R.id.textView1);

		            
					text.setText(toTime(player.getPlayer().getCurrentPosition()) + "/" + toTime(player.getPlayer().getDuration()));

		        }
		        mHandler.postDelayed(this, 1000);
		    }
		};
		mRunnable.run();
		//final Cast cast = getIntent().getExtras().getParcelable("cast");
		this.setTitle(title);
		System.out.println(title.split("/")[1]);
		cast = CastParser.podcasts.get(title.split("/")[1]);
//		System.out.println("Cast: "+cast.getTitle());
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
					player.playToResume(cast.getProgress());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				} 
				return true;
				
			}
		});
		
		//zach's podcasts description stuff
		if(cast != null) {
		TextView Author = (TextView)findViewById(R.id.text_author);
		
		Author.setText("Author"+ cast.getAuthor());
		
		TextView Title = (TextView)findViewById(R.id.text_title);
		Title.setText("Title: "+cast.getTitle());
		
		TextView Duration = (TextView)findViewById(R.id.text_duration);
		Duration.setText("Duration: "+cast.getDuration());
		
		TextView PubDate = (TextView)findViewById(R.id.text_pubdate);
		PubDate.setText("Publicaton Date: "+cast.getPubDate());
		
		TextView Description = (TextView)findViewById(R.id.text_description);
		Description.setText("Description: "+Html.fromHtml(cast.getDescription()).toString());
		}
		
		playButton.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				// TODO Auto-generated method stub
				if(arg1.getAction() != MotionEvent.ACTION_DOWN) return false;
				if(!player.isPlaying()) {
					
					try {
						if(playButton.getText().equals("Play")) {
							player.resume();
							mSeekBar.setMax(player.getPlayer().getDuration()/1000);
							try {
							player.playToResume(cast.getProgress());
							} catch(Exception e) {
								player.resume();
							}
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
		Button seekForward = (Button)findViewById(R.id.seek_forward);
		seekForward.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				// TODO Auto-generated method stub
				if(arg1.getAction() != MotionEvent.ACTION_DOWN) return false;
				player.skipForward(20000);
				return true;
			}
			
		});
		
		Button seekBack = (Button)findViewById(R.id.seek_back);
		seekBack.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				// TODO Auto-generated method stub
				if(arg1.getAction() != MotionEvent.ACTION_DOWN) return false;
				player.skipBack(10000);
				return true;
			}
			
		});
		
	}
	
	private String toTime(int millis) {
        long second = (millis / 1000) % 60;
		long minute = (millis / (1000 * 60)) % 60;
		long hour = (millis / (1000 * 60 * 60)) % 24;

		String time = String.format("%02d:%02d:%02d:%d", hour, minute, second, millis);
		return time;
	}
	
	@Override
	public void onPause() {
		player.stop();
		cast.setPercentPlayed(player.getPercentPlayed());
		cast.setProgress(player.getPosition());
		System.out.println(cast.getPercentPlayed());
		CastParser.podcasts.put(cast.getTitle(), cast);
		Globals.saveTopFive(cast, cast.getParentName());
		
		super.onPause();
		
	}
}
