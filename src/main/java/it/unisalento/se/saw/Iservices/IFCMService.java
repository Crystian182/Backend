package it.unisalento.se.saw.Iservices;

import it.unisalento.se.saw.domain.Lesson;
import it.unisalento.se.saw.domain.Ticket;
import net.minidev.json.parser.ParseException;

public interface IFCMService {
	
	public void newFeedBackLesson(int idlesson);
	public void newFileLesson(int idlesson);
	public void lessonChanged(Lesson lesson, String whatChanged);
	public void newTicket(Ticket ticket, String whatChanged);
	
}
