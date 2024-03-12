package it.corso.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table( name = "corso" )
public class Corso {
	
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	@Column( name = "ID_C" )
	private int id_c;
	
	@Column( name = "Nome_Corso" )
	private String nome_corso;
	
	@Column( name = "Descrizione_breve" )
	private String descrizione_breve;
	
	@Column( name = "Descrizione_completa" )
	private String descrizione_completa;
	
	@Column( name = "Durata" )
	private int durata;

	@ManyToMany( cascade = CascadeType.REFRESH, fetch = FetchType.EAGER )
	@JoinTable
	(
			name = "utenti_corsi", 
			joinColumns = @JoinColumn( name = "FK_CU" , referencedColumnName = "ID_C" ),
			inverseJoinColumns = @JoinColumn( name = "FK_UC", referencedColumnName =  "ID_U" )
			
	)
	private List<Utente> utenti = new ArrayList<>();
	
	@ManyToOne( cascade = CascadeType.REFRESH )
	@JoinColumn( name = "FK_CA", referencedColumnName = "ID_CA" )
	private Categoria categoria;
	
	
	public int getId_c() {
		return id_c;
	}

	public void setId_c(int id_c) {
		this.id_c = id_c;
	}

	public String getNome_corso() {
		return nome_corso;
	}

	public void setNome_corso(String nome_corso) {
		this.nome_corso = nome_corso;
	}

	public String getDescrizione_breve() {
		return descrizione_breve;
	}

	public void setDescrizione_breve(String descrizione_breve) {
		this.descrizione_breve = descrizione_breve;
	}

	public String getDescrizione_completa() {
		return descrizione_completa;
	}

	public void setDescrizione_completa(String descrizione_completa) {
		this.descrizione_completa = descrizione_completa;
	}

	public int getDurata() {
		return durata;
	}

	public void setDurata(int durata) {
		this.durata = durata;
	}

	public List<Utente> getUtenti() {
		return utenti;
	}

	public void setUtenti(List<Utente> utenti) {
		this.utenti = utenti;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	
	
}
