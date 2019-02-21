package it.unisalento.se.saw.services;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unisalento.se.saw.Iservices.IBuildingService;
import it.unisalento.se.saw.domain.Building;
import it.unisalento.se.saw.domain.Classroom;
import it.unisalento.se.saw.domain.ClassroomHasTool;
import it.unisalento.se.saw.domain.ClassroomHasToolId;
import it.unisalento.se.saw.domain.Tool;
import it.unisalento.se.saw.domain.User;
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
	
	//private static final String location = "C:\\Users\\Federico\\Desktop\\Computer Engineering\\Backend\\Backend\\src\\main\\resources\\";
	private static final String location = "/home/Documents/buildings/";
	@Autowired
	BuildingRepository buildingRepository;
	
	@Autowired
	ClassroomRepository classroomRepository;
	
	@Autowired
	ClassroomHasToolRepository classroomHasToolRepository;
	
	@Transactional(readOnly=true)
	public List<BuildingDTO> getAll() throws IOException{
		List<BuildingDTO> buildingDTOs = new ArrayList<BuildingDTO>();
		List<Building> buildings = buildingRepository.findAll();
		for(int i=0; i<buildings.size(); i++) {
			BuildingDTO buildingDTO = new BuildingDTO();
			buildingDTO.setId(buildings.get(i).getIdbuilding());
			buildingDTO.setName(buildings.get(i).getName());
			buildingDTO.setAddress(buildings.get(i).getAddress());
			buildingDTO.setLat(buildings.get(i).getLat());
			buildingDTO.setLng(buildings.get(i).getLng());
			try {
				buildingDTO.setPic(this.getImage(buildings.get(i).getFile().getIdfile()));
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			List<Classroom> classrooms = classroomRepository.findClassesByBuild(buildings.get(i).getIdbuilding());
			if(classrooms != null) {
				List<ClassroomDTO> classroomDTOs = new ArrayList<ClassroomDTO>();
				for(int j = 0; j<classrooms.size(); j++) {
					ClassroomDTO classroomDTO = new ClassroomDTO();
					classroomDTO.setId(classrooms.get(j).getIdclassroom());
					classroomDTO.setName(classrooms.get(j).getName());
					classroomDTO.setSeats(classrooms.get(j).getSeats());
					classroomDTO.setLat(classrooms.get(j).getLat());
					classroomDTO.setLng(classrooms.get(j).getLng());
					
					List<ToolDTO> toolDTOs = new ArrayList<ToolDTO>();
					List<ClassroomHasTool> toolsInClassroom = classroomHasToolRepository.getToolByClassroomId(classrooms.get(j).getIdclassroom());
					
					for(int h=0; h<toolsInClassroom.size(); h++) {
						ToolDTO toolDTO = new ToolDTO();
						toolDTO.setId(toolsInClassroom.get(h).getId().getIdtool());
						toolDTO.setName(toolsInClassroom.get(h).getTool().getName());
						toolDTO.setQuantity(toolsInClassroom.get(h).getQuantity());
						toolDTOs.add(toolDTO);
					}
					classroomDTO.setTool(toolDTOs);
					classroomDTOs.add(classroomDTO);
				}
				buildingDTO.setClassrooms(classroomDTOs);
			}
			
			buildingDTOs.add(buildingDTO);
		}
		return buildingDTOs;
	}
	
	
	public String getImage(int idfile) throws IOException {
		BufferedImage img = ImageIO.read(new File(location + idfile));             
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(img, "jpg", baos);
		baos.flush();
		Base64 base = new Base64(false);
		String encodedImage = base.encodeToString(baos.toByteArray());
		baos.close();
		return encodedImage = java.net.URLEncoder.encode(encodedImage, "ISO-8859-1");
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
					BuildingDTO buildingDTO2 = new BuildingDTO();
					buildingDTO2.setId(building.getIdbuilding());
					buildingDTO2.setName(building.getName());
					classroomDTO.setBuilding(buildingDTO2);
					
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
	public BuildingDTO save(BuildingDTO buildingDTO) throws BuildingNotFoundException {
		Building building = new Building();
		try {
			building.setIdbuilding(buildingDTO.getId());
			building.setFile(buildingRepository.getOne(buildingDTO.getId()).getFile());
		} catch (Exception e) {
			// TODO: handle exception
		}
		building.setName(buildingDTO.getName());
		building.setAddress(buildingDTO.getAddress());
		building.setLat(buildingDTO.getLat());
		building.setLng(buildingDTO.getLng());
		
		Building newBuilding = buildingRepository.save(building);
		
		if(buildingDTO.getClassrooms() != null) {
			for(int i=0; i<buildingDTO.getClassrooms().size(); i++) {
				Classroom classroom = new Classroom();
				classroom.setName(buildingDTO.getClassrooms().get(i).getName());
				try {
					classroom.setIdclassroom(buildingDTO.getClassrooms().get(i).getId());
				} catch (Exception e) {
					// TODO: handle exception
				}
				classroom.setLat(buildingDTO.getClassrooms().get(i).getLat());
				classroom.setLng(buildingDTO.getClassrooms().get(i).getLng());
				classroom.setSeats(buildingDTO.getClassrooms().get(i).getSeats());
				classroom.setBuilding(newBuilding);
				
				Classroom newClassroom = classroomRepository.save(classroom);
				classroomHasToolRepository.removeAllClassroomId(newClassroom.getIdclassroom());
				
				for(int j=0; j<buildingDTO.getClassrooms().get(i).getTool().size(); j++) {
					ClassroomHasTool toolInClassroom = new ClassroomHasTool();
					Tool tool = new Tool();
					tool.setIdtool(buildingDTO.getClassrooms().get(i).getTool().get(j).getId());
					tool.setName(buildingDTO.getClassrooms().get(i).getTool().get(j).getName());
					ClassroomHasToolId id = new ClassroomHasToolId();
					id.setIdclassroom(newClassroom.getIdclassroom());
					id.setIdtool(buildingDTO.getClassrooms().get(i).getTool().get(j).getId());
					toolInClassroom.setId(id);
					toolInClassroom.setTool(tool);
					toolInClassroom.setQuantity(buildingDTO.getClassrooms().get(i).getTool().get(j).getQuantity());
					
					/*if(toolInClassroom.getQuantity() == 0) {
						classroomHasToolRepository.delete(toolInClassroom);
					} else {*/
					if (buildingDTO.getClassrooms().get(i).getTool().get(j).getQuantity() != 0) {
						classroomHasToolRepository.save(toolInClassroom);
					}
					//}
					
				}
			}
		}
		
		return getById(newBuilding.getIdbuilding());
		
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
