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

	private static String[] feeds, casts;

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
        edit.remove("savedCasts");
        edit.commit();
        for(Cast c : CastParser.podcasts.values()) {
        	c.setSaved(false);
        }
	}
	

	@SuppressLint("NewApi") public static void loadSavedCasts() {
        //add code to load saved stuffs :3
        if(mainActivity == null) return;
        SharedPreferences prefs = mainActivity.getPreferences(Context.MODE_PRIVATE);
        Set<String> casts = prefs.getStringSet("savedCasts", new HashSet<String>());
        for(String cast : casts) {
            String[] items = cast.split("<!>");
            Cast castObj = new Cast();
            castObj.setAuthor(items[0]);
            castObj.setDescription(items[1]);
            castObj.setDuration(items[2]);
            castObj.setKeywords(items[3]);
            castObj.setParentName(items[4]);
            castObj.setPubDate(items[5]);
            castObj.setSubtitle(items[6]);
            castObj.setSummary(items[7]);
            castObj.setTitle(items[8]);
            castObj.setURL(items[9]);
            try {
            castObj.setPercentPlayed(Double.parseDouble(items[10]));
            }catch(Exception e) {}
            castObj.setSaved(true);
            
            CastParser.podcasts.put(castObj.getTitle(), castObj);
        }
    }
	
	@SuppressLint("NewApi") 
	public static void saveCast(Cast cast, String feedTitle) {
        SharedPreferences prefs = mainActivity.getPreferences(Context.MODE_PRIVATE);
        String addString = "";
        addString += cast.getAuthor();
        addString += "<!>";
        addString += cast.getDescription();
        addString += "<!>";
        addString += cast.getDuration();
        addString += "<!>";
        addString += cast.getKeywords();
        addString += "<!>";
        addString += cast.getParentName();
        addString += "<!>";
        addString += cast.getPubDate();
        addString += "<!>";
        addString += cast.getSubtitle();
        addString += "<!>";
        addString += cast.getSummary();
        addString += "<!>";
        addString += cast.getTitle();
        addString += "<!>";
        addString += cast.getURL();
        addString += "<!>";
        addString += cast.getPercentPlayed();
        Set<String> hs = prefs.getStringSet("savedCasts", new HashSet<String>());
        hs.add(addString);
        Editor edit = prefs.edit();
        edit.remove("savedCasts");
        edit.commit();
        edit.putStringSet("savedCasts", hs);
        edit.commit();
    }
	
	@SuppressLint("NewApi") 
	public static void saveTopFive(Cast cast, String feedTitle) {
        SharedPreferences prefs = mainActivity.getPreferences(Context.MODE_PRIVATE);
        String addString = "";
        addString += cast.getAuthor();
        addString += "<!>";
        addString += cast.getDescription();
        addString += "<!>";
        addString += cast.getDuration();
        addString += "<!>";
        addString += cast.getKeywords();
        addString += "<!>";
        addString += cast.getParentName();
        addString += "<!>";
        addString += cast.getPubDate();
        addString += "<!>";
        addString += cast.getSubtitle();
        addString += "<!>";
        addString += cast.getSummary();
        addString += "<!>";
        addString += cast.getTitle();
        addString += "<!>";
        addString += cast.getURL();
        addString += "<!>";
        addString += cast.getPercentPlayed();
        Set<String> hs = prefs.getStringSet(feedTitle+"Casts", new HashSet<String>());
        for(String c : hs) {
        	if(c.contains(cast.getTitle())) {
        		System.out.println(cast.getTitle());
        		hs.remove(c);
        		break;
        	}
        }
        hs.add(addString);
        Editor edit = prefs.edit();
        edit.remove(feedTitle+"Casts");
        edit.commit();
        edit.putStringSet(feedTitle+"Casts", hs);
        edit.commit();
    }
	
	@SuppressLint("NewApi") public static void loadTopFive(String feedTitle) {
        //add code to load saved stuffs :3
        if(mainActivity == null) return;
        SharedPreferences prefs = mainActivity.getPreferences(Context.MODE_PRIVATE);
        Set<String> casts = prefs.getStringSet(feedTitle+"Casts", new HashSet<String>());
        System.out.println(casts.toString());
        for(String cast : casts) {
            String[] items = cast.split("<!>");
            Cast castObj = new Cast();
            castObj.setAuthor(items[0]);
            castObj.setDescription(items[1]);
            castObj.setDuration(items[2]);
            castObj.setKeywords(items[3]);
            castObj.setParentName(items[4]);
            castObj.setPubDate(items[5]);
            castObj.setSubtitle(items[6]);
            castObj.setSummary(items[7]);
            castObj.setTitle(items[8]);
            castObj.setURL(items[9]);
            try {
                castObj.setPercentPlayed(Double.parseDouble(items[10]));
                }catch(Exception e) {}
            castObj.setSaved(false);
            
            CastParser.podcasts.put(castObj.getTitle(), castObj);
        }
    }
	
	@SuppressLint("NewApi")
	public static void removeCast(Cast c, String feedTitle) {
		c.setSaved(false);
		SharedPreferences prefs = mainActivity.getPreferences(Context.MODE_PRIVATE);
        Editor edit = prefs.edit();
        Set<String> casts = prefs.getStringSet(feedTitle+"Casts", new HashSet<String>());
        for(String cast : casts) {
        	if(cast.contains(c.getTitle())) {
        		casts.remove(cast);
        		break;
        	}
        }
        edit.remove(feedTitle+"Casts");
        edit.commit();
        edit.putStringSet(feedTitle+"Casts", casts);
        edit.commit();
	}
}
