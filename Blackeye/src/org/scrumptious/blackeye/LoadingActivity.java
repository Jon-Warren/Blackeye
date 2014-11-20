package org.scrumptious.blackeye;

import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class LoadingActivity extends Activity {
	String feedName,feedURL;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loading);
		feedName = getIntent().getStringExtra("feedName");
        feedURL = getIntent().getStringExtra("feedURL");
        this.setTitle("Loading");
        
        CastParser parser = new CastParser(feedURL);
      	parser.execute();
		try {
			parser.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Intent intent = new Intent(LoadingActivity.this,FeedActivity.class);
		intent.putExtra("feedName", feedName);
		intent.putExtra("feedURL", feedURL);
		startActivity(intent);
	}
}
