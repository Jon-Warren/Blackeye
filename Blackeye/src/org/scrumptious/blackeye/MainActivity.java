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
    public static final ArrayList<String> feedURLS = new ArrayList<String>();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Globals.mainActivity = this;
        //setContentView(R.layout.activity_main);
        final LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        
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
                	   linearLayout.removeAllViews();
                	   linearLayout.addView(btn);
                	   
                	   ArrayList<String> names = new ArrayList();
                	   for(String s : CastParser.podcasts.keySet()) {
                		   Cast c = CastParser.podcasts.get(s);
                		   if(c != null && !names.contains(c.getParentName())) {
                			   names.add(c.getParentName());
                		   }
                	   }
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
        
        setContentView(linearLayout);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }
    
    private void startActivity(String url) {
    	Intent i = new Intent(this,FeedActivity.class);
    	i.putExtra("feedUrl", url);
    	startActivity(i);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }
}