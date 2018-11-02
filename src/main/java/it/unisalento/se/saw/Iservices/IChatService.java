package it.unisalento.se.saw.Iservices;

import java.util.List;

import it.unisalento.se.saw.dto.ChatDTO;
import it.unisalento.se.saw.dto.PreviewChatDTO;
import it.unisalento.se.saw.exceptions.ChatNotFoundException;

public interface IChatService {

	public List<PreviewChatDTO> getAllPreviews(String ssn);
	public List<PreviewChatDTO> search(String ssn, String keyword);
	public ChatDTO getSubjectChat(String id) throws ChatNotFoundException;
	public ChatDTO getPrivateChat(String ssn1, String ssn2);
}
