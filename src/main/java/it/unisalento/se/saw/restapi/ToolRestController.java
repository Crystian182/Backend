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

import it.unisalento.se.saw.Iservices.IToolService;
import it.unisalento.se.saw.domain.Classroom;
import it.unisalento.se.saw.dto.ClassroomDTO;
import it.unisalento.se.saw.dto.ToolDTO;
import it.unisalento.se.saw.exceptions.ClassroomNotFoundException;
import it.unisalento.se.saw.exceptions.InstrumentNotFoundException;

@RestController
@RequestMapping("/tool")
public class ToolRestController {
	
	@Autowired
	IToolService toolService;
	
	public ToolRestController() {
		super();
	}
	
	public ToolRestController(IToolService toolService) {
		this.toolService = toolService;
	}

	@GetMapping(value="/getAll", produces=MediaType.APPLICATION_JSON_VALUE)
	public List<ToolDTO> getAll(){
		return toolService.getAll();
	}
	
	@PostMapping(value="/save", produces=MediaType.APPLICATION_JSON_VALUE)
	public ToolDTO save(@RequestBody ToolDTO toolDTO) {
		return toolService.save(toolDTO);
	}
	
	@PostMapping(value="/delete/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public void delete(@PathVariable("id")int id) throws InstrumentNotFoundException {
		toolService.delete(id);
	}	
	
}
