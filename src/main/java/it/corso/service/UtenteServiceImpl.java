package it.corso.service;

import org.apache.commons.codec.digest.DigestUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import it.corso.dto.CorsoDto;
import it.corso.dto.RuoloDto;
import it.corso.dto.UtenteDtoAggiornamento;
import it.corso.dto.UtenteLoginRequestDto;
import it.corso.dto.UtenteRegistrazioneDto;
import it.corso.dto.UtenteRuoloDto;
import it.corso.exceptions.EntityNotFoundException;
import it.corso.model.Corso;
import it.corso.model.Ruolo;
import it.corso.model.Utente;
import it.corso.dao.CorsoDao;
import it.corso.dao.RuoloDao;
import it.corso.dao.UtenteDao;

@Service
public class UtenteServiceImpl implements UtenteService {
	
	@Autowired
	private UtenteDao utenteDao;
	
	@Autowired
	private RuoloDao ruoloDao;
	
	private ModelMapper mapper = new ModelMapper();
	
	@Autowired 
	private CorsoDao corsoDao;
	
	@Override
	public void userRegistration(UtenteRegistrazioneDto utenteDto) {
		
		Utente utente = new Utente();
		
		utente.setNome(utenteDto.getNome());
		utente.setCognome(utenteDto.getCognome());
		utente.setEmail(utenteDto.getEmail());
		// Procedo con crittografare la password dell'utente
		String sha256hex = DigestUtils.sha256Hex(utenteDto.getPassword());
		
		// Setto la password crittografata
		utente.setPassword(sha256hex);
		
		utenteDao.save(utente);

	}

	@Override
	public Utente getUtenteById(int id) {
		
		Optional<Utente> utenteDb = utenteDao.findById(id);
		
		if( !utenteDb.isPresent() ) {
			
			return new Utente();
		}
		
		return utenteDb.get();
				
	}

	@Override
	public void updateUtenteData(UtenteDtoAggiornamento utente) {
		
		try {
			
			Utente utenteDb = utenteDao.findByEmail(utente.getEmail());
			
			if( utenteDb != null ) {
				
				utenteDb.setNome(utente.getNome());
				utenteDb.setCognome(utente.getCognome());
				utenteDb.setEmail(utente.getEmail());
				
				// All'interno del model utente abbiamo questa tipologia
				List<Ruolo> ruoliUtente = new ArrayList<>();
				
				
				Optional<Ruolo> ruoloDb = ruoloDao.findById(utente.getIdRuolo());
				
				if( ruoloDb.isPresent() ) {
					
					Ruolo ruolo = ruoloDb.get();
					ruolo.setId(utente.getIdRuolo());
					
					ruoliUtente.add(ruolo);
					utenteDb.setRuoli(ruoliUtente);
				}
				
				utenteDao.save(utenteDb);
				
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public boolean existsUtenteByEmail(String email) {
		
		return utenteDao.existsByEmail(email);
	}
	
	@Override
	public boolean loginUtente(UtenteLoginRequestDto utente) {
		
		Utente utenteOpt = new Utente();
		
		utenteOpt.setEmail(utente.getEmail());
		utenteOpt.setPassword(utente.getPassword());
		
		// Dobbiamo hashare la password
		String sha256hex = DigestUtils.sha256Hex(utente.getPassword());
			
		Utente credenzialiUtente = utenteDao.findByEmailAndPassword(utenteOpt.getEmail(), sha256hex);
		
		return credenzialiUtente != null ? true : false;
		
	}
	
	@Override
	public Utente findByEmail(String email) {
		return utenteDao.findByEmail(email);
	}

	@Override
	public List<UtenteRuoloDto> getUtenti() {
		
		List<Utente> utenti = (List<Utente>) utenteDao.findAll();
		List<UtenteRuoloDto> utentiDto = new ArrayList<>();
		
		// Facciamo questa lista di dto per evitare i loop di riferimento
		utenti.forEach(u -> utentiDto.add(mapper.map(u, UtenteRuoloDto.class)));
		
		return utentiDto;
	}

	@Override
	public RuoloDto addRuoloToUtente(int id_u, int id_r) throws EntityNotFoundException {
		
		Optional<Utente> utenteDb = utenteDao.findById(id_u);
		
		if( !utenteDb.isPresent() ) {
			
			throw new EntityNotFoundException("Nessun utente trovato con id " + id_u);
		} else {
			
			Optional<Ruolo> ruoloDb = ruoloDao.findById(id_r);
			
			if( !ruoloDb.isPresent() ) {
				
				throw new EntityNotFoundException("Nessun ruolo trovato con id " + id_r);
			} else {
				
				Utente utenteTrovato = utenteDb.get();
				utenteTrovato.getRuoli().add(ruoloDb.get());
				
				utenteDao.save(utenteTrovato);
				
				return new RuoloDto(id_r, ruoloDb.get().getTipologia());
			}
		}
		
	}

	@Override
	public CorsoDto subscribeUtente(int id_u, int id_c) throws EntityNotFoundException {
		
		Optional<Utente> utenteDb = utenteDao.findById(id_u);
		
		if( !utenteDb.isPresent() ) {
			
			throw new EntityNotFoundException("Utente " + id_u + " non trovato.");
		} else {
			
			Optional<Corso> corsoDb = corsoDao.findById(id_c);
			
			if( !corsoDb.isPresent() ) {
				
				throw new EntityNotFoundException("Corso " + id_c + " non trovato.");
			} else {
				
				Utente utenteTrovato = utenteDb.get();
				utenteTrovato.getCorsi().add(corsoDb.get());
				
				utenteDao.save(utenteTrovato);
				
				return mapper.map(corsoDb.get(), CorsoDto.class);
				
			}
		}

	}

	@Override
	public CorsoDto unsubscribeUtente(int id_u, int id_c) throws EntityNotFoundException {
		
		Optional<Utente> utenteDb = utenteDao.findById(id_u);
		
		if( !utenteDb.isPresent() ) {
			
			throw new EntityNotFoundException("Utente " + id_u + " non trovato.");
		} else {
			
			Optional<Corso> corsoDb = corsoDao.findById(id_c);
			
			if( !corsoDb.isPresent() ) {
				
				throw new EntityNotFoundException("Corso " + id_c + " non trovato.");
			} else {
				
				Utente utenteUpdate = utenteDb.get();
				utenteUpdate.setCorsi(utenteUpdate.getCorsi().stream().filter(c -> c.getId_c() != id_c).toList());
				
				utenteDao.save(utenteUpdate);
				
				return mapper.map(corsoDb.get(), CorsoDto.class);
				
			}
		}
	}

	@Override
	public List<UtenteRuoloDto> getUtentiByRuolo(Integer id_r) {
		
		List<Utente> listaUtenti = utenteDao.findByRuoliIdIn(new HashSet<>(Arrays.asList(id_r)));
		
		List<UtenteRuoloDto> listaUtentiDto = listaUtenti.stream().map( u -> mapper.map(u, UtenteRuoloDto.class)).toList();
		
		return listaUtentiDto;
	}

	@Override
	public List<Corso> getCorsiUtente(int id_u) throws EntityNotFoundException {
		
		 Optional<Utente> utente = utenteDao.findById(id_u);
	        if (utente.isPresent()) {
	            return utente.get().getCorsi();
	        } else {
	            throw new EntityNotFoundException("Utente non trovato con ID: " + id_u);
	        }
	}
	
	@Override
	public void cancellaUtente(Integer idUtente) {
		
		Optional<Utente> utente = utenteDao.findById(idUtente);
		if(utente.isPresent()) {
			utenteDao.deleteById(idUtente);
		}
	}



	
	

}
