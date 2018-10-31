package it.unisalento.se.saw.restapi;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.unisalento.se.saw.Iservices.IBuildingService;
import it.unisalento.se.saw.domain.Building;
import it.unisalento.se.saw.dto.BuildingDTO;
import it.unisalento.se.saw.exceptions.BuildingNotFoundException;

@RestController
@RequestMapping("/building")
public class BuildingRestController {
	
	@Autowired
	IBuildingService buildingService;
	
	public BuildingRestController() {
		super();
	}
	
	public BuildingRestController(IBuildingService buildingService) {
		this.buildingService = buildingService;
	}
	
	@GetMapping(value="/getAll", produces=MediaType.APPLICATION_JSON_VALUE)
	public List<BuildingDTO> getAll(){
		List<Building> buildings = buildingService.getAll();
		List<BuildingDTO> buildingDTOs = new ArrayList<BuildingDTO>();
		for(int i=0; i<buildings.size(); i++) {
			Building building = buildings.get(i);
			buildingDTOs.add(this.entityToDTO(building));
		}
		return buildingDTOs;
	}
	
	@GetMapping(value="/getById/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public BuildingDTO getById(@PathVariable("id")int id) throws BuildingNotFoundException {
		
		Building building = buildingService.getById(id);
		BuildingDTO buildingDTO = this.entityToDTO(building);
		
		return buildingDTO;
	}
	
	@PostMapping(value="/edit", produces=MediaType.APPLICATION_JSON_VALUE)
	public BuildingDTO edit(@RequestBody BuildingDTO buildingDTO) throws BuildingNotFoundException {
		
		Building building = buildingService.edit(this.DTOtoEntity(buildingDTO));		
		return this.entityToDTO(building);
	}
	
	@PostMapping(value="/save", produces=MediaType.APPLICATION_JSON_VALUE)
	public BuildingDTO save(@RequestBody BuildingDTO buildingDTO) {
		
		Building building = buildingService.save(this.DTOtoEntity(buildingDTO));		
		return this.entityToDTO(building);
	}
	
	@GetMapping(value="/delete/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public void delete(@PathVariable("id")int id) throws BuildingNotFoundException {
		buildingService.delete(id);
	}
	
	public BuildingDTO entityToDTO(Building building) {
		BuildingDTO buildingDTO = new BuildingDTO();
		buildingDTO.setId(building.getIdbuilding());
		buildingDTO.setName(building.getName());
		buildingDTO.setAddress(building.getAddress());
		buildingDTO.setLatitude(building.getLatitude());
		buildingDTO.setLongitude(building.getLongitude());
		return buildingDTO;
	}
	
	public Building DTOtoEntity(BuildingDTO buildingDTO) {
		Building building = new Building();
		try {
			buildingDTO.setId(building.getIdbuilding());
		} catch (Exception e) {
		}
		building.setName(buildingDTO.getName());
		building.setAddress(buildingDTO.getAddress());
		building.setLatitude(buildingDTO.getLatitude());
		building.setLongitude(buildingDTO.getLongitude());
		return building;
		
	}

}
