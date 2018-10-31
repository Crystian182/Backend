package it.unisalento.se.saw.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unisalento.se.saw.Iservices.IPrivateChatService;
import it.unisalento.se.saw.domain.Privatechat;
import it.unisalento.se.saw.domain.Publicchat;
import it.unisalento.se.saw.exceptions.ChatNotFoundException;
import it.unisalento.se.saw.repositories.PrivateChatRepository;

@Service
public class PrivateChatService implements IPrivateChatService {
	
	@Autowired
	PrivateChatRepository privateChatRepository;
	
	@Transactional(readOnly=true)
	public List<Privatechat> getAll(int id){
		return privateChatRepository.findAllByUserId(id);
	}
	
	@Transactional
	public void delete(Privatechat chat) throws ChatNotFoundException {
		try {
			privateChatRepository.delete(chat);
		} catch (Exception e) {
			throw new ChatNotFoundException();
		}
	}

	@Transactional
	public Privatechat getChat(int userid, int userid1) {
		return privateChatRepository.getChat(userid, userid1);
	}

	/*public List<Privatechat> search(int idUser, String keyword) {
		return privateChatRepository.search(idUser, keyword);
	}*/
}
