package Instagram_Praktikum.Instagram;

import java.util.List;
import java.util.Scanner;


import org.jinstagram.Instagram;
import org.jinstagram.auth.InstagramAuthService;
import org.jinstagram.auth.model.Token;
import org.jinstagram.auth.model.Verifier;
import org.jinstagram.auth.oauth.InstagramService;
import org.jinstagram.entity.users.feed.MediaFeed;
import org.jinstagram.entity.users.feed.MediaFeedData;
import org.jinstagram.exceptions.InstagramException;


public class NewInstagram {

	private String clientId = "fdf02d1b8be448a49bd8518283a570f0";
	private String clientSecret = "1e5f25fc47bf4157b8bb3327852c2abc";
	private String callbackUrl = "http://localhost";

	private Instagram instagram;

	private final String userId = "5450704343";

	public NewInstagram() {
		InstagramService service = new InstagramAuthService().apiKey(clientId).apiSecret(clientSecret)
				.callback(callbackUrl).scope("basic public_content likes follower_list relationships").build();
		// public_content likes follower_list relationships

		String authorizationUrl = service.getAuthorizationUrl();

		System.out.println(authorizationUrl); // paste in browser

		Scanner sc = new Scanner(System.in);

		System.out.println("Paste the code gotten in the browser (at the end of the URL):  ");
		String verCode = sc.nextLine(); // SCAN VERIFIER CODE

		Verifier verifier = new Verifier(verCode);

		Token accessToken = service.getAccessToken(verifier); // Token successfully gotten
		// ** RUNS OK UP TO THIS LINE INCLUDED **//
		instagram = new Instagram(accessToken);
	}

	public int likesCount() throws InstagramException {
		MediaFeed mediaFeed = instagram.getRecentMediaFeed(userId);
		List<MediaFeedData> mediaFeeds = mediaFeed.getData();
		int likesCount = 0;
		for (MediaFeedData mediaData : mediaFeeds) {
			int likes = mediaData.getLikes().getCount();
			likesCount += likes;
		}
		return likesCount;
	}

	public int commentsCount() throws InstagramException {
		MediaFeed mediaFeed = instagram.getRecentMediaFeed(userId);
		List<MediaFeedData> mediaFeeds = mediaFeed.getData();
		int commentsCount = 0;
		for (MediaFeedData mediaData : mediaFeeds) {
			int comments = mediaData.getComments().getCount();
			commentsCount += comments;
		}
		return commentsCount;
	}
}
