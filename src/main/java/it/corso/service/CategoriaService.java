package it.corso.service;

import java.util.List;

import it.corso.dto.CategoriaDto;
import it.corso.dto.CreateCategoriaDto;

public interface CategoriaService {
	
	public CategoriaDto createCategoria(CreateCategoriaDto categoria);
	List<CategoriaDto> getAll();
}
