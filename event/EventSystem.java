package event;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import event.impl.EventAttack;
import event.impl.EventBlockBounds;
import event.impl.EventChat;
import event.impl.EventDeath;
import event.impl.EventMotion;
import event.impl.EventMouse;
import event.impl.EventPacket;
import event.impl.EventRender3D;
import event.impl.EventRenderGui;
import event.impl.EventScreenDisplay;
import event.impl.EventTick;
import event.impl.EventVelocity;

/**
 * Used for registering classes for event handling, and dispatching events to
 * those registered classes.<br>
 * <br>
 * Event usage:
 *
 * <pre>
 * EventSystem.getInstance(EventExample.class).fire();
 * // Or give event-specific data by using casting
 * ((EventExample) EventSystem.getInstance(EventExample.class)).fire(generic_info);
 * </pre>
 */
public class EventSystem {
	private static final HashMap<Event, EventSubscription> registry = new HashMap<Event, EventSubscription>();
	private static final HashMap<Class, Event> instances = new HashMap<Class, Event>();

	/**
	 * Sets up the instances map.
	 */
	static {
		EventSystem.instances.put(EventTick.class, new EventTick());
		EventSystem.instances.put(EventDeath.class, new EventDeath());
		EventSystem.instances.put(EventMouse.class, new EventMouse());
		EventSystem.instances.put(EventRender3D.class, new EventRender3D());
		EventSystem.instances.put(EventRenderGui.class, new EventRenderGui());
		EventSystem.instances.put(EventScreenDisplay.class, new EventScreenDisplay());
		EventSystem.instances.put(EventAttack.class, new EventAttack());
		EventSystem.instances.put(EventPacket.class, new EventPacket());
		EventSystem.instances.put(EventVelocity.class, new EventVelocity());
		EventSystem.instances.put(EventMotion.class, new EventMotion());
		EventSystem.instances.put(EventChat.class, new EventChat());
		EventSystem.instances.put(EventBlockBounds.class, new EventBlockBounds());
	}

	/**
	 * Registers a listener for event handling.
	 *
	 * @param listener
	 */
	public static void register(EventListener listener) {
		List<Event> events = EventSystem.getEvents(listener);
		for (Event event : events) {
			if (EventSystem.isEventRegistered(event)) {
				EventSubscription subscription = EventSystem.registry.get(event);
				subscription.add(listener);
			} else {
				EventSubscription subscription = new EventSubscription(event);
				subscription.add(listener);
				EventSystem.registry.put(event, subscription);
			}
		}
	}

	/**
	 * Unregisters a listener for event handling.
	 *
	 * @param listener
	 */
	public static void unregister(EventListener listener) {
		List<Event> events = EventSystem.getEvents(listener);
		for (Event event : events) {
			if (EventSystem.isEventRegistered(event)) {
				EventSubscription sub = EventSystem.registry.get(event);
				sub.remove(listener);
			}
		}
	}

	/**
	 * Fires an event. The event is sent to every registered listener that has
	 * requested it via an @RegisterEvent annotation.
	 *
	 * @param event
	 * @return
	 */
	public static Event fire(Event event) {
		EventSubscription subscription = EventSystem.registry.get(event);
		if (subscription != null) {
			subscription.fire(event);
		}
		return event;
	}

	/**
	 * Retrieves an instance of an event given its class.
	 *
	 * @param eventClass
	 * @return
	 */
	public static Event getInstance(Class eventClass) {
		return EventSystem.instances.get(eventClass);
	}

	/**
	 * Gets the events requested by a listener.
	 *
	 * @param listener
	 * @return
	 */
	private static List<Event> getEvents(EventListener listener) {
		ArrayList<Event> events = new ArrayList<Event>();
		for (Method method : listener.getClass().getDeclaredMethods()) {
			if (!method.isAnnotationPresent(RegisterEvent.class)) {
				continue;
			}
			RegisterEvent ireg = method.getAnnotation(RegisterEvent.class);
			for (Class eventClass : ireg.events()) {
				events.add(EventSystem.getInstance(eventClass));
			}
		}
		return events;
	}

	/**
	 * Checks if the event is in the registry.
	 *
	 * @param event
	 * @return
	 */
	private static boolean isEventRegistered(Event event) {
		return EventSystem.registry.containsKey(event);
	}
}
