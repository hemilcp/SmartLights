package database;

import java.net.UnknownHostException;
import org.bson.types.ObjectId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;

import Models.Events;
import Models.HubProperties;
import Models.ServerObject;

public class HubDB {

	MongoClient mongoClient;
	DB db;
	DBCollection coll;
	
	public HubDB(){
		try {
			mongoClient = new MongoClient("localhost",27017);
			db = mongoClient.getDB("HubDB");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	public void saveHubInfo(HubProperties hub, ServerObject server){
		if(!db.collectionExists("HubDeviceInfo")){
			BasicDBObject options = new BasicDBObject().append("capped", true).append("size", 100).append("max", 1);
			coll = db.createCollection("HubDeviceInfo", options);
		}
		else coll = db.getCollection("HubDeviceInfo");
		
		Gson gson = new Gson();
		BasicDBObject obj = (BasicDBObject) JSON.parse(gson.toJson(hub));
		obj.put("server",(BasicDBObject) JSON.parse(gson.toJson(server))); 
		coll.insert(obj);
	}
	
	public List<Object> retrieveHub(){
		List<Object> list = new ArrayList<Object>();
		
		coll = db.getCollection("HubDeviceInfo");
		DBObject cursor = coll.findOne();
		HubProperties hub = new Gson().fromJson(cursor.toString(), HubProperties.class);
		ServerObject server = new Gson().fromJson(cursor.get("server").toString(), ServerObject.class);
		
		list.add(hub);
		list.add(server);
		return list;
	}
	
	public int retriveLifetime(){
		coll = db.getCollection("HubDeviceInfo");
		DBObject cursor = coll.findOne();
		return (int) cursor.get("lifetime");
	}
	
	public void updateLifetime(long lifetime){
		coll = db.getCollection("HubDeviceInfo");
		DBObject cursor = coll.findOne();
		System.out.println(cursor.toString());
		BasicDBObject newDoc = new BasicDBObject();
		newDoc.append("$set", new BasicDBObject().append("lifetime", lifetime));
		coll.update(cursor, newDoc);
	}
	
	public void updateResource(int resourceValue){
		if(!db.collectionExists("HubResourceInfo")){
			coll = db.createCollection("HubResourceInfo",null);
			BasicDBObject obj = new BasicDBObject().append("resource", 10);
			coll.insert(obj);
		}
		else coll = db.getCollection("HubResourceInfo");
		
		DBObject cursor = coll.findOne();
		BasicDBObject newDoc = new BasicDBObject();
		newDoc.append("$set", new BasicDBObject().append("resource", resourceValue));
		coll.update(cursor, newDoc);
		
	}
	
	public int retriveResource(){
		coll = db.getCollection("HubResourceInfo");
		DBObject cursor = coll.findOne();
		return (int)cursor.get("resource");
	}
	
	public void registerEvent(Events event){
		if(!db.collectionExists("EventsInfo")){
			coll = db.createCollection("EventsInfo",null);
		}
		else coll = db.getCollection("EventsInfo");
		
		BasicDBObject obj = (BasicDBObject) JSON.parse(new Gson().toJson(event));
		System.out.println("Inside HUBDB!!!!!!!*************");
		coll.insert(obj);
		
	}
	
	public Map<List<ObjectId>,List<Events>> retriveEvents(){
		Map<List<ObjectId>,List<Events>> map = new HashMap<List<ObjectId>,List<Events>>();
		List<ObjectId> idList = new ArrayList<>();
		List<Events> eventList = new ArrayList<>();
		
		BasicDBObject query = new BasicDBObject();
		List<Integer> list = new ArrayList<>();
		list.add(2);
		list.add(3);
		query.put("eventId", new BasicDBObject("$in",list));
		coll = db.getCollection("EventsInfo");
		DBCursor cursor = coll.find( query );
		cursor.sort(new BasicDBObject("startDate", 1).append("startTime", 1));
		
		while(cursor.hasNext()){
			if((Integer.parseInt(cursor.next().get("eventId").toString())!=4)){
			idList.add((ObjectId) cursor.curr().get("_id"));
			eventList.add(new Gson().fromJson(cursor.curr().toString(), Events.class));
			}
		}
		map.put(idList,eventList);
		return map;
	}
	
	public Map<List<ObjectId>,List<Events>> retrieveInstantEvent(){
		Map<List<ObjectId>,List<Events>> map = new HashMap<List<ObjectId>,List<Events>>();
		List<ObjectId> idList = new ArrayList<>();
		List<Events> eventList = new ArrayList<>();
		coll = db.getCollection("EventsInfo");
		
		BasicDBObject query = new BasicDBObject();
		List<Integer> list = new ArrayList<>();
		list.add(1);
		list.add(4);
		query.put("eventId", new BasicDBObject("$in",list));
		DBCursor cursor = coll.find( query );
		
		while(cursor.hasNext()){
			idList.add((ObjectId) cursor.next().get("_id"));
			eventList.add(new Gson().fromJson(cursor.curr().toString(), Events.class));
			
		}	
		map.put(idList, eventList);
		return map;
	}
	
	
	public int getLightStatus(int lightId){
		
		coll= db.getCollection("LightInfo");
		BasicDBObject obj = new BasicDBObject();
		obj.put("lightId", lightId);
		DBObject response = coll.findOne(obj);
		return ((Number)response.get("lightStatus")).intValue();
	}
	
	public void setLightStatus(int lightId, int lightStatus,int eventId){
			if(!(db.collectionExists("LightInfo"))){
				coll = db.createCollection("LightInfo", null);
			}else
		coll = db.getCollection("LightInfo");
		BasicDBObject fObj = new BasicDBObject("lightId", lightId);
		BasicDBObject obj = new BasicDBObject();
	//	obj.put("lightId", lightId);
		obj.append("$set", new BasicDBObject("lightStatus", lightStatus));
		
		coll.update(fObj,obj);
		updateEvent(lightId, eventId);
		
		//	coll.remove(fObj);
	//	coll.insert(obj);
	}
	
	public void changeLightStatus(int lightId){
		
		getLightStatus(lightId);
		if(lightId == 1){
			setLightStatus(lightId, 0, 4);
		}else {
			setLightStatus(lightId, 1, 4);
		}
		
	}
	
	public void setLightStatusBrightness(int lightId, int lightStatus,int brightness, int eventId){

		coll = db.getCollection("LightInfo");
		BasicDBObject fObj = new BasicDBObject("lightId", lightId);
		BasicDBObject obj = new BasicDBObject();
	//	obj.put("lightId", lightId);
		obj.append("$set", new BasicDBObject("lightStatus", lightStatus));
		obj.append("brightness", brightness);
		obj.append("byEvent", eventId);
		coll.update(fObj,obj);
	//	coll.remove(fObj);
	//	coll.insert(obj);
	}

	
	public int getLightOnMinutes(int lightId){
		
		coll= db.getCollection("LightInfo");
		BasicDBObject obj = new BasicDBObject();
		obj.put("lightId", lightId);
		DBObject response = coll.findOne(obj);
		return (int) ((Number)response.get("onMinutes")).intValue() ;
	}
	
	public void updateEvent(int lightId,int eventId){
		
		coll = db.getCollection("LightInfo");
		BasicDBObject fObj = new BasicDBObject("lightId", lightId);
		BasicDBObject objj = new BasicDBObject("$set", new BasicDBObject().append("byEvent", eventId));
		coll.update(fObj, objj);
		
	}
	
	public void updateMinutes(int lightId, int minutes, int brightness ){
		
		coll= db.getCollection("LightInfo");
		BasicDBObject obj = new BasicDBObject("lightId", lightId);
		BasicDBObject uObj = new BasicDBObject().append("$set", new BasicDBObject("onMinutes", minutes));
		coll.update(obj, uObj);
		updateBrightness(lightId, brightness);
	}
	
	public void updateBrightness(int lightId,int brightness){
		
		coll= db.getCollection("LightInfo");
		BasicDBObject obj = new BasicDBObject("lightId", lightId);
		BasicDBObject uObj = new BasicDBObject().append("$set", new BasicDBObject("brightness",brightness));
		coll.update(obj, uObj);
	}
	
	public int getBrightness(int lightId){
		
		int brightness,sensorValue;
		coll= db.getCollection("LightInfo");
		BasicDBObject obj = new BasicDBObject();
		obj.put("lightId", lightId);
		DBObject response = coll.findOne(obj);
		if(((Number)response.get("byEvent")).intValue() == 3 ){
			
			return (int)((Number)response.get("brightness")).intValue();
		}else{
		sensorValue = new Random().nextInt(10);
		brightness = 11- sensorValue;
		return brightness;
		}
	}
	
	public void lightUsage(){
		int onMinutes;
		if(getLightStatus(1)==1){
			onMinutes= getLightOnMinutes(1)+1;
			updateMinutes(1, onMinutes,getBrightness(1));
			
		}
		if(getLightStatus(2)==1){
			onMinutes= getLightOnMinutes(2)+1;
			updateMinutes(2, onMinutes,getBrightness(2));
			
		}
	}
	
	public void deleteInstantEvent(ObjectId id){
		coll = db.getCollection("EventsInfo");
		BasicDBObject doc = new BasicDBObject();
		doc.put("_id",id);
		coll.remove(doc);
	}
	
	public void updateHubRunnigEvent(ObjectId id,long threadId){
		 coll = db.getCollection("EventsInfo");
		 BasicDBObject obj = new BasicDBObject();
		 obj.put("_id", id);
		 DBObject dbObject = coll.findOne(obj);
		 coll.update(dbObject, new BasicDBObject().append("$set", new BasicDBObject().append("threadId",threadId).append("threadStatus","running")));
	
	}
	
	public void checkStatus(int lightid){
		coll = db.getCollection("EventsInfo");
		BasicDBObject obj = new BasicDBObject();
		 obj.put("lightId", lightid);
		 DBCursor cursor = coll.find(obj);
		 
		 while(cursor.hasNext()){
			 if((cursor.next().get("threadStatus"))!=null){
			 if(cursor.curr().get("threadStatus").toString().equals("running")){
				 System.out.println("Ruuning thread Found : "+cursor.curr().get("threadId"));
			
				 Set<Thread> setOfThread = Thread.getAllStackTraces().keySet();
		           System.out.println("Thread to interrupt :"+Long.parseLong(cursor.curr().get("threadId").toString()));

				    //Iterate over set to find yours
				    for(Thread thread : setOfThread){
				        if(thread.getId()==Long.parseLong(cursor.curr().get("threadId").toString())){
				           System.out.println("Thread to interrupt :"+Long.parseLong(cursor.curr().get("threadId").toString()));
				        	thread.interrupt();
				            coll.remove(cursor.curr());
				            System.out.println("Deleted Thread !!");
				        }
				    }
			 	}
			 }
	
		 }
	}
	
	public Events getEvent(ObjectId id){
		coll = db.getCollection("EventsInfo");
		BasicDBObject obj = new BasicDBObject();
		 obj.put("_id", id);
		 DBObject cursor = coll.findOne(obj);
		 return (Events) new Gson().fromJson(cursor.toString(), Events.class);
	}
}
