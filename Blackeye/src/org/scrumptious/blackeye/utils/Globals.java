package org.scrumptious.blackeye.utils;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class Globals {
	private static String[] feeds;
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
		feeds = input.split("<=>");
		
		for(String feed : feeds) {
			
		}
	}
}
