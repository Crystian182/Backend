package it.unisalento.se.saw.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import it.unisalento.se.saw.domain.Scheduler;

public interface SchedulerRepository extends JpaRepository<Scheduler, Integer>{
	
	@Query("SELECT s.idscheduler FROM Scheduler s WHERE s.term.idterm=:idterm AND s.degreeCourse.iddegreeCourse=:idcourse")
	public Integer schedulerExists(@Param("idterm")int idterm, @Param("idcourse")int idcourse);

}
