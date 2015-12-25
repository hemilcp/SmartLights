
package database;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;

import Models.HubProperties;
import Models.ServerObject;
import Models.ClientPOJO;
import Models.Events;

public class ServerDB {
	MongoClient mongoClient;
	DB db;
	DBCollection coll;
	
	public ServerDB(){
		try {
			mongoClient = new MongoClient("localhost",27017);
			db = mongoClient.getDB("ServerDB");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	public void registerHub(HubProperties hub, ServerObject server){
		if(!db.collectionExists("HubDeviceInfo")){
			BasicDBObject options = new BasicDBObject();
			coll = db.createCollection("HubDeviceInfo", options);
		}
		else coll = db.getCollection("HubDeviceInfo");
		
		Gson gson = new Gson();
		BasicDBObject obj = (BasicDBObject) JSON.parse(gson.toJson(hub));
		obj.put("server",(BasicDBObject) JSON.parse(gson.toJson(server))); 
		coll.insert(obj);
	}
	
	public void registerMobile(Models.ClientPOJO client){
		if(!db.collectionExists("MobileDeviceInfo")){
			BasicDBObject options = new BasicDBObject();
			coll = db.createCollection("MobileDeviceInfo", options);
		}
		else coll = db.getCollection("MobileDeviceInfo");
		
		BasicDBObject obj = (BasicDBObject) JSON.parse(new Gson().toJson(client));
		coll.insert(obj);
	}
	
	public String readResource(String objectId,String objectInstance, String resourceId){
		coll = db.getCollection("HubDeviceInfo");
		DBCursor cursor = coll.find();
		String returnValue = null;
		int i = 0;

		System.out.println(cursor.size());
		while(cursor.hasNext()){
			System.out.println(cursor.next());
			if(i==(Integer.parseInt(objectId))){
				if((Integer.parseInt(objectInstance))==0){
					returnValue = (String) cursor.curr().get(resourceId);	
				}
				if((Integer.parseInt(objectInstance))==1){
					DBObject obj = (DBObject) cursor.curr().get("server");
					returnValue = (String) obj.get(resourceId);
				}
				System.out.println(returnValue);
				break;
			}
			i++;
		}
		
		return returnValue;
	}
	
	public Map<String,String> discoverMethod(){
		Map<String,String> map = new HashMap<String,String>();
		coll = db.getCollection("HubDeviceInfo");
		DBCursor cursor = coll.find();
		System.out.println("Inside DB");
	
		while(cursor.hasNext()){
			ServerObject server = new Gson().fromJson(cursor.next().get("server").toString(), ServerObject.class);
				if(server.getServer_Uri().equals("localhost")){
					HubProperties hub = new Gson().fromJson(cursor.curr().toString(), HubProperties.class);
					map.put(new Gson().toJson(hub),new Gson().toJson(server));
				}
		}
		System.out.println("Map : "+map.toString());
		return map;
	}
	
	public void writeMethod(int objectId, int objectInstance, String resourceId, String resourceValue){
		coll = db.getCollection("HubDeviceInfo");
		int i =0;
		DBCursor cursor = coll.find();
		while(cursor.hasNext()){
			if(i==objectId){
				if(objectInstance==0){
					BasicDBObject newValue = new BasicDBObject();
					newValue.append("$set", new BasicDBObject().append(resourceId, resourceValue));
					coll.update(cursor.next(), newValue);
				}
				if(objectInstance==1){
					BasicDBObject newValue = new BasicDBObject();
					newValue.append("$set", new BasicDBObject().append("server."+resourceId, resourceValue));
					coll.update( cursor.next() , newValue);
				}
				break;
			}
			i++;
		}
	}
	
	public void deleteResourcec(int objectId){
		System.out.println("Inside DB");
		coll = db.getCollection("HubDeviceInfo");
		int i =0;
		DBCursor cursor = coll.find();
		System.out.println(cursor.size());
		while(cursor.hasNext()){
			if(i==objectId){
				System.out.println("Got It");
				coll.remove(cursor.next());
				break;
			}
			i++;
		}
		
	}
	
	public void registerEvent(Events event){
		if(!db.collectionExists("EventsInfo")){
			coll = db.createCollection("EventsInfo",null);
		}
		else coll = db.getCollection("EventsInfo");
		
		BasicDBObject obj = (BasicDBObject) JSON.parse(new Gson().toJson(event));
	System.out.println("Insdie Server Code");
		coll.insert(obj);
	}
	
	
}

