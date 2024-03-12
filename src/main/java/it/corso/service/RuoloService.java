package it.corso.service;

import java.util.List;

import it.corso.dto.RuoloDto;
import it.corso.model.Ruolo;

public interface RuoloService {
	
	// Metodo per la creazione di un nuovo ruolo
	Ruolo createRuolo(RuoloDto ruoloDto);
	
	// Metodo per recuperare la lista dei ruoli
	List<RuoloDto> getAll();
	
}
