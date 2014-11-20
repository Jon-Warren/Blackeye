package org.scrumptious.blackeye.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.scrumptious.blackeye.Cast;
import org.scrumptious.blackeye.CastParser;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class Globals {
	private static String[] feeds,casts;
	public static Activity mainActivity;
	public static final HashMap<String,ArrayList<String>> SAVED_HASH = new HashMap<String,ArrayList<String>>();
	public static String[] getFeeds() {
		return Globals.feeds;
	}
	
	public static void setFeeds(String[] f) {
		Globals.feeds = f;
	}
	
	
	@SuppressLint("NewApi") public static String[][] loadFeeds() {
		if(mainActivity == null) return null;
		SharedPreferences prefs = mainActivity.getPreferences(Context.MODE_PRIVATE);
		Set<String> feeds = prefs.getStringSet("Feeds", new HashSet<String>());
		String[] feedNames = new String[feeds.size()];
		String[] feedURLS = new String[feeds.size()];
		int counter = 0;
		if(feeds.size() > 0)
		for(String feed : feeds) {
			Log.i("FeedFound",feed);
			String[] meh = feed.split("<!>");
			feedNames[counter] = meh[0];
			feedURLS[counter] = meh[1];
			counter++;
		}
		return new String[][] {feedNames,feedURLS};
	}
	
	@SuppressLint("NewApi") public static void saveFeed(String feedTitle, String feedURL) {
		SharedPreferences prefs = mainActivity.getPreferences(Context.MODE_PRIVATE);
		String addString = "";
		addString = feedTitle + "<!>" + feedURL;
		Set<String> hs = prefs.getStringSet("Feeds", new HashSet<String>());
		hs.add(addString);
		Editor edit = prefs.edit();
		edit.remove("Feeds");
		edit.commit();
		edit.putStringSet("Feeds", hs);
		edit.commit();
	}
	
	
	public boolean containsFeed(String feedTitle) {
		for(String feed : feeds) {
			if(feed.equals(feedTitle)) return true;
		}
		return false;
	}
	
	@SuppressLint("NewApi") public static void clear(String feedName) {
		SharedPreferences prefs = mainActivity.getPreferences(Context.MODE_PRIVATE);
    	Editor edit = prefs.edit();
		edit.putStringSet(feedName, new HashSet<String>());
		edit.commit();
	}
	
}
