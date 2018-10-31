package it.unisalento.se.saw.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unisalento.se.saw.Iservices.IFileService;
import it.unisalento.se.saw.exceptions.FileNotExistsException;

@Service
public class FileService implements IFileService {
	
	@Transactional
	public void saveFile(InputStream inputStream, String path) {
		try {
			OutputStream outputStream = new FileOutputStream(new File(path));
			int read = 0;
			byte[] bytes = new byte[1024];
			while((read = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}
			outputStream.flush();
			outputStream.close();
		} catch (Exception e) {
			// TODO: handle exception
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

}
