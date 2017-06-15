package Instagram_Praktikum.Instagram;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.brunocvcunha.instagram4j.requests.payload.InstagramUserSummary;
import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;


public class MongoDB {
	static OldInstagram instagram;
	
	public MongoDB() {

	}

	void insertFollower(InstagramUserSummary user, MongoCollection<Document> collection, boolean followStatus) {
		Document insertDoc = new Document();
		insertDoc.append("Follower Name", user.getUsername())
		.append("Follower ID", user.getPk())
		.append("following status", followStatus);
		collection.insertOne(insertDoc);
	}

	void insertNewFollower(MongoCollection<Document> collection, List<InstagramUserSummary> getAllFollowers) {
		ArrayList<Long> oldFollowerIDs = new ArrayList<Long>();
		oldFollowerIDs = getFollowerIDsFromDB(collection);
		int followerCount = 0;

		for (InstagramUserSummary user : getAllFollowers) {
			if (!(oldFollowerIDs.contains(user.getPk()))) {
				System.out.println("Neue Follower: " + user.getUsername());
				insertFollower(user, collection, true);	
				followerCount ++;
			} 
		}
		if( followerCount == 0){
		    System.out.println("Keine neuen Follower in DB eingefügt!");
		}
	}

	ArrayList<Long> getFollowerIDsFromDB(MongoCollection<Document> mongoCollection) {
		MongoCursor<Document> idCursor;
		Document docOldFollower;
		idCursor = mongoCollection.find().iterator();
		ArrayList<Long> oldFollowerIDs = new ArrayList<Long>();
		while (idCursor.hasNext()) {
			docOldFollower = idCursor.next();
			oldFollowerIDs.add(docOldFollower.getLong("Follower ID"));
		}
		return oldFollowerIDs;
	}
	
	ArrayList<String> getFollowerNamesFromDB(MongoCollection<Document> mongoCollection) {
		MongoCursor<Document> idCursor = mongoCollection.find().iterator();
		Document docOldFollower;
		ArrayList<String> oldFollowerNames = new ArrayList<String>();
		while (idCursor.hasNext()) {
			docOldFollower = idCursor.next();
			for (InstagramUserSummary user : instagram.getAllFollowers()) {
				oldFollowerNames.add(user.getUsername());
			}
			//oldFollowerNames.add(getAllFollowers);
		}
		return oldFollowerNames;
	}
	

	void showUnfollowers(MongoCollection<Document> mongoCollection) {
		MongoCursor<Document> unFollowCursor;
		Document unFollowDoc;
		Bson filterStatus = new Document("following status", false);
		unFollowCursor = mongoCollection.find(filterStatus).iterator();
		int unFollowerCount = 0;
		while (unFollowCursor.hasNext()) {
			unFollowDoc = unFollowCursor.next();
			System.out.println("User " + unFollowDoc.getString("Follower Name") + " folgt jetzt nicht mehr!");
			unFollowerCount++;
		}
		System.out.println(unFollowerCount + " User folgen jetzt nicht mehr!");
	}
	

	void setFollowStatus(MongoCollection<Document> collection, List<InstagramUserSummary> getAllFollowers) {
		ArrayList<Long> dbFollowerIDs = new ArrayList<Long>();
		dbFollowerIDs = getFollowerIDsFromDB(collection);
		ArrayList<Long> followerIDsAktuell = new ArrayList<Long>();

		for (int i = 0; i < getAllFollowers.size(); i++) {
			followerIDsAktuell.add(getAllFollowers.get(i).getPk());
		}
		ArrayList<Long> unFollowerIDs = new ArrayList<Long>();

		for (int i = 0; i < dbFollowerIDs.size(); i++) {
			System.out.println(dbFollowerIDs.get(i));
			if (!(followerIDsAktuell.contains(dbFollowerIDs.get(i)))) {
				//unFollowerIDs.add("Follower ID: "+ dbFollowerIDs.get(i));
			}
		}
		for (int i = 0; i < unFollowerIDs.size(); i++) {
			Bson filter = new Document("Follower ID", unFollowerIDs.get(i));
			Bson newValue = new Document("following status", false);
			Bson updateOperationDocument = new Document("$set", newValue);
			collection.updateOne(filter, updateOperationDocument);
		}
	}

	void showCollection(MongoCollection<Document> mongoCollection) {
		System.out.println("aktuelle DB-Einträge:");
		for (Document doc : mongoCollection.find()) {
			System.out.println("      " + doc.toJson());
		}
		System.out.println(mongoCollection.count() + " Einträge in DB");
	}

	public static void main(String[] args) throws ClientProtocolException, IOException {
		MongoDB mongodb = new MongoDB();
		instagram = new OldInstagram();
		MongoClient mongoClient = new MongoClient();
		MongoDatabase database = mongoClient.getDatabase("InstagramDB");
		MongoCollection<Document> mongoCollection = database.getCollection("Follower");

		//collection.drop();
		mongodb.insertNewFollower(mongoCollection, instagram.getAllFollowers());
		mongodb.setFollowStatus(mongoCollection, instagram.getAllFollowers());
		mongodb.showUnfollowers(mongoCollection);
		//System.out.println(mongodb.getFollowerNamesFromDB(mongoCollection));

		mongodb.showCollection(mongoCollection);
		mongoClient.close();
	}
}
