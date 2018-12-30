package it.unisalento.se.saw.restapi;

import java.io.File;
import java.util.List;

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
import it.unisalento.se.saw.dto.FeedbackDTO;
import it.unisalento.se.saw.dto.FileDTO;
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
	
	/*@RequestMapping(value="/upload", method=RequestMethod.POST)
	public String upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
		try {
			String fileName = file.getOriginalFilename();
			String path = location + fileName;
			fileService.saveFile(file.getInputStream(), path);
			return fileName;
		} catch (Exception e) {
			return e.getMessage();
		}
	}*/
	
	@RequestMapping(value="/upload/building/{idbuilding}", method=RequestMethod.POST)
	public HttpStatus uploadBuilding(@PathVariable("idbuilding")int idbuilding, @RequestParam("file") MultipartFile file, HttpServletRequest request) {
		try {
			String fileName = file.getOriginalFilename();
			String path = location;
			fileService.saveFileBuilding(file.getInputStream(), path, fileName, idbuilding);
			return HttpStatus.ACCEPTED;
		} catch (Exception e) {
			return HttpStatus.NOT_ACCEPTABLE;
		}
	}
	
	@RequestMapping(value="/upload/filelesson/{idlesson}", method=RequestMethod.POST)
	public FileDTO uploadFileLesson(@PathVariable("idlesson")int idlesson, @RequestParam("file") MultipartFile file, HttpServletRequest request) {
		try {
			String fileName = file.getOriginalFilename();
			String path = location;
			return fileService.saveFileLesson(file.getInputStream(), path, fileName, idlesson);
		} catch (Exception e) {
			return null;
		}
	}

   @GetMapping("/download/{fileName:.+}")
    public ResponseEntity<Resource> download(@PathVariable String fileName, HttpServletRequest request, HttpServletResponse response) throws FileNotExistsException {
        Resource file = fileService.getFileAsResource(fileName, location);
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        return new ResponseEntity<Resource>(file, HttpStatus.OK);
    }
   
   @GetMapping("/getLessonFiles/{idlesson}")
   public List<FileDTO> getLessonFiles(@PathVariable("idlesson")int idlesson) {
       return fileService.getLessonFiles(idlesson);
   }
   
   @GetMapping("/download/building/{idbuilding}")
   public ResponseEntity<Resource> downloadBuildingImage(@PathVariable("idbuilding")int idbuilding, HttpServletRequest request, HttpServletResponse response) throws FileNotExistsException {
	   it.unisalento.se.saw.domain.File fil = fileService.getBuildingImage(idbuilding);
	   Resource file = fileService.getFileAsResource(String.valueOf(fil.getIdfile()), location);
       response.setHeader("Content-Disposition", "attachment; filename=\"" + fil.getName() + "\"");
       return new ResponseEntity<Resource>(file, HttpStatus.OK);
   }
   
   @GetMapping("/download/filelesson/{idfile}")
   public ResponseEntity<Resource> downloadFileLesson(@PathVariable("idfile")int idfile, HttpServletRequest request, HttpServletResponse response) throws FileNotExistsException {
	   it.unisalento.se.saw.domain.File fil = fileService.getFile(idfile);
	   Resource file = fileService.getFileAsResource(String.valueOf(fil.getIdfile()), location);
       response.setHeader("Content-Disposition", "attachment; filename=\"" + fil.getName() + "\"");
       return new ResponseEntity<Resource>(file, HttpStatus.OK);
   }
   
   @GetMapping("/getFeedbackFile/{idfile}")
   public List<FeedbackDTO> getFeedbackFile(@PathVariable("idfile")int idfile) {
       return fileService.getFeedbackFile(idfile);
   }

	
}
