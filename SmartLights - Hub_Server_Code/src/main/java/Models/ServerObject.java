package Models;

public class ServerObject {

	private String server_Uri;
	private boolean bootstrap_Server;
	private String psk_Key;
	private String aes_Key;
	
	public ServerObject(){}

	public ServerObject(String server_Uri, boolean bootstrap_Server, String psk_Key, String aes_Key) {
		super();
		this.server_Uri = server_Uri;
		this.bootstrap_Server = bootstrap_Server;
		this.psk_Key = psk_Key;
		this.aes_Key = aes_Key;
	}

	public String getServer_Uri() {
		return server_Uri;
	}

	public void setServer_Uri(String server_Uri) {
		this.server_Uri = server_Uri;
	}

	public boolean isBootstrap_Server() {
		return bootstrap_Server;
	}

	public void setBootstrap_Server(boolean bootstrap_Server) {
		this.bootstrap_Server = bootstrap_Server;
	}

	public String getPsk_Key() {
		return psk_Key;
	}

	public void setPsk_Key(String psk_Key) {
		this.psk_Key = psk_Key;
	}

	public String getAes_Key() {
		return aes_Key;
	}

	public void setAes_Key(String aes_Key) {
		this.aes_Key = aes_Key;
	}
	
}
