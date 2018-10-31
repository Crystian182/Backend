package it.unisalento.se.saw.Iservices;

import java.util.List;

import org.springframework.data.domain.Sort;

import it.unisalento.se.saw.domain.Message;

public interface IMessageService {
	
	public List<Message> getAllPrivates(int idChat);
	public List<Message> getAllPublics(int idChat);
	public Message save(Message message);
	public boolean checkPublicOrNot(int idChat);

}
