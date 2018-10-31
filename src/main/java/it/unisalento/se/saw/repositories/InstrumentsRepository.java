package it.unisalento.se.saw.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import it.unisalento.se.saw.domain.Instruments;

@Repository
public interface InstrumentsRepository extends JpaRepository<Instruments, Integer> {



}
