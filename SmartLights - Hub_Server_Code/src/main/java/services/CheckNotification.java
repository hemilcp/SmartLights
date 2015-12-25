package services;

import Models.Notification;
import database.HubDB;
import database.NotifDB;
import database.NotificationsDB;

public class CheckNotification implements Runnable{

	private Thread t;
	HubDB hub = new HubDB();
	Notification notification = new NotifDB().retriveData();
	long nextNotif = 0;
	static int currValue = 20;
	static int newValue = 0;
	 boolean flag = true;
	public CheckNotification(){}
	
	public void start(){
		if(t==null) t= new Thread(this);
		t.start();
	}

	
	public void run(){
		
//		System.out.println(System.currentTimeMillis() + "   "+notification.getMaxP());
		nextNotif = System.currentTimeMillis()+ (notification.getMaxP()*1000);
//		System.out.println(nextNotif);
		throwNotif();
		try {	Thread.sleep(notification.getMinP()*1000);	}
		catch (InterruptedException e) {	e.printStackTrace();	}
		
		try {  this.changeResource(nextNotif,flag);	} 
		catch (InterruptedException e) { e.printStackTrace();	}
	}
	
	
	public void checkChange(){
		if(newValue < notification.getLesst() || newValue > notification.getGrt() || (newValue - currValue)>= notification.getStep()){
			throwNotif();
		}
	}

	public void throwNotif(){
		System.out.println("Current Resource Value : "+hub.retriveResource());
		currValue = hub.retriveResource();
	//	return hub.retriveResource();
	} 
	
	public void changeResource(long nextNotif,boolean flag) throws InterruptedException{
		if(!flag){
			t.interrupt();
			}
	//	System.out.println("Inside Change Resource ");
	//	System.out.println(System.currentTimeMillis());
		while(System.currentTimeMillis()<nextNotif && flag){
			if(!flag) t.interrupt();
	//		System.out.println("inside while" + "    "+flag);
			newValue = (int) (Math.random()*21 + 10);
			System.out.println("New Value : "+newValue);
			hub.updateResource(newValue);
			checkChange();
			Thread.sleep(5000);
		}
		nextNotif = System.currentTimeMillis()+notification.getMaxP()*1000;
		throwNotif();
		Thread.sleep(notification.getMinP()*1000);
		changeResource(nextNotif,flag);
	}
	
	
	public void stopThread() throws InterruptedException{
	
		System.out.println("Inside Stop Thread");
		System.exit(0);
	}
}
