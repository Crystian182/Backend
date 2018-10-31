package it.unisalento.se.saw.Iservices;

import java.util.List;

import it.unisalento.se.saw.domain.Privatechat;
import it.unisalento.se.saw.exceptions.ChatNotFoundException;

public interface IPrivateChatService {
	
	public List<Privatechat> getAll(int id);
	public void delete(Privatechat chat) throws ChatNotFoundException;
	//public List<Privatechat> search(int idUser, String keyword);
	public Privatechat getChat(int user, int user1);

}
