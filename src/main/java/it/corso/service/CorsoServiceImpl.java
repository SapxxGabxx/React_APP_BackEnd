package it.corso.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.corso.dao.CorsoDao;
import it.corso.dto.CorsoDto;
import it.corso.dto.CreateCorsoDto;
import it.corso.dto.UtenteDto;
import it.corso.model.Corso;
import jakarta.persistence.EntityNotFoundException;

@Service
public class CorsoServiceImpl implements CorsoService {
	
	@Autowired
	private CorsoDao corsoDao;
	
	private ModelMapper mapper = new ModelMapper();
	
	@Override
	public CorsoDto createCorso(CreateCorsoDto corso) {
		
		Corso newCorso = new Corso();
		
		newCorso = mapper.map(corso, Corso.class);
		newCorso = corsoDao.save(newCorso);
		
		return mapper.map(newCorso, CorsoDto.class);
	}

	@Override
	public List<UtenteDto> getUtenti(int id_c, Integer id_r) throws EntityNotFoundException {
		
		Optional<Corso> corsoDb = corsoDao.findById(id_c);
			
		if( !corsoDb.isPresent() ) {
			
			throw new EntityNotFoundException("Corso " + id_c + " non trovato.");
		} 
		
		if( id_r != null ) {
			
			return corsoDb.get().getUtenti().stream()
					.filter(u -> u.getRuoli().stream().map(r -> r.getId()).toList().contains(id_r)) // Prendimi tutti gli utenti che dentro la lista dei ruoli contengono il ruolo che ti ho dato
					.map(u -> mapper.map(u, UtenteDto.class)).toList();
		} else {
			return corsoDb.get().getUtenti().stream().map(u -> mapper.map(u, UtenteDto.class)).toList();
		}
		
	}

	@Override
	public List<CorsoDto> getCorsi(int id_ca) {
		
		List<Corso> listaCorsi = corsoDao.findByCategoriaIdCa(id_ca);
		
		return listaCorsi.stream().map(c -> mapper.map(c, CorsoDto.class)).toList();
	}

	@Override
	public List<CorsoDto> getAllCorsi() {
		
		List<Corso> listaCorsi = corsoDao.findAll();
		
		return listaCorsi.stream().map(c -> mapper.map(c, CorsoDto.class)).toList();
		
	}

	@Override
	public void cancellaCorso(Integer idCorso) {
		Optional<Corso> corso = corsoDao.findById(idCorso);
		if(corso.isPresent()) {
			corsoDao.deleteById(idCorso);
		}
		
	}

 
	
	
}
