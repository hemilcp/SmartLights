package services;

import database.HubDB;

public class ChangeResourceValue implements Runnable{

	private Thread t;
	HubDB hub = new HubDB();
	
	public ChangeResourceValue(){}
	
	public void start(){
		if (t==null) t = new Thread(this);
		t.start();
	}
	
	@SuppressWarnings("deprecation")
	public void run(){
		while(true){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
				int newValue = (int) (Math.random()*21);
				System.out.print(newValue);
				hub.updateResource(newValue);
				Thread.currentThread().stop();
		}
		
	}
}
