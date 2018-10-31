package it.unisalento.se.saw.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unisalento.se.saw.Iservices.IAulaService;
import it.unisalento.se.saw.domain.Classroom;
import it.unisalento.se.saw.exceptions.AulaNotFoundException;
import it.unisalento.se.saw.repositories.AulaRepository;

@Service
public class AulaService implements IAulaService {
	
	@Autowired
	AulaRepository aulaRepository;

	@Override
	@Transactional
	public Classroom getById(int i) throws AulaNotFoundException {
		// TODO Auto-generated method stub
		try {
			Classroom aula = aulaRepository.getOne(i);
			return aula;
		} catch (Exception e) {
			// TODO: handle exception
			throw new AulaNotFoundException();
		}
	}

	@Override
	@Transactional
	public List<Classroom> getAll() {
		// TODO Auto-generated method stub
		return aulaRepository.findAll();
	}

	@Override
	@Transactional
	public Classroom save(Classroom aula) {
		// TODO Auto-generated method stub
		return aulaRepository.save(aula);
	}
	
	@Override
	@Transactional
	public void delete(int id) throws AulaNotFoundException {
		// TODO Auto-generated method stub
		try {
			Classroom aula = aulaRepository.getOne(id);
			aulaRepository.delete(aula);
		} catch (Exception e) {
			// TODO: handle exception
			throw new AulaNotFoundException();
		}

	}

	
	@Override
	@Transactional
	public List<Classroom> getByName(String name) {
		// TODO Auto-generated method stub
		List<Classroom> list = aulaRepository.getByName(name);
		if (list == null) {
			return new ArrayList<Classroom>();
		}
		return list;
	}

}
