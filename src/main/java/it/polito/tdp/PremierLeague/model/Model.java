package it.polito.tdp.PremierLeague.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;


public class Model {
	Player best;
	
	PremierLeagueDAO dao;
	SimpleDirectedWeightedGraph <Player, DefaultWeightedEdge> grafo;
	Map <Integer, Player> idMap;
	public Model() {
		dao= new PremierLeagueDAO();
		idMap= new HashMap <Integer, Player>();
		dao.listAllPlayers(idMap);
	}
	
	public void creaGrafo(Match m) {
		grafo= new SimpleDirectedWeightedGraph <Player, DefaultWeightedEdge> (DefaultWeightedEdge.class);
		Graphs.addAllVertices(this.grafo, dao.getVertici(idMap,m));
		
		//aggiunta archi
		for (Adiacenza a : dao.getAdiacenze(idMap, m)) {
			if(grafo.containsVertex(a.getP1())&& grafo.containsVertex(a.getP2())) {
				Graphs.addEdgeWithVertices(this.grafo, a.getP1(), a.getP2(), a.getPeso());
			}
		}
	}
	
	
	public String calcolaGiocatoreMigliore() {
		best= null;
		double pesoBest= Integer.MIN_VALUE;
		for(Player p: this.grafo.vertexSet()) {
			double peso=0;
			double pesoOut=0;
			double pesoIn=0;
			for(DefaultWeightedEdge e: this.grafo.outgoingEdgesOf(p)) {
				pesoOut=pesoOut+this.grafo.getEdgeWeight(e);
			}
			for(DefaultWeightedEdge e: this.grafo.incomingEdgesOf(p)) {
				pesoIn=pesoIn+this.grafo.getEdgeWeight(e);
			}
			peso=pesoOut-pesoIn;
			if(peso>pesoBest) {
				best=p;
				pesoBest= peso;
			}
		}
		
		return "GIOCATORE MIGLIORE "+best+" con efficienza "+pesoBest;
	}
	
	public int getVertici() {
		return grafo.vertexSet().size();
	}
	public int getArchi() {
		return grafo.edgeSet().size();
	}
	
	public List<Match> listAllMatches(){
		return dao.listAllMatches();
	}

	public PremierLeagueDAO getDao() {
		return dao;
	}

	public void setDao(PremierLeagueDAO dao) {
		this.dao = dao;
	}

	public SimpleDirectedWeightedGraph<Player, DefaultWeightedEdge> getGrafo() {
		return grafo;
	}

	public void setGrafo(SimpleDirectedWeightedGraph<Player, DefaultWeightedEdge> grafo) {
		this.grafo = grafo;
	}

	public Map<Integer, Player> getIdMap() {
		return idMap;
	}
	

	public Player getBest() {
		return best;
	}
	


	public void setIdMap(Map<Integer, Player> idMap) {
		this.idMap = idMap;
	}
	
	public Team getSquadraMigliore (Player migliore){
		return dao.getSquadraMigliore(migliore);
	}
	
	
}
