// Yonatan Rubin
// M21105076

import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

public class Graph implements GraphInterface<Town, Road> {
	private Set<Town> towns = new HashSet<>();
	private Set<Road> roads = new HashSet<>();

	// returns a road that connects the 2 towns
	@Override
	public Road getEdge(Town source, Town destination) {
		if (source == null || destination == null)
			return null;
		for (Road r : this.getRoads()) {
			if (r.contains(source) && r.contains(destination)) {
				return r;
			}
		}
		return null;
	}

	@Override
	public Road addEdge(Town source, Town destination, int weight, String description) {
		if (source == null || destination == null)
			throw new NullPointerException();
		// make sure both start and end points are inside the graph
		if (!containsVertex(source)) {
			addVertex(source);
		}
		if (!containsVertex(destination)) {
			addVertex(destination);
		}

		Road newRoad = new Road(source, destination, weight, description);
		this.getRoads().add(newRoad);

		// update the roads list inside the towns
		source.addRoad(newRoad);
		destination.addRoad(newRoad);

		return newRoad;
	}

	@Override
	public void dijkstraShortestPath(Town sourceVertex) {
		// 1. reset all towns (Infinity distance, null backpath)
		for (Town t : towns) {
			t.setWt(Integer.MAX_VALUE);
			t.setBackPath(null);
		}

		// 2. the PriorityQueue for the greediest choice
		PriorityQueue<Town> pq = new PriorityQueue<>((t1, t2) -> Integer.compare(t1.getWt(), t2.getWt()));

		// source stays at 0
		sourceVertex.setWt(0);
		pq.add(sourceVertex);
		// set of vertices that we have visited
		Set<Town> visited = new HashSet<>();

		while (!pq.isEmpty()) {
			Town u = pq.poll();
			if (visited.contains(u))
				continue;
			visited.add(u);

			for (Town v : u.getAdjTowns()) {
				if (!visited.contains(v)) {
					Road road = getEdge(u, v);
					int weight = (road != null) ? road.getWeight() : Integer.MAX_VALUE;

					if (u.getWt() + weight < v.getWt()) {
						v.setWt(u.getWt() + weight);
						v.setBackPath(u);
						pq.add(v);
					}
				}
			}
		}
	}

	@Override
	public ArrayList<String> shortestPath(Town sourceVertex, Town destinationVertex) {
		dijkstraShortestPath(sourceVertex);
		ArrayList<String> path = new ArrayList<>();

		Town current = destinationVertex;
		// build the path from destination back to source
		while (current != null && current.getBackPath() != null) {
			Town prev = current.getBackPath();
			Road road = getEdge(prev, current);
			path.add(0, prev.getName() + " via " + road.getDescription() + " to " + current.getName() + " "
					+ road.getWeight() + " mi"); // miles
			current = prev;
		}
		return path;
	}

	@Override
	public boolean addVertex(Town v) {
		return this.getTowns().add(v);
	}

	@Override
	public boolean containsEdge(Town sourceVertex, Town destinationVertex) {
		// get edge returns null if the edge is not found, and returns a road otherwise
		return (this.getEdge(sourceVertex, destinationVertex) != null);
	}

	@Override
	public boolean containsVertex(Town v) {
		return this.getTowns().contains(v);
	}

	@Override
	public Set<Road> edgeSet() {
		return this.getRoads();
	}

	@Override
	public Set<Road> edgesOf(Town vertex) {
		return vertex.getAdjRoads();
	}

	@Override
	public Road removeEdge(Town sourceVertex, Town destinationVertex, int weight, String description) {
		Road road = getEdge(sourceVertex, destinationVertex);
		if (road != null) {
			roads.remove(road);
			// Clean up the towns' internal lists too
			sourceVertex.getAdjRoads().remove(road);
			destinationVertex.getAdjRoads().remove(road);
		}
		return road;
	}

	@Override
	public boolean removeVertex(Town v) {
		if (v == null || !towns.contains(v))
			return false;

		// 1. find all roads touching this town and remove them from the graph
		Set<Road> roadsToRemove = new HashSet<>(v.getAdjRoads());
		for (Road r : roadsToRemove) {
			removeEdge(r.getSource(), r.getDestination(), r.getWeight(), r.getDescription());
		}

		// 2. remove the town itself
		return towns.remove(v);
	}

	@Override
	public Set<Town> vertexSet() {
		return this.getTowns();
	}

	// methods for me

	/**
	 * getter method for the towns in the graph
	 * 
	 * @return the towns
	 */
	public Set<Town> getTowns() {
		return this.towns;
	}

	/**
	 * setter method for the towns of the graph
	 * 
	 * @param _towns the towns to set
	 */
	public void setTowns(Set<Town> _towns) {
		this.towns = _towns;
	}

	/**
	 * getter method for the roads in a graph
	 * 
	 * @return the roads
	 */
	public Set<Road> getRoads() {
		return this.roads;
	}

	/**
	 * setter method for the roads in a graph
	 * 
	 * @param _roads the roads to set
	 */
	public void setRoads(Set<Road> _roads) {
		this.roads = _roads;
	}
}
