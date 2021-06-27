package it.polito.tdp.food.model;

public class Correlate {
	String porzione;
	int peso;
	
	public Correlate(String porzione, int peso) {
		super();
		this.porzione = porzione;
		this.peso = peso;
	}

	public String getPorzione() {
		return porzione;
	}

	public void setPorzione(String porzione) {
		this.porzione = porzione;
	}

	public int getPeso() {
		return peso;
	}

	public void setPeso(int peso) {
		this.peso = peso;
	}

	@Override
	public String toString() {
		return porzione + " " + peso;
	}
	
	
}
