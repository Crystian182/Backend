package it.unisalento.se.saw.services;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

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
		
		ListIterator<Term> i = terms.listIterator();
		while (i.hasNext()) {
			TermDTO termDTO = new TermDTO();
			Term obj = i.next();
			termDTO.setIdterm(obj.getIdterm());
			termDTO.setStart(obj.getStart());
			termDTO.setEnd(obj.getEnd());
			termDTOs.add(termDTO);
		}
		
		return termDTOs;
	}
	
	public List<AcademicYearDTO> getAllYearsOfCourse(int id) {
		List<AcademicYear> aa = academicYearRepository.getAllYearsByCourse(id);
		
		List<AcademicYearDTO> aaDTOs = new ArrayList<AcademicYearDTO>();
		
		ListIterator<AcademicYear> i = aa.listIterator();
		while (i.hasNext()) {
			AcademicYearDTO aaDTO = new AcademicYearDTO();
			AcademicYear obj = i.next();
			aaDTO.setIdacademicYear(obj.getIdacademicYear());
			aaDTO.setYear(obj.getYear());
			
			aaDTOs.add(aaDTO);
		}
		
		return aaDTOs;
	}

	@Transactional()
	public List<AcademicYearDTO> getAll() {
		List<AcademicYear> academicYears = academicYearRepository.findAll();
		List<AcademicYearDTO> academicYearDTOs = new ArrayList<AcademicYearDTO>();
		
		List<AcademicYearDTO> aaDTOs = new ArrayList<AcademicYearDTO>();
		
		ListIterator<AcademicYear> i = academicYears.listIterator();
		while (i.hasNext()) {
			AcademicYearDTO aaDTO = new AcademicYearDTO();
			AcademicYear obj = i.next();
			aaDTO.setIdacademicYear(obj.getIdacademicYear());
			aaDTO.setYear(obj.getYear());
			List<Term> terms = termRepository.getByAcademicYear(obj.getIdacademicYear());
			List<TermDTO> termDTOs= new ArrayList<TermDTO>();
			ListIterator<Term> it = terms.listIterator();
			while (it.hasNext()) {
				TermDTO termDTO = new TermDTO();
				Term obj2 = it.next();
				termDTO.setIdterm(obj2.getIdterm());
				termDTO.setStart(obj2.getStart());
				termDTO.setEnd(obj2.getEnd());
				termDTOs.add(termDTO);
			}
			aaDTO.setTerms(termDTOs);
			academicYearDTOs.add(aaDTO);
			
			aaDTOs.add(aaDTO);
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
	
	@Transactional
	public List<TermDTO> saveAllTerm(List<TermDTO> termDTOs) {
		List<Term> terms = new ArrayList<Term>();
		ListIterator<TermDTO> i = termDTOs.listIterator();
		while (i.hasNext()) {
			TermDTO obj = i.next();

			Term term = new Term();
			term.setIdterm(obj.getIdterm());
			term.setStart(obj.getStart());
			term.setEnd(obj.getEnd());
			AcademicYear academicYear = new AcademicYear();
			academicYear.setIdacademicYear(obj.getAcademicYear().getIdacademicYear());
			academicYear.setYear(obj.getAcademicYear().getYear());
			term.setAcademicYear(academicYear);
			terms.add(term);
		}
		List<Term> newterms = termRepository.saveAll(terms);
		
		List<TermDTO> newtermDTOs = new ArrayList<TermDTO>();
		
		ListIterator<Term> it = newterms.listIterator();
		while (it.hasNext()) {
			TermDTO termDTO = new TermDTO();
			Term obj = it.next();
			termDTO.setIdterm(obj.getIdterm());
			termDTO.setStart(obj.getStart());
			termDTO.setEnd(obj.getEnd());
			AcademicYearDTO academicYearDTO = new AcademicYearDTO();
			academicYearDTO.setIdacademicYear(obj.getAcademicYear().getIdacademicYear());
			academicYearDTO.setYear(obj.getAcademicYear().getYear());
			termDTO.setAcademicYear(academicYearDTO);
			newtermDTOs.add(termDTO);
		}
		return newtermDTOs;
	}
}
