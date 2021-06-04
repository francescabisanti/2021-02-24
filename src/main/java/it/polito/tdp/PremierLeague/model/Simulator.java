package it.polito.tdp.PremierLeague.model;

import java.util.PriorityQueue;

import it.polito.tdp.PremierLeague.model.Event.EventType;

public class Simulator {
	Model model;
	
	public Simulator(Model model) {
		this.model=model;
	}
	//parametri di input
	private Match m;
	private int N;
	
	//parametri di output
	private int goalCasa;
	private int goalOspite;
	private int espulsiC;
	private int espulsiO;
	
	//Coda degli eventi
	PriorityQueue <Event> queue;
	
	//Stato del mondo
	int tempo;
	String casa;
	String ospite;
	int giocCasa;
	int giocOspite;
	Player migliore;
	public void init(int N, Match m) {
		this.N=N;
		this.m=m;
		this.tempo=0;
		goalCasa=0;
		this.goalOspite=0;
		this.espulsiC=0;
		this.espulsiO=0;
		queue= new PriorityQueue <Event>();
		this.casa=m.getTeamHomeNAME();
		this.ospite=m.getTeamAwayNAME();
		this.giocCasa=11;
		this.giocOspite=11;
		migliore= this.model.getBest();
	}
	
	public void run() {
		while(this.N>0) {
			double probabilita= Math.random();
			if(probabilita>0.50) {
				this.queue.add(new Event(EventType.GOAL,tempo++));
				int differenzaGioc= this.giocCasa-this.giocOspite;
				if(differenzaGioc==0) {
					Team mig= model.getSquadraMigliore(model.getBest());
					if(mig.getName().equals(casa))
						this.goalCasa++;
					else
						this.goalOspite++;
				}
				else if(differenzaGioc<0) {
					this.goalOspite++;
				}
				else
					this.goalCasa++;
			}
			else if(probabilita>0.70) {
				queue.add(new Event(EventType.ESPULSIONE, this.tempo++));
				if(Math.random()>0.40) {
					Team mig= model.getSquadraMigliore(model.getBest());
					if(mig.getName().equals(casa)) {
						this.espulsiC++;
						this.giocCasa--;
					}
					else {
						this.espulsiO++;
						this.giocOspite--;}
				}
				}
			else {
				queue.add(new Event(EventType.INFORTUNIO, this.tempo++));
				if(Math.random()>0.50)
					this.N=N+2;
				else 
					this.N=N+3;
			}
			this.N--;
			}
		}

	public int getGoalCasa() {
		return goalCasa;
	}


	public int getGoalOspite() {
		return goalOspite;
	}

	
	public int getEspulsiC() {
		return espulsiC;
	}

	

	public int getEspulsiO() {
		return espulsiO;
	}

	
	
}
