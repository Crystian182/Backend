package it.unisalento.se.saw.Iservices;

import java.util.List;

import it.unisalento.se.saw.domain.Publicchat;
import it.unisalento.se.saw.domain.User;
import it.unisalento.se.saw.exceptions.ChatNotFoundException;

public interface IPublicChatService {

	public List<Publicchat> getAll(int id);
	public void delete(Publicchat chat) throws ChatNotFoundException;
	public List<Publicchat> search(User user, String keyword);
	public boolean checkSubscribe(int userid, Publicchat chat);
	public Publicchat getChat(Publicchat chat) throws ChatNotFoundException;
	public void subscribe(int userid, int chatid);
}
