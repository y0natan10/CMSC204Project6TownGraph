//Yonatan Rubin
//M21105076

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class TownGraphManager implements TownGraphManagerInterface {

	private Graph townGraph = new Graph();

	/**
	 * reads in the details of a set of towns and their roads and constructs a graph
	 * out of them
	 * 
	 * @param selectedFile
	 */
	public void populateTownGraph(File selectedFile) throws FileNotFoundException, IOException {
		// template yoinked from the assignment doc

		// road-name,miles;town-name; town-name
		// I-94,282;Chicago;Detroit

		Scanner scanner = new Scanner(selectedFile);

		// 1. loop as long as there is another line of data
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			// first 5 lines of each file are comments made by me
			// need to skip them to prevent the program from breaking
			if (line.charAt(0) == '/') {
				continue;
			}
			// 2. split by semicolon OR comma
			// The regex [,;] looks for either character
			String[] parts = line.split("[,;]");

			// 3. validate and process (Road name, miles, Town 1, Town 2)
			if (parts.length == 4) {
				this.townGraph.addEdge(new Town(parts[2]), new Town(parts[3]), Integer.parseInt(parts[1]), parts[0]);
				// debugging line
				System.out.println(
						(new Road(new Town(parts[2]), new Town(parts[3]), Integer.parseInt(parts[1]), parts[0]))
								.toString());
			} else {
				throw new IOException("jinkies JVM, this user sucks at formatting");
			}
		}
		scanner.close();
	}

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
		for (Town t : this.townGraph.getTowns()) {
			if (t.getName().equals(string)) {
				return t;
			}
		}
		return null;
	}
}
