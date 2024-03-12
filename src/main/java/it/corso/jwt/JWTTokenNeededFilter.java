package it.corso.jwt;

import java.io.IOException;
import java.security.Key;
import java.util.List;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

@JWTTokenNeeded
@Provider // Specifichiamo che la classe svolge un ruolo specifico all'interno del framework
public class JWTTokenNeededFilter implements ContainerRequestFilter {
	
	/*
	 * Inietta le informazioni dentro la classe: come bean e metodi
	 */
	@Context
	private ResourceInfo resourceInfo;
	
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		
		/*
		 * Nelle varie risorse, devi controllare se su di esse c'è l'annotation
		 * secured ( che abbiamo creato noi )
		 */
		Secured annotatedRole = resourceInfo.getResourceMethod().getAnnotation(Secured.class);
		
		// E' possibile non trovare l'annotation sul metodo, potrebbe essere sulla classe
		if( annotatedRole == null ) {
			
			// Non ho trovato l'annotation sul metodo: verifico se è sulla classe
			annotatedRole = resourceInfo.getResourceClass().getAnnotation(Secured.class);
		}
		
		// Estraiamo l'autorizzazione http
		// Uso il parametro per estrarre l'autorizzazione dall'intestazione
		// Questo valore poi ci servirà per inserirlo in maniera consona
		// nel nostro filtro per il token di autorizzazione al ruolo
		String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
		
		// Se qualcosa va storto, lancia un eccezione
		// Verifichiamo che l'autorizzazione sia formattata correttamente
		if( authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
			
			throw new NotAuthorizedException("Autorizzazione non fornita. Fornire autorizzazione");
		}
			
		// Dobbiamo estrarre l'autorizzazione: ci rimane il token
		String token = authorizationHeader.substring("Bearer".length()).trim();
		
		// Dobbiamo validare questo token
		try {
			
			// Utilizzo la stessa chiave segreta
			byte[] secret = "stringachiave1234567891111111111111".getBytes();
			Key chiaveSegreta = Keys.hmacShaKeyFor(secret);
			
			// Prende il token, controlla la firma - se valida fa ritornare i claims
			Jws<Claims> claims = Jwts.parserBuilder()
									 .setSigningKey(chiaveSegreta)
									 .build()
									 .parseClaimsJws(token);
			
			// Estrapoliamo i claims - sono coppie di chiave valore
			Claims body = claims.getBody();
			
			// Dall'altra parte abbiamo creato una lista di ruoli
			List<String> ruoliToken = body.get("ruoli", List.class);
			
			// Dobbiamo verificare se il ruolo è all'interno di questa list
			Boolean asRole = false;
			
			for( String ruolo : ruoliToken ) {
				
				// Dobbiamo verificare se è uguale
				if( ruolo.equals(annotatedRole.role())) {
					
					asRole = true;
				}
			}
			
			// Se il ruolo non è presente, lancia una risposta unauthorized
			if( !asRole ) {
				
				requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
			}
			
			
		} catch (Exception e) {
			
			requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
		}
		
	}
	
}
