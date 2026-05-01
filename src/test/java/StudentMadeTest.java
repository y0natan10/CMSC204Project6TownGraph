//Yonatan Rubin
//M21105076

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class StudentMadeTest {
	// need to change this to current type from TownGraphManagerInterface
	// because the interface doesn't have the method getTown(String townName)
	// but the assignment test cases call that method,
	// so it needed to be made regardless of the interface
	private TownGraphManager graph;
	private String[] town;

	// stolen from the given test files because i'm a sneaky boy
	@Before
	public void setUp() throws Exception {
		this.graph = new TownGraphManager();
		this.town = new String[12];

		for (int i = 1; i < 12; ++i) {
			this.town[i] = "Town_" + i;
			this.graph.addTown(this.town[i]);
		}

		this.graph.addRoad(town[1], town[2], 2, "Road_1");
		this.graph.addRoad(town[1], town[3], 4, "Road_2");
		this.graph.addRoad(town[1], town[5], 6, "Road_3");
		this.graph.addRoad(town[3], town[7], 1, "Road_4");
		this.graph.addRoad(town[3], town[8], 2, "Road_5");
		this.graph.addRoad(town[4], town[8], 3, "Road_6");
		this.graph.addRoad(town[6], town[9], 3, "Road_7");
		this.graph.addRoad(town[9], town[10], 4, "Road_8");
		this.graph.addRoad(town[8], town[10], 2, "Road_9");
		this.graph.addRoad(town[5], town[10], 5, "Road_10");
		this.graph.addRoad(town[10], town[11], 3, "Road_11");
		this.graph.addRoad(town[2], town[11], 6, "Road_12");

	}

	@After
	public void tearDown() throws Exception {
		this.graph = null;
	}

	@Test
	public void testStudentPathNotFound() {
		this.graph.addTown("Mars");
		this.graph.addTown("Venus");
		ArrayList<String> path = this.graph.getPath("Mars", "Venus");
		assertEquals(0, path.size());
	}

	@Test
	public void testStudentShortestPath() {
		this.graph.addTown("A");
		this.graph.addTown("B");
		this.graph.addTown("C");
		// Path 1: A -> C (Direct, 10 miles)
		this.graph.addRoad("A", "C", 10, "LongRoad");
		// Path 2: A -> B -> C (Two roads, but only 6 miles total)
		this.graph.addRoad("A", "B", 2, "Short1");
		this.graph.addRoad("B", "C", 4, "Short2");

		ArrayList<String> path = this.graph.getPath("A", "C");
		// It should choose the 2-stop path because 6 < 10
		assertEquals(2, path.size());
		assertTrue(path.get(0).contains("Short1"));

		// Try again but when the total distances are the same
		this.graph.addTown("D");
		this.graph.addTown("E");
		this.graph.addTown("F");
		// Path 1: D -> F (Direct, 10 miles)
		this.graph.addRoad("D", "F", 10, "LongRoad");
		// Path 2: D -> E -> F (Two roads, also 10 miles total)
		this.graph.addRoad("D", "E", 5, "Short1");
		this.graph.addRoad("E", "F", 5, "Short2");

		ArrayList<String> path2 = this.graph.getPath("A", "C");
		// It should choose the 2-stop path,
		// because it should chose the shorter edge before 10
		assertEquals(2, path2.size());
		assertTrue(path2.get(0).contains("Short1"));

	}

	@Test
	public void testStudentDeleteIntegrity() {
		this.graph.addTown("TemporaryTown");
		this.graph.addRoad("Town_1", "TemporaryTown", 5, "TempRoad");
		assertTrue(this.graph.containsRoadConnection("Town_1", "TemporaryTown"));

		this.graph.deleteTown("TemporaryTown");
		assertFalse(this.graph.containsTown("TemporaryTown"));
		// The road should be gone too!
		assertFalse(this.graph.allRoads().contains("TempRoad"));
	}

	@Test
	public void testAllTownsSorting() {
		ArrayList<String> towns = this.graph.allTowns();
		assertEquals("Town_1", towns.get(0));
		assertEquals("Town_10", towns.get(1));
		// "10" comes before "2" in string sorting,
		// because steal is heavier than feathers (look it up)

		assertEquals("Town_11", towns.get(2));
		assertEquals("Town_2", towns.get(3));
	}

	@Test
	public void testGetTown() {
		assertEquals("Town_1", this.graph.getTown("Town_1").getName());
		// Should return null for a town not in the graph
		assertTrue(this.graph.getTown("Atlantis") == null);
	}

	@Test
	public void testGetRoadName() {
		assertEquals("Road_1", this.graph.getRoad(town[1], town[2]));
		assertEquals("Road_12", this.graph.getRoad(town[2], town[11]));
	}
}
