package Instagram_Praktikum.Instagram;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.brunocvcunha.instagram4j.requests.payload.InstagramUserSummary;
import org.bson.Document;

public class Main2 {
	public static void main(String[] args) throws MalformedURLException, IOException, InterruptedException {
		OldInstagram instagram = new OldInstagram();
		// 1a
//		 for (InstagramUserSummary user :
//		 instagram.getAllFollowersFromAnAccount("Github")) {
//		 System.out.println(user.getUsername());
//		 //System.out.println(user.getPk());
//		 }

		// 1b
//		 ArrayList<String> instagramNames = new ArrayList<String>();
//		 instagramNames.addAll(Arrays.asList("hunter.f", "yoyoyo"));
//		 for (String name : instagramNames) {
//		 instagram.setFollowers(name);
//		 System.out.println("Followed: " + name);
//		 }

		// 1c
		//instagram.setLikesOnHashTags("casio");

		// 1d
		NewInstagram newInstagram = new NewInstagram();
		 // String id = "1525434981060739387_5450704343";
		 //
		 System.out.println(newInstagram.getMostRecentMedia("5450704343"));
		ArrayList<String> instagramComments = new ArrayList<String>();
		ArrayList<String> mediaIds = new ArrayList<String>();
		instagramComments
				.addAll(Arrays.asList("Super!", "Schönes Bild!", "Wunderbar!", "Cool!", "Top!", "Wahnsinnig cool!",
						"Ich mag das total!", "Fantastisch!", "Wahnsinnig cool!", "Lässig!", "Sieht echt gut aus!"));
		mediaIds.addAll(Arrays.asList("1525434981060739387_5450704343", "1520287922913749390_5450704343"));
		Collections.shuffle(instagramComments);
		Collections.shuffle(mediaIds);
		for (String comment : instagramComments) {
			for (String mediaId : mediaIds) {
				newInstagram.setComments(comment, mediaId);
				System.out.println("Wait 10 seconds");
				Thread.sleep(10000);
			}
		}
		// System.out.println(newInstagram.getMostRecentMedia("5450704343"));
	}
}
