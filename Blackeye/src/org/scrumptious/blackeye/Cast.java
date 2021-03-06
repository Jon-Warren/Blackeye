package org.scrumptious.blackeye;

import java.io.Serializable;

import android.os.Parcel;
import android.os.Parcelable;

public class Cast extends Podcast implements Parcelable {

	public boolean isSaved() {
		return this.saved;
	}
			
	public void setSaved(boolean saved) {
		this.saved = saved;
		}
	
	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return this.title;
	}

	@Override
	public String getURL() {
		// TODO Auto-generated method stub
		return this.url;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return this.description;
	}

	@Override
	public String getPubDate() {
		// TODO Auto-generated method stub
		return this.date;
	}

	@Override
	public String getAuthor() {
		// TODO Auto-generated method stub
		return this.author;
	}

	@Override
	public String getSummary() {
		// TODO Auto-generated method stub
		return this.summary;
	}

	@Override
	public String getSubtitle() {
		// TODO Auto-generated method stub
		return this.subtitle;
	}

	@Override
	public String getKeywords() {
		// TODO Auto-generated method stub
		return this.keywords;
	}

	@Override
	public String getDuration() {
		// TODO Auto-generated method stub
		return this.duration;
	}

	@Override
	public void setTitle(String string) {
		// TODO Auto-generated method stub
		this.title = string;
	}

	@Override
	public void setURL(String string) {
		// TODO Auto-generated method stub
		this.url = string;
	}

	@Override
	public void setDescription(String string) {
		// TODO Auto-generated method stub
		this.description = string;
	}

	@Override
	public void setPubDate(String string) {
		// TODO Auto-generated method stub
		this.date = string;
	}

	@Override
	public void setAuthor(String string) {
		// TODO Auto-generated method stub
		this.author = string;
	}

	@Override
	public void setSummary(String string) {
		// TODO Auto-generated method stub
		this.summary = string;
	}

	@Override
	public void setSubtitle(String string) {
		// TODO Auto-generated method stub
		this.subtitle = string;
	}

	@Override
	public void setKeywords(String string) {
		// TODO Auto-generated method stub
		this.keywords = string;
	}

	@Override
	public void setDuration(String string) {
		// TODO Auto-generated method stub
		this.duration = string;
	}
	
	public void setPercentPlayed(double percent) {
		this.percentPlayed = percent;
	}
	
	public void setProgress(int progress) {
		this.progress = progress;
	}
	
	public void setListenedTo(boolean bool) {
		isListenedTo = bool;
	}
	
	private boolean saved = false;
	
	public void start() {
		this.isStarted = true;
	}
	
	public void stop() {
		this.isStarted = false;
	}
	
	public boolean isStarted() {
		return this.percentPlayed > 0 || this.isStarted;
	}
	
	public boolean isListenedTo() {
		return this.isListenedTo; 
	}
	
	private boolean isStarted = false;
	
	private boolean isListenedTo = false;

	private String title,url,description,date,author,summary,subtitle,keywords,duration,parentName;
	
	private double percentPlayed;
	
	private int progress;

	@Override
	public void setParentName(String string) {
		// TODO Auto-generated method stub
		this.parentName = string;
	}

	@Override
	public String getParentName() {
		// TODO Auto-generated method stub
		return this.parentName;
	}
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel arg0, int arg1) {
		// TODO Auto-generated method stub
	}

	@Override
	public double getPercentPlayed() {
		// TODO Auto-generated method stub
		return percentPlayed;
	}
	
	public int getProgress() {
		return progress;
	}

}
