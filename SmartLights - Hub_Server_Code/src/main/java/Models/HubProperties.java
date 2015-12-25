package Models;

public class HubProperties {

	private String serial_Number;
	private String model_Number;
	private String firmware_Version;
	private String ip_Address;
	private long lifetime;
	private String lwm2m_Number;
	private String binding_Mode;

	public HubProperties(){}

	public HubProperties(String serial_Number, String model_Number, String firmware_Version, String ip_Address,
			long lifetime, String lwm2m_Number, String binding_Mode) {
		super();
		this.serial_Number = serial_Number;
		this.model_Number = model_Number;
		this.firmware_Version = firmware_Version;
		this.ip_Address = ip_Address;
		this.lifetime = lifetime;
		this.lwm2m_Number = lwm2m_Number;
		this.binding_Mode = binding_Mode;
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

	public String getFirmware_Version() {
		return firmware_Version;
	}

	public void setFirmware_Version(String firmware_Version) {
		this.firmware_Version = firmware_Version;
	}

	public String getIp_Address() {
		return ip_Address;
	}

	public void setIp_Address(String ip_Address) {
		this.ip_Address = ip_Address;
	}

	public long getLifetime() {
		return lifetime;
	}

	public void setLifetime(long lifetime) {
		this.lifetime = lifetime;
	}

	public String getLwm2m_Number() {
		return lwm2m_Number;
	}

	public void setLwm2m_Number(String lwm2m_Number) {
		this.lwm2m_Number = lwm2m_Number;
	}

	public String getBinding_Mode() {
		return binding_Mode;
	}

	public void setBinding_Mode(String binding_Mode) {
		this.binding_Mode = binding_Mode;
	}
	
	
}
