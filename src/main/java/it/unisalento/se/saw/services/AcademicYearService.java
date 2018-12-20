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
			termDTO.setStart(terms.get(i).getStart());
			termDTO.setEnd(terms.get(i).getEnd());
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

	@Transactional()
	public List<AcademicYearDTO> getAll() {
		List<AcademicYear> academicYears = academicYearRepository.findAll();
		List<AcademicYearDTO> academicYearDTOs = new ArrayList<AcademicYearDTO>();
		for(int i=0; i<academicYears.size(); i++) {
			AcademicYearDTO academicYearDTO = new AcademicYearDTO();
			academicYearDTO.setIdacademicYear(academicYears.get(i).getIdacademicYear());
			academicYearDTO.setYear(academicYears.get(i).getYear());
			List<Term> terms = termRepository.getByAcademicYear(academicYears.get(i).getIdacademicYear());
			List<TermDTO> termDTOs= new ArrayList<TermDTO>();
			for(int k=0; k<terms.size(); k++) {
				TermDTO termDTO = new TermDTO();
				termDTO.setIdterm(terms.get(k).getIdterm());
				termDTO.setStart(terms.get(k).getStart());
				termDTO.setEnd(terms.get(k).getEnd());
				termDTOs.add(termDTO);
			}
			academicYearDTO.setTerms(termDTOs);
			academicYearDTOs.add(academicYearDTO);
		}
		return  academicYearDTOs;
	}
	
	@Transactional
	public AcademicYearDTO save(AcademicYearDTO academicYearDTO) {
		AcademicYear academicYear = new AcademicYear();
		academicYear.setIdacademicYear(academicYearDTO.getIdacademicYear());
		academicYear.setYear(academicYearDTO.getYear());
		
		AcademicYear newAcademicYear = academicYearRepository.save(academicYear);
		
		AcademicYearDTO newAcademicYearDTO = new AcademicYearDTO();
		newAcademicYearDTO.setIdacademicYear(newAcademicYear.getIdacademicYear());
		newAcademicYearDTO.setYear(newAcademicYear.getYear());
		
		return newAcademicYearDTO;
	}
	
	@Transactional
	public TermDTO saveTerm(TermDTO termDTO) {
		Term term = new Term();
		term.setIdterm(termDTO.getIdterm());
		term.setStart(termDTO.getStart());
		term.setEnd(termDTO.getEnd());
		AcademicYear academicYear = new AcademicYear();
		academicYear.setIdacademicYear(termDTO.getAcademicYear().getIdacademicYear());
		academicYear.setYear(termDTO.getAcademicYear().getYear());
		term.setAcademicYear(academicYear);
		Term newTerm = termRepository.save(term);
		TermDTO newTermDTO = new TermDTO();
		newTermDTO.setIdterm(newTerm.getIdterm());
		newTermDTO.setStart(newTerm.getStart());
		newTermDTO.setEnd(newTerm.getEnd());
		AcademicYearDTO academicYearDTO = new AcademicYearDTO();
		academicYearDTO.setIdacademicYear(newTerm.getAcademicYear().getIdacademicYear());
		academicYearDTO.setYear(newTerm.getAcademicYear().getYear());
		newTermDTO.setAcademicYear(academicYearDTO);
		return newTermDTO;
	}

}
