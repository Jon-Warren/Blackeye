package org.scrumptious.blackeye;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.os.AsyncTask;
import android.util.Log;

public class CastParser extends AsyncTask {
	
	private String firstUrl;
	private InputStream inputStream;
	private XmlPullParser xmlParser;
	public static final HashMap<String,HashMap<String,String>> podcasts = new HashMap();
	public CastParser(String path) {
		this.firstUrl = path;
		try {
			URL url = new URL(path);
			inputStream = url.openStream();
		} catch (Exception e) {
			Log.e("ParserError","Failed to open stream...");
		}
		try {
			xmlParser = XmlPullParserFactory.newInstance().newPullParser();
		} catch (XmlPullParserException e) {
			Log.e("ParserError","Failed to instantiate PullParser");
		}
		if(xmlParser != null && inputStream != null) {
			try {
				xmlParser.setInput(inputStream,null);
				
			} catch (Exception e) {
				Log.e("ParserError","Failed to set parser input");
			}
		}
		
	}

	
	public void readXML() throws Exception{
		while(xmlParser.nextTag() == XmlPullParser.START_TAG) {
			if(xmlParser.getName().contains("<item>")) {
				Cast cast = new Cast();
				HashMap<String,String> map = new HashMap<String,String>();
				while(xmlParser.nextTag() != -1 && !xmlParser.getName().contains("/item")) {
					if(xmlParser.getName().contains("title")) {
						Log.d("TitleDebug","Adding title: "+xmlParser.getName());
						cast.setTitle(xmlParser.getName());
						map.put("title",cast.getTitle());
					}
				}
				podcasts.put(cast.getTitle(), map);
			}
            try {
                Log.d("ParserDebug", xmlParser.getName() + " | " + xmlParser.nextText());
            } catch(Exception e) {}
        }
	}

	@Override
	protected Object doInBackground(Object... arg0) {
		// TODO Auto-generated method stub
		try {
			readXML();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
