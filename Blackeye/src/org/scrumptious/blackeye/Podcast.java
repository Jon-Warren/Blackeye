package org.scrumptious.blackeye;

public abstract class Podcast {
	public abstract String getTitle();
	public abstract String getURL();
	public abstract String getDescription();
	public abstract String getPubDate();
	public abstract String getAuthor();
	public abstract String getSummary();
	public abstract String getSubtitle();
	public abstract String getKeywords();
	public abstract String getDuration();
	public abstract String getParentName();
	public abstract double getPercentPlayed();
	public abstract void setTitle(String string);
	public abstract void setURL(String string);
	public abstract void setDescription(String string);
	public abstract void setPubDate(String string);
	public abstract void setAuthor(String string);
	public abstract void setSummary(String string);
	public abstract void setSubtitle(String string);
	public abstract void setKeywords(String string);
	public abstract void setDuration(String string);
	public abstract void setParentName(String string);
	public abstract void setPercentPlayed(double percent);
}
