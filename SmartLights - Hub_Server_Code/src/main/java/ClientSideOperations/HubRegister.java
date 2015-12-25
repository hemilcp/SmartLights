package ClientSideOperations;

import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import Models.HubProperties;
import Models.ServerObject;
import database.HubDB;

public class HubRegister {

	public static void main(String args[]){
	List<Object> list =	new HubDB().retrieveHub();
	HubProperties hub = (HubProperties) list.get(0);
	ServerObject server = (ServerObject) list.get(1);
	
	Client client = Client.create();
	WebResource webResource = client
			   .resource("http://localhost:8080/273_Assignment/webapi/project/register/hub");
	MultivaluedMap<String, String> map = new MultivaluedMapImpl();
	map.add("hub", new Gson().toJson(hub));
	map.add("server", new Gson().toJson(server));
	ClientResponse response = webResource.type(MediaType.APPLICATION_FORM_URLENCODED)
			   .post(ClientResponse.class, map);

	}
}
