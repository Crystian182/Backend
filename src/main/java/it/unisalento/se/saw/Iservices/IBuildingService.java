package it.unisalento.se.saw.Iservices;

import java.util.List;

import it.unisalento.se.saw.domain.Building;
import it.unisalento.se.saw.dto.BuildingDTO;
import it.unisalento.se.saw.exceptions.BuildingNotFoundException;

public interface IBuildingService {
	
	public List<BuildingDTO> getAll();
	public BuildingDTO getById(int id) throws BuildingNotFoundException;
	public BuildingDTO save(BuildingDTO buildingDTO);
	public void delete(int idBuilding) throws BuildingNotFoundException;

}