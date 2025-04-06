package com;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Random;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.json.simple.JSONArray; 
import org.json.simple.JSONObject; 
import org.json.simple.parser.*; 
import java.io.File;
public class OptimizeTrainNeuralNetwork{

	static ArrayList<PersonalCharacteristics> matrix = new ArrayList<PersonalCharacteristics>();
	static ArrayList<Tweet> tweets = new ArrayList<Tweet>();
	static ArrayList<String> keywords = new ArrayList<String>();
	
public static void showMatrix() {
	ViewMatrix vm = new ViewMatrix();
	for(int i=0;i<matrix.size();i++){
		PersonalCharacteristics pc = matrix.get(i);
		Object row[] = {pc.getTweet(),pc.getSource(),pc.getRetweet(),pc.getRelativeTag(),pc.getSameTime(),pc.getSameBlogger(),pc.getInfluence()};
		vm.dtm.addRow(row);
	}
	vm.setVisible(true);
	vm.setSize(800,600);
}
public static void clear(){
	tweets.clear();
	keywords.clear();
	matrix.clear();
}
public static void read(File file,DefaultTableModel dtm,JTable table){
	try{
		clear();
		keywords.add("dallas");
		keywords.add("killing");
		keywords.add("enforcement");
		keywords.add("shoot");
		keywords.add("terrorists");
		File list[] = file.listFiles();
		for(int i=0;i<list.length;i++){
			Object obj = new JSONParser().parse(new FileReader(list[i])); 
			JSONObject jo = (JSONObject) obj; 
			String twt = (String) jo.get("text"); 
			long retweet = (long) jo.get("retweet_count");
			String source = (String) jo.get("source");
			String date = (String) jo.get("created_at");
			JSONObject jo1 = (JSONObject)jo.get("user");
			String user = (String)jo1.get("screen_name");
			int start = source.indexOf(">")+1;
			int end = source.lastIndexOf("<");
			source = source.substring(start,end);
			Tweet tweet = new Tweet();
			tweet.setTweet(twt);
			tweet.setRetweet(Double.parseDouble(retweet+""));
			tweet.setSource(source);
			tweet.setDate(date);
			tweet.setUser(user);
			tweets.add(tweet);
			Object row[] = {twt,retweet+"",source,date,user};
			dtm.addRow(row);
		}
		

	}catch(Exception e){
		e.printStackTrace();
	}
}
public static void optimizeParametersTraining(){
	for(int i=0;i<tweets.size();i++) {
		Tweet first = tweets.get(i);
		int same_time = 0;
		int same_source = 0;
		int same_blog = 0;
		for(int j=i+1;j<tweets.size();j++) {
			Tweet second = tweets.get(i);
			if(first.getDate().trim().toLowerCase().equals(second.getDate().trim().toLowerCase())){
				same_time = 1;
			}
			if(first.getSource().trim().toLowerCase().equals(second.getSource().trim().toLowerCase())){
				same_source = 1;
			}
			if(first.getUser().trim().toLowerCase().equals(second.getUser().trim().toLowerCase())){
				same_blog = 1;
			}
		}
		int relative_tag = 0;
		for(int j=0;j<keywords.size();j++) {
			if(first.getTweet().trim().toLowerCase().indexOf(keywords.get(j)) != -1) {
				relative_tag = 1;
				break;
			}
		}
		double retweet_count = 0;
		if(first.getRetweet() > 0) {
			retweet_count = first.getRetweet() / (double)tweets.size();
		}
		PersonalCharacteristics pc = new PersonalCharacteristics();
		pc.setTweet(first.getTweet());
		pc.setSource(same_source);
		pc.setRetweet(retweet_count);
		pc.setRelativeTag(relative_tag);
		pc.setSameTime(same_time);
		pc.setSameBlogger(same_blog);
		pc.setInfluence(Double.parseDouble(first.getRetweet()+""));
		matrix.add(pc);
	}
}
}
