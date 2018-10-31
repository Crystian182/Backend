package it.unisalento.se.saw.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import it.unisalento.se.saw.Iservices.IMessageService;
import it.unisalento.se.saw.domain.Message;
import it.unisalento.se.saw.repositories.MessageRepository;

@Service
public class MessageService implements IMessageService {
	
	@Autowired
	MessageRepository messageRepository;
	
	/*@Transactional
	public Privatemessage getLastMessageByChatId(int idChat) {
		messageRepository.getLast(idChat);
	}*/
	
	@Transactional
	public List<Message> getAllPrivates(int id){
		//return messageRepository.findFirst10ByIdprivatechat(id, new Sort("date"));
		return messageRepository.getPrivateMessagesFromChatId(id);
	}
	
	@Transactional
	public List<Message> getAllPublics(int id){
		//return messageRepository.findFirst10ByIdprivatechat(id, new Sort("date"));
		return messageRepository.getPublicMessagesFromChatId(id);
	}

	public Message save(Message message) {
		return messageRepository.save(message);
	}
	
	public boolean checkPublicOrNot(int idChat) {
		return messageRepository.checkPublicChat(idChat);
	}
}
