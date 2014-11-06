package org.scrumptious.blackeye.utils;

public class Globals {
	private static String[] feeds;
	private static String homeDirectory;
	
	public static String[] getFeeds() {
		return Globals.feeds;
	}
	
	public static void setFeeds(String[] f) {
		Globals.feeds = f;
	}
}
