package it.polito.tdp.PremierLeague.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;


public class Model {
	PremierLeagueDAO dao;
	SimpleDirectedWeightedGraph<Player, DefaultWeightedEdge>grafo;
	Map<Integer, Player> idMap;
	public Model() {
		dao= new PremierLeagueDAO();
		idMap= new HashMap<>();
		dao.listAllPlayers(idMap);
	}
	public List<Match> listAllMatches(){
		return dao.listAllMatches();
	}
	
	public void creaGrafo(Match m) {
		grafo= new SimpleDirectedWeightedGraph<Player, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		Graphs.addAllVertices(this.grafo, dao.getVertici(idMap, m));
		for(Adiacenza a: dao.getAdiacenza(idMap, m)) {
			if(grafo.containsVertex(a.getP1())&& grafo.containsVertex(a.getP2())) {
				Graphs.addEdge(this.grafo, a.getP1(), a.getP2(), a.getPeso());
			}
		}
	}
	
	public GiocatoreMigliore trovaMigliore() {
		GiocatoreMigliore gm=new GiocatoreMigliore(null,null);
		Double deltaMax=0.0;
		for(Player p: grafo.vertexSet()) {
			Double deltaOut=0.0;
			Double deltaIn=0.0;
			for(DefaultWeightedEdge e: this.grafo.outgoingEdgesOf(p)) {
				deltaOut=deltaOut+grafo.getEdgeWeight(e);
			}
			for(DefaultWeightedEdge e: this.grafo.incomingEdgesOf(p)) {
				deltaIn=deltaIn+grafo.getEdgeWeight(e);
			}
			Double delta=deltaOut-deltaIn;
			if(delta>deltaMax) {
				deltaMax=delta;
				gm.setP(p);
				gm.setPeso(delta);
			}
		}
		return gm;
	}
	
	public Integer SquadraMigliore(Player p){
		return dao.SquadraMigliore(p);
	}
	
	public PremierLeagueDAO getDao() {
		return dao;
	}
	public SimpleDirectedWeightedGraph<Player, DefaultWeightedEdge> getGrafo() {
		return grafo;
	}
	public Map<Integer, Player> getIdMap() {
		return idMap;
	}
	public int getNVertici() {
		return grafo.vertexSet().size();
	}
	public int getNArchi() {
		return grafo.edgeSet().size();
	}
	
	public String simula(Integer N, Match m) {
		Simulatore sim= new Simulatore(this);
		sim.init(N, m);
		sim.run();
		return "Goal casa :"+sim.getGoalCasa()+"  Goal ospite: "+sim.getGoalOspite()+" GiocCasa: "+sim.getNumeroGiocatoriCasa();
	}
}
