package Goals;

import java.util.EventObject;

public class DetailEvent extends EventObject{
	
	private static final long serialVersionUID = 162624159042330024L;
	private String text;
	
	public DetailEvent(Object source, String text) {
		super(source);
		this.text = text;
	}
	
	public String getText() {
		return text;
	}
	
}
