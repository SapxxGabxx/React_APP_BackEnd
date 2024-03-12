package it.corso.service;

import java.util.List;

import it.corso.dto.CorsoDto;
import it.corso.dto.CreateCorsoDto;
import it.corso.dto.UtenteDto;
import it.corso.exceptions.EntityNotFoundException;

public interface CorsoService {
	
	CorsoDto createCorso( CreateCorsoDto corso );
	
	// Metodo che restituisce l'elenco degli iscritti al corso
	List<UtenteDto> getUtenti(int id_c, Integer id_r) throws EntityNotFoundException;
	
	// Metodo che restituisce l'elenco dei corsi di una categoria specifica tramite id della categoria
	List<CorsoDto> getCorsi(int id_ca);
	
	
	List<CorsoDto> getAllCorsi();
	
	void cancellaCorso(Integer idCorso);
	
}
