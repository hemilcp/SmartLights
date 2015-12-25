package ClientSideOperations;

public class HubDemoMain {

	public static void main(String args[]){
	//	System.exit(0);
		HubRunning hub = new HubRunning();
		HubRunMinute minute = new HubRunMinute();
		hub.start();
		minute.start();
	}
}
