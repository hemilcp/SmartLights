package Models;

public class MobileClientProperties {

	private String client_Name;
	private String serial_Number;
	private String model_Number;
	private String ip_address;
	private String sms_Number;
	
	public MobileClientProperties(){}

	public MobileClientProperties(String client_Name, String serial_Number, String model_Number, String ip_address,
			String sms_Number) {
		super();
		this.client_Name = client_Name;
		this.serial_Number = serial_Number;
		this.model_Number = model_Number;
		this.ip_address = ip_address;
		this.sms_Number = sms_Number;
	}

	public String getClient_Name() {
		return client_Name;
	}

	public void setClient_Name(String client_Name) {
		this.client_Name = client_Name;
	}

	public String getSerial_Number() {
		return serial_Number;
	}

	public void setSerial_Number(String serial_Number) {
		this.serial_Number = serial_Number;
	}

	public String getModel_Number() {
		return model_Number;
	}

	public void setModel_Number(String model_Number) {
		this.model_Number = model_Number;
	}

	public String getIp_address() {
		return ip_address;
	}

	public void setIp_address(String ip_address) {
		this.ip_address = ip_address;
	}

	public String getSms_Number() {
		return sms_Number;
	}

	public void setSms_Number(String sms_Number) {
		this.sms_Number = sms_Number;
	}
	
	
}
