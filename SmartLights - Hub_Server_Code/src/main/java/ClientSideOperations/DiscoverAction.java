package ClientSideOperations;

import java.util.Map;

import org.eclipse.persistence.oxm.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

import database.ServerDB;

public class DiscoverAction {

	public static void main(String agrs[]){
		Client client = Client.create();
		WebResource webResource = client
				   .resource("http://localhost:8080/273_Assignment/webapi/project/discover");
	
		String s = webResource.get(String.class);
		System.out.println(s);
	}
}
