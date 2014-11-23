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
	
	public static void loadSavedCasts() {
        //add code to load saved stuffs :3
        if(mainActivity == null) return;
        SharedPreferences prefs = mainActivity.getPreferences(Context.MODE_PRIVATE);
        String input = prefs.getString("savedCasts", "none");
        casts = input.split(",");
        
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
            Log.i("Cast:",castObj.getTitle());
            
            CastParser.podcasts.put(castObj.getTitle(), castObj);
        }
    }
	
	@SuppressLint("NewApi") public static void saveCast(Cast cast, String feedTitle) {
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
        Set<String> hs = prefs.getStringSet("savedCasts", new HashSet<String>());
        hs.add(addString);
        Editor edit = prefs.edit();
        edit.remove("savedCasts");
        edit.commit();
        edit.putStringSet("savedCasts", hs);
        edit.commit();
        //Log.i("Cst:",""+prefs.getStringSet(feedTitle,new HashSet<String>()));
    }
	
	
	
}
