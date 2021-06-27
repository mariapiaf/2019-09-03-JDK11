package it.polito.tdp.food.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.FoodDao;

public class Model {
	
	private FoodDao dao;
	private Graph<String, DefaultWeightedEdge> grafo;
	
	
	public Model() {
		dao = new FoodDao();
	}
	
	public void creaGrafo(double calorie) {
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		// aggiungo vertici
		Graphs.addAllVertices(this.grafo, this.dao.getVertici(calorie));
		
		// aggiungo archi
		for(Arco a: this.dao.getArchi()) {
			if(this.grafo.vertexSet().contains(a.getId1()) && this.grafo.vertexSet().contains(a.getId2())) {
				Graphs.addEdge(this.grafo, a.getId1(), a.getId2(), calorie);
			}
		}
	}
	
	public int nVertici() {
		return this.grafo.vertexSet().size();
	}
	
	public int nArchi() {
		return this.grafo.edgeSet().size();
	}
	
	public List<String> getVertici(){
		List<String> result = new ArrayList<>(this.grafo.vertexSet());
		return result;
	}
	
	public List<Correlate> getCorrelate(String porzione) {
		List<Correlate> result = new ArrayList<>();
		for(DefaultWeightedEdge edge: this.grafo.edgesOf(porzione)) {
			Correlate nuova = new Correlate(Graphs.getOppositeVertex(this.grafo, edge, porzione), (int) this.grafo.getEdgeWeight(edge));
			result.add(nuova);
		}
		return result;
	}
}
