package com;
public class PersonalCharacteristics{

	String tweet;
	int source;
	double retweet;
	int relative_tag;
	int same_time;
	int same_bloger;
	double influence;

public void setTweet(String tweet){
	this.tweet = tweet;
}
public String getTweet(){
	return tweet;
}

public void setRetweet(double retweet){
	this.retweet = retweet;
}
public double getRetweet(){
	return retweet;
}

public void setRelativeTag(int relative_tag) {
	this.relative_tag = relative_tag;
}
public int getRelativeTag() {
	return relative_tag;
}

public void setSameTime(int same_time) {
	this.same_time = same_time;
}
public int getSameTime() {
	return same_time;
}
public void setSource(int source) {
	this.source = source;
}
public int getSource() {
	return source;
}

public void setSameBlogger(int same_bloger) {
	this.same_bloger = same_bloger;
}
public int getSameBlogger() {
	return same_bloger;
}

public void setInfluence(double influence) {
	this.influence = influence;
}
public double getInfluence() {
	return influence;
}
}