package it.unisalento.se.saw.Iservices;

import java.util.List;

import it.unisalento.se.saw.domain.Status;

public interface IStatusService {
	public List<Status> getAll();
	public Status getById(int id);

}
