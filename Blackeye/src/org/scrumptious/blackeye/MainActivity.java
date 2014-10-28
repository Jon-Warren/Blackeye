package org.scrumptious.blackeye;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;


public class MainActivity extends Activity {
    public static final ArrayList<CastParser> parsers = new ArrayList<CastParser>();
    private static final ArrayList<String> feedURLS = new ArrayList<String>();
    LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        CastParser x = new CastParser("http://www.sciencefriday.com/audio/scifriaudio.xml");
        //Log.d("Hihi","Next");
        //CastParser y = new CastParser("http://www.npr.org/rss/podcast.php?id=510298");
        
        final Button btn = new Button(this);
        btn.setText("Add Feed");
        btn.setOnTouchListener(new View.OnTouchListener() {
			@SuppressWarnings("deprecation")
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				// TODO Auto-generated method stubr6
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
                	   Log.d("hi","hi");
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
                	   
                	   parsers.add(parser);
                	   linearLayout = new LinearLayout(MainActivity.this);
                	   linearLayout.addView(btn);
                	   for(String parserKey : CastParser.podcasts.keySet()) {
                       	
                       	Cast c = CastParser.podcasts.get(parserKey);
                       	Log.d("Cast",c.getTitle());
                       	Button button = new Button(MainActivity.this);
                       	TextView text = new TextView(MainActivity.this);
                       	LinearLayout layout = new LinearLayout(MainActivity.this);
                       	layout.setOrientation(LinearLayout.HORIZONTAL);
                       	text.setText(c.getTitle());
                       	button.setText("Watch");
                       	layout.addView(text);
                       	layout.addView(button);
                       	linearLayout.addView(layout);
                       }
                	   setContentView(linearLayout);
                   }
                });
                
                alertDialog.create().show();	
				return false;
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }
}