package it.unisalento.se.saw.Iservices;

import it.unisalento.se.saw.domain.Lesson;
import net.minidev.json.parser.ParseException;

public interface IFCMService {
	
	public void newFeedBackLesson(int idlesson);
	public void newFileLesson(int idlesson);
	
}
