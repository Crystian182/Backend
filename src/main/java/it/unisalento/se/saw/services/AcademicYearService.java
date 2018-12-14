package it.unisalento.se.saw.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unisalento.se.saw.Iservices.IAcademicYearService;
import it.unisalento.se.saw.domain.AcademicYear;
import it.unisalento.se.saw.domain.Term;
import it.unisalento.se.saw.dto.AcademicYearDTO;
import it.unisalento.se.saw.dto.BuildingDTO;
import it.unisalento.se.saw.dto.TermDTO;
import it.unisalento.se.saw.exceptions.BuildingNotFoundException;
import it.unisalento.se.saw.repositories.AcademicYearRepository;
import it.unisalento.se.saw.repositories.TermRepository;

@Service
public class AcademicYearService implements IAcademicYearService {
	
	@Autowired
	AcademicYearRepository academicYearRepository;
	
	@Autowired
	TermRepository termRepository;
	
	@Transactional()
	public List<TermDTO> getTermsByAcademicYearId(int id) {
		List<Term> terms = termRepository.getByAcademicYear(id);
		
		List<TermDTO> termDTOs = new ArrayList<TermDTO>();
		
		for(int i=0; i<terms.size(); i++) {
			TermDTO termDTO = new TermDTO();
			termDTO.setIdterm(terms.get(i).getIdterm());
			termDTO.setNumber(terms.get(i).getNumber());
			termDTOs.add(termDTO);
		}
		
		return termDTOs;
	}
	
	public List<AcademicYearDTO> getAllYearsOfCourse(int id) {
		List<AcademicYear> aa = academicYearRepository.getAllYearsByCourse(id);
		
		List<AcademicYearDTO> aaDTOs = new ArrayList<AcademicYearDTO>();
		
		for(int i=0; i < aa.size(); i++) {
			AcademicYearDTO aaDTO = new AcademicYearDTO();
			aaDTO.setIdacademicYear(aa.get(i).getIdacademicYear());
			aaDTO.setYear(aa.get(i).getYear());
			
			aaDTOs.add(aaDTO);
		}
		
		return aaDTOs;
	}

}
