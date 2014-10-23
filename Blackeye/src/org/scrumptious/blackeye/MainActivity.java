package org.scrumptious.blackeye;

import android.app.Activity;
import android.os.AsyncTask;
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
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends Activity {
    public static final ArrayList<MyParser> parsers = new ArrayList<MyParser>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new CastParser("http://www.sciencefriday.com/audio/scifriaudio.xml").execute();
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
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}


/**
 * Created by Tyler on 9/23/2014.
 */
class MyParser {
    private static boolean DEBUG_MODE = true;

    public MyParser(String URL, boolean debug) {
    	XmlDownloader dl = new XmlDownloader(URL);
    	dl.execute();
        try {
            this.DEBUG_MODE = debug;
            if(DEBUG_MODE) Log.d("Parser","Retrieving file..... ");
            if(!URL.contains("http"))
                this.url = new File(URL).toURI().toURL();
            else
                this.url = new URL(URL);
            InputStream inputStream = dl.getStream();
            XML_PARSER.setInput(new BufferedReader(new InputStreamReader(inputStream)));
            if(DEBUG_MODE) Log.d("Parser"," Done!");
            fillMap();
        } catch (Exception e) {
        }
        if(DEBUG_MODE) {
            for(String item : nodes.keySet()) Log.d("Parser",item + " | " + nodes.get(item));
        }
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
        try {
            XML_PARSER = XmlPullParserFactory.newInstance().newPullParser();
        } catch(Exception e) {
            Log.e("ParserError","Parser could not be loaded");
        }
        if(args.length == 0) {
            Log.d("Parser","Wow, give me an XML please.");
            return;
        }
        if(!args[0].contains(".xml") && !args[0].contains("http")) {
            Log.d("Parser","Not an XML file");
            return;
        }
        if(args.length == 2)
            mp = new MyParser(args[0],args[1].equalsIgnoreCase("-Debug"));
        else
            mp = new MyParser(args[0],false);
        Log.d("Parser","WowParser - Tyler Garcia \n");
        Log.d("Parser",mp.toString());
    }
    public static MyParser mp;
    public static final HashMap<String,String> nodes = new HashMap<String, String>();
    private static XmlPullParser XML_PARSER;
    private URL url = null;
    private String node,content;
    
    private class XmlDownloader extends AsyncTask<String, Void, String> {
    	private InputStream iStream;
    	private String URL;
    	
    	public XmlDownloader(String u) {
    		this.URL = u;
    	}
    	
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {
				iStream = downloadUrl(URL);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				iStream = null;
				e.printStackTrace();
			}
			return null;
		}
		
		public InputStream getStream() {
			return this.iStream;
		}
		
		private InputStream downloadUrl(String urlString) throws IOException {
		    URL url = new URL(urlString);
		    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		    conn.setReadTimeout(10000 /* milliseconds */);
		    conn.setConnectTimeout(15000 /* milliseconds */);
		    conn.setRequestMethod("GET");
		    conn.setDoInput(true);
		    // Starts the query
		    conn.connect();
		    return conn.getInputStream();
		}
    	
    }
}