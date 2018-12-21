package it.unisalento.se.saw.Iservices;

import java.util.List;

import it.unisalento.se.saw.dto.SubjectDTO;
import it.unisalento.se.saw.dto.TypeSubjectDTO;
import it.unisalento.se.saw.exceptions.SubjectNotFoundException;

public interface ISubjectService {
	
	public List<SubjectDTO> getAll();
	public SubjectDTO getById(int id) throws SubjectNotFoundException;
	public List<SubjectDTO> getByIdCourse(int id) throws SubjectNotFoundException;
	public SubjectDTO save(SubjectDTO subjectDTO);
	public void delete(int id) throws SubjectNotFoundException;
	public List<TypeSubjectDTO> getAllSubjectTypes();
	public List<SubjectDTO> getByIdTeacher(int id) throws SubjectNotFoundException;
	public List<SubjectDTO> saveAll(List<SubjectDTO> subjectDTOs);
	public TypeSubjectDTO saveType(TypeSubjectDTO typeSubjectDTO);
}
