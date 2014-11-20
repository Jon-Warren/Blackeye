package org.scrumptious.blackeye;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import org.scrumptious.blackeye.utils.Globals;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

@SuppressWarnings("deprecation")
public class FeedActivity extends TabActivity
{
		private String feedURL,feedName;
            /** Called when the activity is first created. */
            @Override
            public void onCreate(Bundle savedInstanceState)
            {
                    super.onCreate(savedInstanceState);
                    setContentView(R.layout.activity_feed);
                    
                    
                    feedName = getIntent().getStringExtra("feedName");
                    feedURL = getIntent().getStringExtra("feedURL");
                    this.setTitle(feedName);
                    ArrayList<String> keys = new ArrayList<String>();
                	/*for(String s : CastParser.podcasts.keySet()) {
                    	Cast c = CastParser.podcasts.get(s);
                    	if(c.getParentName().equals(feedName)) {
                    		keys.add(s);
                    	}
                    }
                	for(String s : keys) {
                		CastParser.podcasts.remove(s);
                	}*/
                	
                    // create the TabHost that will contain the Tabs
    				TabHost tabHost = (TabHost)findViewById(android.R.id.tabhost);


                    TabSpec tab1 = tabHost.newTabSpec("First Tab");
                    TabSpec tab2 = tabHost.newTabSpec("Second Tab");


                   // Set the Tab name and Activity
                   // that will be opened when particular Tab will be selected
                    tab1.setIndicator("Recent");
                    Intent top5Intent = new Intent(this,Top5Activity.class);
                    ArrayList<Cast> topFiveCasts = new ArrayList<Cast>();
                    
                    ArrayList<Date> dateList = new ArrayList<Date>();
                    for(String parserKey : CastParser.podcasts.keySet()) {
                    Cast c = CastParser.podcasts.get(parserKey);
                    Date d;
                    try {
                    String target = c.getPubDate();
                    DateFormat df = new SimpleDateFormat("EEE, dd MMM yyyy kk:mm:ss Z", Locale.ENGLISH);
                    d = df.parse(target);
                    dateList.add(d);
                    } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    }
                    }
                    Collections.sort(dateList);
                    Collections.reverse(dateList);
                    String[] podcastsSorted = new String[dateList.size()];
                    for(String parserKey : CastParser.podcasts.keySet()) {
	                    Date d;
	                    try {
	                    	Cast c = CastParser.podcasts.get(parserKey);
	                    	String pubDate = c.getPubDate();
	                    	DateFormat df = new SimpleDateFormat("EEE, dd MMM yyyy kk:mm:ss Z", Locale.ENGLISH);
	                    	d = df.parse(pubDate);
	                    	for (int index = 0; index < 5; index++) {
	                    		if (d.equals(dateList.get(index)) && podcastsSorted[index] == null) {
	                    			podcastsSorted[index] = parserKey;
	                    			break;
	                    		}
	                    	}
	                    } catch (Exception e) {
	                    // TODO Auto-generated catch block
	                    e.printStackTrace();
	                    }
                    }
                    for(String key : podcastsSorted) {
                    	if(topFiveCasts.size() > 5) break;
                    	topFiveCasts.add(CastParser.podcasts.get(key));
                    }
                    top5Intent.putExtra("fiveFeeds",topFiveCasts);
                    tab1.setContent(top5Intent);
                   
                    tab2.setIndicator("Saved");
                    
                    Intent meh = new Intent(this,Top5Activity.class);
                    final ArrayList<Cast> savedCasts = new ArrayList<Cast>();
                    if(Globals.SAVED_HASH.get(feedName) != null)
                    for(String castName : Globals.SAVED_HASH.get(feedName)) {
                    	for(String s : CastParser.podcasts.keySet()) {
                    		Cast c = CastParser.podcasts.get(s);
                    		if(c != null && c.getTitle() != null && c.getTitle().equals(castName) && !savedCasts.contains(c)) {
                    			savedCasts.add(c);
                    		}
                    	}
                    }
                    meh.putExtra("fiveFeeds",savedCasts);
                    tab2.setContent(meh);
                   
                    /** Add the tabs  to the TabHost to display. */
                    tabHost.addTab(tab1);
                    tabHost.addTab(tab2);
                    Runnable runnable = new Runnable() {
                		public void run() {
                			CastParser parser = new CastParser(feedURL);
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
            				
                		}
                	};
                	new Thread(runnable).start();
            }
            private int group1Id = 1;
            int homeId = Menu.FIRST;
            int profileId = Menu.FIRST +1;
            int searchId = Menu.FIRST +2;
            int dealsId = Menu.FIRST +3;
            int helpId = Menu.FIRST +4;
            int contactusId = Menu.FIRST +5;

               @Override
                public boolean onCreateOptionsMenu(Menu menu) {

                menu.add(group1Id, homeId, homeId, "Refresh Feed");
                menu.add(group1Id, profileId, profileId, "Clear Saved");

                return super.onCreateOptionsMenu(menu); 
                }

               @SuppressLint("NewApi") @Override
                public boolean onOptionsItemSelected(MenuItem item) {

                switch (item.getItemId()) {

            case 1:
                //write your code here
            	ArrayList<String> keys = new ArrayList<String>();
            	for(String s : CastParser.podcasts.keySet()) {
                	Cast c = CastParser.podcasts.get(s);
                	if(c.getParentName().equals(feedName)) {
                		keys.add(s);
                	}
                }
            	for(String s : keys) {
            		CastParser.podcasts.remove(s);
            	}
            	CastParser parser = new CastParser(feedURL);
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
            	finish();
            	startActivity(getIntent());
                return true;

            case 2:
            	Globals.clear(feedName);
            default:
                break;

                   }
                return super.onOptionsItemSelected(item);
            }
               public void update() {
            	   TabHost tabHost = (TabHost)findViewById(android.R.id.tabhost);


                   TabSpec tab1 = tabHost.newTabSpec("First Tab");
                   TabSpec tab2 = tabHost.newTabSpec("Second Tab");


                  // Set the Tab name and Activity
                  // that will be opened when particular Tab will be selected
                   tab1.setIndicator("Recent");
                   Intent top5Intent = new Intent(this,Top5Activity.class);
                   ArrayList<Cast> topFiveCasts = new ArrayList<Cast>();
                   
                   ArrayList<Date> dateList = new ArrayList<Date>();
                   for(String parserKey : CastParser.podcasts.keySet()) {
                   Cast c = CastParser.podcasts.get(parserKey);
                   Date d;
                   try {
                   String target = c.getPubDate();
                   DateFormat df = new SimpleDateFormat("EEE, dd MMM yyyy kk:mm:ss Z", Locale.ENGLISH);
                   d = df.parse(target);
                   dateList.add(d);
                   } catch (Exception e) {
                   // TODO Auto-generated catch block
                   e.printStackTrace();
                   }
                   }
                   Collections.sort(dateList);
                   Collections.reverse(dateList);
                   String[] podcastsSorted = new String[dateList.size()];
                   for(String parserKey : CastParser.podcasts.keySet()) {
	                    Date d;
	                    try {
	                    	Cast c = CastParser.podcasts.get(parserKey);
	                    	String pubDate = c.getPubDate();
	                    	DateFormat df = new SimpleDateFormat("EEE, dd MMM yyyy kk:mm:ss Z", Locale.ENGLISH);
	                    	d = df.parse(pubDate);
	                    	for (int index = 0; index < 5; index++) {
	                    		if (d.equals(dateList.get(index)) && podcastsSorted[index] == null) {
	                    			podcastsSorted[index] = parserKey;
	                    			break;
	                    		}
	                    	}
	                    } catch (Exception e) {
	                    // TODO Auto-generated catch block
	                    e.printStackTrace();
	                    }
                   }
                   for(String key : podcastsSorted) {
                   	if(topFiveCasts.size() > 5) break;
                   	topFiveCasts.add(CastParser.podcasts.get(key));
                   }
                   top5Intent.putExtra("fiveFeeds",topFiveCasts);
                   tab1.setContent(top5Intent);
                  
                   tab2.setIndicator("Saved");
                   
                   Intent meh = new Intent(this,Top5Activity.class);
                   final ArrayList<Cast> savedCasts = new ArrayList<Cast>();
                   if(Globals.SAVED_HASH.get(feedName) != null)
                   for(String castName : Globals.SAVED_HASH.get(feedName)) {
                   	for(String s : CastParser.podcasts.keySet()) {
                   		Cast c = CastParser.podcasts.get(s);
                   		if(c != null && c.getTitle() != null && c.getTitle().equals(castName) && !savedCasts.contains(c)) {
                   			savedCasts.add(c);
                   		}
                   	}
                   }
                   meh.putExtra("fiveFeeds",savedCasts);
                   tab2.setContent(meh);
                  
                   /** Add the tabs  to the TabHost to display. */
                   tabHost.addTab(tab1);
                   tabHost.addTab(tab2);


               }
}