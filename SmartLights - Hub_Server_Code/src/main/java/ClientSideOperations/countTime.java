package ClientSideOperations;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import javax.ws.rs.core.GenericEntity;

import org.bson.types.ObjectId;

import com.google.gson.Gson;

import Models.Events;
import database.HubDB;

public class countTime implements Runnable {

	private Thread t;
	long timeCount, eventDuration;
	ObjectId id;
	Events event;
	boolean alive;
	Date d1, d2;
	int localStatus;
	int localLightId;
	int brightness;

	public void start(long timeCount, long eventDuration, ObjectId id, Events event, Date d1, Date d2) {
		if (t == null) {
			t = new Thread(this);
		}
		this.timeCount = timeCount;
		this.eventDuration = eventDuration;
		this.id = id;
		this.event = event;
		this.d1 = d1;
		this.d2 = d2;
		this.localStatus = event.getLightStatus();
		this.localLightId = event.getLightId();
		this.brightness = new Random().nextInt(10);
		t.start();

	}

	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		try {

			Thread.sleep(timeCount * 1000);
			// killOtherProcesses(id);
			System.out.println("Event"+ event.getEventId()+" Fired for lightId : " + event.getLightId() + "  " + t.getId() + " with Duration "
					+ eventDuration);
		
			//if(event.getEventId()== 3){
			//	new HubDB().setLightStatusBrightness(localLightId, localStatus, brightness, event.getEventId());
			//}else{
				new HubDB().setLightStatus(localLightId, localStatus, event.getEventId());
		//	}
			
			if(event.getEventId() == 4){
				Thread.sleep(eventDuration * 1000);
				for(int i =0 ;i<5;i++){
					String lightStatus = " ON" ;
					System.out.println("Light Id :"+ localLightId +lightStatus);
					Thread.sleep(1000*2);
					lightStatus = " OFF";
					System.out.println("Light Id :"+ localLightId+lightStatus);
				}
				//Thread.sleep(2*1000);
				//new HubDB().changeLightStatus(localLightId);
			}
			
			else{
				
				System.out.println("Light : "+localLightId+" "+localStatus+" for duration of "+eventDuration );
				new HubDB().updateHubRunnigEvent(id, t.getId());
				Thread.sleep(eventDuration * 1000);
				
				new HubDB().deleteInstantEvent(id);
			
			System.out.println("Event Deleted!");
//			if (new HubDB().getLightStatus(localLightId) == localStatus) {
				if (localStatus == 0) {
			//		if(event.getEventId()== 3){
				//		new HubDB().setLightStatusBrightness(localLightId, 1, brightness, event.getEventId());
					//}else{
						new HubDB().setLightStatus(localLightId, 1, event.getEventId());
					//}
				} else {
		//			if(event.getEventId()== 3){
			//			new HubDB().setLightStatusBrightness(localLightId, 0, brightness, event.getEventId());
				//	}else{
						new HubDB().setLightStatus(localLightId, 0, event.getEventId());
					//}
		//		}
			}
			}
			t.interrupt();
		} catch (InterruptedException e) {

		}
	}

	public void killOtherProcesses(ObjectId id) throws ParseException {

		Map<List<ObjectId>, List<Events>> map = new HubDB().retriveEvents();
		List<ObjectId> idList = new ArrayList<>();
		List<Events> eventList = new ArrayList<>();

		Events event = new HubDB().getEvent(id);
		/*
		 * String tempDate = new Gson().toJson(event.getStartDate());
		 * SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm"
		 * ); dateFormat.setLenient(false); Date d1 =
		 * dateFormat.parse(tempDate.substring(1, tempDate.length()-1)); String
		 * tempEndDate = new Gson().toJson(event.getEndDate()); Date d2 =
		 * dateFormat.parse(tempEndDate.substring(1, tempEndDate.length()-1));
		 */
		for (Entry<List<ObjectId>, List<Events>> entry : map.entrySet()) {
			idList = entry.getKey();
			eventList = entry.getValue();
		}

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		for (int i = 0; i < idList.size(); i++) {
			Date sDate = dateFormat
					.parse(eventList.get(i).getStartDate().substring(1, eventList.get(i).getStartDate().length() - 1));
			Date eDate = dateFormat
					.parse(eventList.get(i).getStartDate().substring(1, eventList.get(i).getStartDate().length() - 1));
			if ((d1.getTime() - sDate.getTime()) > 0 && (d1.getTime() - eDate.getTime()) < 0) {
				System.out.println("\nAn Event found to be destroyed : " + idList.get(i));

			}
		}

	}
}
