package Instagram_Praktikum.Instagram;

import java.net.URI;
import java.util.List;
import java.util.Scanner;

import org.jinstagram.Instagram;
import org.jinstagram.auth.InstagramAuthService;
import org.jinstagram.auth.model.Token;
import org.jinstagram.auth.model.Verifier;
import org.jinstagram.auth.oauth.InstagramService;
import org.jinstagram.entity.users.basicinfo.UserInfo;
import org.jinstagram.entity.users.basicinfo.UserInfoData;
import org.jinstagram.entity.users.feed.MediaFeed;
import org.jinstagram.entity.users.feed.MediaFeedData;
import org.jinstagram.entity.users.feed.UserFeed;
import org.jinstagram.entity.users.feed.UserFeedData;
import org.jinstagram.exceptions.InstagramException;

public class NewInstagram {
	
	String clientId = "fdf02d1b8be448a49bd8518283a570f0";
	String clientSecret = "1e5f25fc47bf4157b8bb3327852c2abc";
	String callbackUrl = "http://www.instagram.com/handleInstagramToken";
	

	
	public NewInstagram() {

	}
	
	public String getAccessToken(String uriString) {
	    URI uri = URI.create(uriString);
	    System.out.println(uri);
	    //String[] parameters = uri.getFragment().split("\\&");
	    //for (String parameter : parameters) {
        String[] parts = uri.getFragment().split("\\=");

	        //String[] parts = parameter.split("\\=");
	        if (parts[0].equals("code")) {
	            if (parts.length == 1) {
	                throw new RuntimeException("missing access token");
	            }
	            System.out.println(parts[1]);
	            return parts[1];
	        }
	    //}
	    throw new RuntimeException("no access token");
	}

	public void login() throws InstagramException {
		InstagramService service = new InstagramAuthService().apiKey(clientId).apiSecret(clientSecret)
				.callback(callbackUrl).scope("basic").build();
		//public_content likes follower_list relationships

		String authorizationUrl = service.getAuthorizationUrl();	    
		System.out.println(authorizationUrl); //paste in browser

	    //Scanner sc = new Scanner(System.in);

	    //System.out.println("Paste the code gotten in the browser (at the end of the URL):  ");
	    //String verCode = sc.nextLine();  //SCAN VERIFIER CODE

	    Verifier verifier = new Verifier(getAccessToken(authorizationUrl));

	    Token accessToken = service.getAccessToken(verifier);  //Token successfully gotten

	    //** RUNS OK UP TO THIS LINE INCLUDED **//
	    Instagram instagram = new Instagram(accessToken);
							
		String userId = "5450704343";
		UserInfo userInfo = instagram.getUserInfo(userId);
		
		MediaFeed mediaFeed = instagram.getRecentMediaFeed(userId);
		List<MediaFeedData> mediaFeeds = mediaFeed.getData();

		UserFeed feed = instagram.getUserFollowedByList(userId);
		List<UserFeedData> following = feed.getUserList();
		System.out.println("Anzahl Following: " + following);
		
		UserInfoData userData = userInfo.getData();
		
		
		int likesCount = 0;
		int commentsCount = 0;
		for (MediaFeedData mediaData : mediaFeeds) {
			int likes = mediaData.getLikes().getCount();
			int comments = mediaData.getComments().getCount();
			
			 likesCount += likes;
			 commentsCount += comments;
		}
		System.out.println("Anzahl Likes:" + likesCount);
		System.out.println("Anzahl Comments:" + commentsCount);
		

		
//		System.out.println("id : " + userData.getId());
//		System.out.println("profile_picture : " + userData.getProfilePicture());
//		System.out.println("website : " + userData.getWebsite());
//		System.out.println(feed.getUserList());
	}
}
