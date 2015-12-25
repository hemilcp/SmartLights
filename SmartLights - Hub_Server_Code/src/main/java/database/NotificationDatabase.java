package database;

import java.util.HashMap;
import java.util.Map;

import Models.Notification;

public class NotificationDatabase {

	private static Map<Integer,Notification> map = new HashMap<Integer,Notification>();

	public static Map<Integer,Notification> getNotifs(){
		return map;
	}
}
