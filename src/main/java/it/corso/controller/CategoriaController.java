package it.corso.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import it.corso.dto.CategoriaDto;
import it.corso.dto.CreateCategoriaDto;
import it.corso.service.CategoriaService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/categoria")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CategoriaController {
	
	@Autowired
	private CategoriaService categoriaService;
	
	@POST
	public Response createCategoria( CreateCategoriaDto categoriaDto ) {
		
		CategoriaDto newCategoria = categoriaService.createCategoria(categoriaDto);
		
		return Response.status(Response.Status.OK).entity(newCategoria).build();
	}
	
	@GET
	@Path("/getAll")
	public Response getCategorie( ) {
		
		try {
			List<CategoriaDto> listaCategorie = categoriaService.getAll();
			return Response.status(Response.Status.OK).entity(listaCategorie).build();
			
		} catch (Exception e) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		
	}
}


