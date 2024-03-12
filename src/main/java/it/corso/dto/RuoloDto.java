package it.corso.dto;

//import it.corso.model.tipologiaRuolo;

public class RuoloDto {
	
	private int id_r;
	
//	private tipologiaRuolo tipologia;
	private String tipologia;

	public String getTipologia() {
		return tipologia;
	}

	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}

	public int getId_r() {
		return id_r;
	}

	public void setId_r(int id_r) {
		this.id_r = id_r;
	}

	public RuoloDto(int id_r, String tipologia) {
		super();
		this.id_r = id_r;
		this.tipologia = tipologia;
	}
	
	public RuoloDto() {
		super();
	}
}
