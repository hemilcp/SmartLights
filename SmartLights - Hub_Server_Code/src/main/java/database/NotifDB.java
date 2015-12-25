package database;

import java.net.UnknownHostException;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;

import Models.Notification;

public class NotifDB {

	MongoClient mongoClient;
	DB db;
	DBCollection coll;
	
	public NotifDB(){
		try {
			mongoClient = new MongoClient("localhost",27017);
			db = mongoClient.getDB("HubDB");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	public void saveData(Notification notif){
		if(!db.collectionExists("NotifInfo")){
			BasicDBObject options = new BasicDBObject().append("capped", true).append("size", 100).append("max", 1);
			coll = db.createCollection("NotifInfo", options);
		}
		else coll = db.getCollection("NotifInfo");
		
		BasicDBObject obj = (BasicDBObject) JSON.parse(new Gson().toJson(notif));
		coll.insert(obj);
	}
	
	public Notification retriveData(){
		coll = db.getCollection("NotifInfo");
		DBObject cursor =  coll.findOne();
		return (Notification) new Gson().fromJson(cursor.toString(), Notification.class);
		
	}
}
