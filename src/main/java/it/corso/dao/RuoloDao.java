package it.corso.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import it.corso.model.Ruolo;

public interface RuoloDao extends CrudRepository<Ruolo, Integer> {
	
	Optional<Ruolo> findById(int id_g);
}
