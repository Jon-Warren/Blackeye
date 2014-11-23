package org.scrumptious.blackeye;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import org.scrumptious.blackeye.utils.Globals;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.text.SimpleDateFormat;


public class MainActivity extends Activity {
    public static final ArrayList<CastParser> parsers = new ArrayList<CastParser>();
    public static final ArrayList<String> feedURLS = new ArrayList<String>(), names = new ArrayList<String>();
    int i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        i = 0;
        Globals.mainActivity = this;
        //setContentView(R.layout.activity_main);
        final LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        String[][] feeds = Globals.loadFeeds();
   	  if(feeds.length > 0) {
       	   String[] feedNames = feeds[0];
       	   String[] feedURLs = feeds[1];
       	   for(String feedName : feedNames) {
       		   if(!names.contains(feedName)) {
       			   names.add(feedName);
       		   }
       	   }
       	   for(String feedURL : feedURLs) {

       		   if(!feedURLS.contains(feedURL))
       			   feedURLS.add(feedURL);
       	   }
   	  }
        final Button btn = new Button(this);
        btn.setText("Add Feed");
        btn.setOnTouchListener(new View.OnTouchListener() {
			@SuppressWarnings("deprecation")
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				// TODO Auto-generated method stubr6
				if(arg1.getAction() != MotionEvent.ACTION_DOWN) return false;
				AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this); //Read Update
                alertDialog.setTitle("Add Feed");
                alertDialog.setMessage("Enter feed URL");
                final EditText box = new EditText(MainActivity.this);
                alertDialog.setView(box);

         	   
                alertDialog.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int which) {
                      // here you can add functions
                	   if(!feedURLS.contains(box.getText().toString())) {
	                	   feedURLS.add(box.getText().toString());
	                	   CastParser parser = new CastParser(box.getText().toString());
	                	   
	                	   Toast.makeText(MainActivity.this, "Downloading", Toast.LENGTH_SHORT).show();
	                	   parser.execute();
	                	   Toast.makeText(MainActivity.this, "Downloading Finished", Toast.LENGTH_SHORT).show();
	                	   try {
							parser.get();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ExecutionException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	                	   
	                	   parsers.add(parser);
	                	   Globals.saveFeed(parser.feedTitle, box.getText().toString());
                	   }
                	   linearLayout.removeAllViews();
                	   linearLayout.addView(btn);
                	  
                	   String[][] feeds = Globals.loadFeeds();
                	   String[] feedNames = feeds[0];
                	   String[] feedURLs = feeds[1];
                	   for(String feedName : feedNames) {
                		   if(!names.contains(feedName))
                			   names.add(feedName);
                	   }
                	   for(String feedURL : feedURLs) {
                		   if(!feedURLS.contains(feedURL))
                			   feedURLS.add(feedURL);
                	   }
                	   i = 0;
                	   for(final String s : names) {
                		   Button button = new Button(MainActivity.this);
                		   button.setText(s);
                		   button.setOnTouchListener(new OnTouchListener() {

							@Override
							public boolean onTouch(View arg0, MotionEvent arg1) {
								// TODO Auto-generated method stub
								if(arg1.getAction() != MotionEvent.ACTION_DOWN) return false;
								
								Intent intent = new Intent(MainActivity.this,FeedActivity.class);
								intent.putExtra("feedName", s);
								intent.putExtra("feedURL", box.getText().toString());
								startActivity(intent);
								return true;
							}});
                		   linearLayout.addView(button);
                	   }
                	   setContentView(linearLayout);
                   }
                });
                
                alertDialog.create().show();	
				return true;
			}

        });
        linearLayout.addView(btn);
        for(i = 0; i < names.size(); i++) {
 		   Button button = new Button(MainActivity.this);
 		   button.setText(names.get(i));
 		   final String name = names.get(i);
 		   final String url = feedURLS.get(i);
 		   button.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View arg0, MotionEvent arg1) {
					// TODO Auto-generated method stub
					if(arg1.getAction() != MotionEvent.ACTION_DOWN) return false;
					
					Intent intent = new Intent(MainActivity.this,FeedActivity.class);
					intent.putExtra("feedName", name);
					intent.putExtra("feedURL", url );
					startActivity(intent);
					return true;
				}});
 		   linearLayout.addView(button);
 	   }
        setContentView(linearLayout);
    }

    
    private void startActivity(String url) {
    	Intent i = new Intent(this,FeedActivity.class);
    	i.putExtra("feedUrl", url);
    	startActivity(i);

    }

    private int group1Id = 1;
    int homeId = Menu.FIRST;
    int profileId = Menu.FIRST +1;
    int searchId = Menu.FIRST +2;
    int dealsId = Menu.FIRST +3;
    int helpId = Menu.FIRST +4;
    int contactusId = Menu.FIRST +5;

       @Override
        public boolean onCreateOptionsMenu(Menu menu) {

        menu.add(group1Id, homeId, homeId, "Refresh Feed");

        return super.onCreateOptionsMenu(menu); 
        }

       @Override
        public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

    case 1:
        //write your code here
    	ArrayList<String> keys = new ArrayList<String>();
    	for(String s : CastParser.podcasts.keySet()) {
        	Cast c = CastParser.podcasts.get(s);
        	keys.add(s);
        		
        }
    	for(String s : keys) {
    		CastParser.podcasts.remove(s);
    	}
    	for(String url : feedURLS) {
    		CastParser parser = new CastParser(url);
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
    	}
    	
    	finish();
    	startActivity(getIntent());
        return true;

    default:
        break;

           }
        return super.onOptionsItemSelected(item);
    }
    
    @Override
    protected void onResume() {
    	i = 0;
    	super.onResume();
    }
}