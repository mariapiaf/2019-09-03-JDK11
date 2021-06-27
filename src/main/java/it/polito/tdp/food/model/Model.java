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
	private List<String> camminoOttimo;
	public double pesoMax;
	
	
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
	
	public List<String> camminoOttimo(String partenza, int N) {
		camminoOttimo = new ArrayList<>();
		List<String> parziale = new ArrayList<>();
		pesoMax = 0.0;
		parziale.add(partenza);
		cerca(parziale, 1, N);
		return camminoOttimo;

	}
	
	public void cerca(List<String> parziale, int livello, int N) {
		if(livello == N+1) {
			if(calcolaPeso(parziale) > pesoMax) {
				pesoMax = calcolaPeso(parziale);
				camminoOttimo = new ArrayList<>(parziale);
			}
			return;
		}
		
		List<String> vicini = new ArrayList<>(Graphs.neighborListOf(this.grafo, parziale.get(livello-1)));
		for(String s: vicini) {
			if(!parziale.contains(s)) {
				parziale.add(s);
				cerca(parziale, livello+1, N);
				parziale.remove(parziale.size()-1);
			}
		}
		
		
	}
	
	public double calcolaPeso(List<String> parziale) {
		double peso = 0.0;
		for(int i = 0; i< parziale.size(); i++) {
			double p = this.grafo.getEdgeWeight(this.grafo.getEdge(parziale.get(i-1), parziale.get(i)));
			peso += p;
		}
		return peso;
	}
	
	public List<String> getCammino(){
		return this.camminoOttimo;
	}
	
	public double getPesoMax() {
		return this.pesoMax;
	}
}
