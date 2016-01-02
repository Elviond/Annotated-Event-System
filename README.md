# Annotated-Event-System
Used for registering classes for event handling, and dispatching events tothose registered classes.
Initialy made for minecraft clients.

## Register an event
Just add an annotation to your method which will be called when the event will be fired
```java
@RegisterEvent(events = { EventExample })
public void onEvent(Event event) {
  //Do something
}
```

You can also call multiple events
```java
@RegisterEvent(events = { EventExample, AnotherEventExample.class })
public void onEvent(Event event) {
  //Do something
}
```

## Event Usage
```java
EventSystem.getInstance(EventExample.class).fire();
```
Or give event-specific data by using casting
```java
((EventExample) EventSystem.getInstance(EventExample.class)).fire(generic_info);
```
An event can be cancelled by using isCancelled() method
```java
if(event.isCancelled()) {
  return;
}

```


####Example for the EventPacket
```java
package net.minecraft.network;

public class NetworkManager extends SimpleChannelInboundHandler {
	public void sendPacket(Packet packet) {
		EventPacket ep = (EventPacket) EventSystem.getInstance(EventPacket.class);
		ep.fire(packet, true);
		if (ep.isCancelled()) {
			return;
		}
		if (channel != null && channel.isOpen()) {
			flushOutboundQueue();
			dispatchPacket(packet, (GenericFutureListener[]) null);
		} else {
			outboundPacketsQueue.add(new NetworkManager.InboundHandlerTuplePacketListener(packet, (GenericFutureListener[]) null));
		}
	}
}
```
