package enums;

public enum ApplicationState {
	ACTIVE("Active"),
	PASSIVE("Passive");
	
	private String stringValue;
	
	private ApplicationState(String str)
	{
		stringValue = str;
	}

	@Override
	public String toString() {
		return stringValue;
	}	
}
