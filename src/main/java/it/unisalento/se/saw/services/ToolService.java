package it.unisalento.se.saw.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unisalento.se.saw.Iservices.IToolService;
import it.unisalento.se.saw.domain.Building;
import it.unisalento.se.saw.domain.Classroom;
import it.unisalento.se.saw.domain.Tool;
import it.unisalento.se.saw.dto.BuildingDTO;
import it.unisalento.se.saw.dto.ToolDTO;
import it.unisalento.se.saw.exceptions.ClassroomNotFoundException;
import it.unisalento.se.saw.exceptions.InstrumentNotFoundException;
import it.unisalento.se.saw.repositories.ClassroomHasToolRepository;
import it.unisalento.se.saw.repositories.ToolRepository;

@Service
public class ToolService implements IToolService{

	@Autowired
	ToolRepository toolRepository;
	
	@Autowired
	ClassroomHasToolRepository classroomHasInstrumentsRepository;
	
	@Transactional(readOnly=true)
	public List<ToolDTO> getAll(){
		List<ToolDTO> toolDTOs = new ArrayList<ToolDTO>();
		List<Tool> tools = toolRepository.findAll();
		for(int i=0; i<tools.size(); i++) {
			ToolDTO toolDTO = new ToolDTO();
			toolDTO.setId(tools.get(i).getIdtool());
			toolDTO.setName(tools.get(i).getName());
			toolDTOs.add(toolDTO);
		}
		return toolDTOs;
	}
	 
	/*@Transactional
	public List<ToolDTO> getInstrumentsFromClassroom(int idClassroom){
		List<ClassroomHasInstruments> couples = classroomHasInstrumentsRepository.findInstrumentsByClassroom(idClassroom);
		List<Instruments> instruments = new ArrayList<Instruments>();
		for (int i=0; i<couples.size(); i++) {
			ClassroomHasInstruments chi = new ClassroomHasInstruments();
			chi.setQuantity(couples.get(i).getQuantity());
			Set<ClassroomHasInstruments> classroomHasInstrumentses = new HashSet<ClassroomHasInstruments>(0);
			classroomHasInstrumentses.add(chi);
			Instruments instrument = new Instruments();
			instrument.setIdinstruments(couples.get(i).getInstruments().getIdinstruments());
			instrument.setName(couples.get(i).getInstruments().getName());
			instrument.setClassroomHasInstrumentses(classroomHasInstrumentses);
			
			instruments.add(instrument);
		}
		return instruments;
	}
	
/*	@Transactional
	public List<Instruments> insert(Instruments instruments) {
		return instrumentsRepository.insert(instruments);
	}
	*/
	
	@Transactional
	public ToolDTO save(ToolDTO toolDTO) {
		Tool tool = new Tool();
		tool.setIdtool(toolDTO.getId());
		tool.setName(toolDTO.getName());

		Tool newTool = toolRepository.save(tool);
		ToolDTO newToolDTO = new ToolDTO();
		newToolDTO.setId(newTool.getIdtool());
		newToolDTO.setName(newTool.getName());
		return newToolDTO;
	}

	@Transactional
	public void delete(int id) throws InstrumentNotFoundException {
		// TODO Auto-generated method stub
		try {
			Tool tool = toolRepository.findById(id).get();
			toolRepository.delete(tool);
		} catch (Exception e) {
			// TODO: handle exception
			throw new InstrumentNotFoundException();
		}
		
	}
	
}
