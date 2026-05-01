// Yonatan Rubin
// M21105076

/**
 * This class represents a road through the 2 towns it connects, its
 * distance/weight, and its name.
 */
public class Road implements Comparable<Road> {

	// start of the edge
	private Town source;
	// of the edge
	private Town destination;
	// name of the edge
	private String description;
	// weight of an edge
	private int weight;

	/**
	 * default constructor that sets up an unweighted edge
	 * 
	 * @param _source      start of the edge
	 * @param _destination end of the edge
	 * @param _description name of the edge
	 */
	public Road(Town _source, Town _destination, String _description) {
		this(_source, _destination, 1, _description);
	}

	/**
	 * 
	 * constructor for a weighted edge
	 * 
	 * @param _source      start of the edge
	 * @param _destination end of the edge
	 * @param _description name of the end
	 * @param _weight      the cost/weight of the edge
	 */
	public Road(Town _source, Town _destination, int _weight, String _description) {
		this.setSource(_source);
		this.setDestination(_destination);
		this.setWeight(_weight);
		this.setDescription(_description);

	}

	/**
	 * returns true if the edge contains the given town
	 * 
	 * @param town a vertex of the graph
	 * @return true only if the edge is connected to the given vertex
	 */
	public boolean contains(Town town) {
		return this.getSource().equals(town) || this.getDestination().equals(town);
	}

	/**
	 * checks if 2 edges/objects are the same
	 */
	@Override
	public boolean equals(Object r) {
		if (r == null) {
			return false;
		}
		if (!(r instanceof Road))
			return false;
		Road other = (Road) r;
		// Undirected logic: (A-B) is equal to (B-A)
		boolean sameSame = this.getSource().equals(other.getSource())
				&& this.getDestination().equals(other.getDestination());
		boolean flipped = this.getSource().equals(other.getDestination())
				&& this.getDestination().equals(other.getSource());

		return sameSame || flipped;
	}

	@Override
	public int compareTo(Road o) {
		return this.getDescription().compareTo(o.getDescription());
	}

	@Override
	public String toString() {
		return this.getSource().getName() + " via " + this.getDescription() + " to " + getDestination().getName() + " "
				+ getWeight() + " mi";
	}

	/**
	 * @return the first
	 */
	public Town getSource() {
		return this.source;
	}

	/**
	 * @param _source the first to set
	 */
	public void setSource(Town _source) {
		this.source = _source;
	}

	/**
	 * @return the second
	 */
	public Town getDestination() {
		return this.destination;
	}

	/**
	 * @param _destination the second to set
	 */
	public void setDestination(Town _destination) {
		this.destination = _destination;
	}

	/**
	 * @return the weight
	 */
	public int getWeight() {
		return this.weight;
	}

	/**
	 * @param _weight the weight to set
	 */
	public void setWeight(int _weight) {
		this.weight = _weight;
	}

	/**
	 * @return the name
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * @param _description the name to set
	 */
	public void setDescription(String _description) {
		this.description = _description;
	}
}
