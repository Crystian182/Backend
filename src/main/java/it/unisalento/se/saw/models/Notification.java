package it.unisalento.se.saw.models;

import java.util.Date;

import org.json.JSONObject;

public interface Notification {

	JSONObject getJSONObject(int id, String title, Date date);
}
