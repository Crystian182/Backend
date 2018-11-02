package it.unisalento.se.saw.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unisalento.se.saw.Iservices.IBuildingService;
import it.unisalento.se.saw.domain.Building;
import it.unisalento.se.saw.domain.Classroom;
import it.unisalento.se.saw.dto.BuildingDTO;
import it.unisalento.se.saw.exceptions.BuildingNotFoundException;
import it.unisalento.se.saw.exceptions.ClassroomNotFoundException;
import it.unisalento.se.saw.repositories.BuildingRepository;
import it.unisalento.se.saw.repositories.UserRepository;

@Service
public class BuildingService implements IBuildingService{
	
	@Autowired
	BuildingRepository buildingRepository;
	
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
