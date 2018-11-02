package it.unisalento.se.saw.restapi;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import it.unisalento.se.saw.Iservices.IClassroomService;
import it.unisalento.se.saw.Iservices.IToolService;
import it.unisalento.se.saw.domain.Building;
import it.unisalento.se.saw.domain.Classroom;
import it.unisalento.se.saw.dto.BuildingDTO;
import it.unisalento.se.saw.dto.ClassroomDTO;
import it.unisalento.se.saw.dto.ToolDTO;
import it.unisalento.se.saw.exceptions.ClassroomNotFoundException;

@RestController
@RequestMapping("/classroom")
public class ClassroomRestController {
	
	@Autowired
	IClassroomService classroomService;
	
	public ClassroomRestController() {
		super();
	}
	
	public ClassroomRestController(IClassroomService classroomService) {
		this.classroomService = classroomService;
	}
	
	@GetMapping(value="/getAll", produces=MediaType.APPLICATION_JSON_VALUE)
	public List<ClassroomDTO> getAll(){
		return classroomService.getAll();
	}
	
	@GetMapping(value="/getById/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ClassroomDTO getById(@PathVariable("id")int id) throws ClassroomNotFoundException {
		return classroomService.getById(id);
	}
	
	@GetMapping(value="/getByIdBuilding/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public List<ClassroomDTO> getByIdBuilding(@PathVariable("id")int idBuilding) throws ClassroomNotFoundException {
		return classroomService.getByIdBuilding(idBuilding);
	}
	
	@GetMapping(value="/getByIdBuildingAndName/{idBuilding}&{classroom}", produces=MediaType.APPLICATION_JSON_VALUE)
	public List<ClassroomDTO> getByIdBuildingAndName(@PathVariable("idBuilding")int idBuilding, @PathVariable("classroom")String name) throws ClassroomNotFoundException {
		return classroomService.getByIdBuildingAndName(idBuilding, name);
	}
	
	@GetMapping(value="/getByName/{classroom}", produces=MediaType.APPLICATION_JSON_VALUE)
	public List<ClassroomDTO> getByIName(@PathVariable("classroom")String name) throws ClassroomNotFoundException {
		return classroomService.getByName(name);
	}
	
	@PostMapping(value="/save", produces=MediaType.APPLICATION_JSON_VALUE)
	public ClassroomDTO save(@RequestBody ClassroomDTO classroomDTO) {
		return classroomService.save(classroomDTO);
	}
	
	@GetMapping(value="/delete/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public void delete(@PathVariable("id")int id) throws ClassroomNotFoundException {
		classroomService.delete(id);
	}	

}
