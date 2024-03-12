package it.corso.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import it.corso.dto.RuoloDto;
import it.corso.model.Ruolo;
import it.corso.service.RuoloService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/ruolo")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RuoloController {
	
	@Autowired
	private RuoloService ruoloService;
	
	@POST
	@Path("/crearuolo")
	public Response createRuolo(RuoloDto ruolo) {
		
		Ruolo newRuolo = ruoloService.createRuolo(ruolo);
		
		return Response.status(Response.Status.OK).entity(newRuolo).build();
		
	}
	
	@GET
	@Path("/getall")
	public Response getAll() {
		
		try {
			
			List<RuoloDto> listaRuoli = ruoloService.getAll();
			return Response.status(Response.Status.OK).entity(listaRuoli).build();
		} catch (Exception e) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
	}
	
	
}
