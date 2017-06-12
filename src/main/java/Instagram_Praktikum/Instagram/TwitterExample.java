package Instagram_Praktikum.Instagram;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.apis.TwitterApi;
import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.model.OAuth1RequestToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth10aService;
import com.mongodb.BasicDBList;
import com.mongodb.DBObject;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public final class TwitterExample {

	private static final String PROTECTED_RESOURCE_URL = "https://api.twitter.com/1.1/account/verify_credentials.json";
	private static OAuth10aService service;
	private static OAuth1AccessToken accessToken;

	private TwitterExample() {
	}

	public static void main(String[] args) {

		service = new ServiceBuilder().apiKey("FxvjWjb21Tr4RZvTdoIbuLf6Z")
				.apiSecret("DR2AnDPqqeLi6DmitWJVdZ0Ypny41VJeN18oiMlUtSqofnVc1j").build(TwitterApi.instance());
		accessToken = new OAuth1AccessToken("860156012342497280-4atHE8AAZx0Fadqglgp8TrJzJTTQhgt",
				"1sL6oAHT21X89ZTS4Fm45BXSnyfD07VBQiftrdOeVt9wz");

	}
	
}
