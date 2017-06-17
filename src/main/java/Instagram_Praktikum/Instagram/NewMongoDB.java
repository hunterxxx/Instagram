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


public class NewMongoDB {
	// holt die IDs der Follower aus der Datenbank
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

	// Collection Inhalt anzeigen (Json)
	void showCollection(MongoCollection<Document> mongoCollection) {
		System.out.println("aktuelle DB-Einträge:");
		for (Document doc : mongoCollection.find()) {
			System.out.println("      " + doc.toJson());
		}
		System.out.println(mongoCollection.count() + " Einträge in DB");
	}

	void insertUserinDB(InstagramUserSummary user, MongoCollection<Document> mongoCollection, boolean followStatus) {
		Document insertDoc = new Document();
		insertDoc.append("Follower Name", user.getUsername()).append("Follower ID", user.getPk())
				.append("following status", followStatus);
		mongoCollection.insertOne(insertDoc);
		System.out.println("Follower " + user.getUsername() + " wurde eingefügt!");
	}

	void insertNewFollowersInDB(MongoCollection<Document> mongoCollection,List<InstagramUserSummary> followerList) {
		ArrayList<Long> oldFollowerIDs = new ArrayList<Long>();
		oldFollowerIDs = getFollowerIDsFromDB(mongoCollection);
		int followerCount = 0;
		for (int i = 0; i < followerList.size(); i++) {
			if (!(oldFollowerIDs.contains(followerList.get(i).getPk()))) {
				insertUserinDB(followerList.get(i), mongoCollection, true);
				followerCount++;
			}
		}
		if (followerCount == 0) {
			System.out.println("Keine neuen Follower in DB eingefügt!");
		}
	}
	
	void insert100NewFollowersInDB(MongoCollection<Document> mongoCollection,List<InstagramUserSummary> followerList) {
		ArrayList<Long> oldFollowerIDs = new ArrayList<Long>();
		oldFollowerIDs = getFollowerIDsFromDB(mongoCollection);
		int followerCount = 0;
		for (int i = 0; i < 100; i++) {
			if (!(oldFollowerIDs.contains(followerList.get(i).getPk()))) {
				insertUserinDB(followerList.get(i), mongoCollection, true);
				followerCount++;
			}
		}
		if (followerCount == 0) {
			System.out.println("Keine neuen Follower in DB eingefügt!");
		}
	}

	void setFollowStatus(MongoCollection<Document> mongoCollection, List<InstagramUserSummary> followerList) {
		ArrayList<Long> dbFollowerIDs = new ArrayList<Long>();
		dbFollowerIDs = getFollowerIDsFromDB(mongoCollection);
		ArrayList<Long> followerIDsAktuell = new ArrayList<Long>();
		for (int i = 0; i < followerList.size(); i++) {
			followerIDsAktuell.add(followerList.get(i).getPk());
		}
		ArrayList<Long> unFollowerIDs = new ArrayList<Long>();

		for (int i = 0; i < dbFollowerIDs.size(); i++) {
			//System.out.println("Follower ID: " + dbFollowerIDs.get(i));
			if (!(followerIDsAktuell.contains(dbFollowerIDs.get(i)))) {
				unFollowerIDs.add(dbFollowerIDs.get(i));
			} else {
			}
		}
		// setze Status der nicht mehr folgenden User in DB auf false
		for (int i = 0; i < unFollowerIDs.size(); i++) {
			Bson filter = new Document("Follower ID", unFollowerIDs.get(i));
			Bson newValue = new Document("following status", false);
			Bson updateOperationDocument = new Document("$set", newValue);
			mongoCollection.updateOne(filter, updateOperationDocument);
		}
	}

	// Zeigt Anzahl und Name der nicht mehr folgenden User an
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
}
