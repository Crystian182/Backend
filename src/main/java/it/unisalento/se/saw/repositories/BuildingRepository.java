package it.unisalento.se.saw.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import it.unisalento.se.saw.domain.Building;

@Repository
public interface BuildingRepository extends JpaRepository<Building, Integer> {
	
	@Transactional
	@Modifying
	@Query("UPDATE Building b SET b.file.idfile=:idimage WHERE b.idbuilding=:build")
	public void updateImage(@Param("build")int build, @Param("idimage")int idimage);

}
