package it.corso.dto;

public class CategoriaDto {
	
	private int idCa;
	private String nome_categoria;
	
	public CategoriaDto() {
		super();
	}
	
	public CategoriaDto(int idCa, String nome_categoria) {
		super();
		this.idCa = idCa;
		this.nome_categoria = nome_categoria;
	}
	
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
	
	
}
