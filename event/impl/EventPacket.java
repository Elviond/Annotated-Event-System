package event.impl;

import event.Event;

public class EventPacket extends Event {
	private Packet packet;
	private boolean outgoing;

	public void fire(Packet packet, boolean outgoing) {
		this.packet = packet;
		this.outgoing = outgoing;
		super.fire();
	}

	public Packet getPacket() {
		return packet;
	}

	public boolean isOutgoing() {
		return outgoing;
	}

	public boolean isIncoming() {
		return !outgoing;
	}
}
