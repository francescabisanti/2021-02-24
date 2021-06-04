package it.polito.tdp.PremierLeague.model;

public class Event implements Comparable <Event> {
	
	
	public enum EventType{
		GOAL,
		ESPULSIONE,
		INFORTUNIO,
		
	}
	
	private EventType tipo;
	private int t;
	public Event(EventType tipo, int t) {
		super();
		this.tipo = tipo;
		this.t = t;
	}
	public EventType getTipo() {
		return tipo;
	}
	public void setTipo(EventType tipo) {
		this.tipo = tipo;
	}
	public int getT() {
		return t;
	}
	public void setT(int t) {
		this.t = t;
	}
	@Override
	public int compareTo(Event o) {
		
		return this.t-o.t;
	}
	
	
	
	
}
