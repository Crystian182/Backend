package it.unisalento.se.saw.Iservices;

import java.util.List;

import it.unisalento.se.saw.dto.AcademicYearDTO;
import it.unisalento.se.saw.dto.TermDTO;

public interface IAcademicYearService {
	
	public List<TermDTO> getTermsByAcademicYearId(int id);
	public List<AcademicYearDTO> getAllYearsOfCourse(int id);

}
