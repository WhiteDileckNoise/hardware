package controls;

public class UIEvent {

	public String id;
	public String value;
	public boolean isActive;
	
	public UIEvent(String id, Object value, boolean isActive) {
		this.id = id;
		this.isActive = isActive;
		
		if (value instanceof Integer) this.value = ""+value;
		else if (value instanceof Boolean) this.value = ""+value;
		else this.value = value.toString();
	}

	public UIEvent(String id, Object value) {
		this(id, value, true);
	}
	
	public int getAsInt() {
		// Try catch NumberFormatException
		return Integer.parseInt(""+value);
	}

	public String getAsString() {
		return ""+ value;
	}

	public boolean getAsBoolean() {
		return Boolean.parseBoolean(""+value);
	}

	public float getAsFloat() {
		return Float.parseFloat(""+value);
	}
	
}
