package Models;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Events {
	
		private String serialNum;
		private int eventId;
	    private int lightId;
	    private int lightStatus;
	    private String startDate;
	    private String startTime;
	    private String endDate;
	    private String endTime;
	 
	    
	    public Events(){}

	    public Events(String serialNum,int eventId, int lightId, int lightStatus){
	        super();
	        this.serialNum = serialNum;
	        this.eventId = eventId;
	        this.lightId = lightId;
	        this.lightStatus = lightStatus;
	    }
	    public Events(String serialNum,int eventId, int lightId, int lightStatus, String startDate,String startTime,String endDate, String endTime) {
	        super();
	        this.serialNum = serialNum;
	        this.eventId = eventId;
	        this.lightId = lightId;
	        this.lightStatus = lightStatus;
	        this.startDate = startDate;
	        this.startTime = startTime;
	        this.endDate = endDate;
	        this.endTime = endTime;
	    }

	    public int getEventId() {
	        return eventId;
	    }

	    public void setEventId(int eventId) {
	        this.eventId = eventId;
	    }

	    public int getLightId() {
	        return lightId;
	    }

	    public void setLightId(int lightId) {
	        this.lightId = lightId;
	    }

	    public int getLightStatus(){
	        return this.lightStatus;
	    }

	    public void setLightStatus(int lightStatus){
	        this.lightStatus = lightStatus;
	    }


	    public String getStartDate() {
	        return startDate;
	    }

	    public void setStartDate(String startDate) {
	        this.startDate = startDate;
	    }
	    
	    public String getStartTime() {
	        return startTime;
	    }

	    public void setStartTime(String startTime) {
	        this.startTime = startTime;
	    }

	    public String getEndDate() {
	        return endDate;
	    }

	    public void setEndDate(String endDate) {
	        this.endDate = endDate;
	    }
	    
	    public String getEndTime() {
	        return endTime;
	    }

	    public void setEndTime(String endTime) {
	        this.endTime = endTime;
	    }

}
