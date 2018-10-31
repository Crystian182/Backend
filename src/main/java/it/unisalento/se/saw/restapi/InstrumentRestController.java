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

import it.unisalento.se.saw.Iservices.IInstrumentsService;
import it.unisalento.se.saw.domain.Classroom;
import it.unisalento.se.saw.domain.Instruments;
import it.unisalento.se.saw.dto.ClassroomDTO;
import it.unisalento.se.saw.dto.InstrumentDTO;
import it.unisalento.se.saw.exceptions.ClassroomNotFoundException;
import it.unisalento.se.saw.exceptions.InstrumentNotFoundException;

@RestController
@RequestMapping("/instruments")
public class InstrumentRestController {
	
	@Autowired
	IInstrumentsService instrumentsService;
	
	public InstrumentRestController() {
		super();
	}
	
	public InstrumentRestController(IInstrumentsService instrumentsService) {
		this.instrumentsService = instrumentsService;
	}

	@GetMapping(value="/getAll", produces=MediaType.APPLICATION_JSON_VALUE)
	public List<InstrumentDTO> getAll(){
		List <Instruments> instrumentses = instrumentsService.getAll();
		List <InstrumentDTO> instrumentDTOs = new ArrayList<InstrumentDTO>();
		for (int i=0; i<instrumentses.size(); i++) {
			Instruments instruments = instrumentses.get(i);
			instrumentDTOs.add(this.EntityToDTO(instruments));
		}
		return instrumentDTOs;
	}
	
	
	@PostMapping(value="/save", produces=MediaType.APPLICATION_JSON_VALUE)
	public InstrumentDTO save(@RequestBody InstrumentDTO instrumentDTO) {
		
		Instruments instruments = instrumentsService.save(this.DTOToEntity(instrumentDTO));
		return this.EntityToDTO(instruments);
	}
	
	@PostMapping(value="/delete/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public void delete(@PathVariable("id")int id) throws InstrumentNotFoundException {
		instrumentsService.delete(id);
	}	
	
	
	public InstrumentDTO EntityToDTO(Instruments instruments) {
		InstrumentDTO instrumentDTO = new InstrumentDTO();
		instrumentDTO.setId(instruments.getIdinstruments());
		instrumentDTO.setName(instruments.getName());
	
		return instrumentDTO;
	}
	
	public Instruments DTOToEntity(InstrumentDTO instrumentDTO) {
		Instruments instruments = new Instruments();
		instruments.setIdinstruments(instrumentDTO.getId());
		instruments.setName(instrumentDTO.getName());
	
		return instruments;
	}
	
}
