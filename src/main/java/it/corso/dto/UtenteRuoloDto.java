package it.corso.dto;

//import java.util.ArrayList;
import java.util.List;


public class UtenteRuoloDto {
	
	private String id;
	private String nome;
	private String cognome;
	private String email;
	private String password;
	private List<RuoloDto> ruoli;
	
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
	public List<RuoloDto> getRuoli() {
		return ruoli;
	}
	public void setRuoli(List<RuoloDto> ruoli) {
		this.ruoli = ruoli;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
}
