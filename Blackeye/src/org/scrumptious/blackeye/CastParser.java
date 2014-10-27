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
	public String URL;
	public static final HashMap<String,Cast> podcasts = new HashMap();
	public CastParser(String path) {
		this.firstUrl = path;
		this.URL = path;
		
	}

	
	public void readXML() throws Exception{
		try {
		while(xmlParser.nextTag() != -1) {
			if(xmlParser.getName().contains("owner")) {
				xmlParser.next();
				xmlParser.next();
			}
			if(xmlParser.getName().equals("image")) {
				//Log.d("ImageTag","Found");
				try {
					try {
					xmlParser.require(XmlPullParser.START_TAG, null, "image");
					} catch(Exception e) {continue;}
					xmlParser.nextTag();
					xmlParser.require(XmlPullParser.START_TAG,null,"url");
					//Log.d("ImageURL",xmlParser.nextText());
					try {
					xmlParser.require(XmlPullParser.END_TAG,null,"url");
					} catch(Exception e) {
						xmlParser.nextText();
						//xmlParser.nextTag();
						xmlParser.require(XmlPullParser.END_TAG,null,"url");
					}
					xmlParser.nextTag();
					xmlParser.require(XmlPullParser.START_TAG,null,"title");
					//Log.d("ImageTitle",xmlParser.nextText());
					xmlParser.nextText();
					xmlParser.require(XmlPullParser.END_TAG,null,"title");
					xmlParser.nextTag();
					xmlParser.require(XmlPullParser.START_TAG,null,"link");
					//Log.d("ImageLink",xmlParser.nextText());
					xmlParser.nextText();
					xmlParser.require(XmlPullParser.END_TAG,null,"link");
					xmlParser.nextTag();
					xmlParser.require(XmlPullParser.END_TAG, null, "image");
					//continue;
				} catch(Exception e) {
					e.printStackTrace();
				}
				xmlParser.nextTag();
			}
			if(xmlParser.getName().equals("item")) {
				//Log.d("Cast","Cast found! Grabbing data...");
				Cast cast = new Cast();
				HashMap<String,String> map = new HashMap<String,String>();

				int x = -1;
				while(((x = xmlParser.nextTag()) == XmlPullParser.START_TAG) || x == XmlPullParser.END_TAG && !xmlParser.getName().equals("item")) {
					if(xmlParser.getName().contains("organization")) {
						//Log.d("Hrm","...");
						xmlParser.nextTag();
						xmlParser.nextText();
						xmlParser.nextTag();
						xmlParser.nextText();
						xmlParser.nextTag();
						//xmlParser.nextTag();
						//Log.d("Hi",xmlParser.getName());
						//xmlParser.nextTag();
					}
					try {
						switch(xmlParser.getName()) {
							case "title":
								cast.setTitle(xmlParser.nextText());
								//xmlParser.nextTag();
								break;
							case "description":
								cast.setDescription(xmlParser.nextText());
								//xmlParser.nextTag();
								break;
							case "pubDate":
								cast.setPubDate(xmlParser.nextText());
								//xmlParser.nextTag();
								break;
							case "link":
								cast.setURL(xmlParser.nextText());
								//xmlParser.nextTag();
								break;
							case "guid":
								try {
									cast.setURL(xmlParser.nextText());
								} catch(Exception e) {
									
								}
								//xmlParser.nextTag();
								break;
							case "itunes:summary":
								cast.setSummary(xmlParser.nextText());
								//xmlParser.nextTag();
								break;
							case "itunes:keywords":
								cast.setKeywords(xmlParser.nextText());
								//xmlParser.nextTag();
								break;
							case "itunes:duration":
								cast.setDuration(xmlParser.nextText());
								//xmlParser.nextTag();
								break;
							case "itunes:explicit":
								xmlParser.nextText();
								//xmlParser.nextTag();
								break;
							case "itunes:author":
								cast.setDuration(xmlParser.nextText());
								break;
						}
					} catch(Exception e) {e.printStackTrace();}
				}
				podcasts.put(cast.getTitle(), cast);
			}
            try {
                try {xmlParser.nextText();} catch(Exception e) {}
            } catch(Exception e) {
            	e.printStackTrace();
            }
        }
		} catch(Exception e) {}
		for(String i : podcasts.keySet()) {
			//Log.d("Podcast",i);
		}
	}

	@Override
	protected Object doInBackground(Object... arg0) {
		// TODO Auto-generated method stub
		try {
			URL url = new URL(this.firstUrl);
			inputStream = url.openStream();
		} catch (Exception e) {
			//Log.e("ParserError","Failed to open stream...\n");
			e.printStackTrace();
		}
		try {
			xmlParser = XmlPullParserFactory.newInstance().newPullParser();
		} catch (XmlPullParserException e) {
			//Log.e("ParserError","Failed to instantiate PullParser");
		}
		if(xmlParser != null && inputStream != null) {
			try {
				xmlParser.setInput(inputStream,null);
				
			} catch (Exception e) {
				//Log.e("ParserError","Failed to set parser input");
			}
		}
		try {
			readXML();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
