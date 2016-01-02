package event.impl;

import event.Event;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public class EventAttack extends Event {
	private Entity entity;
	private boolean preAttack;

	public void fire(Entity targetEntity, boolean preAttack) {
		this.entity = targetEntity;
		this.preAttack = preAttack;
		super.fire();
	}

	public Entity getEntity() {
		return entity;
	}

	public boolean isPreAttack() {
		return preAttack;
	}

	public boolean isPostAttack() {
		return !preAttack;
	}
}
