package it.unisalento.se.saw.Iservices;

import java.io.IOException;
import java.util.List;

import it.unisalento.se.saw.dto.BuildingDTO;
import it.unisalento.se.saw.exceptions.BuildingNotFoundException;

public interface IBuildingService {
	
	public List<BuildingDTO> getAll() throws IOException;
	public BuildingDTO getById(int id) throws BuildingNotFoundException;
	public BuildingDTO save(BuildingDTO buildingDTO) throws BuildingNotFoundException;
	public void delete(int idBuilding) throws BuildingNotFoundException;

}
