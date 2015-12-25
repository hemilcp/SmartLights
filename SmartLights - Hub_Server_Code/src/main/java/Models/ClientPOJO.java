package Models;

public class ClientPOJO {

	private String clientName;
	private String serialNumber;
	private String ipAddress;
	private String smsNumber;

	public ClientPOJO(){}
	
	public ClientPOJO(String clientName, String serialNumber, String ipAddress, String smsNumber) {
		super();
		this.clientName = clientName;
		this.serialNumber = serialNumber;
		this.ipAddress = ipAddress;
		this.smsNumber = smsNumber;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getSmsNumber() {
		return smsNumber;
	}

	public void setSmsNumber(String smsNumber) {
		this.smsNumber = smsNumber;
	}

}
