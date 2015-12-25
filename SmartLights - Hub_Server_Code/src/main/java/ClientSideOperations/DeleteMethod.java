package ClientSideOperations;

import java.util.Scanner;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class DeleteMethod {

	public static void main(String args[]){
		System.out.println("Enter Object Id : ");
		Scanner scanner = new Scanner(System.in);
		String objectId = scanner.next();
		
		scanner.close();
		Client client = Client.create();
		WebResource webResource = client
				   .resource("http://localhost:8080/273_Assignment/webapi/project/delete");
		
		MultivaluedMap<String, String> map = new MultivaluedMapImpl();
		map.add("objectId", objectId);
	
		
//ClientResponse response =  webResource.accept(MediaType.APPLICATION_FORM_URLENCODED).delete(ClientResponse.class);
		ClientResponse response =  webResource.queryParam("objectId", objectId).delete(ClientResponse.class);
	
	}
	
	
}
