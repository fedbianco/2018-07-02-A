package it.polito.tdp.extflightdelays.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.extflightdelays.db.ExtFlightDelaysDAO;

public class Model {

	private ExtFlightDelaysDAO dao;
	private List<Airport> airport;
	public Graph< Airport ,DefaultWeightedEdge> graph = null;
	List<List<NeighborsAirport>> result = new ArrayList<>();
	public Model() {
		this.dao = new ExtFlightDelaysDAO();
		airport = this.dao.loadAllAirports();
	} 
	
	public List<Airport> getAllAirport(){
		return airport;
	}
	public Set<Airport> getOriginAirport(){
		return this.graph.vertexSet();
		
	}
	
	public void creaGrafo(double distanza) {
		graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		Graphs.addAllVertices(graph, airport);
		for(Airport a1 : this.airport) {
			for(Airport a2 : this.airport) {
				if(this.dao.getWeight(a1, a2)>distanza) {
					Graphs.addEdge(graph, a1, a2, dao.getWeight(a1, a2));
				}
			}
		}

		System.out.println("Grafo creato!");
		System.out.println("# Vertici: " + graph.vertexSet().size());
		System.out.println("# Archi: " + graph.edgeSet().size());
	}
	
	public List<NeighborsAirport> getNeighbour(Airport a){
		List<Airport> neighbour = new ArrayList<Airport>();
		List<NeighborsAirport> na = new ArrayList<NeighborsAirport>();
		double peso;
		neighbour.addAll(Graphs.neighborListOf(graph, a));
		
		for(Airport air : neighbour) {
			peso = graph.getEdgeWeight(graph.getEdge(a, air));
			na.add(new NeighborsAirport(air ,peso));
			
		}
		Collections.sort(na);

		return na;
	
	}
	
	public List<List<NeighborsAirport>> getRicorsione(Airport airport, double migliaTot , List<NeighborsAirport> neigh){
		List<Airport> neighbour = new ArrayList<Airport>();
		if(migliaTot<=0) {
			return result;
		}
		double peso = 0.0;
		double migliaTotReset = migliaTot;
		neighbour.addAll(Graphs.neighborListOf(graph, airport));
		for(Airport a : neighbour) {
			
			peso = graph.getEdgeWeight(graph.getEdge(airport, a));
			if(peso<migliaTot) {
				
				if(!this.isContains(neigh, a)) { //Per evitare cicli
					migliaTot = migliaTot - peso;
					neigh.add(new NeighborsAirport(a ,peso));
					getRicorsione(a , migliaTot, neigh);
				}
			}
			
		}
		
		
			result.add(new ArrayList<>(neigh));
			neigh = null;
			neigh = new ArrayList<NeighborsAirport>();
			migliaTot=migliaTotReset;
			
			
		
		return result;
	}
	
	public List<NeighborsAirport> listNeigbourAirport(Airport airport, double migliaTot){
		List<NeighborsAirport> neigh = new ArrayList<NeighborsAirport>();
		int max = 0;
		List<NeighborsAirport> migliore = new ArrayList<NeighborsAirport>();
		List<List<NeighborsAirport>> l = getRicorsione(airport, migliaTot , neigh);
		
		for(List<NeighborsAirport> listNa: l) {
			if(listNa.size() > max) {
				max = listNa.size();
				migliore = listNa;
				
			}
			for(NeighborsAirport na : listNa) {
				System.out.println(na + " ");
			}
			System.out.println("\n ---------- \n");
		}
		
		return migliore;
	}
	
	public boolean isContains(List<NeighborsAirport> neigh , Airport a ) {
		for(NeighborsAirport na : neigh) {
			if(na.neighbourAirport.getId() == a.getId()) {
				return true;
			}
		}
		return false;
	}
	
	
}
