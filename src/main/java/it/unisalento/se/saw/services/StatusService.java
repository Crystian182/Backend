package it.unisalento.se.saw.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unisalento.se.saw.Iservices.IStatusService;
import it.unisalento.se.saw.domain.Status;
import it.unisalento.se.saw.repositories.StatusRepository;

@Service
public class StatusService implements IStatusService{

	@Autowired
	StatusRepository statusRepository;
	
	@Transactional(readOnly = true)
	public List<Status> getAll() {
		// TODO Auto-generated method stub
		return statusRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public Status getById(int id) {
		// TODO Auto-generated method stub
		return statusRepository.getById(id);
	}

	

}
