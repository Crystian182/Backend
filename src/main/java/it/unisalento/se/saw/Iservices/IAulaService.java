package it.unisalento.se.saw.Iservices;

import java.util.List;

import it.unisalento.se.saw.domain.Classroom;
import it.unisalento.se.saw.exceptions.AulaNotFoundException;

public interface IAulaService {
	public Classroom getById(int i) throws AulaNotFoundException;
	public List<Classroom> getAll();
	public Classroom save(Classroom aula);
	public void delete(int id) throws AulaNotFoundException;
	public List<Classroom> getByName(String name);
}
