package Instagram_Praktikum.Instagram;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.brunocvcunha.instagram4j.requests.payload.InstagramUserSummary;
import org.bson.Document;

public class Main2 {
	public static void main(String[] args) throws MalformedURLException, IOException {
		OldInstagram instagram = new OldInstagram();		
		//1a
//		for (InstagramUserSummary user : instagram.getAllFollowersFromAnAccount("Github")) {
//			System.out.println(user.getUsername());
//			//System.out.println(user.getPk());
//		}
		
		//1b
		ArrayList<String> instagramNames = new ArrayList<String>();
		instagramNames.addAll(Arrays.asList("hunter.f", "yoyoyo"));
		for (String name : instagramNames) {
			instagram.setFollowers(name);
			System.out.println("Followed: " + name);
		}
		
		//1c
		//instagram.setLikesOnHashTags("cdp100");
		
		//1d		
//		NewInstagram newInstagram = new NewInstagram();
//		String id = "1525434981060739387_5450704343";
//		System.out.println(newInstagram.getMostRecentMedia(id));
//		ArrayList<String> instagramComments = new ArrayList<String>();
//		instagramComments.addAll(Arrays.asList("Super!", "Schönes Bild!", "Wunderbar!", "Cool!"));
//		for (String comment : instagramComments){
//			newInstagram.setComments(comment);
//		}		
	}
}
