
//Yonatan Rubin
//M21105076

import java.util.HashSet;
import java.util.Set;

/**
 * This class represents a town in a graph, it has a name and a set of adjacent
 * towns.
 */
public class Town implements Comparable<Town> {

	// name of the town
	private String name;

	// set of roads, incidence list
	private Set<Road> adjRoads;

	// fields for Dijkstra's nonsense
	private int wt; // how much a vertex 'costs' to get there
	private Town backPath; // previous vertex in the chain

	/**
	 * basic constructor for a town
	 * 
	 * @param name
	 */
	public Town(String name) {
		this.setName(name);
		this.setAdjRoads(new HashSet<Road>());
		this.setWt(Integer.MAX_VALUE); // default for Dijkstra
		this.setBackPath(null);
	}

	/**
	 * Copy constructor.
	 * 
	 * @param templateTown the town we are copying
	 */
	public Town(Town templateTown) {
		this.setName(templateTown.getName());
		this.setAdjRoads(new HashSet<>(templateTown.getAdjRoads()));
		this.setWt(templateTown.getWt()); // default for Dijkstra
		this.setBackPath(templateTown.getBackPath());
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Town))
			return false;
		Town other = (Town) obj;
		return this.getName().equalsIgnoreCase(other.getName());
		// "Two towns will be considered the same if their name is the same."
		// ^from the assignment
	}

	/**
	 * convert a town into a hashable number
	 */
	@Override
	public int hashCode() {
		return this.getName().toLowerCase().hashCode();
	}

	/**
	 * compare 2 towns alphabetically
	 */
	@Override
	public int compareTo(Town o) {
		return this.getName().compareTo(o.getName()); // Alphabetical comparison
	}

	/**
	 * convert a town object into a printable string
	 */
	@Override
	public String toString() {
		return this.getName();
	}

	/**
	 * getter method for the name of the town
	 * 
	 * @return the name of the town
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * setter method for the name of the town
	 * 
	 * @param _name the new name of the town
	 */
	private void setName(String _name) {
		this.name = _name;
	}

	// Dijkstra methods

	/**
	 * getter method for the cost needed to reach this vertex from the starting
	 * vertex using Dijksrta's algorithm
	 * 
	 * @return the cost to reach this vertex
	 */
	public int getWt() {
		return this.wt;
	}

	/**
	 * setter for the cost to reach this vertex using Dijkstra's algorithm
	 * 
	 * @param wt the cost to reach this vertex
	 */
	public void setWt(int wt) {
		this.wt = wt;
	}

	/**
	 * getter method for the previous vertex in the chain during Dijkstra's
	 * algorithm
	 * 
	 * @return the previous vertex in the chain
	 */
	public Town getBackPath() {
		return this.backPath;
	}

	/**
	 * setter method for the previous vertex in the chain during Dijkstra's
	 * algorithm
	 * 
	 * @param backPath the new previous vertex in the chain
	 */
	public void setBackPath(Town backPath) {
		this.backPath = backPath;
	}

	// Adjacency methods

	/**
	 * add a town to the set of adjacent towns
	 * 
	 * @param adjTown the new adjacent town
	 */
	public void addAdjTown(Town adjTown) {
		this.getAdjTowns().add(adjTown);
	}

	/**
	 * getter method for the set of adjacent towns
	 * 
	 * @return the set of adjacent towns
	 */
	public Set<Town> getAdjTowns() {
		Set<Town> adjTowns = new HashSet<Town>();

		for (Road r : this.getAdjRoads()) {
			Town neighbor = r.getSource().equals(this) ? r.getDestination() : r.getSource();
			adjTowns.add(neighbor);
		}
		return adjTowns;
		// had to do an extra space between each line
		// to prevent eclipse from auto formatting it
		/*
		 * Towns A B C, A -- B, C -- A
		 * 
		 * the calling object will be A
		 * 
		 * first iteration, r is the road between A and B
		 * 
		 * A == A, get B
		 * 
		 * second iteration r is the road between C and A
		 * 
		 * C == A is false, get C
		 */
	}

	/**
	 * getter method for the set of connecting roads
	 * 
	 * @return the adjRoads
	 */
	public Set<Road> getAdjRoads() {
		return this.adjRoads;
	}

	/**
	 * Adds a road to the set of roads connected to this town.
	 * 
	 * @param road the road to be added
	 */
	public void addRoad(Road road) {
		if (road != null) {
			this.getAdjRoads().add(road);
		}
	}

	/**
	 * @param adjRoads the adjRoads to set
	 */
	private void setAdjRoads(Set<Road> adjRoads) {
		this.adjRoads = adjRoads;
	}

}