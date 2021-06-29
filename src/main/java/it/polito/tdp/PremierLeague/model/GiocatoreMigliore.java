package it.polito.tdp.PremierLeague.model;

public class GiocatoreMigliore {
	private Player p;
	private Double peso;
	public GiocatoreMigliore(Player p, Double peso) {
		super();
		this.p = p;
		this.peso = peso;
	}
	public Player getP() {
		return p;
	}
	public void setP(Player p) {
		this.p = p;
	}
	public Double getPeso() {
		return peso;
	}
	public void setPeso(Double peso) {
		this.peso = peso;
	}
	@Override
	public String toString() {
		return "GiocatoreMigliore =" + p + " = " + peso + "\n";
	}
	
	
}
