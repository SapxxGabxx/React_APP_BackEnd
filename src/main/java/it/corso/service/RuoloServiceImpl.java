package it.corso.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.corso.dao.RuoloDao;
import it.corso.dto.RuoloDto;
import it.corso.model.Ruolo;

@Service
public class RuoloServiceImpl implements RuoloService {
	
	@Autowired
	private RuoloDao ruoloDao;
	
	private ModelMapper mapper = new ModelMapper();
	
	
	@Override
	public Ruolo createRuolo(RuoloDto ruoloDto) {
		
		Ruolo ruolo = new Ruolo();
		ruolo.setTipologia(ruoloDto.getTipologia());
		
		ruolo = ruoloDao.save(ruolo);
		
		return new Ruolo(ruolo.getId(), ruolo.getTipologia());
	}

	@Override
	public List<RuoloDto> getAll() {
		
		List<Ruolo> listaRuoli = (List<Ruolo>) ruoloDao.findAll();
		
		List<RuoloDto> listRuoli = new ArrayList<>();
		listaRuoli.forEach(r -> listRuoli.add(mapper.map(r, RuoloDto.class)) );
				
		return listRuoli;
	}
	
	
}
