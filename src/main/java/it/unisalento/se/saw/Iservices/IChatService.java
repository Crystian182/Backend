package it.unisalento.se.saw.Iservices;

import java.io.IOException;
import java.util.List;

import it.unisalento.se.saw.dto.PreviewChatDTO;
import it.unisalento.se.saw.dto.UserDTO;
import it.unisalento.se.saw.exceptions.ChatNotFoundException;

public interface IChatService {

	/*public List<PreviewChatDTO> getAllPreviews(int iduser) throws IOException;
	public List<MessageDTO> getMessages(int idchat, int iduser) throws IOException;
	public int sendMessage(MessageDTO messageDTO);*/
	public List<PreviewChatDTO> getContacts(int iduser, String keyword) throws IOException;
	public UserDTO getProfileUser(int iduser) throws IOException;
	public List<PreviewChatDTO> getSubscribedCourses(int iduser);
	
	public String getPropic(int iduser) throws IOException;
	/*public void readAll(int idchat, int iduser);
	public boolean isRead(int idmessage, int iduser);
	public PreviewChatDTO getPreview(int iduser1, int iduser2) throws IOException;
	/*public List<PreviewChatDTO> search(int iduser, String keyword);
	public ChatDTO getSubjectChat(String id) throws ChatNotFoundException;
	public ChatDTO getPrivateChat(String iduser1, String iduser2);*/
}
