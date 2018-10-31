package it.unisalento.se.saw.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unisalento.se.saw.Iservices.IInstrumentsService;
import it.unisalento.se.saw.domain.Classroom;
import it.unisalento.se.saw.domain.ClassroomHasInstruments;
import it.unisalento.se.saw.domain.ClassroomHasInstrumentsId;
import it.unisalento.se.saw.domain.Instruments;
import it.unisalento.se.saw.exceptions.ClassroomNotFoundException;
import it.unisalento.se.saw.exceptions.InstrumentNotFoundException;
import it.unisalento.se.saw.repositories.ClassroomHasInstrumentsRepository;
import it.unisalento.se.saw.repositories.InstrumentsRepository;

@Service
public class InstrumentsService implements IInstrumentsService{

	@Autowired
	InstrumentsRepository instrumentsRepository;
	
	@Autowired
	ClassroomHasInstrumentsRepository classroomHasInstrumentsRepository;
	
	@Transactional(readOnly=true)
	public List<Instruments> getAll(){
		return instrumentsRepository.findAll();
	}
	 
	@Transactional
	public List<Instruments> getInstrumentsFromClassroom(int idClassroom){
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
	public Instruments save(Instruments instruments) {
		return instrumentsRepository.save(instruments);
	}

	@Transactional
	public void delete(int id) throws InstrumentNotFoundException {
		// TODO Auto-generated method stub
		try {
			Instruments instruments = instrumentsRepository.findById(id).get();
			instrumentsRepository.delete(instruments);
		} catch (Exception e) {
			// TODO: handle exception
			throw new InstrumentNotFoundException();
		}
		
	}
	
}
