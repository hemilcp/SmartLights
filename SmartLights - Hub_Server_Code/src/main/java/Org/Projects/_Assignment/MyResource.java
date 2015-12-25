package Org.Projects._Assignment;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import ClientSideOperations.ObserveMethod;
import Models.HubProperties;
import Models.Notification;
import Models.ServerObject;
import database.HubDB;
import database.NotifDB;
import database.NotificationsDB;
import database.ServerDB;
import Models.ClientPOJO;
import Models.Events;

@Path("project")
public class MyResource {

	ObserveMethod observe = new ObserveMethod();
	NotificationsDB notification = new NotificationsDB();
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return "Got it!";
    }
    
    @Path("/read")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String readResource(@QueryParam("objectId") String objectId,@QueryParam("objectInstance") String objectInstance,@QueryParam("resourceId") String resourceId){
    
    	System.out.println("Inside MyResource");
    	String returnValue = new ServerDB().readResource(objectId,objectInstance, resourceId);
    
    return returnValue;
    }
    
    @Path("/register/hub")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public void registerHub(@FormParam(value="hub") String hub, @FormParam(value="server") String server){
  
    	new ServerDB().registerHub(new Gson().fromJson(hub, HubProperties.class), new Gson().fromJson(server, ServerObject.class));

    }
    
    @Path("/discover")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
//    public Map<Object,Object> discoverMethod(){
    public String discoverMethod(){
    	Map<String,String> map = new ServerDB().discoverMethod();
    	System.out.println(map.toString());
    	return map.toString();
    }
    
    @Path("/writeAttr/put")
    @Consumes(MediaType.APPLICATION_JSON)
    @PUT
   public void writeAttributes(Notification notif){
    //	return  notification.updateNotif(notif);
    	new NotifDB().saveData(notif);
    }
    
    @Path("/writeAttr/get")
    @Consumes(MediaType.APPLICATION_JSON)
    @GET
    public Notification getNotification(){
    //	return notification.getNotification();
    	return new NotifDB().retriveData();
    }
    
    @Path("/observe/on")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public void startObservation(){
    	observe.startObservation();
    }
    
    @Path("/observe/off")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String cancelObeservation() throws InterruptedException{
    	observe.stopObservation();
    	return "Thread Stopped !!";
    }
    
    @Path("/delete")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String deleteMethod(@QueryParam("objectId") int objectId){
    	System.out.println("INside delete resourcec");
    	new ServerDB().deleteResourcec(objectId);
    	return "Deleted";
    }
    
    @Path("/testPost")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String testPost(@FormParam(value="ClientName") String clientName,
    		@FormParam(value="SerialNumber") String serialNumber,
    		@FormParam(value="ipAddress") String ipAddress,
    		@FormParam(value="SMSNumber") String SMSNumber){
    	System.out.println(""+clientName+" "+serialNumber+" "+ipAddress+" "+SMSNumber);
    
    	ClientPOJO client = new ClientPOJO();
    	client.setClientName(clientName);
    	client.setSerialNumber(serialNumber);
    	client.setIpAddress(ipAddress);
    	client.setSmsNumber(SMSNumber);
    	
    	new ServerDB().registerMobile(client);
    	
    	return clientName+" "+serialNumber+" "+ipAddress+" "+SMSNumber;
    }
    
   @Path("/registerEvent") 
   @POST
   @Produces(MediaType.APPLICATION_JSON)
   @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
   public String registerEvent(@FormParam(value="serialNum") String serialNum,
		   @FormParam(value="eventId") String eventId,
    		@FormParam(value="lightId") String lightId,
    		@FormParam(value="lightStatus") String lightStatus){
	   
	   Events event = new Events(serialNum,Integer.parseInt(eventId),Integer.parseInt(lightId),Integer.parseInt(lightStatus));
	   
	   new ServerDB().registerEvent(event);
	
	   Client client = Client.create();
		WebResource webResource = client
				   .resource("http://localhost:8080/273_Assignment/webapi/hub/eventRegister");
		
		MultivaluedMap<String, String> map = new MultivaluedMapImpl();
		map.add("eventObject", new Gson().toJson(event));
		ClientResponse response = webResource.type(MediaType.APPLICATION_FORM_URLENCODED)
				   .post(ClientResponse.class, map);
	   System.out.println(response.getStatus()+"   "+response.getEntity(String.class));
			
	   return "Event added !!!";
   }
   
   @Path("/registerTimeLineEvent") 
   @POST
   @Produces(MediaType.APPLICATION_JSON)
   @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
   public String registerTimeLineEvent(@FormParam(value="serialNum") String serialNum,
		   @FormParam(value="eventId") String eventId,
    		@FormParam(value="lightId") String lightId,
    		@FormParam(value="lightStatus") String lightStatus,
    		@FormParam(value="StartDate") String sDate,
    		@FormParam(value="StartTime") String sTime,
    		@FormParam(value="EndDate") String eDate,
    		@FormParam(value="EndTime") String eTime) throws NumberFormatException, ParseException{
	   
	   SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
	   SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
	   
	   Events event = new Events(serialNum,Integer.parseInt(eventId),Integer.parseInt(lightId),Integer.parseInt(lightStatus),sDate,sTime,eDate,eTime);;
	
	//   System.out.println(dateFormat.parseObject(sDate)+" "+sTime+"  "+dateFormat.parse(eDate)+" "+eTime);
	  System.out.println("Registering serverside");
	   new ServerDB().registerEvent(event);
	   
	   Client client = Client.create();
		WebResource webResource = client
				   .resource("http://localhost:8080/273_Assignment/webapi/hub/eventRegister");
		
		MultivaluedMap<String, String> map = new MultivaluedMapImpl();
		map.add("eventObject", new Gson().toJson(event));
		ClientResponse response = webResource.type(MediaType.APPLICATION_FORM_URLENCODED)
				   .post(ClientResponse.class, map);
	   System.out.println(response.getStatus()+"   "+response.getEntity(String.class));
	   return "Event added !!!";
   }
   
   @Path("/usageInfo")
   @GET
   @Produces(MediaType.APPLICATION_JSON)
   public String showUsage(){
	   
	  int light1 =  new HubDB().getLightOnMinutes(1);
	  int light2 = new HubDB().getLightOnMinutes(2);
	  
	  return (""+light1+" "+light2);
	   
	   
   }
   
   
   
   
   
}
