package event.impl;

import event.Event;

public class EventChat extends Event {
	private String text;

	public void fire(String text) {
		this.text = text;
		super.fire();
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
