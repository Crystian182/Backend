package it.unisalento.se.saw.models;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;

public class FileNotification implements Notification {

	@Override
	public JSONObject getJSONObject(int id, String title, Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		JSONObject msg = new JSONObject();
		msg.put("title", "Nuovo Upload!");
		msg.put("body", "E' presente un nuovo file per la lezione di " + title + " del " + sdf.format(date));
		
	   msg.put("type", "lesson");
	   
	   msg.put("idlesson", id);
	   
	   return msg;
	}
}
