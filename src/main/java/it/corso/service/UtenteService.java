package it.corso.service;


import java.util.List;

import it.corso.dto.CorsoDto;
import it.corso.dto.RuoloDto;
import it.corso.dto.UtenteDtoAggiornamento;
import it.corso.dto.UtenteLoginRequestDto;
import it.corso.dto.UtenteRegistrazioneDto;
import it.corso.dto.UtenteRuoloDto;
import it.corso.exceptions.EntityNotFoundException;
import it.corso.model.Corso;
import it.corso.model.Utente;

public interface UtenteService {
	
	// Metodo per la registrazione dell'utente
	void userRegistration(UtenteRegistrazioneDto utenteDto);
	
	// Metodo per ottenere un utente in base all'id
	Utente getUtenteById(int id);
	
	// Metodo per l'aggiornamento dati di un utente tramite l'email
	void updateUtenteData(UtenteDtoAggiornamento utente);
	
	// Metodo per verificare se esiste l'utente tramite email
	boolean existsUtenteByEmail(String email);
	
	void cancellaUtente(Integer idUtente);
	
	// Metodo per effettuare il login
	boolean loginUtente(UtenteLoginRequestDto utente);
	
	// Metodo per ottenere un utente in base all'email
	Utente findByEmail(String email);
	
	// Metodo per ottenere tutti gli utenti 
	List<UtenteRuoloDto> getUtenti();
	
	List<Corso> getCorsiUtente(int id_u) throws EntityNotFoundException;
	
	// Metodo per aggiungere un ruolo ad un utente: solleva un entity not found personalizzato
	RuoloDto addRuoloToUtente(int id_u, int id_r) throws EntityNotFoundException;
	
	// Metodo per aggiungere un corso all'utente
	CorsoDto subscribeUtente(int id_u, int id_c) throws EntityNotFoundException;

	// Metodo per rimuovere un utente da un corso
	CorsoDto unsubscribeUtente(int id_u, int id_c) throws EntityNotFoundException;
	
	// Metodo per recuperari gli utenti in base al ruolo
	List<UtenteRuoloDto> getUtentiByRuolo(Integer id_r);
	

}
