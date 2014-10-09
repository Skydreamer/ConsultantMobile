package enums;

public enum ConnectionState {
	OFFLINE("Offline"),
	ONLINE("Online");
	
	private String stringValue;
	
	private ConnectionState (String str)
	{
		stringValue = str;
	}

	@Override
	public String toString() {
		return stringValue;
	}
}
