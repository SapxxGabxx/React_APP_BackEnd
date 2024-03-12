package it.corso;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import it.corso.jwt.JWTTokenNeededFilter;
import jakarta.ws.rs.ApplicationPath;

@Component
@Configuration
@ApplicationPath("api")
public class JerseyConfig extends ResourceConfig {
	
	public JerseyConfig() {
		
		register(JWTTokenNeededFilter.class); // Aggiunto al nostro framework
		register(CorsFilter.class);
		packages("it.corso");
	}
}
