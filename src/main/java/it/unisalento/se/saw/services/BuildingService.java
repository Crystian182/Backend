package it.unisalento.se.saw.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unisalento.se.saw.Iservices.IBuildingService;
import it.unisalento.se.saw.domain.Building;
import it.unisalento.se.saw.domain.Classroom;
import it.unisalento.se.saw.exceptions.BuildingNotFoundException;
import it.unisalento.se.saw.exceptions.ClassroomNotFoundException;
import it.unisalento.se.saw.repositories.BuildingRepository;
import it.unisalento.se.saw.repositories.UserRepository;

@Service
public class BuildingService implements IBuildingService{
	
	@Autowired
	BuildingRepository buildingRepository;
	
	@Transactional(readOnly=true)
	public List<Building> getAll(){
		return buildingRepository.findAll();
	}
	
	@Transactional(rollbackFor=BuildingNotFoundException.class)
	public Building getById(int idBuilding) throws BuildingNotFoundException {
		
		try {
			return buildingRepository.findById(idBuilding).get();
		} catch (Exception e) {
			throw new BuildingNotFoundException();
		}
	}
	
	@Transactional
	public Building edit(Building building) throws BuildingNotFoundException {
		try {
			buildingRepository.findById(building.getIdbuilding()).get();
			return buildingRepository.save(building);
		} catch (Exception e) {
			throw new BuildingNotFoundException();
		}
		
	}
	
	@Transactional
	public Building save(Building building) {
		return buildingRepository.save(building);
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
