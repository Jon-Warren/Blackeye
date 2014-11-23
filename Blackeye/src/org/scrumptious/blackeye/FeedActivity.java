package org.scrumptious.blackeye;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.scrumptious.blackeye.utils.Globals;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

@SuppressWarnings("deprecation")
public class FeedActivity extends TabActivity
{
		private String feedURL,feedName;
		private Bundle bundle;
		
            /** Called when the activity is first created. */
            @Override
            public void onCreate(Bundle savedInstanceState)
            {
                    super.onCreate(savedInstanceState);
                    bundle = savedInstanceState;
                    setContentView(R.layout.activity_feed);
                    
                    
                    feedName = getIntent().getStringExtra("feedName");
                    feedURL = getIntent().getStringExtra("feedURL");
                    this.setTitle(feedName);
                    // create the TabHost that will contain the Tabs
                    TabHost tabHost = (TabHost)findViewById(android.R.id.tabhost);


                    TabSpec tab1 = tabHost.newTabSpec("First Tab");
                    TabSpec tab2 = tabHost.newTabSpec("Second Tab");


                   // Set the Tab name and Activity
                   // that will be opened when particular Tab will be selected
                    tab1.setIndicator("Recent");
                    Intent top5Intent = new Intent(this,Top5Activity.class);
                    ArrayList<Cast> topFiveCasts = new ArrayList<Cast>();
                    for(String s : CastParser.podcasts.keySet()) {
                    	if(topFiveCasts.size() >= 5) break;
                    	Cast c = CastParser.podcasts.get(s);
                    	if(c.getParentName().equals(feedName)) {
                    		topFiveCasts.add(c);
                    	}
                    }
                    top5Intent.putExtra("fiveFeeds",topFiveCasts);
                    tab1.setContent(top5Intent);
                   
                    tab2.setIndicator("Saved");
                    Intent savedTabIntent = new Intent(this,SavedTabActivity.class);
                    final ArrayList<Cast> savedCasts = new ArrayList<Cast>();
                    for(String s : CastParser.podcasts.keySet()) {
                    		Cast c = CastParser.podcasts.get(s);
                    		if(c.isSaved() && c.getParentName().equals(feedName)) {
                    			savedCasts.add(c);
                    		}
                    }
                    
                    savedTabIntent.putExtra("savedCasts",savedCasts);
                    tab2.setContent(savedTabIntent);
                   
                    /** Add the tabs  to the TabHost to display. */
                    tabHost.addTab(tab1);
                    tabHost.addTab(tab2);

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

                return super.onCreateOptionsMenu(menu); 
                }

               @Override
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

            default:
                break;

                   }
                return super.onOptionsItemSelected(item);
            }
               
            //@Override   
            //public void onResume() {
            //	
            //	onCreate(bundle);
            //   super.onResume();
           	//}
}