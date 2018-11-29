package it.unisalento.se.saw.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unisalento.se.saw.Iservices.IBuildingService;
import it.unisalento.se.saw.domain.Building;
import it.unisalento.se.saw.domain.Classroom;
import it.unisalento.se.saw.domain.ClassroomHasTool;
import it.unisalento.se.saw.domain.ClassroomHasToolId;
import it.unisalento.se.saw.domain.Tool;
import it.unisalento.se.saw.dto.BuildingDTO;
import it.unisalento.se.saw.dto.ClassroomDTO;
import it.unisalento.se.saw.dto.ToolDTO;
import it.unisalento.se.saw.exceptions.BuildingNotFoundException;
import it.unisalento.se.saw.exceptions.ClassroomNotFoundException;
import it.unisalento.se.saw.repositories.BuildingRepository;
import it.unisalento.se.saw.repositories.ClassroomHasToolRepository;
import it.unisalento.se.saw.repositories.ClassroomRepository;
import it.unisalento.se.saw.repositories.UserRepository;

@Service
public class BuildingService implements IBuildingService{
	
	@Autowired
	BuildingRepository buildingRepository;
	
	@Autowired
	ClassroomRepository classroomRepository;
	
	@Autowired
	ClassroomHasToolRepository classroomHasToolRepository;
	
	@Transactional(readOnly=true)
	public List<BuildingDTO> getAll(){
		List<BuildingDTO> buildingDTOs = new ArrayList<BuildingDTO>();
		List<Building> buildings = buildingRepository.findAll();
		for(int i=0; i<buildings.size(); i++) {
			BuildingDTO buildingDTO = new BuildingDTO();
			buildingDTO.setId(buildings.get(i).getIdbuilding());
			buildingDTO.setName(buildings.get(i).getName());
			buildingDTO.setAddress(buildings.get(i).getAddress());
			buildingDTO.setLat(buildings.get(i).getLat());
			buildingDTO.setLng(buildings.get(i).getLng());
			buildingDTOs.add(buildingDTO);
		}
		return buildingDTOs;
	}
	
	@Transactional(rollbackFor=BuildingNotFoundException.class)
	public BuildingDTO getById(int idBuilding) throws BuildingNotFoundException {
		try {
			Building building = buildingRepository.findById(idBuilding).get();
			BuildingDTO buildingDTO = new BuildingDTO();
			buildingDTO.setId(building.getIdbuilding());
			buildingDTO.setName(building.getName());
			buildingDTO.setAddress(building.getAddress());
			buildingDTO.setLat(building.getLat());
			buildingDTO.setLng(building.getLng());
			
			List<Classroom> classrooms = classroomRepository.findClassesByBuild(building.getIdbuilding());
			if(classrooms != null) {
				List<ClassroomDTO> classroomDTOs = new ArrayList<ClassroomDTO>();
				for(int i = 0; i<classrooms.size(); i++) {
					ClassroomDTO classroomDTO = new ClassroomDTO();
					classroomDTO.setId(classrooms.get(i).getIdclassroom());
					classroomDTO.setName(classrooms.get(i).getName());
					classroomDTO.setSeats(classrooms.get(i).getSeats());
					classroomDTO.setLat(classrooms.get(i).getLat());
					classroomDTO.setLng(classrooms.get(i).getLng());
					
					List<ToolDTO> toolDTOs = new ArrayList<ToolDTO>();
					List<ClassroomHasTool> toolsInClassroom = classroomHasToolRepository.getToolByClassroomId(classrooms.get(i).getIdclassroom());
					
					for(int j=0; j<toolsInClassroom.size(); j++) {
						ToolDTO toolDTO = new ToolDTO();
						toolDTO.setId(toolsInClassroom.get(j).getId().getIdtool());
						toolDTO.setName(toolsInClassroom.get(j).getTool().getName());
						toolDTO.setQuantity(toolsInClassroom.get(j).getQuantity());
						toolDTOs.add(toolDTO);
					}
					classroomDTO.setTool(toolDTOs);
					classroomDTOs.add(classroomDTO);
				}
				buildingDTO.setClassrooms(classroomDTOs);
			}
			return buildingDTO;
		} catch (Exception e) {
			throw new BuildingNotFoundException();
		}
	}
	
	@Transactional
	public BuildingDTO save(BuildingDTO buildingDTO) {
		Building building = new Building();
		building.setIdbuilding(buildingDTO.getId());
		building.setName(buildingDTO.getName());
		building.setAddress(buildingDTO.getAddress());
		building.setLat(buildingDTO.getLat());
		building.setLng(buildingDTO.getLng());
		
		if(buildingDTO.getClassrooms() != null) {
			for(int i=0; i<buildingDTO.getClassrooms().size(); i++) {
				Classroom classroom = new Classroom();
				classroom.setName(buildingDTO.getClassrooms().get(i).getName());
				classroom.setIdclassroom(buildingDTO.getClassrooms().get(i).getId());
				classroom.setLat(buildingDTO.getClassrooms().get(i).getLat());
				classroom.setLng(buildingDTO.getClassrooms().get(i).getLng());
				classroom.setSeats(buildingDTO.getClassrooms().get(i).getSeats());
				classroom.setBuilding(building);
				
				classroomRepository.save(classroom);
				
				List<ClassroomHasTool> toolsInClassroom = new ArrayList<ClassroomHasTool>();
				
				for(int j=0; j<buildingDTO.getClassrooms().get(i).getTool().size(); j++) {
					ClassroomHasTool toolInClassroom = new ClassroomHasTool();
					Tool tool = new Tool();
					tool.setIdtool(buildingDTO.getClassrooms().get(i).getTool().get(j).getId());
					tool.setName(buildingDTO.getClassrooms().get(i).getTool().get(j).getName());
					ClassroomHasToolId id = new ClassroomHasToolId();
					id.setIdclassroom(buildingDTO.getClassrooms().get(i).getId());
					id.setIdtool(buildingDTO.getClassrooms().get(i).getTool().get(j).getId());
					toolInClassroom.setId(id);
					toolInClassroom.setTool(tool);
					toolInClassroom.setQuantity(buildingDTO.getClassrooms().get(i).getTool().get(j).getQuantity());
					toolsInClassroom.add(toolInClassroom);
					
					if(toolInClassroom.getQuantity() == 0) {
						classroomHasToolRepository.delete(toolInClassroom);
					} else {
						classroomHasToolRepository.save(toolInClassroom);
					}
					
				}
			}
		}
		Building newBuilding = buildingRepository.save(building);
		BuildingDTO newBuildingDTO = new BuildingDTO();
		newBuildingDTO.setId(newBuilding.getIdbuilding());
		newBuildingDTO.setName(newBuilding.getName());
		newBuildingDTO.setAddress(newBuilding.getAddress());
		newBuildingDTO.setLat(newBuilding.getLat());
		newBuildingDTO.setLng(newBuilding.getLng());
		return newBuildingDTO;
	}
	
	@Transactional
	public void delete(int id) throws BuildingNotFoundException {
		// TODO Auto-generated method stub
		try {
			Building building = buildingRepository.findById(id).get();
			buildingRepository.delete(building);
		} catch (Exception e) {
			// TODO: handle exception
			throw new BuildingNotFoundException();
		}

	}

}
