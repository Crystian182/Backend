package it.unisalento.se.saw.restapi;

import java.io.IOException;
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
	public List<BuildingDTO> getAll() throws IOException{
		return buildingService.getAll();
	}
	
	@GetMapping(value="/getById/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public BuildingDTO getById(@PathVariable("id")int id) throws BuildingNotFoundException {
		return buildingService.getById(id);
	}
	
	@PostMapping(value="/save", produces=MediaType.APPLICATION_JSON_VALUE)
	public BuildingDTO save(@RequestBody BuildingDTO buildingDTO) throws BuildingNotFoundException {		
		return buildingService.save(buildingDTO);
	}
	
	@GetMapping(value="/delete/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public void delete(@PathVariable("id")int id) throws BuildingNotFoundException {
		buildingService.delete(id);
	}

}
