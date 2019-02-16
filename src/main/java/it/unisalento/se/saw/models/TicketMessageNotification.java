package it.unisalento.se.saw.models;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;

public class TicketMessageNotification implements Notification {
	
	@Override
	public JSONObject getJSONObject(int id, String title, Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		JSONObject msg = new JSONObject();
		msg.put("title", "Nuovo Messaggio!");
		msg.put("body", "Hai ricevuto un nuovo Messaggio per la tua segnalazione del " + sdf.format(date));
		
	   msg.put("type", "ticket");
	   
	   msg.put("idticket", id);
	   
	   return msg;
	}

}
