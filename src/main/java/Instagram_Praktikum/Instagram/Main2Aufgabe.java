package Instagram_Praktikum.Instagram;

import java.io.IOException;
import java.net.MalformedURLException;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class Main2Aufgabe {
	public static void main(String[] args) throws MalformedURLException, IOException {
		OldInstagram oldInstagram = new OldInstagram();
		// NewInstagram newInstagram= new NewInstagram();

		// 2.Aufgabe
		NewMongoDB mongodb = new NewMongoDB();
		MongoClient mongoClient = new MongoClient();
		MongoDatabase database = mongoClient.getDatabase("InstagramDB");

		// 2a extract 100 Followers from Cristiano Ronaldo @cristiano and put into DB
		// MongoCollection<Document> FCollection = database.getCollection("FFollower");
		// mongodb.insert100NewFollowersInDB(FCollection,oldInstagram.getAllFollowersFromAnAccount("cristiano"));
		//
		// for(String name : mongodb.getFollowerNamesFromDB(FCollection)){
		// System.out.println(name);
		// oldInstagram.setFollowers(name);
		// }
		//
		// //FCollection.drop();
		// mongodb.showCollection(FCollection);
		// mongoClient.close();

		// 2b extract 100 Followers from Selena Gomez @selenagomez and like the
		// latest post and put into DB
		MongoCollection<Document> FLCollection = database.getCollection("FLFollower");
		NewInstagram newInstagram = new NewInstagram();

		mongodb.insert100NewFollowersInDB(FLCollection, oldInstagram.getAllFollowersFromAnAccount("selenagomez"));

		for (String name : mongodb.getFollowerNamesFromDB(FLCollection)) {
			// newInstagram.getMostRecentMedia(Long.toString(id));
			oldInstagram.setFollowers(name);
			System.out.println("New Following: " + name);
			oldInstagram.setLikesOnIdOfUsername(name);
		}

		mongodb.showCollection(FLCollection);
		mongoClient.close();

		// 2c extract 100 Followers from Ariana Grande @arianagrande, like and
		// comment the latest post and put into DB
		// MongoCollection<Document> FLKCollection =
		// database.getCollection("FLKFollower");
		// mongodb.insert100NewFollowersInDB(FLKCollection,oldInstagram.getAllFollowersFromAnAccount("arianagrande"));
		// mongodb.showCollection(FLKCollection);
		// mongoClient.close();
	}
}
