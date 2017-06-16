package Instagram_Praktikum.Instagram;

import java.io.IOException;
import java.net.MalformedURLException;

import org.brunocvcunha.instagram4j.requests.payload.InstagramUserSummary;
import org.bson.Document;
import org.jinstagram.exceptions.InstagramException;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class Main {
	public static void main(String[] args) throws MalformedURLException, IOException {
		OldInstagram instagram = new OldInstagram();
		NewInstagram newInstagram = new NewInstagram();
		
		System.out.println("All Followers: ");
		for (InstagramUserSummary user : instagram.getAllFollowers()) {
			System.out.println(user.getUsername() + " follows CarRx7Hunter!");
			//System.out.println(user.getPk());
		}
				
		System.out.println("Anazahl Follower: " + instagram.getFollowerCount());
		System.out.println("Anzahl Following: " + instagram.getFollowingCount());
		System.out.println("Anzahl Posts: " + instagram.getPostCount());
		System.out.println("Anzahl Likes: " + newInstagram.likesCount());
		System.out.println("Anzahl Kommentare: " + newInstagram.commentsCount());
		
		NewMongoDB mongodb = new NewMongoDB();
		MongoClient mongoClient = new MongoClient();
		MongoDatabase database = mongoClient.getDatabase("InstagramDB");
		MongoCollection<Document> mongoCollection = database.getCollection("Follower");

		//mongoCollection.drop();
		mongodb.insertNewFollowersInDB(mongoCollection, instagram.getAllFollowers());
		mongodb.setFollowStatus(mongoCollection, instagram.getAllFollowers());
		mongodb.showUnfollowers(mongoCollection);

		mongodb.showCollection(mongoCollection);
		mongoClient.close();
	}
}
