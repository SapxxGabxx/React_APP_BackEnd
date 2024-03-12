package it.corso.dto;

import java.util.ArrayList;
import java.util.List;


public class UtenteDto {
	
	private int id_u;
	private String nome;
	private String cognome;
	private String email;
	private String password;
	private int idRuolo;
	private List<CorsoDto> corsi = new ArrayList<>();
	
	public int getId_u() {
		return id_u;
	}
	public void setId_u(int id_u) {
		this.id_u = id_u;
	}
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
	public List<CorsoDto> getCorsi() {
		return corsi;
	}
	public void setCorsi(List<CorsoDto> corsi) {
		this.corsi = corsi;
	}
	public int getIdRuolo() {
		return idRuolo;
	}
	public void setIdRuolo(int idRuolo) {
		this.idRuolo = idRuolo;
	}
	
	
}
