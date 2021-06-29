package it.polito.tdp.PremierLeague.model;

public class Evento {
	public enum EventType{
		GOAL,
		ESPULSIONE,
		INFORTUNIO,
	}
	
	private EventType tipo;
	private Integer squadra;
	public Evento(EventType tipo, Integer squadra) {
		super();
		this.tipo = tipo;
		this.squadra = squadra;
	}
	public EventType getTipo() {
		return tipo;
	}
	public void setTipo(EventType tipo) {
		this.tipo = tipo;
	}
	public Integer getSquadra() {
		return squadra;
	}
	public void setSquadra(Integer squadra) {
		this.squadra = squadra;
	}
	
	
}
