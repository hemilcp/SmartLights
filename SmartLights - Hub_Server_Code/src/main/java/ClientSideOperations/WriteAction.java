
package ClientSideOperations;

import java.util.Scanner;

import database.ServerDB;

public class WriteAction {

	public static void main(String args[]){
		System.out.println("Enter Object : ");
		Scanner scanner = new Scanner (System.in);
		int objectId = scanner.nextInt();
		System.out.println("Object Instance : ");
		int objectInstance = scanner.nextInt();
		System.out.println("ResourceId : ");
		String resourceId = scanner.next();
		System.out.println("Resource Value : ");
		String resourceValue = scanner.next();
		scanner.close();
		
		new ServerDB().writeMethod(objectId, objectInstance, resourceId, resourceValue);
	}
}
