package it.unisalento.se.saw.restapi;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletResponse;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import it.unisalento.se.saw.Iservices.IFileService;
import it.unisalento.se.saw.exceptions.FileNotExistsException;

@RestController
@RequestMapping("/file")
public class FileRestController {
	
	private static final String location = "C:\\Users\\Federico\\Desktop\\Computer Engineering\\Backend\\Backend\\src\\main\\resources\\";
	
	@Autowired
	IFileService fileService;

	public FileRestController() {
		super();
	}
	
	public FileRestController(IFileService fileService) {
		this.fileService = fileService;
	}
	
	@RequestMapping(value="/upload", method=RequestMethod.POST)
	public String upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
		try {
			String fileName = file.getOriginalFilename();
			String path = location + fileName;
			fileService.saveFile(file.getInputStream(), path);
			return fileName;
		} catch (Exception e) {
			return e.getMessage();
		}
	}
	
	@RequestMapping(value="/upload/building", method=RequestMethod.POST)
	public HttpStatus uploadBuilding(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
		File dir = new File(location + "\\building");
		if (!dir.exists()) {
			dir.mkdir();
		}
		try {
			String fileName = file.getOriginalFilename();
			String path = location + "\\building\\" + fileName;
			fileService.saveFile(file.getInputStream(), path);
			return HttpStatus.ACCEPTED;
		} catch (Exception e) {
			return HttpStatus.NOT_ACCEPTABLE;
		}
	}

   @GetMapping("/download/{fileName:.+}")
    public ResponseEntity<Resource> download(@PathVariable String fileName, HttpServletRequest request, HttpServletResponse response) throws FileNotExistsException {
        Resource file = fileService.getFileAsResource(fileName, location);
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        return new ResponseEntity<Resource>(file, HttpStatus.OK);
    }

	
}
