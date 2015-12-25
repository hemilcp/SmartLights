package Org.Projects._Assignment;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

import Models.Events;
import database.HubDB;


@Path("/hub")
public class HubResource {
	
	@Path("/eventRegister")
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public String addEventOnHub(@FormParam(value="eventObject") String event){
		new HubDB().registerEvent(new Gson().fromJson(event, Events.class));
		System.out.println("Event registered on HUB!!");
		return "Event registered on HUB!!";
	}
}
