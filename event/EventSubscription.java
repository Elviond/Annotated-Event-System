package event;

import java.util.ArrayList;
import java.util.List;

/**
 * Keeps track of subscribed listeners for a given assigned event.
 */
public class EventSubscription<T extends Event> {
	private final T event;
	private final List<EventListener> subscribed = new ArrayList<EventListener>();

	public EventSubscription(T event) {
		this.event = event;
	}

	public void fire(Event event) {
		for (EventListener module : subscribed) {
			module.onEvent(event);
		}
	}

	public void add(EventListener listener) {
		subscribed.add(listener);
	}

	public void remove(EventListener listener) {
		if (subscribed.contains(listener)) {
			subscribed.remove(listener);
		}
	}

	public List<EventListener> getSubscribed() {
		return subscribed;
	}

	public Event getEvent() {
		return event;
	}
}
