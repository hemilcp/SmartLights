package services;
import static java.util.concurrent.TimeUnit.*;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import Models.Notification;
import database.NotificationsDB;

public class Scheduler {

	Notification notification = new NotificationsDB().getNotification();
	
	private final ScheduledExecutorService scheduler =
		       Executors.newScheduledThreadPool(1);
	
	 public void beepForAnHour() {
	        final Runnable beeper = new Runnable() {
	                public void run() { 
	                	System.out.println("beep"); }
	            };
	            
	        final ScheduledFuture<?> beeperHandle =
	            scheduler.scheduleAtFixedRate(beeper, notification.getMinP(), 10, SECONDS);
	        scheduler.schedule(new Runnable() {
	                public void run() { beeperHandle.cancel(true); }
	            }, 60 * 60, SECONDS);
	    }
}
