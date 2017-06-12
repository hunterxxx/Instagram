package Instagram_Praktikum.Instagram;

import java.util.ArrayList;
import java.util.List;

import org.brunocvcunha.instagram4j.requests.payload.InstagramUserSummary;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bytedeco.javacpp.avformat.AVOutputFormat.Get_output_timestamp_AVFormatContext_int_LongPointer_LongPointer;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class MongoDB {

	static Instagram instagram = new Instagram();

	public MongoDB() {

	}

	public void insertFollower(InstagramUserSummary user, MongoCollection<Document> collection, boolean followStatus) {
		Document insertDoc = new Document();
		insertDoc.append("user", user.getUsername())
		.append("follow status", followStatus)
		.append("Follower ID", user.getPk());
		collection.insertOne(insertDoc);
	}

	public void insertNewFollower(MongoCollection<Document> collection, List<InstagramUserSummary> getAllFollowers) {
		ArrayList<Long> oldFollowerIDs = new ArrayList<Long>();
		oldFollowerIDs = getFollowerIDsFromDB(collection);
		for (InstagramUserSummary user : getAllFollowers) {
			if (!(oldFollowerIDs.contains(user.getPk()))) {
				System.out.println(user.getPk());
				System.out.println(user.getUsername());
				insertFollower(user, collection, true);				
			} else{
				System.out.println("No new Follower");
			}
		}
	}

	private ArrayList<Long> getFollowerIDsFromDB(MongoCollection<Document> mongoCollection) {
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
	
	private ArrayList<String> getFollowerNamesFromDB(MongoCollection<Document> mongoCollection) {
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

	private void showUnfollowers(MongoCollection<Document> collection) {
		MongoCursor<Document> unFollowCursor;
		Document unFollowDoc;
		Bson filterStatus = new Document("following status", false);
		unFollowCursor = collection.find(filterStatus).iterator();
		int unFollowerCount = 0;
		while (unFollowCursor.hasNext()) {
			unFollowDoc = unFollowCursor.next();
			System.out.println("User " + unFollowDoc.getString("Follower Name") + " folgt jetzt nicht mehr!");
			unFollowerCount++;
		}
		System.out.println(unFollowerCount + " User folgen jetzt nicht mehr!");
	}

	private void setFollowStatus(MongoCollection<Document> collection, List<InstagramUserSummary> getAllFollowers) {
		ArrayList<Long> dbFollowerIDs = new ArrayList<Long>();
		dbFollowerIDs = getFollowerIDsFromDB(collection);
		ArrayList<String> followerNamesAktuell = new ArrayList<String>();

		for (int i = 0; i < getAllFollowers.size(); i++) {
			followerNamesAktuell.add(getAllFollowers.get(i).getUsername());
		}
		ArrayList<Long> unFollowerIDs = new ArrayList<Long>();

		for (int i = 0; i < dbFollowerIDs.size(); i++) {
			if (!(followerNamesAktuell.contains(dbFollowerIDs.get(i)))) {
				unFollowerIDs.add(dbFollowerIDs.get(i));
			}
		}
		for (int i = 0; i < unFollowerIDs.size(); i++) {
			Bson filter = new Document("Follower ID", unFollowerIDs.get(i));
			Bson newValue = new Document("following status", false);
			Bson updateOperationDocument = new Document("$set", newValue);
			collection.updateOne(filter, updateOperationDocument);
		}
	}

	private void showCollection(MongoCollection<Document> mongoCollection) {
		System.out.println("aktuelle DB-Einträge:");
		for (Document doc : mongoCollection.find()) {
			System.out.println("      " + doc.toJson());
		}
		System.out.println(mongoCollection.count() + " Einträge in DB");
	}

	public static void main(String[] args) {
		MongoDB mongodb = new MongoDB();

		MongoClient mongoClient = new MongoClient();
		MongoDatabase database = mongoClient.getDatabase("InstagramDB");
		MongoCollection<Document> collection = database.getCollection("Follower");

		 //collection.drop();
		mongodb.insertNewFollower(collection, instagram.getAllFollowers());
		mongodb.setFollowStatus(collection, instagram.getAllFollowers());
		mongodb.showUnfollowers(collection);
		System.out.println(mongodb.getFollowerNamesFromDB(collection));

		mongodb.showCollection(collection);
		mongoClient.close();
	}
}
