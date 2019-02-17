package it.unisalento.se.saw.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import it.unisalento.se.saw.domain.AcademicYear;
import it.unisalento.se.saw.domain.Term;
import it.unisalento.se.saw.dto.AcademicYearDTO;
import it.unisalento.se.saw.dto.TermDTO;
import it.unisalento.se.saw.repositories.AcademicYearRepository;
import it.unisalento.se.saw.repositories.TermRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.any;

@RunWith(MockitoJUnitRunner.class)
public class AcademicYearServiceTest {

    @Mock
    AcademicYearRepository academicYearRepository;
    
    @Mock
    TermRepository termRepository;

    @InjectMocks
    AcademicYearService academicYearService;

    @Test
    public void getAllTest() {
    	
    	Date mockDate = new Date();
    	
    	List<Term> terms = new ArrayList<Term>();
		
		Term term1 = new Term();
		term1.setIdterm(1);
		term1.setStart(mockDate);
		term1.setEnd(mockDate);
		terms.add(term1);
		
		Term term2 = new Term();
		term2.setIdterm(2);
		term2.setStart(mockDate);
		term2.setEnd(mockDate);
		terms.add(term2);
		
		List<AcademicYear> years = new ArrayList<AcademicYear>();

		AcademicYear year1 = new AcademicYear();
		year1.setIdacademicYear(1);
		year1.setYear(2018);
		years.add(year1);

        when(academicYearRepository.findAll()).thenReturn(years);
        when(termRepository.getByAcademicYear(year1.getIdacademicYear())).thenReturn(terms);

        List<AcademicYearDTO> yearsDTOs = academicYearService.getAll();
        assertEquals(year1.getIdacademicYear(), yearsDTOs.get(0).getIdacademicYear());
    }

    @Test
    public void getTermsByAcademicYearId() {
    	
    	Date mockDate = new Date();
    	
    	List<Term> terms = new ArrayList<Term>();
		
		Term term1 = new Term();
		term1.setIdterm(1);
		term1.setStart(mockDate);
		term1.setEnd(mockDate);
		terms.add(term1);
		
		Term term2 = new Term();
		term2.setIdterm(2);
		term2.setStart(mockDate);
		term2.setEnd(mockDate);
		terms.add(term2);
		
        when(termRepository.getByAcademicYear(1)).thenReturn(terms);

        List<TermDTO> termDTOs = academicYearService.getTermsByAcademicYearId(1);
        assertEquals((Integer) 1, termDTOs.get(0).getIdterm());
    }
    
    @Test
    public void getAllYearsOfCourseTest() {
    	
    	Date mockDate = new Date();
    	
    	List<Term> terms = new ArrayList<Term>();
		
		Term term1 = new Term();
		term1.setIdterm(1);
		term1.setStart(mockDate);
		term1.setEnd(mockDate);
		terms.add(term1);
		
		Term term2 = new Term();
		term2.setIdterm(2);
		term2.setStart(mockDate);
		term2.setEnd(mockDate);
		terms.add(term2);
		
		List<AcademicYear> years = new ArrayList<AcademicYear>();

		AcademicYear year1 = new AcademicYear();
		year1.setIdacademicYear(1);
		year1.setYear(2018);
		years.add(year1);

        when(academicYearRepository.getAllYearsByCourse(1)).thenReturn(years);

        List<AcademicYearDTO> yearsDTOs = academicYearService.getAllYearsOfCourse(1);
        assertEquals(year1.getIdacademicYear(), yearsDTOs.get(0).getIdacademicYear());
    }
    
    @Test
    public void saveTest() {
    	AcademicYear year1 = new AcademicYear();
		year1.setIdacademicYear(1);
		year1.setYear(2018);
		
		AcademicYearDTO yearDTO1 = new AcademicYearDTO();
		yearDTO1.setIdacademicYear(1);
		yearDTO1.setYear(2018);

        when(academicYearRepository.save(any(AcademicYear.class))).thenReturn(year1);

        AcademicYearDTO y = academicYearService.save(yearDTO1);
        assertEquals(yearDTO1.getIdacademicYear(), y.getIdacademicYear());
    	
    }
    
    @Test
    public void saveTermTest() {
    	
    	AcademicYear year1 = new AcademicYear();
		year1.setIdacademicYear(1);
		year1.setYear(2018);
    	
    	Date mockDate = new Date();
    	Term term1 = new Term();
		term1.setIdterm(1);
		term1.setStart(mockDate);
		term1.setEnd(mockDate);
		term1.setAcademicYear(year1);
		
		AcademicYearDTO yearDTO1 = new AcademicYearDTO();
		yearDTO1.setIdacademicYear(1);
		yearDTO1.setYear(2018);
		
		TermDTO termDTO1 = new TermDTO();
		termDTO1.setIdterm(1);
		termDTO1.setStart(mockDate);
		termDTO1.setEnd(mockDate);
		termDTO1.setAcademicYear(yearDTO1);
		
        when(termRepository.save(any(Term.class))).thenReturn(term1);

        TermDTO termDTO = academicYearService.saveTerm(termDTO1);
        assertEquals(termDTO1.getIdterm(), termDTO.getIdterm());
    	
    }
    
    @Test
    public void saveAllTermTest() {
    	
    	AcademicYear year1 = new AcademicYear();
		year1.setIdacademicYear(1);
		year1.setYear(2018);
    	
    	List<Term> terms = new ArrayList<Term>();
    	Date mockDate = new Date();
    	Term term1 = new Term();
		term1.setIdterm(1);
		term1.setStart(mockDate);
		term1.setEnd(mockDate);
		term1.setAcademicYear(year1);
		term1.setNumber(1);
		terms.add(term1);
		
		AcademicYearDTO yearDTO1 = new AcademicYearDTO();
		yearDTO1.setIdacademicYear(1);
		yearDTO1.setYear(2018);
		
		List<TermDTO> termDTOs = new ArrayList<TermDTO>();
		TermDTO termDTO1 = new TermDTO();
		termDTO1.setIdterm(1);
		termDTO1.setStart(mockDate);
		termDTO1.setEnd(mockDate);
		termDTO1.setAcademicYear(yearDTO1);
		termDTOs.add(termDTO1);
		
        when(termRepository.saveAll(Matchers.anyListOf(Term.class))).thenReturn(terms);

        List<TermDTO> termDTOsres = academicYearService.saveAllTerm(termDTOs);
        assertEquals(termDTO1.getIdterm(), termDTOsres.get(0).getIdterm());
    	
    }
    
}