package event.impl;

import event.Event;

public class EventMotion extends Event {
	private boolean isPre;

	public void fire(boolean isPre) {
		this.isPre = isPre;
		super.fire();
	}

	public boolean isPre() {
		return isPre;
	}

	public boolean isPost() {
		return !isPre;
	}
}
