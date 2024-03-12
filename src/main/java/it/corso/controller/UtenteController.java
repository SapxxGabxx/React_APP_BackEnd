package it.corso.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.security.*;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;

import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.security.*;
import io.jsonwebtoken.security.Keys;

import it.corso.dto.CorsoDto;
import it.corso.dto.RuoloDto;
import it.corso.dto.UtenteDtoAggiornamento;
import it.corso.dto.UtenteLoginRequestDto;
import it.corso.dto.UtenteLoginResponseDto;
import it.corso.dto.UtenteRegistrazioneDto;
import it.corso.dto.UtenteRuoloDto;
import it.corso.exceptions.EntityNotFoundException;
import it.corso.model.Corso;
//import it.corso.model.Ruolo;
import it.corso.model.Utente;
import it.corso.service.CorsoService;
import it.corso.service.UtenteService;

import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


@Path("/utente")
public class UtenteController {
	
	@Autowired
	private UtenteService utenteService;
	@Autowired
	private CorsoService corsoService;
	
	@POST
	@Path("/registrazione")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response userRegistration( @Valid @RequestBody UtenteRegistrazioneDto utenteDto ) {
		
		try {
			
			// Validazione password
			if( !Pattern.matches("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,20}", utenteDto.getPassword()) ) {
				
				return Response.status(Response.Status.BAD_REQUEST).build();
			}
			
			// Verifico se esiste già l'utente attraverso l'email
			if( utenteService.existsUtenteByEmail(utenteDto.getEmail())) {
				
				return Response.status(Response.Status.BAD_REQUEST).build();
			}
			
			utenteService.userRegistration(utenteDto);
			return Response.status(Response.Status.OK).build();
			
		} catch (Exception e) {
			
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		
	}
	
	
	@PUT
	@Path("/aggiornaUtente")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response userUpdate( @Valid @RequestBody UtenteDtoAggiornamento utente) {
		
		try {
			
			utenteService.updateUtenteData(utente);
			return Response.status(Response.Status.OK).build();
			
		} catch (Exception e) {
			
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		
	}
	
	
	@DELETE
	@Path("/eliminaUtente/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response userDelete( @PathParam("id") Integer id_u) {
		
		try {
			
			utenteService.cancellaUtente(id_u);
			return Response.status(Response.Status.OK).build();
			
		} catch (Exception e) {
			
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		
	}
	
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response loginUtente(@Valid @RequestBody UtenteLoginRequestDto utente) {
		
		try {
			
			if( utenteService.loginUtente(utente) ) {
				// Se il login è true, le credenziali sono valide e di conseguenza
				// dobbiamo fare tornare il token: lo inserisco nel corpo della risposta
				return Response.ok(issueToken(utente.getEmail())).build();
			}
			
			// Se non passa, ritorno che la richiesta è sbagliata
			return Response.status(Response.Status.BAD_REQUEST).build();
		} catch (Exception e) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
	}
	
	@GET
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUtente( @PathParam("id") Integer id ) {
		
		try {
			
			Utente utente = utenteService.getUtenteById(id);
			return Response.status(Response.Status.OK).entity(utente).build();
			
		} catch (Exception e) {
			
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		
	}
	
	
	@GET
	@Path("/getUtenti")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUtenti( @QueryParam("id_r") Integer id_r ) {
		
		try {
			
			List<UtenteRuoloDto> listaUtenti = new ArrayList<>();
			
			if( id_r == null ) {
				listaUtenti = utenteService.getUtenti();
			} else {
				listaUtenti = utenteService.getUtentiByRuolo(id_r);
			}
			
			return Response.status(Response.Status.OK).entity(listaUtenti).build();
			
			
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
	}
	
	@GET
	@Path("/getCorsi")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCorsi( ) {
		
		try {
			List<CorsoDto> listaCorsi = corsoService.getAllCorsi();
			return Response.status(Response.Status.OK).entity(listaCorsi).build();
			
		} catch (Exception e) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		
	}
	

	
	
	// Creiamo un metodo per il token: per recuperare l'utente, utilizzo l'email
	public UtenteLoginResponseDto issueToken(String email) {
		
		// Creo la chiave segreta
		byte[] secret = "stringachiave1234567891111111111111".getBytes();
		
		Key chiaveSegreta = Keys.hmacShaKeyFor(secret);
		
		// Dobbiamo prendere i dati dell'utente
		Utente infoUtente = utenteService.findByEmail(email);
		
		// Devo riprendermi le informazioni di questo utente utilizzando una mappa
		Map<String, Object> map = new HashMap<>();
		map.put("id", infoUtente.getId());
		map.put("nome", infoUtente.getNome());
		map.put("cognome", infoUtente.getCognome());
		map.put("email", email);
		
		List<String> ruoliUtente = new ArrayList<>();
		infoUtente.getRuoli().forEach(r -> ruoliUtente.add(r.getTipologia()));
		
		map.put("ruoli", ruoliUtente);
		
		// Creo la creazione della data
		Date creation = new Date();
		// Crea la data di fine del token utilizzando una classe che ci permette di avere 
		// il timestamp(tempo in millisecondi) + i 15 minuti scelti
		Date end = java.sql.Timestamp.valueOf(LocalDateTime.now().plusMinutes(120L));
		
		// Creazione token
		String jwtToken = Jwts.builder()
							  .setClaims(map)
							  .setIssuer("http://localhost:8080") // Chi emette il token = il server
							  .setIssuedAt(creation) // Data di creazione del token
							  .setExpiration(end) // Data fine validità del token
							  .signWith(chiaveSegreta) // Chiave segreta per firmare
							  .compact();
		
		// Dobbiamo creare l'entità per la risposta
		UtenteLoginResponseDto token = new UtenteLoginResponseDto();
		token.setToken(jwtToken);
		token.setTtl(end);
		token.setTokenCreationTime(creation);
		
		return token;
	}
	
	
	@PUT
	@Path("/{id}/addruolo/{idRuolo}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addRuoloToUtente( @PathParam("id") int id_u, @PathParam("idRuolo") int id_r ) {
		
		try {
			
			RuoloDto ruoloDto = utenteService.addRuoloToUtente(id_u, id_r);
			return Response.status(Response.Status.OK).entity(ruoloDto).build();
			
		} catch (EntityNotFoundException e) {
			
			return Response.status(Response.Status.OK).entity(e.getMessage()).build();
		}
	}
	
	@PUT
	@Path("/{id_u}/subscribe/{id_c}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response subscribeUtente( @PathParam("id_u") int id_u, @PathParam("id_c") int id_c) {
		
		try {
			CorsoDto corsoDto = utenteService.subscribeUtente(id_u, id_c);
			return Response.status(Response.Status.OK).entity(corsoDto).build();
		} catch (EntityNotFoundException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
	}
	
	@DELETE
	@Path("/{id_u}/unsubscribe-utente/{id_c}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response unsubscribeUtente( @PathParam("id_u") int id_u, @PathParam("id_c") int id_c) {
		
		try {
			CorsoDto corsoDto = utenteService.unsubscribeUtente(id_u, id_c);
			return Response.status(Response.Status.OK).entity(corsoDto).build();
		} catch (EntityNotFoundException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
	}
	
	@GET
	@Path("/{id}/getCorsiUtente")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCorsiUtente(@PathParam("id") int id_u ) {
	    
	    try {
	        // Prendo i corsi dell'utente con l'ID specificato
	        List<Corso> listaCorsi = utenteService.getCorsiUtente(id_u);
	        
	        // Converto la lista di Corso in una lista di CorsoDto 
	        
	        List<CorsoDto> corsiDto = listaCorsi.stream().map(corso -> {
	            // istanza di CorsoDto e imposto i valori corrispondenti.
	            CorsoDto dto = new CorsoDto();
	            dto.setId_c(corso.getId_c());
	            dto.setNome_corso(corso.getNome_corso());
	            dto.setDescrizione_breve(corso.getDescrizione_breve());
	            dto.setDescrizione_completa(corso.getDescrizione_completa());
	            return dto;
	        }).collect(Collectors.toList());
	        
	        //  lista corsiDto dell'utenete se 200 OK.
	        return Response.ok(corsiDto).build();
	    } catch (EntityNotFoundException e) {
	        // se non vengono trovati corsi per l'utente.
	        return Response.status(Response.Status.NOT_FOUND).build();
	    }
	}
	
	
}
