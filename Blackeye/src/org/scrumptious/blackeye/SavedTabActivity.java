package org.scrumptious.blackeye;

import java.util.ArrayList;

import org.scrumptious.blackeye.utils.Globals;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class SavedTabActivity extends Activity {
	private Cast cast;

	@SuppressLint({ "NewApi", "ResourceAsColor" }) @SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 Intent intent = getIntent();
		 ArrayList<Cast> feeds = 
		     (ArrayList<Cast>)intent.getSerializableExtra("savedCasts");
		 
		 ScrollView sv = new ScrollView(this);
		 sv.setBackgroundColor(android.R.color.transparent);
		 sv.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
		                                              LayoutParams.FILL_PARENT));
         
         LinearLayout layout = new LinearLayout(this);
         layout.setOrientation(LinearLayout.VERTICAL);
         if(feeds != null)
         for(final Cast c : feeds) {
        	 RelativeLayout castLayout = new RelativeLayout(this);
        	 
        	 TextView tv = new TextView(this);
        	 
        	 RelativeLayout.LayoutParams params = 
     			    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
     			        RelativeLayout.LayoutParams.WRAP_CONTENT);
 			 RelativeLayout.LayoutParams params2 = 
 					    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
 					        RelativeLayout.LayoutParams.WRAP_CONTENT);

 			 params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
 			 Display display = getWindowManager().getDefaultDisplay();
 			 Point size = new Point();
 			 display.getSize(size);
 			 int width = size.x;
 			 params.width = width/2;
 			 tv.setLayoutParams(params);
     			 
        	 tv.setText(c.getTitle());
        	 castLayout.addView(tv);
        	 Button playButton = new Button(this);
        	 playButton.setText("Play");
        	 Button saveButton = new Button(this);
        	 params2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,RelativeLayout.TRUE);
        	 
        	 LinearLayout buttonLayout = new LinearLayout(this);
        	 buttonLayout.setOrientation(LinearLayout.HORIZONTAL);
        	 buttonLayout.setLayoutParams(params2);
        	 saveButton.setText("Remove");
        	 saveButton.setWidth(width/4);
        	 playButton.setWidth(width/4);
        	 playButton.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View arg0, MotionEvent arg1) {
					// TODO Auto-generated method stub
					if(arg1.getAction() != MotionEvent.ACTION_DOWN) return false;
					Intent intent = new Intent(SavedTabActivity.this,PlayerActivity.class);
					intent.putExtra("castTitle", c.getParentName()+"/"+c.getTitle());
					startActivity(intent);
					return true;
				}});
        	 
        	 saveButton.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View arg0, MotionEvent arg1) {
					Globals.removeCast(c,c.getParentName());
					return false;
				}});
        	 buttonLayout.addView(playButton);
        	 buttonLayout.addView(saveButton);
        	 castLayout.addView(buttonLayout);
        	 layout.addView(castLayout);
         }
         sv.addView(layout);
         setContentView(sv);
	}
}
