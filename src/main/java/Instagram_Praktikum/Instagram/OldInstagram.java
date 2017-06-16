package Instagram_Praktikum.Instagram;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.brunocvcunha.instagram4j.Instagram4j;
import org.brunocvcunha.instagram4j.requests.InstagramFollowRequest;
import org.brunocvcunha.instagram4j.requests.InstagramGetUserFollowersRequest;
import org.brunocvcunha.instagram4j.requests.InstagramLikeRequest;
import org.brunocvcunha.instagram4j.requests.InstagramSearchUsernameRequest;
import org.brunocvcunha.instagram4j.requests.InstagramTagFeedRequest;
import org.brunocvcunha.instagram4j.requests.payload.InstagramFeedItem;
import org.brunocvcunha.instagram4j.requests.payload.InstagramFeedResult;
import org.brunocvcunha.instagram4j.requests.payload.InstagramGetUserFollowersResult;
import org.brunocvcunha.instagram4j.requests.payload.InstagramSearchUsernameResult;
import org.brunocvcunha.instagram4j.requests.payload.InstagramUserSummary;

public class OldInstagram {

	public final String id = "carrx7hunter";
	private Instagram4j instagram;
	
	public OldInstagram() throws ClientProtocolException, IOException{
		// Disable Logs
		List<Logger> loggers = Collections.<Logger>list(LogManager.getCurrentLoggers());
		loggers.add(LogManager.getRootLogger());
		for (Logger logger : loggers) {
			logger.setLevel(Level.OFF);
		}
		
		instagram = Instagram4j.builder().username("lisaleehunterfoo@gmail.com").password("hehe1234")
				.build();
		instagram.setup();
		instagram.login();
	}

	public InstagramSearchUsernameResult request() {
		InstagramSearchUsernameResult userResult = null;
		try {
			userResult = instagram.sendRequest(new InstagramSearchUsernameRequest(id));
			return userResult;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return userResult;
	}

	public int getFollowerCount() {
		int followerCount = request().getUser().getFollower_count();
		return followerCount;
	}

	public int getFollowingCount() {
		int followingCount = request().getUser().getFollowing_count();
		return followingCount;
	}

	public int getPostCount() {
		int postCount = request().getUser().getMedia_count();
		return postCount;
	}

	public List<InstagramUserSummary> getAllFollowers() {
		InstagramGetUserFollowersResult myFollowers;
		List<InstagramUserSummary> users = null;
		try {
			myFollowers = instagram.sendRequest(new InstagramGetUserFollowersRequest(request().getUser().getPk()));
			users = myFollowers.getUsers();
			return users;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return users;
	}
	
/*
 * Aufgabe 5	
 */
	//1a
	public List<InstagramUserSummary> getAllFollowersFromAnAccount(String id) {
		InstagramGetUserFollowersResult myFollowers;
		InstagramSearchUsernameResult userResult;
		List<InstagramUserSummary> users = null;
		try {
			userResult = instagram.sendRequest(new InstagramSearchUsernameRequest(id));
			myFollowers = instagram.sendRequest(new InstagramGetUserFollowersRequest(userResult.getUser().getPk()));
			users = myFollowers.getUsers();
			return users;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return users;
	}
	
	//1b
	public void setFollowers(String name) throws ClientProtocolException, IOException{
		InstagramSearchUsernameResult userResult = instagram.sendRequest(new InstagramSearchUsernameRequest(name));
		instagram.sendRequest(new InstagramFollowRequest(userResult.getUser().getPk()));
	}
	
	//1c
	public void setLikesOnHashTags(String hashtag) throws ClientProtocolException, IOException{
		InstagramFeedResult tagFeed = instagram.sendRequest(new InstagramTagFeedRequest(hashtag));
		for (InstagramFeedItem feedResult : tagFeed.getItems()) {
		    System.out.println("Post ID: " + feedResult.getPk());
		    instagram.sendRequest(new InstagramLikeRequest(feedResult.getPk()));
		}
	}


	public static void main(String[] args) throws ClientProtocolException, IOException {
		OldInstagram instagram = new OldInstagram();
		NewInstagram newInstagram = new NewInstagram();
		for (InstagramUserSummary user : instagram.getAllFollowers()) {
			//System.out.println(user.getUsername() + " follows CarRx7Hunter!");
			System.out.println(user.getPk());
		}
		System.out.println("Anazahl Follower: " + instagram.getFollowerCount());
		System.out.println("Anzahl Following: " + instagram.getFollowingCount());
		System.out.println("Anzahl Posts: " + instagram.getPostCount());
		System.out.println("Anzahl Likes: " + newInstagram.likesCount());
		System.out.println("Anzahl Kommentare: " + newInstagram.commentsCount());
	}
}
