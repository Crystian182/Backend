package it.unisalento.se.saw.Iservices;

import java.util.List;

import it.unisalento.se.saw.dto.ExamDTO;
import it.unisalento.se.saw.dto.ExamTypeDTO;
import it.unisalento.se.saw.dto.StudentDTO;
import it.unisalento.se.saw.exceptions.ExamNotFoundException;

public interface IExamService {
	/*public List<ExamDTO> getAll();
	public ExamDTO getById(int id) throws ExamNotFoundException;*/
	public List<ExamDTO> getAllByCourseAndTerm(int idcourse, int idterm) throws ExamNotFoundException;
	public List<ExamTypeDTO> getAllTypes();
	public void save(List<ExamDTO> examDTOs);
	/*public void delete(int id)  throws ExamNotFoundException;
	public ExamDTO save(ExamDTO examDTO);
	public ExamDTO subscribe(int idexam, StudentDTO studentDTO);*/

}
