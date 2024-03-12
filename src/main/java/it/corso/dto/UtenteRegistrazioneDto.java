package it.corso.dto;

import jakarta.validation.constraints.Pattern;

public class UtenteRegistrazioneDto {
	
	@Pattern(regexp = "[a-zA-Z\\àèìòù]{1,50}", message = "Nome con caratteri non ammessi.")
	private String nome;
	
	@Pattern(regexp = "[a-zA-Z\\àèìòù]{1,50}", message = "Cognome con caratteri non ammessi.")
	private String cognome;
	
	@Pattern(regexp = "[A-z0-9\\.\\+_-]+@[A-z0-9\\._-]+\\.[A-z]{2,8}", message = "Email non valida.")
	private String email;
	
	// Il controllo per la password viene effettuato all'interno dell'user controller.
	private String password;
	
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
}
