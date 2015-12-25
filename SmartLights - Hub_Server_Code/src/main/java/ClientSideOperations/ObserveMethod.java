package ClientSideOperations;

import services.CheckNotification;

public class ObserveMethod {
	CheckNotification c = new CheckNotification();

	//public static void main(String args[]){
	//ChangeResourceValue c = new ChangeResourceValue();
	public void startObservation(){
	
		c.start();
	}
	
	public void stopObservation() throws InterruptedException{
		System.out.println("Inside Stop Observation");
		c.stopThread();
	}
}
