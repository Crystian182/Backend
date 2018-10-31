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
import it.unisalento.se.saw.Iservices.IInstrumentsService;
import it.unisalento.se.saw.domain.Building;
import it.unisalento.se.saw.domain.Classroom;
import it.unisalento.se.saw.domain.ClassroomHasInstruments;
import it.unisalento.se.saw.domain.ClassroomHasInstrumentsId;
import it.unisalento.se.saw.domain.Instruments;
import it.unisalento.se.saw.dto.BuildingDTO;
import it.unisalento.se.saw.dto.ClassroomDTO;
import it.unisalento.se.saw.dto.InstrumentDTO;
import it.unisalento.se.saw.exceptions.ClassroomNotFoundException;

@RestController
@RequestMapping("/classroom")
public class ClassroomRestController {
	
	@Autowired
	IClassroomService classroomService;
	
	@Autowired
	IInstrumentsService instrumentService;
	
	public ClassroomRestController() {
		super();
	}
	
	public ClassroomRestController(IClassroomService classroomService) {
		this.classroomService = classroomService;
	}
	
	@GetMapping(value="/getAll", produces=MediaType.APPLICATION_JSON_VALUE)
	public List<ClassroomDTO> getAll(){
		List<Classroom> classrooms = classroomService.getAll();
		List<ClassroomDTO> newClassroomDTOs = new ArrayList<ClassroomDTO>();
		for(int i=0; i<classrooms.size(); i++) {
			Classroom classroom = classrooms.get(i);
			newClassroomDTOs.add(this.entityToDTO(classroom));
		}
		
		return newClassroomDTOs;
	}
	
	@GetMapping(value="/getById/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ClassroomDTO getById(@PathVariable("id")int id) throws ClassroomNotFoundException {
		Classroom classroom = classroomService.getById(id);
		ClassroomDTO classroomDTO = this.entityToDTO(classroom);
		List<Instruments> instruments = instrumentService.getInstrumentsFromClassroom(id);
		List<InstrumentDTO> instrumentsDTO = new ArrayList<InstrumentDTO>();
		for(int i=0; i<instruments.size(); i++) {
			List<ClassroomHasInstruments> list = new ArrayList<ClassroomHasInstruments>(instruments.get(i).getClassroomHasInstrumentses());
			InstrumentDTO instrumentDTO = new InstrumentDTO();
			instrumentDTO.setId(instruments.get(i).getIdinstruments());
			instrumentDTO.setName(instruments.get(i).getName());
			instrumentDTO.setQuantity(list.get(0).getQuantity());
			instrumentsDTO.add(instrumentDTO);
		}
		classroomDTO.setInstruments(instrumentsDTO);
		return classroomDTO;
	}
	
	@GetMapping(value="/getByIdBuilding/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public List<ClassroomDTO> getByIdBuilding(@PathVariable("id")int idBuilding) throws ClassroomNotFoundException {
		List<Classroom> classrooms = classroomService.getByIdBuilding(idBuilding);
		List<ClassroomDTO> classroomDTOs = new ArrayList<ClassroomDTO>();
		for(int i=0; i<classrooms.size(); i++) {
			Classroom classroom = classrooms.get(i);
			classroomDTOs.add(this.entityToDTO(classroom));
		}
		
		return classroomDTOs;
	}
	
	@GetMapping(value="/getByIdBuildingAndName/{idBuilding}&{classroom}", produces=MediaType.APPLICATION_JSON_VALUE)
	public List<ClassroomDTO> getByIdBuildingAndName(@PathVariable("idBuilding")int idBuilding, @PathVariable("classroom")String name) throws ClassroomNotFoundException {
		List<Classroom> classrooms = classroomService.getByIdBuildingAndName(idBuilding, name);
		List<ClassroomDTO> classroomDTOs = new ArrayList<ClassroomDTO>();
		for(int i=0; i<classrooms.size(); i++) {
			Classroom classroom = classrooms.get(i);
			classroomDTOs.add(this.entityToDTO(classroom));
		}
		
		return classroomDTOs;
	}
	
	@GetMapping(value="/getByName/{classroom}", produces=MediaType.APPLICATION_JSON_VALUE)
	public List<ClassroomDTO> getByIName(@PathVariable("classroom")String name) throws ClassroomNotFoundException {
		List<Classroom> classrooms = classroomService.getByName(name);
		List<ClassroomDTO> classroomDTOs = new ArrayList<ClassroomDTO>();
		for(int i=0; i<classrooms.size(); i++) {
			Classroom classroom = classrooms.get(i);
			classroomDTOs.add(this.entityToDTO(classroom));
		}
		
		return classroomDTOs;
	}
	
	@PostMapping(value="/edit", produces=MediaType.APPLICATION_JSON_VALUE)
	public ClassroomDTO edit(@RequestBody ClassroomDTO classroomDTO) throws ClassroomNotFoundException {

		Classroom classroom = classroomService.edit(this.DTOtoEntity(classroomDTO));
		return this.entityToDTO(classroom);
	}
	
	@PostMapping(value="/save", produces=MediaType.APPLICATION_JSON_VALUE)
	public ClassroomDTO save(@RequestBody ClassroomDTO classroomDTO) {
		
		Classroom newClassroom = classroomService.save(this.DTOtoEntity(classroomDTO));
		return this.entityToDTO(newClassroom);
	}
	
	@GetMapping(value="/delete/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public void delete(@PathVariable("id")int id) throws ClassroomNotFoundException {
		classroomService.delete(id);
	}	
	
	public ClassroomDTO entityToDTO(Classroom classroom) {
		BuildingDTO buildingDTO = new BuildingDTO();
		buildingDTO.setId(classroom.getBuilding().getIdbuilding());
		buildingDTO.setName(classroom.getBuilding().getName());
		buildingDTO.setAddress(classroom.getBuilding().getAddress());
		buildingDTO.setLatitude(classroom.getBuilding().getLatitude());
		buildingDTO.setLongitude(classroom.getBuilding().getLongitude());
		
		ClassroomDTO classroomDTO = new ClassroomDTO();
		classroomDTO.setId(classroom.getIdclassroom());
		classroomDTO.setName(classroom.getName());
		classroomDTO.setSeats(classroom.getSeats());
		classroomDTO.setLatitude(classroom.getLatitude());
		classroomDTO.setLongitude(classroom.getLongitude());
		classroomDTO.setBuilding(buildingDTO);
		return classroomDTO;
		
	}
	
	public Classroom DTOtoEntity(ClassroomDTO classroomDTO) {
		
		Building building = new Building();
		building.setIdbuilding(classroomDTO.getBuilding().getId());
		building.setName(classroomDTO.getBuilding().getName());
		building.setAddress(classroomDTO.getBuilding().getAddress());
		building.setLatitude(classroomDTO.getBuilding().getLatitude());
		building.setLongitude(classroomDTO.getBuilding().getLongitude());
		
		Classroom classroom = new Classroom();
		try {
			classroom.setIdclassroom(classroomDTO.getId());
		} catch (Exception e) {
		}
		classroom.setName(classroomDTO.getName());
		classroom.setSeats(classroomDTO.getSeats());
		classroom.setLatitude(classroomDTO.getLatitude());
		classroom.setLongitude(classroomDTO.getLongitude());
		classroom.setBuilding(building);
		
		return classroom;
		
	}

}
