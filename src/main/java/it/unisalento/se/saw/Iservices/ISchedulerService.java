package it.unisalento.se.saw.Iservices;

import org.springframework.web.bind.annotation.RequestBody;

import it.unisalento.se.saw.dto.SchedulerDTO;
import it.unisalento.se.saw.dto.TermDTO;

public interface ISchedulerService {
	
	public SchedulerDTO save(SchedulerDTO schedulerDTO);
	public Integer periodHasScheduler(int idterm, int idcourse);

}
