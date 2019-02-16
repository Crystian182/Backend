package it.unisalento.se.saw.models;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;

public class LessonChangedBothNotification implements Notification {
	
	@Override
	public JSONObject getJSONObject(int id, String title, Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		JSONObject msg = new JSONObject();
		msg.put("title", "Aggiornamento Lezione!");
		msg.put("body", "La lezione di " + title + " del " + sdf.format(date) + " ha subito delle variazioni!");
		
	   msg.put("type", "lesson");
	   
	   msg.put("idlesson", id);
	   
	   return msg;
	}

}
