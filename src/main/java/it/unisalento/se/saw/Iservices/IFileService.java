package it.unisalento.se.saw.Iservices;

import java.io.InputStream;

import org.springframework.core.io.Resource;

import it.unisalento.se.saw.exceptions.FileNotExistsException;

public interface IFileService {
	
	public void saveFile(InputStream inputStream, String path);
	public Resource getFileAsResource(String filename, String location) throws FileNotExistsException;

}
