package it.corso.jwt;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.ws.rs.NameBinding;

@NameBinding
@Retention( RetentionPolicy.RUNTIME ) 
@Target( { ElementType.TYPE, ElementType.METHOD } )
public @interface Secured {
	
	// Specifichiamo il ruolo: specifichiamo la tipologia di ruolo permesso
	/*
	 * Specifica il ruolo della risorsa annotata.
	 * role() è un metodo dell'annotation richiesto per accedere alla risorsa. 
	 * La risorsa è accessibile rispetto alla stringa che vi è definito: in questo
	 * caso tutti possono accedere alla risorsa.
	 */
	String role() default "all";
	
	
	
	
}
