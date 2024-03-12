package it.corso.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
//import jakarta.persistence.EnumType;
//import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table( name = "categoria" )
public class Categoria {
	
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	@Column( name = "ID_CA" )
	private int idCa;
	
	@Column( name = "Nome_Categoria" )
//	@Enumerated( EnumType.STRING )
	private String nome_categoria;
	
	@OneToMany( mappedBy = "categoria", cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, orphanRemoval = true )
	private List<Corso> corsi = new ArrayList<>();
	
	public int getId_ca() {
		return idCa;
	}

	public void setId_ca(int idCa) {
		this.idCa = idCa;
	}

	public String getNome_categoria() {
		return nome_categoria;
	}

	public void setNome_categoria(String nome_categoria) {
		this.nome_categoria = nome_categoria;
	}

	public List<Corso> getCorsi() {
		return corsi;
	}

	public void setCorsi(List<Corso> corsi) {
		this.corsi = corsi;
	}
	
	
}
