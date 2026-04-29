//Yonatan Rubin
//M21105076

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class TownGraphManager implements TownGraphManagerInterface {

	private Graph townGraph = new Graph();

	@Override
	public boolean addRoad(String town1, String town2, int weight, String roadName) {
		Town source = new Town(town1);
		Town destination = new Town(town2);

		// addEdge handles what happens if either town1 or town2 already exists
		return (this.townGraph.addEdge(source, destination, weight, roadName) != null);
	}

	@Override
	public String getRoad(String town1, String town2) {
		Town source = new Town(town1);
		Town destination = new Town(town2);
		return this.townGraph.getEdge(source, destination).getDescription();
	}

	@Override
	public boolean addTown(String v) {
		return this.townGraph.addVertex(new Town(v));
	}

	@Override
	public boolean containsTown(String v) {
		return this.townGraph.containsVertex(new Town(v));
	}

	@Override
	public boolean containsRoadConnection(String town1, String town2) {
		return this.townGraph.containsEdge(new Town(town1), new Town(town2));
	}

	@Override
	public ArrayList<String> allRoads() {
		ArrayList<String> res = new ArrayList<String>();
		for (Road r : this.townGraph.getRoads()) {
			res.add(r.getDescription());
		}
		Collections.sort(res);
		return res;
	}

	@Override
	public boolean deleteRoadConnection(String town1, String town2, String road) {
		return (this.townGraph.removeEdge(new Town(town1), new Town(town2), 1, road) != null);
	}

	@Override
	public boolean deleteTown(String v) {
		return (this.townGraph.removeVertex(new Town(v)));
	}

	@Override
	public ArrayList<String> allTowns() {
		ArrayList<String> res = new ArrayList<String>();
		for (Town r : this.townGraph.getTowns()) {
			res.add(r.getName());
		}
		Collections.sort(res);
		return res;
	}

	@Override
	public ArrayList<String> getPath(String town1, String town2) {
		ArrayList<String> res = new ArrayList<String>();
		this.townGraph.dijkstraShortestPath(new Town(town1));
		Town goal = new Town(town1);
		Town current = new Town(town2);
		while (current.getBackPath() != null && !current.getBackPath().equals(goal)) {
			res.addFirst(current.getName());
			current = current.getBackPath();
		}
		return res;
	}

	/**
	 * needed to add this method because it was called in FXMainPane
	 * 
	 * @param string
	 * @return a town with the name of string
	 */
	public Town getTown(String string) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 
	 * @param selectedFile
	 */
	public void populateTownGraph(File selectedFile) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub

	}

}
