package it.unisalento.se.saw.Iservices;

import java.util.List;

import it.unisalento.se.saw.dto.ToolDTO;
import it.unisalento.se.saw.exceptions.InstrumentNotFoundException;

public interface IToolService {
	
	public List<ToolDTO> getAll();
	public ToolDTO save(ToolDTO toolDTO);
	public void delete(int id) throws InstrumentNotFoundException;
	//public List<Instruments> getInstrumentsFromClassroom(int idClassroom);
}
