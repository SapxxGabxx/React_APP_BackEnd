package it.corso.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import it.corso.dto.CorsoDto;
import it.corso.dto.CreateCorsoDto;
import it.corso.dto.UtenteDto;
import it.corso.exceptions.EntityNotFoundException;
import it.corso.jwt.JWTTokenNeeded;
import it.corso.jwt.Secured;
import it.corso.service.CorsoService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Secured( role = "Admin" )
@JWTTokenNeeded 
@Path("/corso")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CorsoController {
	
	@Autowired
	private CorsoService corsoService;
	
	@POST
	@Path("/creaCorso")
	public Response createCorso( CreateCorsoDto newCorso ) {
		
		try {
			
			CorsoDto corsoSave = corsoService.createCorso(newCorso);
			return Response.status(Response.Status.OK).entity(corsoSave).build();
		
		} catch (Exception e) {
			
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		
	}
	
	@GET
	@Path("/{id_c}/getutenti")
	public Response getUtenti( @PathParam("id_c") int id_c, @QueryParam("idRuolo") Integer id_r ) {
		
		try {
			
			List<UtenteDto> listaUtenti = corsoService.getUtenti(id_c, id_r);
			return Response.status(Response.Status.OK).entity(listaUtenti).build();
		
		} catch (EntityNotFoundException e) {
			
			return Response.status(Response.Status.BAD_REQUEST).entity(e).build();
		}
	}
	
	@GET
	@Path("/getcorsi")
	public Response getCorsi( @QueryParam("id_ca") int id_ca ) {
		
		try {
			List<CorsoDto> listaCorsi = corsoService.getCorsi(id_ca);
			return Response.status(Response.Status.OK).entity(listaCorsi).build();
			
		} catch (Exception e) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		
	}
	
	@GET
	@Path("/getAll")
	public Response getCorsi( ) {
		
		try {
			List<CorsoDto> listaCorsi = corsoService.getAllCorsi();
			return Response.status(Response.Status.OK).entity(listaCorsi).build();
			
		} catch (Exception e) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		
	}
	
	@DELETE
	@Path("/eliminaCorso/{id}")
	public Response cancellaCorso( @PathParam("id") Integer id_c) {
			
		try {
				
			corsoService.cancellaCorso(id_c);
			return Response.status(Response.Status.OK).build();
				
		} catch (Exception e) {
				
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
			
	}
}
