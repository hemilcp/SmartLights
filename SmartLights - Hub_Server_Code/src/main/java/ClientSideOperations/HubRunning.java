package ClientSideOperations;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bson.types.ObjectId;

import com.google.gson.Gson;

import Models.Events;
import database.HubDB;

public class HubRunning implements Runnable {
		static Thread t;
		public void start(){
			if (t==null){
				t = new Thread(this);
			}
			t.start();
		}
	@Override
	public void run() {
			while(true){
				try {	Thread.sleep(2*1000);
						performEventList();
				}
				catch (InterruptedException | ParseException e) {	e.printStackTrace();	}
			}
		}
	public void performEventList() throws ParseException, InterruptedException{
	
		Map<List<ObjectId>,List<Events>> map = new HubDB().retrieveInstantEvent();
		List<ObjectId> idList = new ArrayList<>();
		List<Events> eventList = new ArrayList<>();
	
		for(Entry<List<ObjectId>, List<Events>> entry : map.entrySet()){
			idList = entry.getKey();
			eventList = entry.getValue();
		}
	
		for(int i =0;i<idList.size();i++){
			switch(eventList.get(i).getEventId()){
			case 1 :	System.out.println("Event 1 performing!");
						System.out.println("_ID :"+idList.get(i)+"  "+new Gson().toJson(eventList.get(i)));
						
						new HubDB().setLightStatus(eventList.get(i).getLightId(), eventList.get(i).getLightStatus(), 1);
						new HubDB().deleteInstantEvent(idList.get(i));
						break;
			case 4 : 	System.out.println("Event 4 Performing! with endTime: "+eventList.get(i).getEndTime());
						System.out.println("_ID :"+idList.get(i)+"  "+new Gson().toJson(eventList.get(i)));
						String tempEndDate = new Gson().toJson(eventList.get(i).getEndDate());
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
						Date d1 = new Date();
						Date d2 = dateFormat.parse(tempEndDate.substring(1, tempEndDate.length()-1));
					//	long duration = ((System.currentTimeMillis()/1000)+(System.currentTimeMillis()/60000-d2.getMinutes()))/1000;
						long duration = (System.currentTimeMillis()-d2.getTime())/1000;	
						System.out.println("Duration event 4 *:"+duration);
						new countTime().start(0,duration,idList.get(i),eventList.get(i),d1,d2);

					//	Thread.sleep(3000);
						new HubDB().deleteInstantEvent(idList.get(i));

						
						break;
			}
		}
	}
}	


class HubRunMinute implements Runnable{

	Thread t;

	public void start(){
		if (t==null){
			t = new Thread(this);
		}
		t.start();
	}
	@Override
	public void run() {
				while(true){
					try {	
						System.out.println("\nNew Cycle");
						performEventMinute();
						new HubDB().lightUsage();
						Thread.sleep(60*1000);	
							
							}
					 catch (InterruptedException | ParseException e) {	e.printStackTrace();	}
				}
	}
	
	public void performEventMinute() throws ParseException{
		Map<List<ObjectId>,List<Events>> map = new HubDB().retriveEvents();
		List<ObjectId> idList = new ArrayList<>();
		List<Events> eventList = new ArrayList<>();
	
		for(Entry<List<ObjectId>, List<Events>> entry : map.entrySet()){
			idList = entry.getKey();
			eventList = entry.getValue();
		}
		for(int i =0;i<idList.size();i++){
		
			
			System.out.println("_ID :"+idList.get(i)+"  "+new Gson().toJson(eventList.get(i)));
				String tempDate = new Gson().toJson(eventList.get(i).getStartDate());
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
				dateFormat.setLenient(false);
				Date d1 = dateFormat.parse(tempDate.substring(1, tempDate.length()-1));
	
				long secRemaining = (d1.getTime() - new Date().getTime())/1000;
				System.out.println("Time left : "+(d1.getTime() - new Date().getTime())/1000);
		
				String tempEndDate = new Gson().toJson(eventList.get(i).getEndDate());
				Date d2 = dateFormat.parse(tempEndDate.substring(1, tempEndDate.length()-1));
				long eventDuration = (d2.getTime()-d1.getTime())/1000;
				System.out.println("Event Duration : "+eventDuration);
				
				
				if(d2.getTime()<new Date().getTime()){
					new HubDB().deleteInstantEvent(idList.get(i));
				}
				
				if(secRemaining<=60 && secRemaining >0){
					checkStatus(eventList.get(i).getLightId());
					new countTime().start(secRemaining,eventDuration,idList.get(i),eventList.get(i),d1,d2);
				}
			}				
	}
	
	public void checkStatus(int lightId){
		 new HubDB().checkStatus(lightId);
	//	System.out.println("CHeck Status : "+value);
			
	}
	
}