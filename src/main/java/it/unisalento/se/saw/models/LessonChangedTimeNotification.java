package it.unisalento.se.saw.models;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;

public class LessonChangedTimeNotification implements Notification {
	
	//input[0] lesson id
	//input[1] lesson name
	//input[2] lesson start

	@Override
	public JSONObject getJSONObject(int id, String title, Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		JSONObject msg = new JSONObject();
		msg.put("title", "Aggiornamento Lezione!");
		msg.put("body", "L'orario della lezione di " + title + " del " + sdf.format(date) + " è variato!");
		
	   msg.put("type", "lesson");
	   
	   msg.put("idlesson", id);
	   
	   return msg;
	}
}
