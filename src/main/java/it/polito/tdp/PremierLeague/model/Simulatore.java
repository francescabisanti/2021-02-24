package it.polito.tdp.PremierLeague.model;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import it.polito.tdp.PremierLeague.model.Evento.EventType;

public class Simulatore {
	//valori di input
	private int N;
	private Match m;
	
	//valori di output
	private int goalCasa;
	private int goalOspite;
	private int espulsi;
	
	//coda degli eventi
	List<Evento> queue;
	
	//stato del mondo
	int numeroGiocatoriCasa;
	int numeroGiocatoriOspite;
	private Model model;
	
	public Simulatore(Model model) {
		this.model=model;
	}
	
	public void init(int N, Match m) {
		this.m=m;
		this.N=N;
		this.numeroGiocatoriCasa=11;
		this.numeroGiocatoriOspite=11;
		this.goalCasa=0;
		this.goalOspite=0;
		this.espulsi=0;
		queue= new ArrayList<Evento>();
	}
	
	
	public void run() {
		while(queue.size()<N) {
		if(Math.random()>0.5) {
			if(numeroGiocatoriCasa>numeroGiocatoriOspite) {
				Evento e= new Evento(EventType.GOAL, m.teamHomeID);
				this.goalCasa++;
				queue.add(e);
			}
			else if(numeroGiocatoriCasa<numeroGiocatoriOspite) {
				Evento e= new Evento(EventType.GOAL, m.teamAwayID);
				this.goalOspite++;
				queue.add(e);
			}
			else {
				Evento e= new Evento(EventType.GOAL, model.SquadraMigliore(model.trovaMigliore().getP()));
				if(model.SquadraMigliore(model.trovaMigliore().getP())==m.teamHomeID)
					this.goalCasa++;
				else
					this.goalOspite++;
				queue.add(e);
			}
		}
		if(Math.random()>0.7) {
			if(Math.random()>0.4) {
				Evento e= new Evento(EventType.ESPULSIONE, model.SquadraMigliore(model.trovaMigliore().getP()));
				if(model.SquadraMigliore(model.trovaMigliore().getP())==m.teamHomeID) {
					numeroGiocatoriCasa--;
					this.espulsi++;
					}
				
				else {
					numeroGiocatoriOspite--;
					this.espulsi++;}
				queue.add(e);
			}
			else {
				if(model.SquadraMigliore(model.trovaMigliore().getP())==m.teamHomeID) {
					Evento e= new Evento(EventType.ESPULSIONE, m.teamAwayID);
					this.numeroGiocatoriOspite--;
					this.espulsi++;
					queue.add(e);
					}
				else {
					Evento e= new Evento(EventType.ESPULSIONE, m.teamHomeID);
					this.numeroGiocatoriCasa--;
					this.espulsi++;
					queue.add(e);
				}
					
			}
		}else {
			Evento e= new Evento(EventType.INFORTUNIO, null);
			if(Math.random()>0.5) {
				this.N=this.N+2;
			}
			else
				this.N=this.N+3;
			queue.add(e);
			
		}
		
		}
		
	}

	public int getGoalCasa() {
		return goalCasa;
	}

	public void setGoalCasa(int goalCasa) {
		this.goalCasa = goalCasa;
	}

	public int getGoalOspite() {
		return goalOspite;
	}

	public void setGoalOspite(int goalOspite) {
		this.goalOspite = goalOspite;
	}

	public int getNumeroGiocatoriCasa() {
		return numeroGiocatoriCasa;
	}

	public void setNumeroGiocatoriCasa(int numeroGiocatoriCasa) {
		this.numeroGiocatoriCasa = numeroGiocatoriCasa;
	}

	public int getNumeroGiocatoriOspite() {
		return numeroGiocatoriOspite;
	}

	public void setNumeroGiocatoriOspite(int numeroGiocatoriOspite) {
		this.numeroGiocatoriOspite = numeroGiocatoriOspite;
	}
	
	
}
