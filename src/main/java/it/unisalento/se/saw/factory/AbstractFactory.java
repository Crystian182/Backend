package it.unisalento.se.saw.factory;

import it.unisalento.se.saw.models.Notification;

public abstract class AbstractFactory {
	
	public abstract Notification getNotification(String type);

}
