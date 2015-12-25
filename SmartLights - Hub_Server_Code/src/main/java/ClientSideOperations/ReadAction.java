package ClientSideOperations;

import java.util.Scanner;

import javax.ws.rs.core.MultivaluedMap;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class ReadAction {

	public static void main(String args[]){
		
		System.out.println("Enter Object Id : ");
		Scanner scanner = new Scanner(System.in);
		String objectId = scanner.next();
		System.out.println("Enter Object Instance : ");
		String objectInstance = scanner.next();
		System.out.println("Enter Resource : ");
		String resourceId = scanner.next();
		scanner.close();
		Client client = Client.create();
		WebResource webResource = client
				   .resource("http://localhost:8080/273_Assignment/webapi/project/read");
		
		MultivaluedMap<String, String> map = new MultivaluedMapImpl();
		map.add("objectId", objectId);
		map.add("objectInstance", objectInstance);
		map.add("resourceId", resourceId);
	
		ClientResponse response = webResource.queryParams(map).get(ClientResponse.class);

		System.out.println(response.getEntity(String.class));
	}
}
