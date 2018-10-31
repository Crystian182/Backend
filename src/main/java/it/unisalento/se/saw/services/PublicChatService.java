package it.unisalento.se.saw.services;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unisalento.se.saw.Iservices.IPublicChatService;
import it.unisalento.se.saw.domain.Privatechat;
import it.unisalento.se.saw.domain.Publicchat;
import it.unisalento.se.saw.domain.PublicchatHasUser;
import it.unisalento.se.saw.domain.PublicchatHasUserId;
import it.unisalento.se.saw.domain.User;
import it.unisalento.se.saw.exceptions.ChatNotFoundException;
import it.unisalento.se.saw.repositories.PublicChatHasUserRepository;
import it.unisalento.se.saw.repositories.PublicChatRepository;

@Service
public class PublicChatService implements IPublicChatService {
	
	@Autowired
	PublicChatRepository publicChatRepository;
	
	@Autowired
	PublicChatHasUserRepository publicChatHasUserRepository;
	
	@Transactional(readOnly=true)
	public List<Publicchat> getAll(int id){
		List<PublicchatHasUser> joinings = publicChatHasUserRepository.getChatIds(id);
		List<Publicchat> publicChats = new ArrayList<Publicchat>();
		for(int i=0; i<joinings.size(); i++) {
			Publicchat chat = publicChatRepository.getChatById(joinings.get(i).getId().getPublicchatIdpublicchat());
			publicChats.add(chat);
		}
		return publicChats;
	}
	
	@Transactional
	public void delete(Publicchat chat) throws ChatNotFoundException {
		try {
			publicChatRepository.delete(chat);
		} catch (Exception e) {
			throw new ChatNotFoundException();
		}
	}
	
	@Transactional
	public List<Publicchat> search(User user, String keyword) {
		return publicChatRepository.search(user.getCourse().getIdcourse(), keyword);
	}

	@Transactional
	public boolean checkSubscribe(int userid, Publicchat chat) {
		return publicChatHasUserRepository.checkSubscribe(userid, chat.getIdpublicchat());
	}
	
	@Transactional
	public Publicchat getChat(Publicchat chat) throws ChatNotFoundException {
		try {
			return publicChatRepository.findById(chat.getIdpublicchat()).get();
		} catch (Exception e) {
			throw new ChatNotFoundException();
		}
	}
	
	public void subscribe(int userid, int chatid) {
		User user = new User();
		user.setIduser(userid);
		PublicchatHasUserId id = new PublicchatHasUserId();
		id.setPublicchatIdpublicchat(chatid);
		id.setUserIduser(userid);
		Publicchat publicChat = new Publicchat();
		publicChat.setIdpublicchat(chatid);
		PublicchatHasUser joining = new PublicchatHasUser();
		joining.setUser(user);
		joining.setPublicchat(publicChat);
		joining.setDate(new Timestamp(System.currentTimeMillis()));
		joining.setId(id);
		publicChatHasUserRepository.save(joining);
	}

}
