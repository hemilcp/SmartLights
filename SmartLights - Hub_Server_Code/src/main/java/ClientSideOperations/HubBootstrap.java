package ClientSideOperations;

import Models.HubProperties;
import Models.ServerObject;
import database.HubDB;

public class HubBootstrap {

	public static void main(String args[]){
	HubProperties hub = new HubProperties();
	hub.setSerial_Number("GHTYJJJBBJJ");
	hub.setBinding_Mode("RQ");
	hub.setFirmware_Version("1.0.2");
	hub.setIp_Address("128.10.67.108");
	hub.setLifetime(3600);
	hub.setLwm2m_Number("1.1");
	hub.setModel_Number("N560");
	
	ServerObject server = new ServerObject();
	server.setBootstrap_Server(false);
	server.setServer_Uri("localhost");
	server.setAes_Key("345605054A429998BF83263A807BG87H");
	server.setPsk_Key("c0de573f34dec6c5cec37d8eaf305df9055341074211b2725bd03d5e8ab8e027");
	new HubDB().saveHubInfo(hub,server);
	}
}
