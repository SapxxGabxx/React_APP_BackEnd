package it.corso.jwt;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.ws.rs.NameBinding;

/* Annotation personalizzata per il filtro della JWT Token
 * Va ad indicare che il nostro annotation è di tipo NameBindingAnnotation: 
 * in JEE sono utilizzare per associare i filtri di tipo ContainerRequestFilter
 * a specifiche classi/metodi - fa da legante tra il filtro e la nostra classe
 * creata. */
@NameBinding // Lega la classe al filtro: saprà che serve il token
@Retention( RetentionPolicy.RUNTIME ) // Specifica che questa annotation sarà disponibile solamente durante il runtime.
@Target( { ElementType.TYPE, ElementType.METHOD } ) // Specifica dove poter applicare l'annotation che stiamo creando: i questo caso sia su classi che su metodi
public @interface JWTTokenNeeded {
	
}
