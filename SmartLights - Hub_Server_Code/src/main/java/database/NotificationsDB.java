package database;

import java.util.Map;

import Models.Notification;

public class NotificationsDB {


	Map<Integer,Notification> map = NotificationDatabase.getNotifs();
	public NotificationsDB(){	}
	
	public Notification updateNotif(Notification notif){
		map.put(1, notif);
		return notif;
	}
	
	public Notification getNotification(){
		return map.get(1);
	}
	
	
}
