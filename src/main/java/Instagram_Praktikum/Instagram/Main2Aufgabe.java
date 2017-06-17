package Instagram_Praktikum.Instagram;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.brunocvcunha.instagram4j.requests.payload.InstagramUserSummary;
import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class Main2Aufgabe {
	public static void main(String[] args) throws MalformedURLException, IOException {
		OldInstagram instagram = new OldInstagram();		

		//2a extract 100 Followers from Cristiano Ronaldo @cristiano and put into DB
		
		NewMongoDB mongodb = new NewMongoDB();
		MongoClient mongoClient = new MongoClient();
		MongoDatabase database = mongoClient.getDatabase("InstagramDB");
		MongoCollection<Document> FCollection = database.getCollection("Follower");

		//mongoCollection.drop();
		mongodb.insert100NewFollowersInDB(FCollection, instagram.getAllFollowers());
		
		List<InstagramUserSummary> user = instagram.getAllFollowersFromAnAccount("cristiano");
		
		System.out.println("100 Followers from Cristiano Ronaldo are added to DB");
		for (int i = 0; i < 100; i++) {
			System.out.println(user.get(i).getUsername());
		}
		
		mongodb.showCollection(FCollection);
		mongoClient.close();
	}
}
