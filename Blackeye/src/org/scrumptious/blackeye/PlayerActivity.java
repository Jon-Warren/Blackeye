package org.scrumptious.blackeye;

import java.io.IOException;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
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
		final Button playButton = (Button)this.findViewById(R.id.button1);
		final Playback player = new Playback();
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
		            if(player.isPlaying())
		            	mSeekBar.setProgress(mCurrentPosition/1000);
		            TextView text = (TextView)findViewById(R.id.textView1);

		            
					text.setText(toTime(player.getPlayer().getCurrentPosition()) + "/" + toTime(player.getPlayer().getDuration()));

		        }
		        mHandler.postDelayed(this, 1000);
		    }
		};
		mRunnable.run();
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
	
}
