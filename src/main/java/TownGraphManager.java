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
		// TODO: double check this method, file doesn't seem to be read
		// template yoinked from the assignment doc
		// road-name,miles;town-name; town-name
		// I-94,282;Chicago;Detroit

		Scanner scanner = new Scanner(selectedFile);

		// 1. loop as long as there is another line of data
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			// first 5 lines of each file are comments made by me
			// need to skip them to prevent the program from breaking
			if (line.trim().isEmpty() || line.startsWith("//")) {
				// if a line empty or if it's a comment line
				continue;
			}
			// 2. split by semicolon OR comma
			// The regex [,;] looks for either character
			String[] parts = line.split("[,;]");

			// 3. validate and process (Road name, miles, Town 1, Town 2)
			if (parts.length == 4) {
				String roadName = parts[0];
				int distance = Integer.parseInt(parts[1]);
				Town start = (this.getTown(parts[2]) == null ? new Town(parts[2]) : this.getTown(parts[2]));
				Town end = (this.getTown(parts[3]) == null ? new Town(parts[3]) : this.getTown(parts[3]));

				this.townGraph.addEdge(start, end, distance, roadName);

				// debugging line to see the roads get added 1 by 1
				// System.out.println(start.toString() + " via " + roadName + " to " + end + " "
				// + distance + " mi ");
			} else {
				throw new IOException("jinkies, this user sucks at formatting");
			}
		}

		// to make sure the roads are sorted alphabetically
		// ArrayList<String> myList = new ArrayList<String>(this.allRoads());
		// for (int i = 0; i < myList.size(); ++i) {
		// System.out.println(myList.get(i).toString());
		// }

		scanner.close();
	}

	@Override
	public boolean addRoad(String town1, String town2, int weight, String roadName) {
		Town t1 = this.getTown(town1);
		Town t2 = this.getTown(town2);

		// if they don't exist, add them
		if (t1 == null) {
			t1 = new Town(town1);
		}
		if (t2 == null) {
			t2 = new Town(town2);
		}

		return (this.townGraph.addEdge(t1, t2, weight, roadName) != null);
	}

	@Override
	public String getRoad(String town1, String town2) {
		return this.townGraph.getEdge(this.getTown(town1), this.getTown(town2)).getDescription();
	}

	@Override
	public boolean addTown(String v) {
		return this.townGraph.addVertex(new Town(v));
	}

	@Override
	public boolean containsTown(String v) {
		return this.townGraph.containsVertex(this.getTown(v));
	}

	@Override
	public boolean containsRoadConnection(String town1, String town2) {
		return this.townGraph.containsEdge(this.getTown(town1), this.getTown(town2));
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
		return (this.townGraph.removeEdge(this.getTown(town1), this.getTown(town2), 1, road) != null);
	}

	@Override
	public boolean deleteTown(String v) {
		return (this.townGraph.removeVertex(this.getTown(v)));
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
		Town start = this.getTown(town1);
		Town end = this.getTown(town2);

		// one line :]
		return ((start == null || end == null) ? new ArrayList<String>()
				: this.townGraph.shortestPath(this.getTown(town1), this.getTown(town2)));
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
