package it.unisalento.se.saw.Iservices;

import java.io.InputStream;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.PathVariable;

import it.unisalento.se.saw.dto.FeedbackDTO;
import it.unisalento.se.saw.dto.FileDTO;
import it.unisalento.se.saw.exceptions.FileNotExistsException;

public interface IFileService {
	
	public void saveFileBuilding(InputStream inputStream, String path, String filename, int idbuilding);
	public FileDTO saveFileLesson(InputStream inputStream, String path, String filename, int idlesson);
	public Resource getFileAsResource(String filename, String location) throws FileNotExistsException;
	public List<FileDTO> getLessonFiles(int idlesson);
	public it.unisalento.se.saw.domain.File getBuildingImage(int idbuilding);
	public it.unisalento.se.saw.domain.File getFile(int idfile);
	public List<FeedbackDTO> getFeedbackFile(int idfile);

}
