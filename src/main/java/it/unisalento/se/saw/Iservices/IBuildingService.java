package it.unisalento.se.saw.Iservices;

import java.util.List;

import it.unisalento.se.saw.domain.Building;
import it.unisalento.se.saw.exceptions.BuildingNotFoundException;

public interface IBuildingService {
	
	public List<Building> getAll();
	public Building getById(int idBuilding) throws BuildingNotFoundException;
	public Building edit(Building building) throws BuildingNotFoundException;
	public Building save(Building building);
	public void delete(int idBuilding) throws BuildingNotFoundException;

}
