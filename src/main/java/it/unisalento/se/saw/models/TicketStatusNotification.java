package it.unisalento.se.saw.models;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;

public class TicketStatusNotification implements Notification {
	
	@Override
	public JSONObject getJSONObject(int id, String title, Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		JSONObject msg = new JSONObject();
		msg.put("title", "Nuovo Aggiornamento!");
		msg.put("body", "La tua segnalazione del " + sdf.format(date) + " ha ricevuto un aggiornamento.");
		
	   msg.put("type", "ticket");
	   
	   msg.put("idticket", id);
	   
	   return msg;
	}

}
