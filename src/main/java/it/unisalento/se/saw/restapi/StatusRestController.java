package it.unisalento.se.saw.restapi;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.unisalento.se.saw.Iservices.IStatusService;
import it.unisalento.se.saw.domain.Status;
import it.unisalento.se.saw.dto.StatusDTO;


@RestController
@RequestMapping("/status")
public class StatusRestController {

	@Autowired
	IStatusService statusService;
	
	public StatusRestController() {
		super();
	}
	
	public StatusRestController(IStatusService statusService) {
		this.statusService = statusService;
	}
	
	@GetMapping(value="/getAll", produces=MediaType.APPLICATION_JSON_VALUE)
	public List<StatusDTO> getAll(){
		List<Status> stati = statusService.getAll();
		List<StatusDTO> statiDTO = new ArrayList<StatusDTO>();
		for(int i=0; i<stati.size(); i++) {
			Status status = stati.get(i);
			statiDTO.add(this.EntityToDTO(status));
		}
		return statiDTO;
	}
	
	public StatusDTO EntityToDTO(Status status) {
	
		StatusDTO statusDTO = new StatusDTO();
		statusDTO.setIdstatus(status.getIdstatus());
		statusDTO.setDescription(status.getDescription());
		return statusDTO;
	}
}
