package it.corso;

import java.io.IOException;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

@Provider
public class CorsFilter implements ContainerResponseFilter, ContainerRequestFilter{
	
	// ContainerRequestFilter: intercetta e gestisce direttamente le richieste con metodo OPTIONS
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		
		/* 
		 * Diamo l'ok a tutte le richieste di pre-flight inviate dai client prima
		 * dell'operazione effettiva senza dovers necessariamente implementare il 
		 * metodo OPTIONS a livello di logica.
		 */
		if( requestContext.getHeaderString("Origin") != null 
				&& requestContext.getMethod().equalsIgnoreCase("OPTIONS") ) {
			
			requestContext.abortWith(Response.ok().build());
		}
	}
	
	
	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
			throws IOException {
		
		// Permettiamo tutte le connessioni cross-origin (esterne)
		responseContext.getHeaders().add("Access-Control-Allow-Origin", 
				"*");
		responseContext.getHeaders().add("Access-Control-Allow-Credentials", 
				"*");
		responseContext.getHeaders().add("Access-Control-Allow-Methods", 
				"GET, POST, PUT, DELETE, HEAD");
		responseContext.getHeaders().add("Access-Control-Expose-Headers", 
				"Location, Authorization, *"); 
		responseContext.getHeaders().add("Access-Control-Allow-Headers", 
				"Origin, X-Requested-With, Content-Type, Accept, Authorization, X-Custom-header, *");// INSERIRE HEADERS MANCANTI

	}
	
	
}
