package it.unisalento.se.saw.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unisalento.se.saw.Iservices.IClassroomService;
import it.unisalento.se.saw.domain.Building;
import it.unisalento.se.saw.domain.Classroom;
import it.unisalento.se.saw.domain.User;
import it.unisalento.se.saw.exceptions.AulaNotFoundException;
import it.unisalento.se.saw.exceptions.ClassroomNotFoundException;
import it.unisalento.se.saw.repositories.BuildingRepository;
import it.unisalento.se.saw.repositories.ClassroomRepository;

@Service
public class ClassroomService implements IClassroomService{
	
	@Autowired
	ClassroomRepository classroomRepository;
	
	@Transactional(readOnly=true)
	public List<Classroom> getAll(){
		return classroomRepository.findAll();	
	}
	
	@Transactional(rollbackFor=ClassroomNotFoundException.class)
	public Classroom getById(int idClassroom) throws ClassroomNotFoundException {
		
		try {
			return classroomRepository.findById(idClassroom).get();
		} catch (Exception e) {
			throw new ClassroomNotFoundException();
		}
	}
	
	@Transactional(rollbackFor=ClassroomNotFoundException.class)
	public List<Classroom> getByIdBuilding(int idBuilding) throws ClassroomNotFoundException {
		
		try {
			return classroomRepository.findClassesByBuild(idBuilding);
		} catch (Exception e) {
			throw new ClassroomNotFoundException();
		}
	}
	
	@Transactional(rollbackFor=ClassroomNotFoundException.class)
	public List<Classroom> getByIdBuildingAndName(int idBuilding, String name) throws ClassroomNotFoundException {
		
		try {
			return classroomRepository.findClasses(name, idBuilding);
		} catch (Exception e) {
			throw new ClassroomNotFoundException();
		}
	}
	
	@Transactional(rollbackFor=ClassroomNotFoundException.class)
	public List<Classroom> getByName(String name) throws ClassroomNotFoundException {
		
		try {
			return classroomRepository.findClassesByName(name);
		} catch (Exception e) {
			throw new ClassroomNotFoundException();
		}
	}
	
	@Transactional
	public Classroom edit(Classroom classroom) throws ClassroomNotFoundException {
		try {
			classroomRepository.findById(classroom.getIdclassroom()).get();
			return classroomRepository.save(classroom);
		} catch (Exception e) {
			throw new ClassroomNotFoundException();
		}
	}
	
	@Transactional
	public Classroom save(Classroom classroom) {
		return classroomRepository.save(classroom);
	}
	
	@Transactional
	public void delete(int id) throws ClassroomNotFoundException {
		// TODO Auto-generated method stub
		try {
			Classroom classroom = classroomRepository.findById(id).get();
			classroomRepository.delete(classroom);
		} catch (Exception e) {
			// TODO: handle exception
			throw new ClassroomNotFoundException();
		}

	}

}
