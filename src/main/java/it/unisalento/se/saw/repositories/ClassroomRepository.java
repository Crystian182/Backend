package it.unisalento.se.saw.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.unisalento.se.saw.domain.Classroom;

@Repository
public interface ClassroomRepository extends JpaRepository<Classroom, Integer> {
	
	@Query("SELECT c FROM Classroom c WHERE c.building.idbuilding=:build")
	public List<Classroom> findClassesByBuild(@Param("build")int build);
	
	@Query("SELECT c FROM Classroom c WHERE c.name like %:name%")
	public List<Classroom> findClassesByName(@Param("name")String name);
	
	@Query("SELECT c FROM Classroom c WHERE c.name like %:name% AND c.building.idbuilding=:build")
	public List<Classroom> findClasses(@Param("name")String name, @Param("build")int build);

}
