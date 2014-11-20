package org.scrumptious.blackeye;

import java.util.ArrayList;

import org.scrumptious.blackeye.utils.Globals;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Top5Activity extends Activity {
	private Cast cast;

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 Intent intent = getIntent();
		 ArrayList<Cast> feeds = 
		     (ArrayList<Cast>)intent.getSerializableExtra("fiveFeeds");
		 
         //String[] feeds = intent.getStringArrayExtra("fiveFeeds");
         LinearLayout layout = new LinearLayout(this);
         layout.setOrientation(LinearLayout.VERTICAL);
         if(feeds != null)
         for(final Cast c : feeds) {
        	 if(c == null) continue;
        	 LinearLayout castLayout = new LinearLayout(this);
        	 castLayout.setOrientation(LinearLayout.HORIZONTAL);
        	 TextView tv = new TextView(this);
        	 tv.setText(c.getTitle());
        	 castLayout.addView(tv);
        	 Button playButton = new Button(this);
        	 playButton.setText("Play");
        	 Button saveButton = new Button(this);
        	 saveButton.setText("Save");
        	 playButton.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View arg0, MotionEvent arg1) {
					// TODO Auto-generated method stub
					if(arg1.getAction() != MotionEvent.ACTION_DOWN) return false;
					Intent intent = new Intent(Top5Activity.this,PlayerActivity.class);
					intent.putExtra("castTitle", c.getParentName()+"/"+c.getTitle());
					startActivity(intent);
					return true;
				}});
        	 
        	 saveButton.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View arg0, MotionEvent arg1) {
					// TODO Auto-generated method stub
					//Toast.makeText(getApplicationContext(),"Need to add save code here",1000).show();
					//Globals.saveCast(c, c.getParentName());
					return false;
				}});
        	 castLayout.addView(playButton);
        	 castLayout.addView(saveButton);
        	 layout.addView(castLayout);
         }
         setContentView(layout);
	}
}
