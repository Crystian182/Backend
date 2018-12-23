package it.unisalento.se.saw.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unisalento.se.saw.Iservices.IFileService;
import it.unisalento.se.saw.domain.Building;
import it.unisalento.se.saw.domain.FileLesson;
import it.unisalento.se.saw.domain.FileLessonId;
import it.unisalento.se.saw.domain.Lesson;
import it.unisalento.se.saw.dto.FileDTO;
import it.unisalento.se.saw.exceptions.FileNotExistsException;
import it.unisalento.se.saw.repositories.BuildingRepository;
import it.unisalento.se.saw.repositories.FeedbackFileRepository;
import it.unisalento.se.saw.repositories.FeedbackRepository;
import it.unisalento.se.saw.repositories.FileLessonRepository;
import it.unisalento.se.saw.repositories.FileRepository;

@Service
public class FileService implements IFileService {
	
	@Autowired
	FileRepository fileRepository;
	
	@Autowired
	FeedbackFileRepository feedbackFileRepository;
	
	@Autowired
	FileLessonRepository fileLessonRepository;
	
	@Autowired
	BuildingRepository buildingRepository;
	
	@Transactional
	public void saveFileBuilding(InputStream inputStream, String path, String filename, int idbuilding) {
		it.unisalento.se.saw.domain.File file = new it.unisalento.se.saw.domain.File();
		file.setName(filename);
		
		it.unisalento.se.saw.domain.File savedFile = fileRepository.save(file);

		buildingRepository.updateImage(idbuilding, savedFile.getIdfile());
		
		try {
			OutputStream outputStream = new FileOutputStream(new File(path + savedFile.getIdfile()));
			int read = 0;
			byte[] bytes = new byte[1024];
			while((read = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}
			outputStream.flush();
			outputStream.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("error");
		}
	}
	
	@Transactional
	public FileDTO saveFileLesson(InputStream inputStream, String path, String filename, int idlesson) {
		try {
			it.unisalento.se.saw.domain.File file = new it.unisalento.se.saw.domain.File();
			file.setName(filename);
			
			Lesson lesson = new Lesson();
			lesson.setIdlesson(idlesson);
			
			it.unisalento.se.saw.domain.File savedFile = fileRepository.save(file);
			
			FileLessonId idFileLesson = new FileLessonId();
			idFileLesson.setLessonIdlesson(idlesson);
			idFileLesson.setFileIdfile(savedFile.getIdfile());
			
			FileLesson fileLesson = new FileLesson();
			fileLesson.setId(idFileLesson);
			fileLesson.setDate(new Date());
			
			fileLessonRepository.save(fileLesson);
			
			OutputStream outputStream = new FileOutputStream(new File(path + savedFile.getIdfile()));
			int read = 0;
			byte[] bytes = new byte[1024];
			while((read = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}
			outputStream.flush();
			outputStream.close();
			
			FileDTO fileDTO = new FileDTO();
			fileDTO.setIdFile(savedFile.getIdfile());
			fileDTO.setName(savedFile.getName());
			//fileDTO.setLink(link);
			return fileDTO;
		} catch (Exception e) {
			return null;
		}
	}
	
	@Transactional(rollbackFor=FileNotExistsException.class)
	public Resource getFileAsResource(String filename, String location) throws FileNotExistsException {
	    	
	        String filePath = location + filename;
	        Resource file = loadAsResource(filePath, location);
	        return file;
	    }

	@Transactional(rollbackFor=FileNotExistsException.class)
    private Resource loadAsResource(String filename, String location) throws FileNotExistsException {
    	
		Path fileStorageLocation = Paths.get(location)
                .toAbsolutePath().normalize();
        Path filePath = fileStorageLocation.resolve(filename).normalize();
        try {
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } 
        } catch (MalformedURLException ex) {
            throw new FileNotExistsException();
        }
    	throw new FileNotExistsException();
    }
	
	public List<FileDTO> getLessonFiles(int idlesson) {
		
		List<it.unisalento.se.saw.domain.File> files = fileLessonRepository.getLessonFiles(idlesson);
		List<FileDTO> fileDTOs = new ArrayList<FileDTO>();
		for(int i=0; i<files.size(); i++) {
			FileDTO fileDTO = new FileDTO();
			fileDTO.setIdFile(files.get(i).getIdfile());
			fileDTO.setName(files.get(i).getName());
			fileDTO.setStars(feedbackFileRepository.getStars(files.get(i).getIdfile()));

			fileDTOs.add(fileDTO);
		}
		
		return fileDTOs;
		
	}
	
	public it.unisalento.se.saw.domain.File getBuildingImage(int idbuilding) {
		return buildingRepository.getOne(idbuilding).getFile();
	}
	
	public it.unisalento.se.saw.domain.File getFile(int idfile) {
		return fileRepository.getOne(idfile);
	}

}
