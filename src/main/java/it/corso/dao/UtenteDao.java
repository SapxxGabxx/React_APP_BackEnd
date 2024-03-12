package it.corso.dao;

import java.util.List;
import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import it.corso.model.Utente;

public interface UtenteDao extends CrudRepository<Utente, Integer> {
	
	boolean existsByEmail(String email);
	Utente findByEmail(String email);
	Utente findByEmailAndPassword(String email, String password);
	List<Utente> findByRuoliIdIn(Set<Integer> id_r);
	Utente deleteById(int id_u);
}
