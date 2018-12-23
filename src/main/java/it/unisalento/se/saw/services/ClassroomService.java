package it.unisalento.se.saw.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unisalento.se.saw.Iservices.IClassroomService;
import it.unisalento.se.saw.domain.Building;
import it.unisalento.se.saw.domain.Classroom;
import it.unisalento.se.saw.domain.ClassroomHasTool;
import it.unisalento.se.saw.domain.ClassroomHasToolId;
import it.unisalento.se.saw.domain.Tool;
import it.unisalento.se.saw.domain.User;
import it.unisalento.se.saw.dto.BuildingDTO;
import it.unisalento.se.saw.dto.ClassroomDTO;
import it.unisalento.se.saw.dto.ToolDTO;
import it.unisalento.se.saw.dto.TypeLessonDTO;
import it.unisalento.se.saw.exceptions.AulaNotFoundException;
import it.unisalento.se.saw.exceptions.ClassroomNotFoundException;
import it.unisalento.se.saw.repositories.BuildingRepository;
import it.unisalento.se.saw.repositories.ClassroomHasToolRepository;
import it.unisalento.se.saw.repositories.ClassroomRepository;

@Service
public class ClassroomService implements IClassroomService{
	
	@Autowired
	ClassroomRepository classroomRepository;
	
	@Autowired
	ClassroomHasToolRepository classroomHasToolRepository;
	
	@Transactional(readOnly=true)
	public List<ClassroomDTO> getAll(){
		List<Classroom> classrooms = classroomRepository.findAll();	
		List<ClassroomDTO> classroomDTOs = new ArrayList<ClassroomDTO>();
		for(int i=0; i<classrooms.size(); i++) {
			BuildingDTO buildingDTO = new BuildingDTO();
			buildingDTO.setId(classrooms.get(i).getBuilding().getIdbuilding());
			buildingDTO.setName(classrooms.get(i).getBuilding().getName());
			buildingDTO.setAddress(classrooms.get(i).getBuilding().getAddress());
			buildingDTO.setLat(classrooms.get(i).getBuilding().getLat());
			buildingDTO.setLng(classrooms.get(i).getBuilding().getLng());
			
			List<ToolDTO> toolDTOs = new ArrayList<ToolDTO>();
			List<ClassroomHasTool> toolsInClassroom = classroomHasToolRepository.getToolByClassroomId(classrooms.get(i).getIdclassroom());
			
			for(int j=0; j<toolsInClassroom.size(); j++) {
				ToolDTO toolDTO = new ToolDTO();
				toolDTO.setId(toolsInClassroom.get(j).getId().getIdtool());
				toolDTO.setName(toolsInClassroom.get(j).getTool().getName());
				toolDTO.setQuantity(toolsInClassroom.get(j).getQuantity());
				toolDTOs.add(toolDTO);
			}
			
			ClassroomDTO classroomDTO = new ClassroomDTO();
			classroomDTO.setId(classrooms.get(i).getIdclassroom());
			classroomDTO.setName(classrooms.get(i).getName());
			classroomDTO.setSeats(classrooms.get(i).getSeats());
			classroomDTO.setLat(classrooms.get(i).getLat());
			classroomDTO.setLng(classrooms.get(i).getLng());
			classroomDTO.setBuilding(buildingDTO);
			classroomDTO.setTool(toolDTOs);
			classroomDTOs.add(classroomDTO);
		}
		return classroomDTOs;
	}
	
	@Transactional(rollbackFor=ClassroomNotFoundException.class)
	public ClassroomDTO getById(int idClassroom) throws ClassroomNotFoundException {
		
		try {
			Classroom classroom = classroomRepository.findById(idClassroom).get();

			BuildingDTO buildingDTO = new BuildingDTO();
			buildingDTO.setId(classroom.getBuilding().getIdbuilding());
			buildingDTO.setName(classroom.getBuilding().getName());
			buildingDTO.setAddress(classroom.getBuilding().getAddress());
			buildingDTO.setLat(classroom.getBuilding().getLat());
			buildingDTO.setLng(classroom.getBuilding().getLng());
			
			List<ToolDTO> toolDTOs = new ArrayList<ToolDTO>();
			List<ClassroomHasTool> toolsInClassroom = classroomHasToolRepository.getToolByClassroomId(classroom.getIdclassroom());
			
			for(int j=0; j<toolsInClassroom.size(); j++) {
				ToolDTO toolDTO = new ToolDTO();
				toolDTO.setId(toolsInClassroom.get(j).getId().getIdtool());
				toolDTO.setName(toolsInClassroom.get(j).getTool().getName());
				toolDTO.setQuantity(toolsInClassroom.get(j).getQuantity());
				toolDTOs.add(toolDTO);
			}
			
			ClassroomDTO classroomDTO = new ClassroomDTO();
			classroomDTO.setId(classroom.getIdclassroom());
			classroomDTO.setName(classroom.getName());
			classroomDTO.setSeats(classroom.getSeats());
			classroomDTO.setLat(classroom.getLat());
			classroomDTO.setLng(classroom.getLng());
			classroomDTO.setBuilding(buildingDTO);
			classroomDTO.setTool(toolDTOs);
			return classroomDTO;
		} catch (Exception e) {
			throw new ClassroomNotFoundException();
		}
	}
	
	@Transactional(rollbackFor=ClassroomNotFoundException.class)
	public List<ClassroomDTO> getByIdBuilding(int idBuilding) throws ClassroomNotFoundException {
		
		try {
			List<Classroom> classrooms = classroomRepository.findClassesByBuild(idBuilding);
			List<ClassroomDTO> classroomDTOs = new ArrayList<ClassroomDTO>();
			for(int i=0; i<classrooms.size(); i++) {
				BuildingDTO buildingDTO = new BuildingDTO();
				buildingDTO.setId(classrooms.get(i).getBuilding().getIdbuilding());
				buildingDTO.setName(classrooms.get(i).getBuilding().getName());
				buildingDTO.setAddress(classrooms.get(i).getBuilding().getAddress());
				buildingDTO.setLat(classrooms.get(i).getBuilding().getLat());
				buildingDTO.setLng(classrooms.get(i).getBuilding().getLng());
				
				List<ToolDTO> toolDTOs = new ArrayList<ToolDTO>();
				List<ClassroomHasTool> toolsInClassroom = classroomHasToolRepository.getToolByClassroomId(classrooms.get(i).getIdclassroom());
				
				for(int j=0; j<toolsInClassroom.size(); j++) {
					ToolDTO toolDTO = new ToolDTO();
					toolDTO.setId(toolsInClassroom.get(j).getId().getIdtool());
					toolDTO.setName(toolsInClassroom.get(j).getTool().getName());
					toolDTO.setQuantity(toolsInClassroom.get(j).getQuantity());
					toolDTOs.add(toolDTO);
				}
				
				ClassroomDTO classroomDTO = new ClassroomDTO();
				classroomDTO.setId(classrooms.get(i).getIdclassroom());
				classroomDTO.setName(classrooms.get(i).getName());
				classroomDTO.setSeats(classrooms.get(i).getSeats());
				classroomDTO.setLat(classrooms.get(i).getLat());
				classroomDTO.setLng(classrooms.get(i).getLng());
				classroomDTO.setBuilding(buildingDTO);
				classroomDTO.setTool(toolDTOs);
				classroomDTOs.add(classroomDTO);
			}
			return classroomDTOs;
		} catch (Exception e) {
			throw new ClassroomNotFoundException();
		}
	}
	
	@Transactional(rollbackFor=ClassroomNotFoundException.class)
	public List<ClassroomDTO> getByIdBuildingAndName(int idBuilding, String name) throws ClassroomNotFoundException {
		
		try {
			List<Classroom> classrooms = classroomRepository.findClasses(name, idBuilding);
			List<ClassroomDTO> classroomDTOs = new ArrayList<ClassroomDTO>();
			for(int i=0; i<classrooms.size(); i++) {
				BuildingDTO buildingDTO = new BuildingDTO();
				buildingDTO.setId(classrooms.get(i).getBuilding().getIdbuilding());
				buildingDTO.setName(classrooms.get(i).getBuilding().getName());
				buildingDTO.setAddress(classrooms.get(i).getBuilding().getAddress());
				buildingDTO.setLat(classrooms.get(i).getBuilding().getLat());
				buildingDTO.setLng(classrooms.get(i).getBuilding().getLng());
				
				List<ToolDTO> toolDTOs = new ArrayList<ToolDTO>();
				List<ClassroomHasTool> toolsInClassroom = classroomHasToolRepository.getToolByClassroomId(classrooms.get(i).getIdclassroom());
				
				for(int j=0; j<toolsInClassroom.size(); j++) {
					ToolDTO toolDTO = new ToolDTO();
					toolDTO.setId(toolsInClassroom.get(j).getId().getIdtool());
					toolDTO.setName(toolsInClassroom.get(j).getTool().getName());
					toolDTO.setQuantity(toolsInClassroom.get(j).getQuantity());
					toolDTOs.add(toolDTO);
				}
				
				ClassroomDTO classroomDTO = new ClassroomDTO();
				classroomDTO.setId(classrooms.get(i).getIdclassroom());
				classroomDTO.setName(classrooms.get(i).getName());
				classroomDTO.setSeats(classrooms.get(i).getSeats());
				classroomDTO.setLat(classrooms.get(i).getLat());
				classroomDTO.setLng(classrooms.get(i).getLng());
				classroomDTO.setBuilding(buildingDTO);
				classroomDTO.setTool(toolDTOs);
				classroomDTOs.add(classroomDTO);
			}
			return classroomDTOs;
		} catch (Exception e) {
			throw new ClassroomNotFoundException();
		}
	}
	
	@Transactional(rollbackFor=ClassroomNotFoundException.class)
	public List<ClassroomDTO> getByName(String name) throws ClassroomNotFoundException {
		
		try {
			List<Classroom> classrooms = classroomRepository.findClassesByName(name);
			List<ClassroomDTO> classroomDTOs = new ArrayList<ClassroomDTO>();
			for(int i=0; i<classrooms.size(); i++) {
				BuildingDTO buildingDTO = new BuildingDTO();
				buildingDTO.setId(classrooms.get(i).getBuilding().getIdbuilding());
				buildingDTO.setName(classrooms.get(i).getBuilding().getName());
				buildingDTO.setAddress(classrooms.get(i).getBuilding().getAddress());
				buildingDTO.setLat(classrooms.get(i).getBuilding().getLat());
				buildingDTO.setLng(classrooms.get(i).getBuilding().getLng());
				
				List<ToolDTO> toolDTOs = new ArrayList<ToolDTO>();
				List<ClassroomHasTool> toolsInClassroom = classroomHasToolRepository.getToolByClassroomId(classrooms.get(i).getIdclassroom());
				
				for(int j=0; j<toolsInClassroom.size(); j++) {
					ToolDTO toolDTO = new ToolDTO();
					toolDTO.setId(toolsInClassroom.get(j).getId().getIdtool());
					toolDTO.setName(toolsInClassroom.get(j).getTool().getName());
					toolDTO.setQuantity(toolsInClassroom.get(j).getQuantity());
					toolDTOs.add(toolDTO);
				}
				
				ClassroomDTO classroomDTO = new ClassroomDTO();
				classroomDTO.setId(classrooms.get(i).getIdclassroom());
				classroomDTO.setName(classrooms.get(i).getName());
				classroomDTO.setSeats(classrooms.get(i).getSeats());
				classroomDTO.setLat(classrooms.get(i).getLat());
				classroomDTO.setLng(classrooms.get(i).getLng());
				classroomDTO.setBuilding(buildingDTO);
				classroomDTO.setTool(toolDTOs);
				classroomDTOs.add(classroomDTO);
			}
			return classroomDTOs;
		} catch (Exception e) {
			throw new ClassroomNotFoundException();
		}
	}
	
	@Transactional
	public ClassroomDTO save(ClassroomDTO classroomDTO) {
		Building building = new Building();
		building.setIdbuilding(classroomDTO.getBuilding().getId());
		building.setName(classroomDTO.getBuilding().getName());
		building.setAddress(classroomDTO.getBuilding().getAddress());
		building.setLat(classroomDTO.getBuilding().getLat());
		building.setLng(classroomDTO.getBuilding().getLng());
		
		Classroom classroom = new Classroom();
		try {
			classroom.setIdclassroom(classroomDTO.getId());
		} catch (Exception e) {
			// TODO: handle exception
		}
		classroom.setName(classroomDTO.getName());
		classroom.setSeats(classroomDTO.getSeats());
		classroom.setLat(classroomDTO.getLat());
		classroom.setLng(classroomDTO.getLng());
		classroom.setBuilding(building);
		
		Classroom newClassroom = classroomRepository.save(classroom);
		
		List<ClassroomHasTool> toolsInClassroom = new ArrayList<ClassroomHasTool>();
		
		for(int j=0; j<classroomDTO.getTool().size(); j++) {
			ClassroomHasTool toolInClassroom = new ClassroomHasTool();
			Tool tool = new Tool();
			tool.setIdtool(classroomDTO.getTool().get(j).getId());
			tool.setName(classroomDTO.getTool().get(j).getName());
			ClassroomHasToolId id = new ClassroomHasToolId();
			id.setIdclassroom(newClassroom.getIdclassroom());
			id.setIdtool(classroomDTO.getTool().get(j).getId());
			toolInClassroom.setId(id);
			toolInClassroom.setTool(tool);
			toolInClassroom.setQuantity(classroomDTO.getTool().get(j).getQuantity());
			toolsInClassroom.add(toolInClassroom);
			
			if(toolInClassroom.getQuantity() == 0) {
				classroomHasToolRepository.delete(toolInClassroom);
			} else {
				classroomHasToolRepository.save(toolInClassroom);
			}
			
		}
		
		BuildingDTO buildingDTO = new BuildingDTO();
		buildingDTO.setId(newClassroom.getBuilding().getIdbuilding());
		buildingDTO.setName(newClassroom.getBuilding().getName());
		buildingDTO.setAddress(newClassroom.getBuilding().getAddress());
		buildingDTO.setLat(newClassroom.getBuilding().getLat());
		buildingDTO.setLng(newClassroom.getBuilding().getLng());
		
		List<ToolDTO> toolDTOs = new ArrayList<ToolDTO>();

		List<ClassroomHasTool> newToolsInClassroom = classroomHasToolRepository.getToolByClassroomId(newClassroom.getIdclassroom());
		
		for(int j=0; j<newToolsInClassroom.size(); j++) {
			ToolDTO toolDTO = new ToolDTO();
			toolDTO.setId(newToolsInClassroom.get(j).getId().getIdtool());
			toolDTO.setName(newToolsInClassroom.get(j).getTool().getName());
			toolDTO.setQuantity(newToolsInClassroom.get(j).getQuantity());

			toolDTOs.add(toolDTO);
		}
		
		ClassroomDTO newClassroomDTO = new ClassroomDTO();
		newClassroomDTO.setId(newClassroom.getIdclassroom());
		newClassroomDTO.setName(newClassroom.getName());
		newClassroomDTO.setSeats(newClassroom.getSeats());
		newClassroomDTO.setLat(newClassroom.getLat());
		newClassroomDTO.setLng(newClassroom.getLng());
		newClassroomDTO.setBuilding(buildingDTO);
		newClassroomDTO.setTool(toolDTOs);
		
		
		return newClassroomDTO;
	}
	
	@Transactional
	public void delete(int id) throws ClassroomNotFoundException {
		// TODO Auto-generated method stub
		try {
			Classroom classroom = classroomRepository.findById(id).get();
			classroomRepository.delete(classroom);
		} catch (Exception e) {
			// TODO: handle exception
			throw new ClassroomNotFoundException();
		}

	}
	
	@Transactional(rollbackFor=ClassroomNotFoundException.class)
	public List<ClassroomDTO> getAvailableByIdBuilding(int idBuilding, TypeLessonDTO typeLessonDTO) throws ClassroomNotFoundException {
		
		try {
			List<Classroom> classrooms = classroomRepository.findAvailableClassesByBuild(idBuilding, typeLessonDTO.getScheduler().getTerm().getIdterm(), typeLessonDTO.getStart(), typeLessonDTO.getEnd(), typeLessonDTO.getDay().getIdDay());
			List<ClassroomDTO> classroomDTOs = new ArrayList<ClassroomDTO>();
			for(int i=0; i<classrooms.size(); i++) {
				BuildingDTO buildingDTO = new BuildingDTO();
				buildingDTO.setId(classrooms.get(i).getBuilding().getIdbuilding());
				buildingDTO.setName(classrooms.get(i).getBuilding().getName());
				buildingDTO.setAddress(classrooms.get(i).getBuilding().getAddress());
				buildingDTO.setLat(classrooms.get(i).getBuilding().getLat());
				buildingDTO.setLng(classrooms.get(i).getBuilding().getLng());
				
				ClassroomDTO classroomDTO = new ClassroomDTO();
				classroomDTO.setId(classrooms.get(i).getIdclassroom());
				classroomDTO.setName(classrooms.get(i).getName());
				classroomDTO.setSeats(classrooms.get(i).getSeats());
				classroomDTO.setLat(classrooms.get(i).getLat());
				classroomDTO.setLng(classrooms.get(i).getLng());
				classroomDTO.setBuilding(buildingDTO);
				classroomDTOs.add(classroomDTO);
			}
			return classroomDTOs;
		} catch (Exception e) {
			throw new ClassroomNotFoundException();
		}
	}

}
