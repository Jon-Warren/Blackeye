package org.scrumptious.blackeye;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends Activity {
    public static final ArrayList<MyParser> parsers = new ArrayList<MyParser>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyParser p = new MyParser("http://www.npr.org/rss/podcast.php?id=510298",true);
     
        
        Log.d("Main","Adding parser"+p.getTitle());
        parsers.add(p);
        for(MyParser parser : parsers) {
            if(parser.getTitle() != null) {
                LinearLayout view = (LinearLayout)findViewById(R.id.mainLinear);
                RelativeLayout child = new RelativeLayout(this);
                TextView tv = new TextView(this);
                Button btn = new Button(this);
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)tv.getLayoutParams();
                params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                tv.setLayoutParams(params);
                tv.setText(parser.getTitle());

                params = (RelativeLayout.LayoutParams)btn.getLayoutParams();
                params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                btn.setLayoutParams(params);
                btn.setText("Watch");
                child.addView(tv);
                child.addView(btn);
                view.addView(child);
            }
        }
        setContentView(R.layout.activity_main);
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


/**
 * Created by Tyler on 9/23/2014.
 */
class MyParser implements Runnable {
    private static boolean DEBUG_MODE = true;
    public static boolean isDone = false;
    private String URL;
    private boolean debug;
    
    public void run() {
    	try {
            XML_PARSER = XmlPullParserFactory.newInstance().newPullParser();
        } catch(Exception e) {
            Log.e("ParserError","Parser could not be loaded");
        }
        try {
            this.DEBUG_MODE = debug;
            if(DEBUG_MODE) Log.d("Parser","Retrieving file..... ");
            if(!URL.contains("http"))
                this.url = new File(URL).toURI().toURL();
            else
                this.url = new URL(URL);
            InputStream inputStream = url.openStream();
            Log.d("Parser","Size: "+inputStream.available());
            XML_PARSER.setInput(new BufferedReader(new InputStreamReader(inputStream)));
            if(DEBUG_MODE) Log.e("Parser"," Done!");
            fillMap();
        } catch (Exception e) {
        	e.printStackTrace();
        	return;
        }
        if(DEBUG_MODE) {
            for(String item : nodes.keySet()) Log.e("Parser",item + " | " + nodes.get(item));
        }
        try {
			fillMap();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        isDone = true;
        Thread.currentThread().interrupt();
    }
    public MyParser(String URL, boolean debug) {
    	this.URL = URL;
    	this.debug = debug;
    }

    /**
     *  Fills a HashMap with all the nodes in the XML passed into the program
     * @throws IOException
     * @throws XmlPullParserException
     */
    private void fillMap() throws IOException, XmlPullParserException {
    	
        while(XML_PARSER.nextTag() == XmlPullParser.START_TAG) {
            try {
                node = XML_PARSER.getName();
                content = XML_PARSER.nextText();
                nodes.put(node,content);
            } catch(Exception e) {}
        }
    }

    public String getLocation() {
        return nodes.get("link");
    }

    public String getTitle() {
        return nodes.get("title");
    }

    public String getDescription() {
        return nodes.get("description");
    }

    public String getDate() {
        return nodes.get("pubDate");
    }

    public String getLanguage() { return nodes.get("language");}

    public String getCopyright() { return nodes.get("copyright");}

    public String toString() {
        return "Title: " + getTitle() +
                "\nDescription: "+getDescription() +
                "\nDate: "+getDate() +
                "\nLocation: " + getLocation() +
                "\nCopyright: " + getCopyright() +
                "\nLanguage: " + getLanguage() + "\n\nDone.";
    }

    public static void main(String[] args) {
        
        if(args.length == 0) {
            Log.e("Parser","Wow, give me an XML please.");
            return;
        }
        if(!args[0].contains(".xml") && !args[0].contains("http")) {
            Log.e("Parser","Not an XML file");
            return;
        }
        if(args.length == 2)
            mp = new MyParser(args[0],args[1].equalsIgnoreCase("-Debug"));
        else
            mp = new MyParser(args[0],false);
        Log.e("Parser","WowParser - Tyler Garcia \n");
        Log.e("Parser",mp.toString());
    }
    public static MyParser mp;
    public static final HashMap<String,String> nodes = new HashMap<String, String>();
    private static XmlPullParser XML_PARSER;
    private URL url = null;
    private String node,content;
}