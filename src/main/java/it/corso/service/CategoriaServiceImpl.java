package it.corso.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import it.corso.dao.CategoriaDao;
import it.corso.dto.CategoriaDto;
import it.corso.dto.CreateCategoriaDto;
import it.corso.model.Categoria;

@Service
public class CategoriaServiceImpl implements CategoriaService {
	
	@Autowired
	private CategoriaDao categoriaDao;
	
	private ModelMapper mapper = new ModelMapper();
	
	@Override
	public CategoriaDto createCategoria(CreateCategoriaDto categoria) {
		
		Categoria categoriaNew = new Categoria();
		categoriaNew.setNome_categoria(categoria.getNome_categoria());
		categoriaNew = categoriaDao.save(categoriaNew);
		
		return new CategoriaDto(categoriaNew.getId_ca(), categoriaNew.getNome_categoria());
	}

	@Override
	public List<CategoriaDto> getAll() {
		
		List<Categoria> listaCategoria = categoriaDao.findAll();
		
		return listaCategoria.stream().map(c -> mapper.map(c, CategoriaDto.class)).toList();
	}
	
	
}
