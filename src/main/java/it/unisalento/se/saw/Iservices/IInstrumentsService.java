package it.unisalento.se.saw.Iservices;

import java.util.List;

import it.unisalento.se.saw.domain.Instruments;
import it.unisalento.se.saw.exceptions.InstrumentNotFoundException;

public interface IInstrumentsService {
	
	public List<Instruments> getAll();
	public Instruments save(Instruments instruments);
	public void delete(int id) throws InstrumentNotFoundException;
	public List<Instruments> getInstrumentsFromClassroom(int idClassroom);
}
