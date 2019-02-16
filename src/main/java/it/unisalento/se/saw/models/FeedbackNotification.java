package it.unisalento.se.saw.models;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;

public class FeedbackNotification implements Notification {

	@Override
	public JSONObject getJSONObject(int id, String title, Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		JSONObject msg = new JSONObject();
		msg.put("title", "Nuovo Feedback!");
		msg.put("body", "Hai ricevuto un nuovo FeedBack per la tua lezione di " + title + " del " + sdf.format(date));
		
	   msg.put("type", "lesson");
	   
	   msg.put("idlesson", id);
	   
	   return msg;
	}
}
