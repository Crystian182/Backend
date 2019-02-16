package it.unisalento.se.saw.factory;

import it.unisalento.se.saw.models.FeedbackNotification;
import it.unisalento.se.saw.models.FileNotification;
import it.unisalento.se.saw.models.LessonChangedBothNotification;
import it.unisalento.se.saw.models.LessonChangedRoomNotification;
import it.unisalento.se.saw.models.LessonChangedTimeNotification;
import it.unisalento.se.saw.models.Notification;
import it.unisalento.se.saw.models.TicketMessageNotification;
import it.unisalento.se.saw.models.TicketStatusNotification;

public class NotificationFactory extends AbstractFactory{
	
	@Override
		public Notification getNotification(String type){
	      
	      if(type.equalsIgnoreCase("ROOMCHANGED")){
	         return new LessonChangedRoomNotification();
	         
	      }
	      
	      if(type.equalsIgnoreCase("TIMECHANGED")){
		         return new LessonChangedTimeNotification();
		         
		      }
	      
	      if(type.equalsIgnoreCase("BOTHCHANGED")){
		         return new LessonChangedBothNotification();
		         
		      }
	      
	      if(type.equalsIgnoreCase("NEWFEEDBACKLESSON")){
		         return new FeedbackNotification();
		         
		      }
	      
	      if(type.equalsIgnoreCase("NEWFILE")){
		         return new FileNotification();
		         
		      }
	      
	      if(type.equalsIgnoreCase("NEWTICKETMESSAGE")){
		         return new TicketMessageNotification();
		         
		      }
	      
	      if(type.equalsIgnoreCase("NEWTICKETSTATUS")){
		         return new TicketStatusNotification();
		         
		      }
	      
	      
	      
	      return null;
	   }

}
